package com.group7.banking.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="privileges")
@NoArgsConstructor
@RequiredArgsConstructor
public class PrivilegeEntity {
 
	@Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Getter
    @Setter
    @NonNull
    private String name;
 
    @Getter
    @Setter
    @ManyToMany(mappedBy = "privileges")
    private Collection<RoleEntity> roles;
}