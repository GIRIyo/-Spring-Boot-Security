package com.loreal.service;

import java.math.BigInteger;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loreal.dto.UserDto;
import com.loreal.entity.User;
import com.loreal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSerivce implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
    // UserDetailService 상속시 필수로 구현해야 하는 메소드
    // UserDetails가 기본 반환 타입, UserInfo가 이를 상속하고 있으므로 자동으로 다운캐스팅됨
    @Override
    public User loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(name);
        return user
                .orElseThrow(() -> new UsernameNotFoundException(name));
    }

    public BigInteger save(UserDto userDto) {
		
		 userDto.setSalt(SHA256.generateSalt());
		 userDto.setPassword(SHA256.getEncrypt(userDto.getPassword(), userDto.getSalt()));
    	
//       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//       userDto.setPassword(encoder.encode(userDto.getPassword()));

        return userRepository.save(User.builder()
        .name(userDto.getName())
        .auth(userDto.getAuth())
        .password(userDto.getPassword())
        .salt(userDto.getSalt())
        .build()).getId();
    }
}
