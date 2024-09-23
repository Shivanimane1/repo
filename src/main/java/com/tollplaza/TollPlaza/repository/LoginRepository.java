package com.tollplaza.TollPlaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.LoginRequest;

@Repository
public interface LoginRepository  extends JpaRepository<LoginRequest, Long>{
	
	LoginRequest findByEmailAndPassword(String email, String password);

}
