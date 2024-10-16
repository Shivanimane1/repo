package com.tollplaza.TollPlaza.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
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
		return "overallReport"; 
	}

	@GetMapping("/receipt")
	public String receipt() {
		return "receipt";
	}

	@GetMapping("/ticket")
	public String ticket() {
		return "ticket";
	}

	
//	@PostMapping("/saveVehicle/{opId}")
//	public ResponseEntity<Booth> saveVehicle(@RequestBody Booth booth, @PathVariable Long opId) {
//		Optional<Operators> operator = opRepository.findById(opId);
//		if (operator.isPresent()) {
//			booth.setOperators(operator.get());
//			Booth savedBooth = boothRepository.save(booth);
//			return ResponseEntity.ok(savedBooth);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		}
//	}
	
	@PostMapping("/saveVehicle/{opId}")
	public ResponseEntity<Booth> saveVehicle(@RequestBody Booth booth, @PathVariable Long opId) {
	    Optional<Operators> operator = opRepository.findById(opId);
	    if (operator.isPresent()) {
	        booth.setOperators(operator.get());
	        booth.setBoothNo(operator.get().getBoothNo());// Set boothNo from Operators
	        Booth savedBooth = boothRepository.save(booth);
	        return ResponseEntity.ok(savedBooth);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}

	
	


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
	
	
	@GetMapping("/vehiclesByDate/{date}")
	public ResponseEntity<?> getVehiclesByDate(@PathVariable("date") String date) {
	    try {
	        LocalDate selectedDate = LocalDate.parse(date);
	        List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByDate(selectedDate);
	        return ResponseEntity.ok(vehicleDetails);
	    } catch (DateTimeParseException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("Invalid date format. Please use the format 'yyyy-MM-dd'.");
	    } 
	}

	

	
	 @GetMapping("/vehiclesByBooth/{boothNo}")
	    public ResponseEntity<List<Booth>> getVehiclesByBoothNo(@PathVariable String boothNo) {
	        List<Booth> booths = boothService.getVehiclesByBoothNo(boothNo);
	        if (booths.isEmpty()) {
	            return ResponseEntity.noContent().build(); // Return 204 if no data found
	        }
	        return ResponseEntity.ok(booths);
	    }


	 
	  @GetMapping("/returnType/{returnType}")
	  @ResponseBody
	    public List<Booth> getBoothByReturnType(@PathVariable String returnType) {
	        return boothRepository.findByReturnType(returnType);
	    }

	//--------------fetching data by 2 entities
	  
	  @GetMapping("/vehiclesByOperatorIdAndDate/{opId}/{date}")
	  public ResponseEntity<?> getVehiclesByOperatorIdAndDate(@PathVariable Long opId, @PathVariable String date) {
	      try {
	          // Parse the date string
	          LocalDate selectedDate = LocalDate.parse(date);

	          // Call the service method to fetch data
	          List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorIdAndDate(opId, selectedDate);

	          // Check if the data is empty
	          if (vehicleDetails.isEmpty()) {
	              return ResponseEntity.noContent().build(); // Return 204 if no data found
	          }

	          // Return the fetched data
	          return ResponseEntity.ok(vehicleDetails);

	      } catch (DateTimeParseException e) {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'yyyy-MM-dd'.");
	      }
	  }
	  
	  
	  @GetMapping("/vehiclesByOperatorAndBooth/{opId}/{boothNo}")
	  public ResponseEntity<?> getVehiclesByOperatorAndBooth(@PathVariable Long opId, @PathVariable String boothNo) {
	      Optional<Operators> operator = opRepository.findById(opId);
	      if (operator.isPresent()) {
	          List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorAndBooth(opId, boothNo);
	          if (vehicleDetails.isEmpty()) {
	              return ResponseEntity.noContent().build(); // Return 204 if no data found
	          }
	          return ResponseEntity.ok(vehicleDetails);
	      } else {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found.");
	      }
	  }
	  
	  
	  @GetMapping("/vehiclesByOperatorIdAndReturnType/{opId}/{returnType}")
	  public ResponseEntity<?> getVehiclesByOperatorIdAndReturnType(@PathVariable Long opId, @PathVariable String returnType) {
	      Optional<Operators> operator = opRepository.findById(opId);
	      if (operator.isPresent()) {
	          List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorIdAndReturnType(opId, returnType);
	          if (vehicleDetails.isEmpty()) {
	              return ResponseEntity.noContent().build(); // Return 204 if no data found
	          }
	          return ResponseEntity.ok(vehicleDetails);
	      } else {
	          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found.");
	      }
	  }
	  
	  
	  @GetMapping("/vehiclesByDateAndBooth/{date}/{boothNo}")
	  public ResponseEntity<?> getVehiclesByDateAndBooth(@PathVariable("date") String date, @PathVariable("boothNo") String boothNo) {
	      try {
	          LocalDate selectedDate = LocalDate.parse(date);
	          List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByDateAndBooth(selectedDate, boothNo);
	          if (vehicleDetails.isEmpty()) {
	              return ResponseEntity.noContent().build(); // Return 204 if no data found
	          }
	          return ResponseEntity.ok(vehicleDetails);
	      } catch (DateTimeParseException e) {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'yyyy-MM-dd'.");
	      }
	  }

	  
	  

	  @GetMapping("/vehiclesByDateAndReturnType/{date}/{returnType}")
	  public ResponseEntity<?> getVehiclesByDateAndReturnType(@PathVariable("date") String date, @PathVariable("returnType") String returnType) {
	      try {
	          LocalDate selectedDate = LocalDate.parse(date);
	          List<Booth> vehicles = boothService.getVehiclesByDateAndReturnType(selectedDate, returnType);
	          if (vehicles.isEmpty()) {
	              return ResponseEntity.noContent().build(); 
	          }
	          return ResponseEntity.ok(vehicles);
	      } catch (DateTimeParseException e) {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                  .body("Invalid date format. Please use the format 'yyyy-MM-dd'.");
	      }
	  }

	  
	  @GetMapping("/vehiclesByBoothAndReturnType/{boothNo}/{returnType}")
	  public ResponseEntity<List<Booth>> getVehiclesByBoothAndReturnType(
	          @PathVariable String boothNo,
	          @PathVariable String returnType) {
	      List<Booth> booths = boothRepository.findByBoothNoAndReturnType(boothNo, returnType);
	      if (booths.isEmpty()) {
	          return ResponseEntity.noContent().build(); 
	      }
	      return ResponseEntity.ok(booths);
	  }

	 
	  
	  
	  
	  
	//--------------fetching data by 3 entities
	  
	  @GetMapping("/getVehiclesData/{date}/{boothNo}/{returnType}")
	    public ResponseEntity<List<Booth>> getVehiclesDataByDateBoothReturnType(
	            @PathVariable("date") String date,
	            @PathVariable("boothNo") String boothNo,
	            @PathVariable("returnType") String returnType) {
	        
	        try {
	            LocalDate selectedDate = LocalDate.parse(date);
	            List<Booth> vehicles = boothService.getVehiclesByDateBoothReturnType(selectedDate, boothNo, returnType);
	            if (vehicles.isEmpty()) {
	                return ResponseEntity.noContent().build(); // Return 204 if no data found
	            }
	            return ResponseEntity.ok(vehicles);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(null);
	        }
	    }

	  
	  
	  @GetMapping("/vehiclesByOperatorIdDateAndBooth/{opId}/{date}/{boothNo}")
	  public ResponseEntity<?> getVehiclesByOperatorIdDateAndBooth(@PathVariable Long opId, @PathVariable String date, @PathVariable String boothNo) {
	      try {
	          // Parse the date string into a LocalDate object
	          LocalDate selectedDate = LocalDate.parse(date);

	          // Check if the operator exists
	          Optional<Operators> operator = opRepository.findById(opId);
	          if (operator.isPresent()) {
	              // Fetch the vehicle details by operatorId, date, and boothNo
	              List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorIdDateAndBooth(opId, selectedDate, boothNo);

	              // Return 204 if no data is found
	              if (vehicleDetails.isEmpty()) {
	                  return ResponseEntity.noContent().build(); 
	              }

	              // Return the vehicle details
	              return ResponseEntity.ok(vehicleDetails);
	          } else {
	              // Return a 404 if the operator is not found
	              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found.");
	          }

	      } catch (DateTimeParseException e) {
	          // Return a 400 if the date format is invalid
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'yyyy-MM-dd'.");
	      }
	  }
	  
	  
	  
	  @GetMapping("/vehiclesByOperatorIdDateAndReturnType/{opId}/{date}/{returnType}")
	    public ResponseEntity<?> getVehiclesByOperatorIdDateAndReturnType(
	            @PathVariable Long opId,
	            @PathVariable String date,
	            @PathVariable String returnType) {
	        try {
	            // Parse the date string
	            LocalDate selectedDate = LocalDate.parse(date);

	            // Call the service method to fetch data
	            List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorIdDateAndReturnType(opId, selectedDate, returnType);

	            // Check if the data is empty
	            if (vehicleDetails.isEmpty()) {
	                return ResponseEntity.noContent().build(); // Return 204 if no data found
	            }

	            // Return the fetched data
	            return ResponseEntity.ok(vehicleDetails);

	        } catch (DateTimeParseException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'yyyy-MM-dd'.");
	        }
	    }
	  
	  
	  @GetMapping("/vehiclesByOperatorBoothReturnType/{opId}/{boothNo}/{returnType}")
	    public ResponseEntity<?> getVehiclesByOperatorBoothReturnType(@PathVariable Long opId, @PathVariable String boothNo, @PathVariable String returnType) {
	        Optional<Operators> operator = opRepository.findById(opId);
	        if (operator.isPresent()) {
	            List<Map<String, Object>> vehicleDetails = boothService.getVehicleDetailsByOperatorBoothReturnType(opId, boothNo, returnType);
	            if (vehicleDetails.isEmpty()) {
	                return ResponseEntity.noContent().build(); // Return 204 if no data found
	            }
	            return ResponseEntity.ok(vehicleDetails);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Operator not found.");
	        }
	    }
	  
	  

	  
	//--------------fetching data by all 4 entities


	  @GetMapping("/vehiclesByOperatorDateBoothReturnType/{opId}/{date}/{boothNo}/{returnType}")
	  public ResponseEntity<?> getVehiclesByOperatorDateBoothReturnType(
	          @PathVariable Long opId, 
	          @PathVariable String date, 
	          @PathVariable String boothNo, 
	          @PathVariable String returnType) {
	      try {
	          // Parse the date string
	          LocalDate selectedDate = LocalDate.parse(date);

	          // Call the service method to fetch data
	          List<Map<String, Object>> vehicleDetails = boothService.getVehiclesByOperatorDateBoothReturnType(opId, selectedDate, boothNo, returnType);

	          // Check if the data is empty
	          if (vehicleDetails.isEmpty()) {
	              return ResponseEntity.noContent().build(); // Return 204 if no data found
	          }

	          // Return the fetched data
	          return ResponseEntity.ok(vehicleDetails);

	      } catch (DateTimeParseException e) {
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please use 'yyyy-MM-dd'.");
	      }
	  }


	 
}
