package com.group7.banking.component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.group7.banking.model.EmailAddressEntity;
import com.group7.banking.model.NameEntity;
import com.group7.banking.model.PrivilegeEntity;
import com.group7.banking.model.RoleEntity;
import com.group7.banking.model.UserEntity;
import com.group7.banking.repository.NameRepository;
import com.group7.banking.repository.PrivilegeRepository;
import com.group7.banking.repository.RoleRepository;
import com.group7.banking.repository.UserRepository;

@Component
public class SetupDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NameRepository nameRepository;
 
    @Autowired
    private RoleRepository roleRepository;
 
    @Autowired
    private PrivilegeRepository privilegeRepository;
 
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup)
            return;
        
        createAdminUser();
 
        alreadySetup = true;
    }
    
    private void createAdminUser() {
    	UserEntity user = new UserEntity("admin", bCryptPasswordEncoder.encode("adminPass"), LocalDate.now());
    	
    	PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
    	PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
    	
    	List<PrivilegeEntity> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);        
    	
    	createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
    	createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
    	
    	Optional<RoleEntity> adminRole = roleRepository.findByName("ROLE_ADMIN");
    	
    	if (adminRole.isPresent()) {
    		user.setRoles(Arrays.asList(adminRole.get()));
    	}
    	
    	NameEntity name = new NameEntity(user, "Admin", "", "");
    	user.setName(name);
    	
    	EmailAddressEntity email = new EmailAddressEntity(user, "admin@admin.com");
    	user.setEmailAddress(email);
        user.setEnabled(true);
    	
    	userRepository.save(user);
    	nameRepository.save(name);
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
    RoleEntity createRoleIfNotFound(String name, Collection<PrivilegeEntity> privileges) {
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