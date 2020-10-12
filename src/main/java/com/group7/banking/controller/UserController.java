package com.group7.banking.controller;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.dto.SignUpDTO;
import com.group7.banking.dto.UserDTO;
import com.group7.banking.model.ConfirmationTokenEntity;
import com.group7.banking.service.ConfirmationTokenService;
import com.group7.banking.service.UserService;

@RestController("UserController")
public class UserController {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    
    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
    	return userService.findById(id);
    }
    
    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO signUp(@RequestBody SignUpDTO signUpDto) throws Exception {
		UserDTO userData = new UserDTO();
		userData = userService.signUpUser(signUpDto);
		
		return userData;
	}

	@GetMapping("/sign-up/confirm")
	public String confirmMail(@RequestParam("token") String token) throws Exception {

		Optional<ConfirmationTokenEntity> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

		if(optionalConfirmationToken.isPresent()) {
			ConfirmationTokenEntity confirmationToken = optionalConfirmationToken.get();
			userService.confirmUser(confirmationToken);
				
			return "Successfully confirmed! Thank you for signing up!";
		} else {
			throw new Exception(MessageFormat.format("Confirmation token {0} not found! Please contact your"
					+ " administrator.", token));
		}
	}
}