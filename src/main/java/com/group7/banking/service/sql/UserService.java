package com.group7.banking.service.sql;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group7.banking.component.UserEntityConverter;
import com.group7.banking.component.UserProfileConverter;
import com.group7.banking.dto.SignUpDTO;
import com.group7.banking.dto.UserDTO;
import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.model.nosql.ConfirmationTokenEntity;
import com.group7.banking.model.nosql.EmailAddressEntity;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.nosql.PhoneNumberEntity;
import com.group7.banking.model.nosql.ProvidedIncomeEntity;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.BillingAddressEntity;
import com.group7.banking.model.sql.RoleEntity;
import com.group7.banking.model.sql.SsnEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.EmailAddressRepository;
import com.group7.banking.repository.nosql.NameRepository;
import com.group7.banking.repository.nosql.PhoneNumberRepository;
import com.group7.banking.repository.nosql.ProvidedIncomeRepository;
import com.group7.banking.repository.sql.AccountRepository;
import com.group7.banking.repository.sql.BillingAddressRepository;
import com.group7.banking.repository.sql.RoleRepository;
import com.group7.banking.repository.sql.SSNRepository;
import com.group7.banking.repository.sql.UserRepository;
import com.group7.banking.service.nosql.ConfirmationTokenService;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Value("${app.host}")
	private String appHost;
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserEntityConverter userEntityConverter;
    
    @Autowired
    private UserProfileConverter userProfileConverter;
    
    @Autowired
    private BillingAddressRepository billingAddressRepository;
    
    @Autowired
    private EmailAddressRepository emailAddressRepository;
    
    @Autowired
    private NameRepository nameRepository;
    
    @Autowired
    private ProvidedIncomeRepository providedIncomeRepository;
    
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;
    
    @Autowired
    private SSNRepository ssnRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    @Autowired
    private EmailSenderService emailSenderService;
    
    @Autowired
    private RoleRepository roleRepository;
    
    public UserDTO findById(Long userId) {
    	UserDTO userData = new UserDTO();
    	Optional<UserEntity> optionalUser = userRepository.findById(userId);
    	
    	if (optionalUser.isPresent()) {
    		UserEntity user = optionalUser.get();
    		userData = getUserData(user);
    	}
    	
    	return userData;
    }
    
    public void deleteById(Long userId) {
    	userRepository.deleteById(userId);
    }
    
    public UserProfileDTO getUserProfile(UserDetails principalUser) throws Exception {
    	Optional<UserEntity> optionalPrincipalUserEntity = userRepository.findByUsername(principalUser.getUsername());
    	
    	if (!optionalPrincipalUserEntity.isPresent()) {
    		throw new Exception("User not found with username: " + principalUser.getUsername());
    	}
    	
    	return userProfileConverter.toResponse(optionalPrincipalUserEntity.get());
    }
    
    public UserDTO signUpUser(SignUpDTO signUpDto) throws Exception {
    	UserEntity user = parseSignUpRequest(signUpDto);
    	
    	Optional<RoleEntity> userRole = roleRepository.findByName("ROLE_USER");
    	
    	if (userRole.isPresent()) {
    		Set<RoleEntity> roles = new HashSet<RoleEntity>();
    		roles.add(userRole.get());
    		user.setRoles(roles);
    	}
    	
    	logger.debug(user.toString());
    	String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    	user.setPassword(encryptedPassword);
    	
    	userRepository.save(user);
    	
    	ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user.getId());
    	LocalDateTime expirationDate = confirmationTokenService.getExpirationDate(confirmationToken.getCreatedDate());
    	confirmationToken.setExpirationDate(expirationDate);
    	
    	confirmationTokenService.saveConfirmationToken(confirmationToken);
    	
    	sendConfirmationMail(signUpDto.getEmailAddress(), confirmationToken.getConfirmationToken());
    	
    	return getUserData(user);
    }
    
    private UserEntity parseSignUpRequest(SignUpDTO signUpDto) throws Exception {
    	if (doesUserExist(signUpDto.getUsername())) {
    		throw new Exception(MessageFormat.format("Username - {0} - already exists.", signUpDto.getUsername()));
    	}
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    	LocalDate birthDate = LocalDate.parse(signUpDto.getBirthDate(), formatter);
    	
    	UserEntity user = new UserEntity(signUpDto.getUsername(), signUpDto.getPassword(), birthDate);
    	userRepository.save(user);
    	
    	// This should look up matching addresses first
    	// Then either create and add the user to it OR get the match and add the user to that
    	BillingAddressEntity billingAddress = new BillingAddressEntity(signUpDto.getAddressLine1(),
    			signUpDto.getAddressLine2(),
    			signUpDto.getCity(), signUpDto.getState(), signUpDto.getZipcode());
    	
    	if (billingAddress.getUsers() == null) {
    		Set<UserEntity> users = new HashSet<UserEntity>();
    		users.add(user);
    		billingAddress.setUsers(users);
    	}
    	user.setBillingAddress(billingAddress);
    	
    	SsnEntity ssn = new SsnEntity(user, signUpDto.getSsn());
    	user.setSsn(ssn);
    	
    	// TODO: This can probably be removed and we can have some kind of add account
    	// functionality (start with no accounts)
    	AccountEntity account = new AccountEntity(user, AccountEntity.Type.CHECKING, 0.0);
    	user.addAccount(account);
    	accountRepository.save(account);
    	
    	NameEntity name = new NameEntity(user.getId(), signUpDto.getFirstName(),
    			signUpDto.getMiddleName(), signUpDto.getLastName());
    	
    	EmailAddressEntity emailAddress = new EmailAddressEntity(user.getId(),
    			signUpDto.getEmailAddress());
    	
    	ProvidedIncomeEntity providedIncome = new ProvidedIncomeEntity(user.getId(), signUpDto.getProvidedIncome());
    	
    	PhoneNumberEntity phoneNumber = new PhoneNumberEntity(user.getId(), signUpDto.getPhoneNumber());
    	
    	// Mongo repos
    	nameRepository.save(name);
    	emailAddressRepository.save(emailAddress);
    	providedIncomeRepository.save(providedIncome);
    	phoneNumberRepository.save(phoneNumber);
    	
    	// Sql repos
    	billingAddressRepository.save(user.getBillingAddress());
    	ssnRepository.save(user.getSsn());
    	
    	return user;
    }
    
    private void sendConfirmationMail(String emailAddress, String token) {
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailAddress);
		mailMessage.setSubject("Mail Confirmation Link!");
		mailMessage.setFrom("<MAIL>");
		mailMessage.setText("Thank you for registering. Please click on the "
				+ "below link to activate your account."
				+ appHost + "/sign-up/confirm?token=" + token);

		emailSenderService.sendEmail(mailMessage);
	}
    
    public void confirmUser(ConfirmationTokenEntity confirmationToken) throws Exception {
    	Optional<UserEntity> optionalUser = userRepository.findById(confirmationToken.getUserId());
    	
    	if (!optionalUser.isPresent()) {
    		throw new Exception(MessageFormat.format("Error confirming user {0} - not found.", confirmationToken.getUserId().toString()));
    	}
    	
    	if (confirmationToken.getExpirationDate().isAfter(LocalDateTime.now())) {
    		UserEntity user = optionalUser.get();
    		user.setEnabled(true);
    		
        	userRepository.save(user);
        	confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    	} else {
    		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    		
    		ConfirmationTokenEntity newConfirmationToken = new ConfirmationTokenEntity(confirmationToken.getUserId());
        	LocalDateTime expirationDate = confirmationTokenService.getExpirationDate(newConfirmationToken.getCreatedDate());
        	newConfirmationToken.setExpirationDate(expirationDate);
        	
        	confirmationTokenService.saveConfirmationToken(newConfirmationToken);
        	
        	List<EmailAddressEntity> emailAddresses = emailAddressRepository.findByUserId(optionalUser.get().getId());
        	
        	if (emailAddresses.size() == 0) {
        		throw new Exception("Email not found for userId: " + optionalUser.get().getId());
        	}
        	
        	sendConfirmationMail(emailAddresses.get(0).getEmailAddress(), newConfirmationToken.getConfirmationToken());
        	
        	throw new Exception("Confirmation token expired. Please check your email for a new token.");
    	}
	}
    
    private boolean doesUserExist(String username) {
    	Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
    	
    	if (optionalUser.isPresent()) {
    		return true;
    	}
    	
    	return false;
    }
    
    public UserDTO getUserData(UserEntity userEntity) {
        return userEntityConverter.toResponse(userEntity);
    }
}