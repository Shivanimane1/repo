package com.tollplaza.TollPlaza.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.Booth;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Integer> {

	  List<Booth> findTop10ByOrderByIdDesc();
	    
	    List<Booth> findByOperatorsOpId(Long opId);
	    
	    List<Booth> findByDate(LocalDate localDate);

	    @Query("SELECT b FROM Booth b WHERE b.operators.opId = :opId AND b.date = CURRENT_DATE")
	    List<Booth> findTodayBoothsByOperatorId(@Param("opId") Long opId);

		
	    
	    List<Booth> findByBoothNo(String boothNo);

		List<Booth> findByReturnType(String returnType);

		
		
		
		//----------------------------------------------------
		
		@Query("SELECT new map(b.vehicleType as vehicleType, b.returnType as returnType, b.amount as amount) " +
		           "FROM Booth b WHERE b.date = :date AND b.boothNo = :boothNo")
		    List<Map<String, Object>> findVehicleDetailsByDateAndBooth(@Param("date") LocalDate date, @Param("boothNo") String boothNo);

		 List<Booth> findByDateAndReturnType(LocalDate date, String returnType);

		List<Booth> findByBoothNoAndReturnType(String boothNo, String returnType);

		List<Booth> findByDateAndBoothNoAndReturnType(LocalDate date, String boothNo, String returnType);

		 List<Booth> findByOperatorsOpIdAndDate(Long opId, LocalDate date);
		
		 List<Booth> findByOperatorsOpIdAndBoothNo(Long opId, String boothNo);
		 
		 List<Booth> findByOperatorsOpIdAndReturnType(Long opId, String returnType);
		 
		 List<Booth> findByOperatorsOpIdAndDateAndBoothNo(Long opId, LocalDate date, String boothNo);
		 
		 List<Booth> findByOperatorsOpIdAndDateAndReturnType(Long opId, LocalDate date, String returnType);
		 
		 List<Booth> findByOperatorsOpIdAndBoothNoAndReturnType(Long opId, String boothNo, String returnType);
		 
		 List<Booth> findByOperatorsOpIdAndDateAndBoothNoAndReturnType(Long opId, LocalDate date, String boothNo, String returnType);

}

