package com.tollplaza.TollPlaza.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.Vehicles;
import com.tollplaza.TollPlaza.repository.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	private VehicleRepository vehicleRepository;

	public Vehicles save(Vehicles vehicles) {
		return vehicleRepository.save(vehicles);
		
	}

	public List<Vehicles> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	public List<String> getAllVehicleTypes() {
		 return vehicleRepository.findAll()
                 .stream()
                 .map(Vehicles::getVehicleType)
                 .distinct()
                 .collect(Collectors.toList());
	}
	
	
	public void deleteVehicleById(Long id) {
		vehicleRepository.deleteById(id);
	
		
	}

//	 public List<Vehicles> getVehiclesByDate(String date) {
//	        // Assuming you have a method in your repository to find vehicles by date
//	        return vehicleRepository.findByDate(date);
//	    }

	
	
	
}
