package com.group7.banking.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.group7.banking.model.ConfirmationTokenEntity;
import com.group7.banking.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
	
	@Value("${app.confirmationToken.expirationTimeInMinutes}")
	private int confirmationTokenExpirationTime;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	public LocalDateTime getExpirationDate(LocalDateTime createdDate) {
		return createdDate.plusMinutes(confirmationTokenExpirationTime);
	}
	
	public void saveConfirmationToken(ConfirmationTokenEntity confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}

	public void deleteConfirmationToken(Long id) {
		confirmationTokenRepository.deleteById(id);
	}

	public Optional<ConfirmationTokenEntity> findConfirmationTokenByToken(String token) {
		return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
	}
}