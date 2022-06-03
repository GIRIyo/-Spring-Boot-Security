package com.loreal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.loreal.dto.UserDto;
import com.loreal.service.UserSerivce;


@Controller
public class UserController {
	
	@Autowired
	private UserSerivce userSerivce;

	@GetMapping("/")
	public String userPage(Authentication a) {
		
		return "user.html";
	}
	
	@GetMapping("/login")
	public String login(UserDto userDto) {
		return "login.html";
	}
	
	@GetMapping("/signUp")
	public String signUp(UserDto userDto) {
		return "signUp.html";
	}
	
	@PostMapping("/signUp") 
    public String signUpForm(UserDto userDto) {
		userSerivce.save(userDto);
		
		
		
        return "redirect:/login";
    }
	
	@GetMapping("/admin")
	public String adminPage(UserDto userDto) {
		return "admin.html";
	}
	
	@GetMapping(value = "/logout")
	  public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
	    new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    return "redirect:/login";
	  }
}
	
	

