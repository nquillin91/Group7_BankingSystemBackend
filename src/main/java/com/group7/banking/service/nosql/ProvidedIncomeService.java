package com.group7.banking.service.nosql;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.ProvidedIncomeDTO;
import com.group7.banking.model.nosql.ProvidedIncomeEntity;
import com.group7.banking.repository.nosql.ProvidedIncomeRepository;

@Service
public class ProvidedIncomeService {
	Logger logger = LoggerFactory.getLogger(ProvidedIncomeService.class);
	
	@Autowired
	private ProvidedIncomeRepository providedIncomeRepository;
	
	public void updateProvidedIncome(long userId, ProvidedIncomeDTO dto) {
		ProvidedIncomeEntity providedIncome = providedIncomeRepository.findByUserId(userId);
		
		if (providedIncome != null) {
			providedIncome.setIncomeAmount(dto.getIncomeAmount());
			providedIncome.setLastUpdatedDate(LocalDateTime.now());
			
			providedIncomeRepository.save(providedIncome);
		} else {
			ProvidedIncomeEntity newIncome = new ProvidedIncomeEntity(userId, dto.getIncomeAmount());
			
			providedIncomeRepository.save(newIncome);
		}
	}
	
	public void deleteById(Long id) {
		providedIncomeRepository.deleteById(id);
    }
}