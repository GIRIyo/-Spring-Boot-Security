package com.loreal.entity;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_")
@Getter
@NoArgsConstructor
@SuperBuilder
public class User implements UserDetails{
	
		@Id
		@Column(name = "id")
		@GeneratedValue(strategy= GenerationType.IDENTITY)
		private BigInteger id;
		
		@Column(name = "password")
		private String password;
		
		@Column(unique = true, name= "name")
		private String name;
		
		@Column(name = "auth")
		private String auth;
		
		@Column(name = "salt")
		private String salt;
		
			
		 @Override
		    public Collection<? extends GrantedAuthority> getAuthorities() {
		        Set<GrantedAuthority> roles = new HashSet<>();
		        for(String role : auth.split(",")) {
		            roles.add(new SimpleGrantedAuthority(role));
		        }
		        return roles;
		    }

		    // 사용자의 unique한 값 return(보통 pk or id)
		    @Override
		    public String getUsername() {
		        return name;
		    }

		    // 사용자의 password 반환
		    @Override
		    public String getPassword() {
		        return password;
		    }

		    // 계정 만료 여부 반환
		    @Override
		    public boolean isAccountNonExpired() {
		    	//만료되었는지 확인하는 로직
		        return true; // 만료되지 않음
		    }

		    // 계정 잠김 여부 반환
		    @Override
		    public boolean isAccountNonLocked() {
		    	//계정 잠금되었는지 확인하는 로직
		        return true; // 잠기지 않음
		    }

		    // 비밀번호 만료 여부 반환
		    @Override
		    public boolean isCredentialsNonExpired() {
		    	//패스워드가 만료되었는지 확인하는 로직
		        return true; // 만료되지 않음
		    }

		    // 계정의 활성화 여부 반환
		    @Override
		    public boolean isEnabled() {
		    	//계정이 사용 가능한지 확인하는 로직
		        return true; // 활성화 됨
		    }


}

