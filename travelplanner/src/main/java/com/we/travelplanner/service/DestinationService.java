package com.we.travelplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.travelplanner.dao.DestinationRepository;
import com.we.travelplanner.model.Destination;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // get all destinations

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    // get destination by id

    public Destination getDestinationById(Integer id) {
        return destinationRepository.findById(id).orElse(null);
    }

    // add destination

    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    // delete destination

    public void deleteDestination(Integer id) {
        destinationRepository.deleteById(id);
    }

    // get destination by name
    public Destination getDestinationByName(String name) {
        return destinationRepository.findByName(name).orElse(null);
    }
}
