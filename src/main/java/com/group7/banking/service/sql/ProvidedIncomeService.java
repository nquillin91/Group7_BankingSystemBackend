package com.group7.banking.service.sql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.sql.ProvidedIncomeEntity;
import com.group7.banking.repository.sql.ProvidedIncomeRepository;

@Service
public class ProvidedIncomeService {
	Logger logger = LoggerFactory.getLogger(ProvidedIncomeService.class);
	
    @Autowired
    private ProvidedIncomeRepository providedIncomeRepository;
    
    public List<ProvidedIncomeEntity> findAll() {

        Iterable<ProvidedIncomeEntity> it = providedIncomeRepository.findAll();

        ArrayList<ProvidedIncomeEntity> users = new ArrayList<ProvidedIncomeEntity>();
        it.forEach(e -> {
        	users.add(e);
        });

        return users;
    }

    public void deleteById(Long id) {
    	providedIncomeRepository.deleteById(id);
    }
}