package com.tollplaza.TollPlaza.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.LoginDetails;

@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Long> {
	

    // Custom method to find LoginDetails by operator ID
    List<LoginDetails> findByOperator_OpIdAndLoginDate(Long operatorId, LocalDate loginDate);
    
//    List<LoginDetails> findByLoginDate(LocalDate loginDate);
    
    @Query(nativeQuery = true, value = "SELECT * FROM (" +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY operator_id ORDER BY id DESC) as rn" +
            "    FROM operator.login_details" +
            ") as subquery " +
            "WHERE rn = 1 AND login_date = :today")
    List<LoginDetails> findByLoginDate(@Param("today") LocalDate today);

	
    
    List<LoginDetails> findByOperator_OpId(Long operatorId);
    
    

}
