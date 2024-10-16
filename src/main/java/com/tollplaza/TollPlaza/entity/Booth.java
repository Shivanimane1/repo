package com.tollplaza.TollPlaza.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="booth")
public class Booth {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private String vehicleNumber;
    private String vehicleType;
    private String returnType;
    private Double amount;
    private String boothNo;

    @ManyToOne
    @JoinColumn(name = "op_id")
 
    @JsonBackReference
    private Operators operators;
    
   

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Operators getOperators() {
		return operators;
	}

	public void setOperators(Operators operators) {
		this.operators = operators;
	}

	public String getBoothNo() {
		return boothNo;
	}

	public void setBoothNo(String boothNo) {
		this.boothNo = boothNo;
	}

	
	
    
    
    

}