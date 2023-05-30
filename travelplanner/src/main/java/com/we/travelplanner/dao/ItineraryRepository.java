package com.we.travelplanner.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.we.travelplanner.model.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {

}
