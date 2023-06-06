package com.we.travelplanner.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.we.travelplanner.model.Destination;
import com.we.travelplanner.model.Itinerary;
import com.we.travelplanner.service.DestinationService;
import com.we.travelplanner.service.ItineraryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    ItineraryService itineraryService;

    @Autowired
    DestinationService destinationService;

    @GetMapping("/")
    public String index(Model model) {

        // get sample itins
        List<Itinerary> sampleItineraries = itineraryService.getSampleItineraries();

        // add to model
        model.addAttribute("itineraries", sampleItineraries);

        // return view
        return "index";
    }

    @PostMapping("/generate")
    public String generateItinerary(@RequestParam String userInput, RedirectAttributes redirectAttributes) {

        // generate itin
        Itinerary itinerary = itineraryService.generateItinerary(userInput);

        // send id for redirect
        redirectAttributes.addAttribute("id", itinerary.getId());

        return "redirect:/itinerary/{id}";
    }

    @GetMapping("/destinations")
    public String search(Model model) {

        // a list of all destinations
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);

        return "destinations";
    }

    @PostMapping("/destination/{id}/delete")
    public String deleteDestination(@PathVariable int id) {
        destinationService.deleteDestination(id);
        return "redirect:/destinations";
    }

    @GetMapping("/destination/{name}")
    public String viewItineraries(@PathVariable String name, Model model) {
        List<Itinerary> itineraries = itineraryService.getiItineraryByDestinatioName(name);
        model.addAttribute("destinationName", name);
        model.addAttribute("itineraries", itineraries);
        return "itineraries";
    }

    // itin view page for a single one

    @GetMapping("/itinerary/{id}")
    public String viewItinerary(@PathVariable("id") int id, Model model) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        model.addAttribute("itinerary", itinerary);
        return "itinerary";
    }

    // delete itin

    @PostMapping("/itinerary/{id}/delete")
    public String deleteItinerary(@PathVariable("id") int id) {
        itineraryService.deleteItinerary(id);
        return "redirect:/"; // redirect to the home page after deleting the itinerary
    }

    @PostMapping("/itinerary/{id}/edit")
    public String updateItinerary(@PathVariable("id") int id, @RequestParam String plan, Model model) {
        Itinerary updatedItinerary = itineraryService.updateItinerary(id, plan);
        model.addAttribute("itinerary", updatedItinerary);
        return "itinerary";
    }

}
