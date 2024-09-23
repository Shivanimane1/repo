package com.tollplaza.TollPlaza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tollplaza.TollPlaza.entity.Vehicles;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicles, Long> {
    Optional<Vehicles> findByVehicleType(String vehicleType);

}
