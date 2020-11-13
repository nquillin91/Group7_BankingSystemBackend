package com.group7.banking.controller;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.config.JwtUserDetails;
import com.group7.banking.dto.BillingAddressDTO;
import com.group7.banking.dto.EmailAddressDTO;
import com.group7.banking.dto.PasswordDTO;
import com.group7.banking.dto.PhoneNumberDTO;
import com.group7.banking.dto.ProvidedIncomeDTO;
import com.group7.banking.dto.SignUpDTO;
import com.group7.banking.dto.UserDTO;
import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.dto.UsernameDTO;
import com.group7.banking.model.nosql.ConfirmationTokenEntity;
import com.group7.banking.service.nosql.ConfirmationTokenService;
import com.group7.banking.service.nosql.EmailAddressService;
import com.group7.banking.service.nosql.PhoneNumberService;
import com.group7.banking.service.nosql.ProvidedIncomeService;
import com.group7.banking.service.sql.BillingAddressService;
import com.group7.banking.service.sql.UserService;

@RestController("UserController")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    @Autowired
    private EmailAddressService emailAddressService;
    
    @Autowired
    private BillingAddressService billingAddressService;
    
    @Autowired
    private ProvidedIncomeService providedIncomeService;
    
    @Autowired
    private PhoneNumberService phoneNumberService;
    
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
    	return userService.findById(id);
    }
    
    @GetMapping("/users/user/profile")
    public UserProfileDTO getUserProfile(HttpServletRequest request) throws Exception {
    	String username = request.getUserPrincipal().getName();
    	
        return userService.getUserProfile(username);
    }
    
    // Sql
    @PutMapping("/users/user/billing-address")
    public void updateBillingAddress(HttpServletRequest request,
    		@RequestBody BillingAddressDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	billingAddressService.updateBillingAddress(userId, dto);
    }
    
    // NoSql
    @PutMapping("/users/user/email-address")
    public void updateEmailAddress(HttpServletRequest request,
    		@RequestBody EmailAddressDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	emailAddressService.updateEmailAddress(userId, dto);
    }
    
    // NoSql
    @PutMapping("/users/user/phone-number")
    public void updatePhoneNumber(HttpServletRequest request,
    		@RequestBody PhoneNumberDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	phoneNumberService.updatePhoneNumber(userId, dto);
    }
    
    // NoSql
    @PutMapping("/users/user/income")
    public void updateIncome(HttpServletRequest request,
    		@RequestBody ProvidedIncomeDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	providedIncomeService.updateProvidedIncome(userId, dto);
    }
    
    // Sql
    @PutMapping("/users/user/username")
    public void updateUsername(HttpServletRequest request,
    		@RequestBody UsernameDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	userService.updateUsername(userId, dto);
    }
    
    // Sql
    @PutMapping("/users/user/password")
    public void updatePassword(HttpServletRequest request,
    		@RequestBody PasswordDTO dto) throws Exception {
    	JwtUserDetails userDetails = (JwtUserDetails) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal();
    	long userId = userDetails.getUserId();
    	
    	userService.updatePassword(userId, dto);
    }
    
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO signUp(@RequestBody SignUpDTO signUpDto) throws Exception {
		UserDTO userData = new UserDTO();
		userData = userService.signUpUser(signUpDto);
		
		return userData;
	}

	@GetMapping("/sign-up/confirm")
	public String confirmMail(@RequestParam("token") String token) throws Exception {
		List<ConfirmationTokenEntity> searchResult = confirmationTokenService.findConfirmationTokenByToken(token);

		if(searchResult.size() == 1) {
			ConfirmationTokenEntity confirmationToken = searchResult.get(0);
			userService.confirmUser(confirmationToken);
				
			return "Successfully confirmed! Thank you for signing up!";
		} else {
			throw new Exception(MessageFormat.format("Confirmation token {0} not found! Please contact your"
					+ " administrator.", token));
		}
	}
}