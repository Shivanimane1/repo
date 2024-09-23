package com.tollplaza.TollPlaza.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.LoginDetails;
import com.tollplaza.TollPlaza.repository.LoginDetailsRepository;

@Service
public class LoginDetailsService {
	
	 @Autowired
	    private LoginDetailsRepository loginDetailsRepository;

	 public List<LoginDetails> getTodaysLoginDetailsByOperatorId(Long operatorId) {
	        LocalDate today = LocalDate.now();
	        return loginDetailsRepository.findByOperator_OpIdAndLoginDate(operatorId, today);
	    }
	 
	 
	 public List<LoginDetails> getAllTodaysLoginDetails() {
		 LocalDate today = LocalDate.now();
	        return loginDetailsRepository.findByLoginDate(today);
	    }


	 public List<LoginDetails> getLoginDetailsByOperatorId(Long operatorId) {
		    return loginDetailsRepository.findByOperator_OpId(operatorId);
		}


//	 public List<LoginDetails> getLoginDetailsByDate(String date) {
//		    // Convert the date string to LocalDate if needed
//		    LocalDate localDate = LocalDate.parse(date);
//		    return loginDetailsRepository.findByLoginDate(localDate);
//		}
	 
	 public List<LoginDetails> getLoginDetailsByDate(String date) {
		    // Convert the date string to LocalDate if necessary
		    LocalDate localDate = LocalDate.parse(date);
		    // Fetch and return the login details for the specified date
		    return loginDetailsRepository.findByLoginDate(localDate);
		}





}
