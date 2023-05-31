package com.we.travelplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.we.travelplanner.model.Itinerary;
import com.we.travelplanner.service.ItineraryService;

@RestController
@RequestMapping("/itineraries")
@CrossOrigin
public class ItineraryController {

    private final ItineraryService itineraryService;

    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    // get all
    @GetMapping("/all")
    public ResponseEntity<List<Itinerary>> getAllItineraries() {
        List<Itinerary> itineraries = itineraryService.getAllItineraries();
        return ResponseEntity.status(HttpStatus.OK).body(itineraries);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Itinerary> getItineraryById(@PathVariable Integer id) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        if (itinerary != null) {
            return ResponseEntity.ok(itinerary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable Integer id, @RequestBody Itinerary itinerary) {
        Itinerary updatedItinerary = itineraryService.saveItinerary(itinerary);
        if (updatedItinerary != null) {
            return ResponseEntity.ok(updatedItinerary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(@PathVariable Integer id) {
        itineraryService.deleteItinerary(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateItinerary(@RequestBody String destinationName) {
        String generatedItinerary = itineraryService.generateItinerary(destinationName);
        return ResponseEntity.ok(generatedItinerary);
    }

}
