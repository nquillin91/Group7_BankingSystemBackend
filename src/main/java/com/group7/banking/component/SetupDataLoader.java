package com.group7.banking.component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.nosql.EmailAddressEntity;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.PrivilegeEntity;
import com.group7.banking.model.sql.RoleEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.ConfirmationTokenRepository;
import com.group7.banking.repository.nosql.EmailAddressRepository;
import com.group7.banking.repository.nosql.NameRepository;
import com.group7.banking.repository.nosql.PhoneNumberRepository;
import com.group7.banking.repository.nosql.ProvidedIncomeRepository;
import com.group7.banking.repository.sql.AccountRepository;
import com.group7.banking.repository.sql.PrivilegeRepository;
import com.group7.banking.repository.sql.RoleRepository;
import com.group7.banking.repository.sql.UserRepository;

@Component
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NameRepository nameRepository;
    
    @Autowired
    private EmailAddressRepository emailAddressRepository;
    
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;
    
    @Autowired
    private ProvidedIncomeRepository providedIncomeRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
 
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup)
            return;
        
        nameRepository.deleteAll();
        emailAddressRepository.deleteAll();
        phoneNumberRepository.deleteAll();
        providedIncomeRepository.deleteAll();
        confirmationTokenRepository.deleteAll();
        
        createAdminUser();
 
        alreadySetup = true;
    }
    
    private void createAdminUser() {
    	UserEntity user = new UserEntity("admin", bCryptPasswordEncoder.encode("adminPass"), LocalDate.now());
    	UserEntity user1 = new UserEntity("a", bCryptPasswordEncoder.encode("aa"), LocalDate.now());
    	
    	PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
    	PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
    	
    	Set<PrivilegeEntity> adminPrivileges = new HashSet<PrivilegeEntity>();
    	adminPrivileges.add(readPrivilege);
    	adminPrivileges.add(writePrivilege);
    	
    	Set<PrivilegeEntity> userPrivileges = new HashSet<PrivilegeEntity>();
    	userPrivileges.add(readPrivilege);
    	
    	createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    	createRoleIfNotFound("ROLE_USER", userPrivileges);
    	
    	Optional<RoleEntity> adminRole = roleRepository.findByName("ROLE_ADMIN");
    	
    	if (adminRole.isPresent()) {
    		Set<RoleEntity> roles = new HashSet<RoleEntity>();
    		roles.add(adminRole.get());
    		user.setRoles(roles);
    		user1.setRoles(roles);
    	}
    	user.setEnabled(true);
    	userRepository.save(user);
    	
    	user1.setEnabled(true);
    	userRepository.save(user1);
    	
    	NameEntity name = new NameEntity(user.getId(), "Admin", "", "");
    	EmailAddressEntity email = new EmailAddressEntity(user.getId(), "admin@admin.com");
    	nameRepository.save(name);
    	emailAddressRepository.save(email);
    	
    	NameEntity name1 = new NameEntity(user1.getId(), "a", "", "");
    	EmailAddressEntity email1 = new EmailAddressEntity(user1.getId(), "a@a.com");
    	nameRepository.save(name1);
    	emailAddressRepository.save(email1);
    	AccountEntity account = new AccountEntity(user1, 0, 200.0);
    	user.addAccount(account);
    	AccountEntity account1 = new AccountEntity(user1, 1, 1000.0);
    	user.addAccount(account1);
    	accountRepository.save(account);
    	accountRepository.save(account1);
    }
 
    @Transactional
    PrivilegeEntity createPrivilegeIfNotFound(String name) {
    	PrivilegeEntity privilege = new PrivilegeEntity();
    	Optional<PrivilegeEntity> optionalPrivilege = privilegeRepository.findByName(name);
        
    	if (!optionalPrivilege.isPresent()) {
            privilege = new PrivilegeEntity(name);
            privilegeRepository.save(privilege);
        }
    	
        return privilege;
    }
 
    @Transactional
    RoleEntity createRoleIfNotFound(String name, Set<PrivilegeEntity> privileges) {
    	RoleEntity role = new RoleEntity();
    	Optional<RoleEntity> optionalRole = roleRepository.findByName(name);
        
    	if (!optionalRole.isPresent()) {
            role = new RoleEntity(name);
            role.setPrivileges(privileges);
            
            roleRepository.save(role);
        }
    	
        return role;
    }
}