package com.group7.banking.service.sql;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.BillingAddressDTO;
import com.group7.banking.model.sql.BillingAddressEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.sql.BillingAddressRepository;
import com.group7.banking.repository.sql.UserRepository;

@Service
public class BillingAddressService {
	Logger logger = LoggerFactory.getLogger(BillingAddressService.class);
	
	@Autowired
	private BillingAddressRepository billingAddressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void updateBillingAddress(long userId, BillingAddressDTO dto) throws Exception {
		Optional<BillingAddressEntity> optionalBillingAddress = billingAddressRepository.findByUserId(userId);
		
		if (optionalBillingAddress.isPresent()) {
			BillingAddressEntity address = optionalBillingAddress.get();

			address.setAddressLine1(dto.getAddressLine1());
			address.setAddressLine2(dto.getAddressLine2());
			address.setCity(dto.getCity());
			address.setState(dto.getState());
			address.setZipcode(dto.getZipcode());
			address.setLastUpdatedDate(LocalDateTime.now());
			
			billingAddressRepository.save(address);
		} else {
			Optional<UserEntity> optionalUser = userRepository.findById(userId);
			
			if (optionalUser.isPresent()) {
				UserEntity user = optionalUser.get();
				
				BillingAddressEntity newAddress = new BillingAddressEntity(user, dto.getAddressLine1(), dto.getAddressLine2(),
						dto.getCity(), dto.getState(), dto.getZipcode());
				
				user.setBillingAddress(newAddress);
				billingAddressRepository.save(newAddress);
				userRepository.save(user);
			} else {
				throw new Exception("USER_DOES_NOT_EXIST");
			}
		}
	}
	
    public void deleteById(Long id) {
    	billingAddressRepository.deleteById(id);
    }
}