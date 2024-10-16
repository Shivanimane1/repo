package com.tollplaza.TollPlaza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.Operators;


@Repository
public interface OpRepository extends JpaRepository<Operators, Long> {
   // LoginRequest findByEmailAndPassword(String email, String password);
	
	

	
	Optional<Operators> findByEmailAndPassword(String email,String password);

}	

