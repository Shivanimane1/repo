package com.tollplaza.TollPlaza.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.Booth;
import com.tollplaza.TollPlaza.repository.BoothRepository;

@Service
public class BoothService {

	@Autowired
	private BoothRepository boothRepository;

	public Booth saveBooth(Booth booth) {
		return boothRepository.save(booth);
	}

	public Optional<Booth> getoperbyid(Long opId) {

		return null;
	}

	public List<Booth> getBoothsByOperatorId(Long opId) {
		return boothRepository.findByOperatorsOpId(opId);
	}

	/*---------------------------------------------------------------*/

	public List<Booth> getTodayBoothsByOperatorId(Long opId) {
		return boothRepository.findTodayBoothsByOperatorId(opId);
	}

	

	public Map<String, Object> getVehicleCountsByDate(LocalDate date) {
		List<Booth> booths = boothRepository.findByDate(date);
		Map<String, Object> result = new HashMap<>();
		int singleCount = 0;
		int returnCount = 0;
		double singleAmount = 0.0;
		double returnAmount = 0.0;

		for (Booth booth : booths) {
			if ("single".equalsIgnoreCase(booth.getReturnType())) {
				singleCount++;
				singleAmount += booth.getAmount();
			} else if ("return".equalsIgnoreCase(booth.getReturnType())) {
				returnCount++;
				returnAmount += booth.getAmount();
			}
		}

		result.put("singleCount", singleCount);
		result.put("returnCount", returnCount);
		result.put("singleAmount", singleAmount);
		result.put("returnAmount", returnAmount);

		return result;
	}
/*----------------------------------------------------------------------------------------------------*/

		
	public List<Map<String, Object>> getVehicleDetailsByDate(LocalDate date) {
	    List<Booth> booths = boothRepository.findByDate(date);

	    // Log fetched booth data for debugging
	    booths.forEach(booth -> {
	        System.out.println("Fetched Booth Data: " + booth.getVehicleType());
	    });

	    Map<String, Map<String, Object>> vehicleDetails = new HashMap<>();

	    for (Booth booth : booths) {
	        String vehicleType = booth.getVehicleType();  // Fetch vehicleType here
	        double rate = booth.getAmount();
	        String returnType = booth.getReturnType();

	        if (vehicleType == null) {
	            System.out.println("Null vehicleType found for booth ID: " + booth.getId());
	            continue;  // Skip this iteration if vehicleType is null
	        }

	        if (!vehicleDetails.containsKey(vehicleType)) {
	            vehicleDetails.put(vehicleType, new HashMap<>() {{
	                put("vehicleType", vehicleType);  // Explicitly put vehicleType in the map
	                put("singleRate", 0.0);
	                put("singleCount", 0);
	                put("singleAmount", 0.0);
	                put("returnRate", 0.0);
	                put("returnCount", 0);
	                put("returnAmount", 0.0);
	            }});
	        }

	        Map<String, Object> details = vehicleDetails.get(vehicleType);

	        if ("return".equals(returnType)) {
	            details.put("returnRate", rate);
	            details.put("returnCount", (Integer) details.get("returnCount") + 1);
	            details.put("returnAmount", (Double) details.get("returnAmount") + rate);
	        } else {
	            details.put("singleRate", rate);
	            details.put("singleCount", (Integer) details.get("singleCount") + 1);
	            details.put("singleAmount", (Double) details.get("singleAmount") + rate);
	        }
	    }

	    return vehicleDetails.values().stream()
	        .map(details -> new HashMap<String, Object>() {{
	            // Ensure vehicleType is included in the final map
	            put("vehicleType", details.get("vehicleType"));
	            put("singleRate", details.get("singleRate"));
	            put("singleCount", details.get("singleCount"));
	            put("singleAmount", details.get("singleAmount"));
	            put("returnRate", details.get("returnRate"));
	            put("returnCount", details.get("returnCount"));
	            put("returnAmount", details.get("returnAmount"));
	        }}).collect(Collectors.toList());
	}
	
	
//	public List<Booth> getVehiclesByBooth(String booth_no) {
//	 return boothRepository.findByBoothNo(booth_no);
//}

	 public List<Booth> getVehiclesByBoothNo(String boothNo) {
	        return boothRepository.findByBoothNo(boothNo);
	    }

	 //-----------------------------------------------------------------
	 
	 
	 
	 public List<Map<String, Object>> getVehicleDetailsByDateAndBooth(LocalDate date, String boothNo) {
	        return boothRepository.findVehicleDetailsByDateAndBooth(date, boothNo);
	    }

	 public List<Booth> getVehiclesByDateAndReturnType(LocalDate date, String returnType) {
		    return boothRepository.findByDateAndReturnType(date, returnType);
		}

	 public List<Booth> getVehiclesByDateBoothReturnType(LocalDate date, String boothNo, String returnType) {
	        return boothRepository.findByDateAndBoothNoAndReturnType(date, boothNo, returnType);
	    }

	 
	 public List<Map<String, Object>> getVehicleDetailsByOperatorIdAndDate(Long opId, LocalDate date) {
		    List<Booth> booths = boothRepository.findByOperatorsOpIdAndDate(opId, date);

		    // Transform Booth objects to a map for response
		    return booths.stream().map(booth -> {
		        Map<String, Object> boothDetails = new HashMap<>();
		        boothDetails.put("vehicleNumber", booth.getVehicleNumber());
		        boothDetails.put("vehicleType", booth.getVehicleType());
		        boothDetails.put("returnType", booth.getReturnType());
		        boothDetails.put("amount", booth.getAmount());
		        boothDetails.put("boothNo", booth.getBoothNo());
		        boothDetails.put("date", booth.getDate());
		        return boothDetails;
		    }).collect(Collectors.toList());
		}

	 



