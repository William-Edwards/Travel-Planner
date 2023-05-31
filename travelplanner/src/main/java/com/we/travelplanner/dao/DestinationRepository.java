package com.we.travelplanner.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.we.travelplanner.model.Destination;

// will be auto implemented by spring, provides crud

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    Optional<Destination> findByName(String name);
}
