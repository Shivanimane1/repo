package com.tollplaza.TollPlaza.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tollplaza.TollPlaza.entity.LoginDetails;
import com.tollplaza.TollPlaza.entity.LoginRequest;
import com.tollplaza.TollPlaza.entity.Operators;
import com.tollplaza.TollPlaza.repository.LoginDetailsRepository;
import com.tollplaza.TollPlaza.service.OpService;

@Controller
public class OpLoginController {


//	@Autowired
//	private OpRepository opRepository;
	
	@Autowired
	private OpService opService;
	
	@Autowired
	private LoginDetailsRepository loginDetailsRepository;
	

	@GetMapping("/op_login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("operators", new Operators());
		return mav;
	}
	
	
	
	@PostMapping("/op_login")
	@ResponseBody
	public ResponseEntity<Map<String, String>> loginPage(@RequestBody LoginRequest loginRequest) {
	    Operators authenOp = opService.login(loginRequest.getEmail(), loginRequest.getPassword());
	    if (authenOp != null && authenOp.getPassword().equals(loginRequest.getPassword())) {
	        
	        // Create a new LoginDetails instance
	        LoginDetails loginDetails = new LoginDetails();
	        loginDetails.setLoginDate(LocalDate.now());

	        // Set the operator details manually
	        loginDetails.setLoginOperatorId(authenOp.getOpId());
	        loginDetails.setLoginOperatorName(authenOp.getName());
	        loginDetails.setLoginOperatorEmail(authenOp.getEmail());
	        loginDetails.setLoginOperatorBooth(authenOp.getBooth());

	        // Set the operator relationship
	        loginDetails.setOperator(authenOp);

	        // Save the login details to the database
	        loginDetailsRepository.save(loginDetails);

        // Prepare response with redirection URL
	        Map<String, String> response = new HashMap<>();
	        response.put("redirectUrl", "/newVehicleDetails/" + authenOp.getOpId()); // Redirect with operator ID
	        System.out.println("Operator Id is : " + authenOp.getOpId());
	        
	        return ResponseEntity.ok(response);
	    } else {
	        Map<String, String> response = new HashMap<>();
	        response.put("error", "Invalid username or password.");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	}


	
	
	
	
	
	

	//Operator temp
	@GetMapping("/newVehicleDetails")
	public String operatorboothLogin() {
		return "opbooth";
	}
}

	
//	@RequestMapping("/logout")
//	public ModelAndView logoutDo(HttpServletRequest request, HttpServletResponse response) {
//		// Perform logout actions (if any)
//		ModelAndView mav = new ModelAndView("redirect:/login");
//		return mav;
//	}



