package com.group7.banking.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group7.banking.component.UserEntityConverter;
import com.group7.banking.dto.SignUpDTO;
import com.group7.banking.dto.UserDTO;
import com.group7.banking.model.BillingAddressEntity;
import com.group7.banking.model.ConfirmationTokenEntity;
import com.group7.banking.model.EmailAddressEntity;
import com.group7.banking.model.NameEntity;
import com.group7.banking.model.PhoneNumberEntity;
import com.group7.banking.model.ProvidedIncomeEntity;
import com.group7.banking.model.RoleEntity;
import com.group7.banking.model.SsnEntity;
import com.group7.banking.model.UserEntity;
import com.group7.banking.repository.BillingAddressRepository;
import com.group7.banking.repository.EmailAddressRepository;
import com.group7.banking.repository.NameRepository;
import com.group7.banking.repository.PhoneNumberRepository;
import com.group7.banking.repository.ProvidedIncomeRepository;
import com.group7.banking.repository.RoleRepository;
import com.group7.banking.repository.SSNRepository;
import com.group7.banking.repository.UserRepository;

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
    		
    		userData.setUserName(user.getUsername());
    		userData.setFirstName(user.getName().getFirstName());
    		userData.setMiddleName(user.getName().getMiddleName());
    		userData.setLastName(user.getName().getLastName());
    	}
    	
    	return userData;
    }
    
    public void deleteById(Long userId) {
    	userRepository.deleteById(userId);
    }
    
    public UserDTO signUpUser(SignUpDTO signUpDto) throws Exception {
    	UserEntity user = parseSignUpRequest(signUpDto);
    	
    	Optional<RoleEntity> userRole = roleRepository.findByName("ROLE_USER");
    	
    	if (userRole.isPresent()) {
    		user.setRoles(Arrays.asList(userRole.get()));
    	}
    	
    	logger.debug(user.toString());
    	String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    	user.setPassword(encryptedPassword);
    	
    	userRepository.save(user);
    	nameRepository.save(user.getName());
    	providedIncomeRepository.save(user.getProvidedIncome());
    	billingAddressRepository.save(user.getBillingAddress());
    	emailAddressRepository.save(user.getEmailAddress());
    	phoneNumberRepository.save(user.getPhoneNumber());
    	ssnRepository.save(user.getSsn());
    	
    	ConfirmationTokenEntity confirmationToken = new ConfirmationTokenEntity(user);
    	LocalDateTime expirationDate = confirmationTokenService.getExpirationDate(confirmationToken.getCreatedDate());
    	confirmationToken.setExpirationDate(expirationDate);
    	
    	confirmationTokenService.saveConfirmationToken(confirmationToken);
    	sendConfirmationMail(user.getEmailAddress(), confirmationToken.getConfirmationToken());
    	
    	return getUserData(user);
    }
    
    private UserEntity parseSignUpRequest(SignUpDTO signUpDto) throws Exception {
    	if (doesUserExist(signUpDto.getUsername())) {
    		throw new Exception(MessageFormat.format("Username - {0} - already exists.", signUpDto.getUsername()));
    	}
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    	LocalDate birthDate = LocalDate.parse(signUpDto.getBirthDate(), formatter);
    	
    	UserEntity user = new UserEntity(signUpDto.getUsername(), signUpDto.getPassword(), birthDate);
    	
    	NameEntity name = new NameEntity(user, signUpDto.getFirstName(), signUpDto.getMiddleName(), signUpDto.getLastName());
    	user.setName(name);
    	
    	ProvidedIncomeEntity providedIncome = new ProvidedIncomeEntity(user, signUpDto.getProvidedIncome());
    	user.setProvidedIncome(providedIncome);
    	
    	BillingAddressEntity billingAddress = new BillingAddressEntity(user, signUpDto.getAddressLine1(),
    			signUpDto.getAddressLine2(),
    			signUpDto.getCity(), signUpDto.getState(), signUpDto.getZipcode());
    	user.setBillingAddress(billingAddress);
    	
    	EmailAddressEntity emailAddress = new EmailAddressEntity(user, signUpDto.getEmailAddress());
    	user.setEmailAddress(emailAddress);
    	
    	PhoneNumberEntity phoneNumber = new PhoneNumberEntity(user, signUpDto.getPhoneNumber());
    	user.setPhoneNumber(phoneNumber);
    	
    	SsnEntity ssn = new SsnEntity(user, signUpDto.getSsn());
    	user.setSsn(ssn);
    	
    	return user;
    }
    
    private void sendConfirmationMail(EmailAddressEntity emailAddress, String token) {
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailAddress.getEmailAddress());
		mailMessage.setSubject("Mail Confirmation Link!");
		mailMessage.setFrom("<MAIL>");
		mailMessage.setText("Thank you for registering. Please click on the "
				+ "below link to activate your account."
				+ appHost + "/sign-up/confirm?token=" + token);

		emailSenderService.sendEmail(mailMessage);
	}
    
    public void confirmUser(ConfirmationTokenEntity confirmationToken) throws Exception {
    	Optional<UserEntity> optionalUser = userRepository.findById(confirmationToken.getUser().getId());
    	
    	if (confirmationToken.getExpirationDate().isAfter(LocalDateTime.now())) {
    		if (optionalUser.isPresent()) {
        		UserEntity user = optionalUser.get();
        		user.setEnabled(true);
        		
            	userRepository.save(user);
            	confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
        	} else {
        		throw new Exception(MessageFormat.format("Error confirming user {0} - not found.", confirmationToken.getUser().getId().toString()));
        	}
    	} else {
    		confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    		
    		ConfirmationTokenEntity newConfirmationToken = new ConfirmationTokenEntity(confirmationToken.getUser());
        	LocalDateTime expirationDate = confirmationTokenService.getExpirationDate(newConfirmationToken.getCreatedDate());
        	newConfirmationToken.setExpirationDate(expirationDate);
        	
        	confirmationTokenService.saveConfirmationToken(newConfirmationToken);
        	sendConfirmationMail(newConfirmationToken.getUser().getEmailAddress(), newConfirmationToken.getConfirmationToken());
        	
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