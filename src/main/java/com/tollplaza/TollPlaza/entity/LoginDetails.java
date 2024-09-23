package com.tollplaza.TollPlaza.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_details")
public class LoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loginDate;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonBackReference
    private Operators operator;
    
    
    private Long loginOperatorId;

    private String loginOperatorName;

    private String loginOperatorEmail;

    private String loginOperatorBooth;
    
    private String amount;
    
	private String returnType;
    
    

    // Constructors, getters, and setters
	
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
	

    public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public LoginDetails() {}

    public LoginDetails(LocalDate loginDate, Operators operator) {
        this.loginDate = loginDate;
        this.operator = operator;
    }
    
    

    public Long getLoginOperatorId() {
		return loginOperatorId;
	}

	public void setLoginOperatorId(Long loginOperatorId) {
		this.loginOperatorId = loginOperatorId;
	}

	public String getLoginOperatorName() {
		return loginOperatorName;
	}

	public void setLoginOperatorName(String loginOperatorName) {
		this.loginOperatorName = loginOperatorName;
	}

	public String getLoginOperatorEmail() {
		return loginOperatorEmail;
	}

	public void setLoginOperatorEmail(String loginOperatorEmail) {
		this.loginOperatorEmail = loginOperatorEmail;
	}

	public String getLoginOperatorBooth() {
		return loginOperatorBooth;
	}

	public void setLoginOperatorBooth(String loginOperatorBooth) {
		this.loginOperatorBooth = loginOperatorBooth;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDate loginDate) {
        this.loginDate = loginDate;
    }

    public Operators getOperator() {
        return operator;
    }

    public void setOperator(Operators operator) {
        this.operator = operator;
    }
}
