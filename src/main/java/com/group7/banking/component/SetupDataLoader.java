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
import com.group7.banking.model.sql.EmailAddressEntity;
import com.group7.banking.model.sql.PrivilegeEntity;
import com.group7.banking.model.sql.RoleEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.NameRepository;
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
    	}
    	
//    	NameEntity name = new NameEntity(user, "Admin", "", "");
//    	user.setName(name);
//    	
    	EmailAddressEntity email = new EmailAddressEntity(user, "admin@admin.com");
    	user.setEmailAddress(email);
        user.setEnabled(true);
    	
      //  nameRepository.save(name);
    	userRepository.save(user);
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