package com.we.travelplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.travelplanner.dao.DestinationRepository;
import com.we.travelplanner.model.Destination;

/**
 * Service for handling operations related to destinations.
 */
@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    /**
     * Get all destinations.
     *
     * @return A List of all Destination objects.
     */
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    /**
     * Get a specific destination by its ID.
     *
     * @param id The ID of the destination.
     * @return The Destination object with the given ID, or null if no such
     *         destination exists.
     */
    public Destination getDestinationById(Integer id) {
        return destinationRepository.findById(id).orElse(null);
    }

    /**
     * Save a destination. This method can be used for creating a new destination or
     * updating an existing one.
     *
     * @param destination The Destination object to save.
     */
    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    /**
     * Delete a destination.
     *
     * @param id The ID of the destination to delete.
     */
    public void deleteDestination(Integer id) {
        destinationRepository.deleteById(id);
    }

    /**
     * Get a specific destination by its name.
     *
     * @param name The name of the destination.
     * @return The Destination object with the given name, or null if no such
     *         destination exists.
     */
    public Destination getDestinationByName(String name) {
        return destinationRepository.findByName(name).orElse(null);
    }
}
