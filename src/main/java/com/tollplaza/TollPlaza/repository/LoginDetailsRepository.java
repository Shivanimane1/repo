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
    

    
    @Query(nativeQuery = true, value = "SELECT * FROM (" +
            "    SELECT *, ROW_NUMBER() OVER (PARTITION BY operator_id ORDER BY id DESC) as rn" +
            "    FROM operator.login_details" +
            ") as subquery " +
            "WHERE rn = 1 AND login_date = :today")
    List<LoginDetails> findByLoginDate(@Param("today") LocalDate today);
    
   // List<LoginDetails> findByOperator_OpId(Long operatorId);
    
//    --------------------------------------------------------
    
 // Fetch all login details by operator ID
    List<LoginDetails> findByOperator_OpId(Long operatorId);

    // Fetch count of all vehicles passed through a specific operator ID
    @Query("SELECT COUNT(ld) FROM LoginDetails ld WHERE ld.operator.opId = :operatorId")
    Long countTotalVehiclesByOperatorId(@Param("operatorId") Long operatorId);

    // Fetch count of "Single" return type vehicles
    @Query("SELECT COUNT(ld) FROM LoginDetails ld WHERE ld.operator.opId = :operatorId AND ld.returnType = 'Single'")
    Long countSingleVehiclesByOperatorId(@Param("operatorId") Long operatorId);

    // Fetch count of "Return" type vehicles
    @Query("SELECT COUNT(ld) FROM LoginDetails ld WHERE ld.operator.opId = :operatorId AND ld.returnType = 'Return'")
    Long countReturnVehiclesByOperatorId(@Param("operatorId") Long operatorId);
    
//    @Query("SELECT ld FROM LoginDetails ld WHERE ld.operator.id = :operatorId")
//    List<LoginDetails> findByOperatorId(@Param("operatorId") Long operatorId);

}
