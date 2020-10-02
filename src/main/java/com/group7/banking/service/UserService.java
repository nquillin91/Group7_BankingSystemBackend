package com.group7.banking.service;

import java.text.MessageFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.group7.banking.model.BillingAddress;
import com.group7.banking.model.ConfirmationToken;
import com.group7.banking.model.EmailAddress;
import com.group7.banking.model.Name;
import com.group7.banking.model.PhoneNumber;
import com.group7.banking.model.SSN;
import com.group7.banking.model.SignUpRequest;
import com.group7.banking.model.User;
import com.group7.banking.repository.BillingAddressRepository;
import com.group7.banking.repository.EmailAddressRepository;
import com.group7.banking.repository.NameRepository;
import com.group7.banking.repository.PhoneNumberRepository;
import com.group7.banking.repository.SSNRepository;
import com.group7.banking.repository.UserRepository;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BillingAddressRepository billingAddressRepository;
    
    @Autowired
    private EmailAddressRepository emailAddressRepository;
    
    @Autowired
    private NameRepository nameRepository;
    
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
    
    public void signUpUser(SignUpRequest signUpRequest) throws Exception {
    	User user = parseSignUpRequest(signUpRequest);
    	
    	logger.debug(user.toString());
    	
    	String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    	user.setPassword(encryptedPassword);
    	userRepository.save(user);

    	final ConfirmationToken confirmationToken = new ConfirmationToken(user);

    	confirmationTokenService.saveConfirmationToken(confirmationToken);
    	sendConfirmationMail(user.getEmailAddress(), confirmationToken.getConfirmationToken());
    }
    
    private User parseSignUpRequest(SignUpRequest signUpRequest) throws Exception {
    	if (doesUserExist(signUpRequest.getUsername())) {
    		throw new Exception(MessageFormat.format("Username - {0} - already exists.", signUpRequest.getUsername()));
    	}
    	
    	Name nameA = new Name(signUpRequest.getFirstName(), signUpRequest.getLastName());
    	nameRepository.save(nameA);
    	
    	BillingAddress billing = new BillingAddress(signUpRequest.getAddressLine1(),
    			signUpRequest.getAddressLine2(),
    			signUpRequest.getCity(), signUpRequest.getState(), signUpRequest.getZipcode());
    	billingAddressRepository.save(billing);
    	
    	EmailAddress email = new EmailAddress(signUpRequest.getEmailAddress());
    	emailAddressRepository.save(email);
    	
    	PhoneNumber phoneNum = new PhoneNumber(signUpRequest.getPhoneNumber());
    	phoneNumberRepository.save(phoneNum);
    	
    	SSN ssn = new SSN(signUpRequest.getSsn());
    	ssnRepository.save(ssn);
    	
    	return new User(signUpRequest.getUsername(), signUpRequest.getPassword(), nameA, billing, email, phoneNum, ssn);
    }
    
    private boolean doesUserExist(String username) {
    	Optional<User> optionalUser = userRepository.findByUsername(username);
    	
    	if (optionalUser.isPresent()) {
    		return true;
    	}
    	
    	return false;
    }
    
    private void sendConfirmationMail(EmailAddress emailAddress, String token) {
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailAddress.getEmailAddress());
		mailMessage.setSubject("Mail Confirmation Link!");
		mailMessage.setFrom("<MAIL>");
		mailMessage.setText("Thank you for registering. Please click on the "
				+ "below link to activate your account."
				+ "http://localhost:8080/sign-up/confirm?token=" + token);

		emailSenderService.sendEmail(mailMessage);
	}
    
    public void confirmUser(ConfirmationToken confirmationToken) throws Exception {
    	Optional<User> optionalUser = userRepository.findById(confirmationToken.getUserId());
    	
    	if (optionalUser.isPresent()) {
    		User user = optionalUser.get();
    		user.setEnabled(true);
    		
        	userRepository.save(user);
        	confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    	} else {
    		throw new Exception(MessageFormat.format("Error confirming user {0} - not found.", confirmationToken.getUserId().toString()));
    	}
	}
    
	public User loadUserByUsernameOrEmail(String usernameOrEmail) throws Exception {
		final Optional<User> optionalUserByUsername = userRepository.findByUsername(usernameOrEmail);

		if (optionalUserByUsername.isPresent()) {
			return optionalUserByUsername.get();
		}
		else {
			final Optional<User> optionalUserByEmail = userRepository.findByEmailAddress(usernameOrEmail);
			
			if (optionalUserByEmail.isPresent()) {
				return optionalUserByEmail.get();
			} else {
				throw new Exception(MessageFormat.format("User with username or email {0} cannot be found.", usernameOrEmail));
			}
		}
	}

    public void deleteById(Long userId) {
    	userRepository.deleteById(userId);
    }
}