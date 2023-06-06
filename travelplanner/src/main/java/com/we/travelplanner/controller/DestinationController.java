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

import com.we.travelplanner.model.Destination;
import com.we.travelplanner.service.DestinationService;

@RestController
@RequestMapping("/destinations")
@CrossOrigin
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping("/all")
    public ResponseEntity<List<Destination>> getAllDestinations() {
        // get all destinations
        List<Destination> destinations = destinationService.getAllDestinations();
        // return status 200 ok and response
        return ResponseEntity.status(HttpStatus.OK).body(destinations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Integer id) {
        Destination destination = destinationService.getDestinationById(id);

        return new ResponseEntity<Destination>(destination, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createDestination(@RequestBody Destination destination) {
        destinationService.saveDestination(destination);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable Integer id,
            @RequestBody Destination destination) {
        destinationService.saveDestination(destination);
        return new ResponseEntity<Destination>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Integer id) {
        destinationService.deleteDestination(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
