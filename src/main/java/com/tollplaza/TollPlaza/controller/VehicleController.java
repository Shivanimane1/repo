package com.tollplaza.TollPlaza.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tollplaza.TollPlaza.entity.Vehicles;
import com.tollplaza.TollPlaza.repository.VehicleRepository;
import com.tollplaza.TollPlaza.service.VehicleService;

@Controller
public class VehicleController {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleService vehicleService;

	@GetMapping("/vehicle_register")
	public String vehicleRegister() {
		return "vehicleRegister";
	}

	@GetMapping("/vehicleList")
	public String vehicleList() {
		return "vehicleList";
	}

	@GetMapping("/fragmentPage")
	public String fragment() {
		return "fragment";
	}

	@PostMapping("/save_vehicle")
	@ResponseBody
	public ResponseEntity<String> addVehicle(@RequestBody Vehicles v) {
		vehicleService.save(v);
		return new ResponseEntity<>("Vehicle added successfully", HttpStatus.OK);
	}

	@GetMapping("/get_vehicles")
	@ResponseBody
	public List<Vehicles> getAllVehicles() {
		return vehicleService.getAllVehicles();
	}

	@GetMapping("/get_vehicle/{id}")
    @ResponseBody
    public ResponseEntity<Vehicles> getVehicleById(@PathVariable Long id) {
        Optional<Vehicles> veh = vehicleRepository.findById(id);
        if (veh.isPresent()) {
            return new ResponseEntity<>(veh.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PutMapping("/update_vehicle/{id}")
    @ResponseBody
    public ResponseEntity<String> updateVehicle(@PathVariable Long id, @RequestBody Vehicles updatedVehicle) {
        Optional<Vehicles> existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle.isPresent()) {
            Vehicles vehicle = existingVehicle.get();
            vehicle.setVehicleType(updatedVehicle.getVehicleType());
            vehicle.setSingle_amt(updatedVehicle.getSingle_amt());
            vehicle.setReturn_amt(updatedVehicle.getReturn_amt());
            vehicleRepository.save(vehicle);
            return new ResponseEntity<>("Vehicle updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
        }
    }
	
	

	    @DeleteMapping("/delete_vehicle/{id}")
	    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
	        try {
	            // Your service to delete the vehicle by id
	            vehicleService.deleteVehicleById(id);
	            return ResponseEntity.ok("Vehicle deleted successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
	        }
	    }
	
	
	  @PostMapping("/getAmount")
	    public ResponseEntity<Map<String, Object>> getAmount(@RequestBody Map<String, String> requestData) {
	        String vehicleType = requestData.get("vehicleType");
	        String returnType = requestData.get("returnType");

	        Optional<Vehicles> vehicle = vehicleRepository.findByVehicleType(vehicleType);
	        if (vehicle.isPresent()) {
	            Map<String, Object> response = new HashMap<>();
	            if ("single".equals(returnType)) {
	                response.put("amount", vehicle.get().getSingle_amt());
	            } else if ("return".equals(returnType)) {
	                response.put("amount", vehicle.get().getReturn_amt());
	            }
	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	
	  @GetMapping("/vehicleTypes")
	  @ResponseBody
	  public List<String> getVehicleTypes() {
	      return vehicleService.getAllVehicleTypes();
	  }
	  
	  @GetMapping("/dailyReport")
		public String dailyReport() {
			return "dailyReport";
		}
	  
	  @GetMapping("/reports")
		public String reports() {
			return "reports";
		}
	 
	 
}
