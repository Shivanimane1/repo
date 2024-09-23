package com.tollplaza.TollPlaza.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tollplaza.TollPlaza.entity.LoginDetails;
import com.tollplaza.TollPlaza.service.LoginDetailsService;

@Controller
public class LoginDetailsController {
	
	@Autowired
    private LoginDetailsService loginDetailsService;

	 @GetMapping("/todays-login-details/{operatorId}")
	    @ResponseBody
	    public ResponseEntity<List<LoginDetails>> getTodaysLoginDetails(@PathVariable Long operatorId) {
	        List<LoginDetails> todaysLoginDetails = loginDetailsService.getTodaysLoginDetailsByOperatorId(operatorId);
	        if (!todaysLoginDetails.isEmpty()) {
	            return ResponseEntity.ok(todaysLoginDetails);
	        } else {
	            return ResponseEntity.noContent().build();
	        }
	    }
	 
	 @GetMapping("/todays-login-details")
	    @ResponseBody
	    public ResponseEntity<List<LoginDetails>> getAllTodaysLoginDetails() {
	        List<LoginDetails> todaysLoginDetails = loginDetailsService.getAllTodaysLoginDetails();
	        if (!todaysLoginDetails.isEmpty()) {
	            return ResponseEntity.ok(todaysLoginDetails);
	        } else {
	            return ResponseEntity.noContent().build();
	        }
	    }
	 
	 //fetching data by operator id
	 @GetMapping("/login-details/{operatorId}")
	 @ResponseBody
	 public ResponseEntity<List<LoginDetails>> getLoginDetailsByOperatorId(@PathVariable Long operatorId) {
	     System.out.println("Received Operator ID: " + operatorId);  // Log the operator ID for debugging
	     List<LoginDetails> loginDetails = loginDetailsService.getLoginDetailsByOperatorId(operatorId);
	     if (!loginDetails.isEmpty()) {
	         return ResponseEntity.ok(loginDetails);
	     } else {
	         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	     }
	 }

     //fetching data by date
//	 @GetMapping("/login-details/date/{date}")
//	 public List<LoginDetails> getLoginDetailsByDate(@PathVariable String date) {
//	     // Logic to fetch login details for the specified date
//	     return loginDetailsService.getLoginDetailsByDate(date);
//	 }
	 
	 @GetMapping("/login-details/date/{date}")
	 public ResponseEntity<List<LoginDetails>> getLoginDetailsByDate(@PathVariable String date) {
	     try {
	         List<LoginDetails> details = loginDetailsService.getLoginDetailsByDate(date);
	         if (details.isEmpty()) {
	             return ResponseEntity.noContent().build(); // Return 204 No Content if no data is found
	         }
	         return ResponseEntity.ok(details);
	     } catch (Exception e) {
	         e.printStackTrace(); // Log the exception for debugging
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 Internal Server Error
	     }
	 }



	 
	 
}


