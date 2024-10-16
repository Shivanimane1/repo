package com.tollplaza.TollPlaza.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="vehicles")
public class Vehicles {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleType;
    private double single_amt;
    private double return_amt;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getSingle_amt() {
        return single_amt;
    }

    public void setSingle_amt(double single_amt) {
        this.single_amt = single_amt;
    }

    public double getReturn_amt() {
        return return_amt;
    }

    public void setReturn_amt(double return_amt) {
        this.return_amt = return_amt;
    }
}
