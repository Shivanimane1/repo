package com.tollplaza.TollPlaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.LoginRequest;
import com.tollplaza.TollPlaza.repository.LoginRepository;

@Service
public class AdminService {
	@Autowired
	private LoginRepository loginRepository;
	
	 public boolean authenticate(String email, String password) {
	        // Replace with your actual authentication logic
	        return "admin@example.com".equals(email) && "password".equals(password);
	    }

	public LoginRequest login(String email, String password) {
		LoginRequest loginReq = loginRepository.findByEmailAndPassword(email, password);
		return loginReq;
	}

	
	
}
