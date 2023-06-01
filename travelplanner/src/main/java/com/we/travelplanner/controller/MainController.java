package com.we.travelplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.we.travelplanner.model.Itinerary;
import com.we.travelplanner.service.ItineraryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    ItineraryService itineraryService;

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

    @GetMapping("/search")
    public String search(Model model) {
        // You can add any data you need to the model here.
        // For instance, if you want to display a list of all destinations, you could
        // add them to the model.

        // Return the name of the Thymeleaf template for the search page.
        // Assuming it's named "search.html".
        return "search";
    }

    // itin view page for a single one

    @GetMapping("/itinerary/{id}")
    public String viewItinerary(@PathVariable("id") int id, Model model) {
        Itinerary itinerary = itineraryService.getItineraryById(id);
        model.addAttribute("itinerary", itinerary);
        return "itinerary";
    }

    @PostMapping("/itinerary/{id}/delete")
    public String deleteItinerary(@PathVariable("id") int id) {
        itineraryService.deleteItinerary(id);
        return "redirect:/"; // redirect to the home page after deleting the itinerary
    }

}
