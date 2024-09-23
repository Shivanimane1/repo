package com.tollplaza.TollPlaza.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.Operators;
import com.tollplaza.TollPlaza.repository.OpRepository;

@Service
public class OpService {
	@Autowired 
	private OpRepository opRepository;
	
	

	public void save(Operators operators) {
		opRepository.save(operators);
	}
	
	public List<Operators> getAllOperators() {
		return opRepository.findAll();
	}

	public Optional<Operators> findById(Long opId) {
		return opRepository.findById(opId);
	}

	public Operators login(String email, String password) {
		return opRepository.findByEmailAndPassword(email, password).orElse(null);
		
	}





}


