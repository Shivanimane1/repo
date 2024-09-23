package com.tollplaza.TollPlaza.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tollplaza.TollPlaza.entity.Booth;
import com.tollplaza.TollPlaza.repository.BoothRepository;

@Service
public class BoothService {

	@Autowired
	private BoothRepository boothRepository;

	public Booth saveBooth(Booth booth) {
	  return boothRepository.save(booth); }
	 



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
	 
	
	
}