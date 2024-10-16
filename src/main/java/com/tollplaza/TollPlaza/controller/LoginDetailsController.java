package com.tollplaza.TollPlaza.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tollplaza.TollPlaza.entity.Booth;
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

	 
	 
	 @GetMapping("/login-details/{operatorId}")
	 @ResponseBody
	 public ResponseEntity<Map<String, Object>> getLoginDetailsByOperatorId(
	         @PathVariable Long operatorId) {
	     
	     System.out.println("Received Operator ID: " + operatorId);

	     List<LoginDetails> loginDetails = loginDetailsService.getLoginDetailsByOperatorId(operatorId);

	     if (!loginDetails.isEmpty()) {
	         long totalVehicles = loginDetails.size();
	         long singleVehicles = loginDetails.stream()
	                                           .filter(ld -> "single".equals(ld.getReturnType()))
	                                           .count();
	         long returnVehicles = loginDetails.stream()
	                                           .filter(ld -> "return".equals(ld.getReturnType()))
	                                           .count();

	         // Fetch booth data through LoginDetails
	         List<Map<String, Object>> vehicleDetails = loginDetails.stream().map(ld -> {
	             Booth booth = ld.getBooth();
	             Map<String, Object> vehicleInfo = new HashMap<>();
	             
	             // Fetching vehicleType from Booth and other vehicle-related details
	             vehicleInfo.put("vehicleType", booth.getVehicleType());
	             vehicleInfo.put("vehicleNumber", booth.getVehicleNumber());
	             vehicleInfo.put("singleVehicleCount", singleVehicles);
	             vehicleInfo.put("returnVehicleCount", returnVehicles);
	             
	             // Using booth amount for rates
	             if ("single".equals(ld.getReturnType())) {
	                 vehicleInfo.put("singleAmount", singleVehicles * booth.getAmount());
	             } else if ("return".equals(ld.getReturnType())) {
	                 vehicleInfo.put("returnAmount", returnVehicles * booth.getAmount());
	             }
	             
	             return vehicleInfo;
	         }).toList();

	         // Prepare the response
	         Map<String, Object> response = new HashMap<>();
	         response.put("vehicleDetails", vehicleDetails);
	         response.put("totalVehicles", totalVehicles);
	         response.put("singleVehicles", singleVehicles);
	         response.put("returnVehicles", returnVehicles);

	         return ResponseEntity.ok(response);
	     } else {
	         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	     }
	 }



	 @GetMapping("/getVehiclesDetailsById/{opId}")
	    public ResponseEntity<List<Booth>> getBoothsDetailsByOperatorId(@PathVariable Long opId) {
	        List<Booth> booths = loginDetailsService.getBoothDeatilsById(opId);
	        if (booths.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.ok(booths);
	        }
	    }

}


