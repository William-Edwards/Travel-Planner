package com.we.travelplanner.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.we.travelplanner.model.Itinerary;
import com.we.travelplanner.service.ItineraryService;

@RestController
@RequestMapping("/itineraries")
@CrossOrigin
public class ItineraryController {

    @Autowired
    private ItineraryService itineraryService;

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

    // get all by name
    @GetMapping("/by-destination")
    public ResponseEntity<List<Itinerary>> getItineraryByName(@RequestParam String name) {
        List<Itinerary> itineraries = itineraryService.getItineraryByDestinatioName(name);
        return ResponseEntity.status(HttpStatus.OK).body(itineraries);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Itinerary> updateItinerary(@PathVariable Integer id, @RequestBody String editedPlan) {
        Itinerary updatedItinerary = itineraryService.getItineraryById(id);
        if (updatedItinerary != null) {
            updatedItinerary.setPlan(editedPlan);
            updatedItinerary.setUpdatedAt(new Date());
            itineraryService.saveItinerary(updatedItinerary);
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
    public ResponseEntity<Itinerary> generateItinerary(@RequestBody String destinationName) {
        Itinerary generatedItinerary = itineraryService.generateItinerary(destinationName);
        return ResponseEntity.ok(generatedItinerary);
    }

}
