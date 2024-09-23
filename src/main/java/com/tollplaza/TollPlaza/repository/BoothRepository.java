package com.tollplaza.TollPlaza.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.Booth;

@Repository
public interface BoothRepository extends JpaRepository<Booth, Integer> {

//	List<Booth> findTop10ByOrderByIdDesc();
//	
//	List<Booth> findByOperatorsId(Long opId);
//	
//	List<Booth> findByDate(LocalDate localDate);
//
//	//<Booth> findByDateAndOperatorId(LocalDate today, Long opId);
//	
//	 @Query("SELECT b FROM Booth b WHERE b.operators.opId = :opId AND b.date = CURRENT_DATE")
//	    List<Booth> findTodayBoothsByOperatorId(@Param("opId") Long opId);
//	
//		 List<Booth> findByOperatorsOpId(Long opId);


	  List<Booth> findTop10ByOrderByIdDesc();
	    
	    List<Booth> findByOperatorsOpId(Long opId);
	    
	    List<Booth> findByDate(LocalDate localDate);

	    @Query("SELECT b FROM Booth b WHERE b.operators.opId = :opId AND b.date = CURRENT_DATE")
	    List<Booth> findTodayBoothsByOperatorId(@Param("opId") Long opId);

}

