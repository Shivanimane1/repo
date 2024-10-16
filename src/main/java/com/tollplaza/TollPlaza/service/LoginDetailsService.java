package com.tollplaza.TollPlaza.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.Booth;
import com.tollplaza.TollPlaza.entity.LoginDetails;
import com.tollplaza.TollPlaza.repository.BoothRepository;
import com.tollplaza.TollPlaza.repository.LoginDetailsRepository;

@Service
public class LoginDetailsService {
	
	 @Autowired
	    private LoginDetailsRepository loginDetailsRepository;
	 
	 @Autowired
	 private BoothRepository boothRepository;

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

	 
	 public List<LoginDetails> getLoginDetailsByDate(String date) {
		    // Convert the date string to LocalDate if necessary
		    LocalDate localDate = LocalDate.parse(date);
		    // Fetch and return the login details for the specified date
		    return loginDetailsRepository.findByLoginDate(localDate);
		}



//---------------------------------------
//	 // Get all login details by operator ID
//	    public List<LoginDetails> getLoginDetailsByOperatorId(Long operatorId) {
//	        return loginDetailsRepository.findByOperator_OpId(operatorId);
//	    }

	    // Get total vehicle count by operator ID
	    public Long countTotalVehiclesByOperatorId(Long operatorId) {
	        return loginDetailsRepository.countTotalVehiclesByOperatorId(operatorId);
	    }

	    // Get count of "Single" return type vehicles
	    public Long countSingleVehiclesByOperatorId(Long operatorId) {
	        return loginDetailsRepository.countSingleVehiclesByOperatorId(operatorId);
	    }

	    // Get count of "Return" type vehicles
	    public Long countReturnVehiclesByOperatorId(Long operatorId) {
	        return loginDetailsRepository.countReturnVehiclesByOperatorId(operatorId);
	    }

	    
	    public List<Booth> getBoothDeatilsById(Long opId) {
	        return boothRepository.findByOperatorsOpId(opId);
	    }
}
