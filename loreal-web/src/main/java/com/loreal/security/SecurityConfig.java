package com.loreal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.loreal.dto.UserDto;
import com.loreal.service.SHA256;
import com.loreal.service.UserSerivce;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity     //spring security 를 적용한다는 annotation  1번
@RequiredArgsConstructor
@Configuration //bean을 관리하는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //어댑터 설정 2번
	
	@Autowired
	private UserSerivce userSerivce; // 유저 정보를 가져올 클래s스 3번

    @Override
    // 인증을 무시할 경로 설정
    public void configure(WebSecurity web) {  // WebSecurityConfigurerAdapter 상속받으면 오버라이드 가능4번
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    // http 관련 인증 설정 가능
    public void configure(HttpSecurity http) throws Exception { // WebSecurityConfigurerAdapter 상속받으면 오버라이드 가능5번
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()  //URL별권한 시작 점 선언되어야 아래 옵션 작동함 6번
                        .antMatchers("/login", "/signUp").permitAll() // 누구나 접근 가능
                        .antMatchers("/").hasRole("USER") // USER, ADMIN 만 접근 가능
                        .antMatchers("/admin").hasRole("ADMIN") // ADMIN 만 접근 가능
                        .anyRequest().authenticated() // 나머지는 권한이 있기만 하면 접근 가능
                .and()
                    .formLogin() // 로그인에 대한 설정 7번
                        .loginPage("/login") // 로그인 페이지 링크
                        .defaultSuccessUrl("/") // 로그인 성공시 연결되는 주소
                        .usernameParameter("name")
                    	/* .passwordParameter("pw") */
                .and()
                    .logout() // 로그아웃 관련 설정 8번
                        .logoutSuccessUrl("/login") // 로그아웃 성공시 연결되는 주소
                        .invalidateHttpSession(true) // 로그아웃시 저장해 둔 세션 날리기
        ;
    }

    public void configure(AuthenticationManagerBuilder auth, UserDto userDto) throws Exception {  //9번
        auth.userDetailsService(userSerivce) // 유저 정보는 userService 에서 가져온다
        //해당 서비스에서는 UserDetailsService 를 implements 해서 loadUserByUsername()구현해야함
        .passwordEncoder(new BCryptPasswordEncoder()); // 패스워드 인코더는 passwordEncoder(BCrypt 사용)
        		
  
        
        
    }
	
}