	 public List<Map<String, Object>> getVehicleDetailsByOperatorAndBooth(Long opId, String boothNo) {
		    List<Booth> booths = boothRepository.findByOperatorsOpIdAndBoothNo(opId, boothNo);

		    return booths.stream().map(booth -> {
		        Map<String, Object> vehicleMap = new HashMap<>();
		        vehicleMap.put("id", booth.getId());
		        vehicleMap.put("date", booth.getDate());
		        vehicleMap.put("vehicleNumber", booth.getVehicleNumber());
		        vehicleMap.put("vehicleType", booth.getVehicleType());
		        vehicleMap.put("returnType", booth.getReturnType());
		        vehicleMap.put("amount", booth.getAmount());
		        vehicleMap.put("boothNo", booth.getBoothNo());
		        return vehicleMap;
		    }).collect(Collectors.toList());
		}

	
	 public List<Map<String, Object>> getVehicleDetailsByOperatorIdAndReturnType(Long opId, String returnType) {
	        List<Booth> booths = boothRepository.findByOperatorsOpIdAndReturnType(opId, returnType);

	        // Map the list of Booth entities to a list of Maps with desired details
	        return booths.stream().map(booth -> {
	            Map<String, Object> details = new HashMap<>();
	            details.put("vehicleNumber", booth.getVehicleNumber());
	            details.put("vehicleType", booth.getVehicleType());
	            details.put("returnType", booth.getReturnType());
	            details.put("amount", booth.getAmount());
	            details.put("date", booth.getDate());
	            details.put("boothNo", booth.getBoothNo());
	            return details;
	        }).collect(Collectors.toList());
	    }
	 
	 
	 
	 public List<Map<String, Object>> getVehicleDetailsByOperatorIdDateAndBooth(Long opId, LocalDate date, String boothNo) {
	        List<Booth> booths = boothRepository.findByOperatorsOpIdAndDateAndBoothNo(opId, date, boothNo);

	        // Map the list of Booth entities to a list of Maps with the desired details
	        return booths.stream().map(booth -> {
	            Map<String, Object> details = new HashMap<>();
	            details.put("vehicleNumber", booth.getVehicleNumber());
	            details.put("vehicleType", booth.getVehicleType());
	            details.put("returnType", booth.getReturnType());
	            details.put("amount", booth.getAmount());
	            details.put("date", booth.getDate());
	            details.put("boothNo", booth.getBoothNo());
	            return details;
	        }).collect(Collectors.toList());
	    }
	 
	 
	 public List<Map<String, Object>> getVehicleDetailsByOperatorIdDateAndReturnType(Long opId, LocalDate date, String returnType) {
	        List<Booth> booths = boothRepository.findByOperatorsOpIdAndDateAndReturnType(opId, date, returnType);

	        // Map the list of Booth entities to a list of Maps with desired details
	        return booths.stream().map(booth -> {
	            Map<String, Object> details = new HashMap<>();
	            details.put("vehicleNumber", booth.getVehicleNumber());
	            details.put("vehicleType", booth.getVehicleType());
	            details.put("returnType", booth.getReturnType());
	            details.put("amount", booth.getAmount());
	            details.put("date", booth.getDate());
	            details.put("boothNo", booth.getBoothNo());
	            return details;
	        }).collect(Collectors.toList());
	    }
	 
	 
	 
	 public List<Map<String, Object>> getVehicleDetailsByOperatorBoothReturnType(Long opId, String boothNo, String returnType) {
	        List<Booth> booths = boothRepository.findByOperatorsOpIdAndBoothNoAndReturnType(opId, boothNo, returnType);

	        // Map the list of Booth entities to a list of Maps with desired details
	        return booths.stream().map(booth -> {
	            Map<String, Object> details = new HashMap<>();
	            details.put("vehicleNumber", booth.getVehicleNumber());
	            details.put("vehicleType", booth.getVehicleType());
	            details.put("returnType", booth.getReturnType());
	            details.put("amount", booth.getAmount());
	            details.put("date", booth.getDate());
	            details.put("boothNo", booth.getBoothNo());
	            return details;
	        }).collect(Collectors.toList());
	    }




	 public List<Map<String, Object>> getVehiclesByOperatorDateBoothReturnType(Long opId, LocalDate date, String boothNo, String returnType) {
	        List<Booth> booths = boothRepository.findByOperatorsOpIdAndDateAndBoothNoAndReturnType(opId, date, boothNo, returnType);

	        // Map the list of Booth entities to a list of Maps with the desired details
	        return booths.stream().map(booth -> {
	            Map<String, Object> details = new HashMap<>();
	            details.put("vehicleNumber", booth.getVehicleNumber());
	            details.put("vehicleType", booth.getVehicleType());
	            details.put("returnType", booth.getReturnType());
	            details.put("amount", booth.getAmount());
	            details.put("date", booth.getDate());
	            details.put("boothNo", booth.getBoothNo());
	            return details;
	        }).collect(Collectors.toList());
	    }

	 
	


}