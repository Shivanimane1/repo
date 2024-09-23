package com.tollplaza.TollPlaza.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection")
public class CollectionData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	 private Long id;
	

	private double totalManualAmount;
	private double shortAmount;
	private double accessAmount;
	private Long operatorId;
	private double totalAmount;
	
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getTotalManualAmount() {
		return totalManualAmount;
	}
	public void setTotalManualAmount(double totalManualAmount) {
		this.totalManualAmount = totalManualAmount;
	}
	public double getShortAmount() {
		return shortAmount;
	}
	public void setShortAmount(double shortAmount) {
		this.shortAmount = shortAmount;
	}
	public double getAccessAmount() {
		return accessAmount;
	}
	public void setAccessAmount(double accessAmount) {
		this.accessAmount = accessAmount;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
	

}
