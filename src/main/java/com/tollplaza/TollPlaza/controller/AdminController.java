package com.tollplaza.TollPlaza.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.tollplaza.TollPlaza.entity.LoginRequest;
import com.tollplaza.TollPlaza.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/first")
    public String first() {
        return "first";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String indexPage(HttpSession session) {
        // Retrieve the loggedInUser attribute from the session
        LoginRequest loggedInUser = (LoginRequest) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // If no user is logged in, redirect to the login page
            return "redirect:/login";
        }
        // If a user is logged in, display the index page
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> loginPage(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // Authenticate the user
        LoginRequest authenAdmin = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (authenAdmin != null && authenAdmin.getPassword().equals(loginRequest.getPassword())) {
            // Authentication successful, store user in session
            session.setAttribute("loggedInUser", authenAdmin);

            // Prepare a response with the redirect URL
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/index"); // Redirect to index page upon successful login

            return ResponseEntity.ok(response);
        } else {
            // Authentication failed, return an error message
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid username or password.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @RequestMapping("/logout")
    public ModelAndView logoutDo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // Invalidate the session to log the user out
        session.invalidate();

        // Redirect to the first page
        return new ModelAndView("redirect:/first");
    }
}




















//package com.tollplaza.TollPlaza.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.tollplaza.TollPlaza.entity.LoginRequest;
//import com.tollplaza.TollPlaza.service.AdminService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@Controller
//public class AdminController {
//
//	@Autowired
//	private AdminService adminService;
//
//	@GetMapping("/first")
//	public String first() {
//		return "first";
//	}
//
//	@GetMapping("/login")
//	public String adminLogin() {
//		return "login";
//	}
//
////	@GetMapping("/index")
////	public String indexPage() {
////		return "index";
////	}
//	
//	@GetMapping("/index")
//	public String indexPage(HttpSession session) {
//		LoginRequest loggedInUser = (LoginRequest) session.getAttribute("loggedInUser");
//		if (loggedInUser == null) {
//			return "redirect:/login";
//		}
//		return "index";
//	}
//
//
//	
//	
//	@PostMapping("/login")
//	@ResponseBody
//	public ResponseEntity<Map<String, String>> loginPage(@RequestBody LoginRequest loginRequest) {
//	    LoginRequest authenAdmin = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());
//	    if (authenAdmin != null && authenAdmin.getPassword().equals(loginRequest.getPassword())) {
//
//	        // Authentication successful, return redirect URL
//
//	        Map<String, String> response = new HashMap<>();
//
//	        response.put("redirectUrl", "/index"); // Redirect to index page upon successful login
//
//	        return ResponseEntity.ok(response);
//
//	    } else {
//
//	        // Authentication failed, return error message
//
//	        Map<String, String> response = new HashMap<>();
//
//	        response.put("error", "Invalid username or password.");
//
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//
//	    }
//
//	}
//	
//
//	
//	@RequestMapping("/logout")
//	public ModelAndView logoutDo(HttpServletRequest request, HttpServletResponse response) {
//		// Perform logout actions (if any)
//		ModelAndView mav = new ModelAndView("redirect:/first");
//		return mav;
//	}
//}
//
