package com.we.travelplanner.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.we.travelplanner.model.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    Optional<List<Itinerary>> findByDestinationName(String name);
}
