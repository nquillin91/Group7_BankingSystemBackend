package com.group7.banking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.model.ConfirmationTokenEntity;
import com.group7.banking.model.SignUpRequest;
import com.group7.banking.model.UserData;
import com.group7.banking.service.ConfirmationTokenService;
import com.group7.banking.service.UserService;

@RestController("UserController")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    @GetMapping("/login")
	public String signIn(SignUpRequest signUpRequest) {
    	
    	return "Login Page";
	}
    
    @GetMapping("/users/{id}")
    public UserData getUser(@PathVariable Long id) {
    	return userService.findById(id);
    }
    
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserData signUp(@RequestBody SignUpRequest signUpRequest) {
		UserData userData = new UserData();
		
    	try {
			userData = userService.signUpUser(signUpRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userData;
	}

	@GetMapping("/sign-up/confirm")
	public String confirmMail(@RequestParam("token") String token) {

		Optional<ConfirmationTokenEntity> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

		if(optionalConfirmationToken.isPresent()) {
			ConfirmationTokenEntity confirmationToken = optionalConfirmationToken.get();

			try {
				userService.confirmUser(confirmationToken);
			} catch (Exception e) {
				return e.getMessage();
			}
				
			return "Successfully confirmed! Thank you for signing up!";
		} else {
			return "Error!";
		}
	}
}