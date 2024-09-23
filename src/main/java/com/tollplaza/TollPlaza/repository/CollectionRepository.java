package com.tollplaza.TollPlaza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.CollectionData;

@Repository
	public interface CollectionRepository extends JpaRepository<CollectionData, Long> {
	    // Additional query methods can be defined here if needed

	
	@Query(nativeQuery = true, value = "SELECT * FROM (\r\n"
			+ "    SELECT *, ROW_NUMBER() OVER (PARTITION BY operator_id ORDER BY id DESC) as rn\r\n"
			+ "    FROM operator.collection\r\n"
			+ ") as subquery\r\n"
			+ "WHERE rn = 1")
	List<CollectionData> getamount();
	
	
	 @Query(value = "SELECT * FROM operator.collection ORDER BY id DESC LIMIT 1", nativeQuery = true)
	    CollectionData findLatestCollection();
	
}


