package com.tollplaza.TollPlaza.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tollplaza.TollPlaza.entity.Booth;
import com.tollplaza.TollPlaza.entity.Operators;
import com.tollplaza.TollPlaza.repository.BoothRepository;
import com.tollplaza.TollPlaza.repository.OpRepository;
import com.tollplaza.TollPlaza.service.BoothService;

@Controller
public class BoothController {

	@Autowired
	private BoothRepository boothRepository;
	
	 @Autowired
	    private BoothService boothService;
	 
	 @Autowired 
	 private OpRepository opRepository;
	 
	 

		@GetMapping("/dailyCollection")
		public String dailyCollection() {
			return "dailyCollection";
		}
		
		@GetMapping("/overallReport")
		public String showReportsPage() {
		    return "overallReport";  // Ensure the template is named 'overallReport.html'
		}

		
		@GetMapping("/receipt")
		public String receipt() {
			return "receipt";
		}
		
		@GetMapping("/ticket")
		public String ticket() {
			return "ticket";
		}




	 @PostMapping("/saveVehicle/{opId}")
	    public ResponseEntity<Booth> saveVehicle(@RequestBody Booth booth, @PathVariable Long opId) {
	        Optional<Operators> operator = opRepository.findById(opId);
	        if (operator.isPresent()) {
	            booth.setOperators(operator.get());
	            Booth savedBooth = boothRepository.save(booth);
	            return ResponseEntity.ok(savedBooth);
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        }
	    }
	 
		/*
		 * @GetMapping("/getVehiclesByOperator/{opId}") public
		 * ResponseEntity<List<Booth>> getBoothsByOperatorId(@PathVariable Long opId) {
		 * List<Booth> booths = boothService.getBoothsByOperatorId(opId); if
		 * (booths.isEmpty()) { return ResponseEntity.noContent().build(); } else {
		 * return ResponseEntity.ok(booths); } }
		 */
	 

	 @GetMapping("/getVehiclesByOperator/{opId}")
	    public ResponseEntity<List<Booth>> getBoothsByOperatorId(@PathVariable Long opId) {
	        List<Booth> booths = boothService.getBoothsByOperatorId(opId);
	        if (booths.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.ok(booths);
	        }
	    }
	 
	 
	@GetMapping("/getAllVehicles")
	@ResponseBody
	public List<Booth> getAllVehicles() {
		return boothRepository.findAll();
	}

	
	 @GetMapping("/getRecentVehicles") 
	 public List<Booth> getRecentVehicles() {
	 return boothRepository.findTop10ByOrderByIdDesc();
	 }
	 

	

	@PostMapping("/newVehiclePassed")
	public Booth createBooth(@RequestBody Booth boothRequest) {
		return boothService.saveBooth(boothRequest);
	}	
	
	/*******************************************/
	
	 @GetMapping("/getTodayVehiclesByOperator/{opId}")
	    public ResponseEntity<List<Booth>> getTodayBoothsByOperatorId(@PathVariable Long opId) {
	        List<Booth> booths = boothService.getTodayBoothsByOperatorId(opId);
	        if (booths.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.ok(booths);
	        }
	    }	 
}

