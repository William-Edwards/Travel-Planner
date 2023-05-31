package com.we.travelplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.we.travelplanner.dao.ItineraryRepository;
import com.we.travelplanner.model.Destination;
import com.we.travelplanner.model.Itinerary;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final DestinationService destinationService;
    private final OpenAiService openAiService;

    @Autowired
    public ItineraryService(ItineraryRepository itineraryRepository, DestinationService destinationService) {
        this.itineraryRepository = itineraryRepository;
        this.destinationService = destinationService;

        // get api key from env

        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("OPENAI_KEY");

        if (token == null) {
            throw new IllegalArgumentException("API_KEY not set in the environment variables.");
        }
        this.openAiService = new OpenAiService(token);
    }

    public List<Itinerary> getAllItineraries() {
        // get all
        return itineraryRepository.findAll();
    }

    public Itinerary getItineraryById(Integer id) {
        // get by id
        return itineraryRepository.findById(id).orElse(null);
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        // save to database
        return itineraryRepository.save(itinerary);
    }

    public void deleteItinerary(Integer id) {
        // delete from database
        itineraryRepository.deleteById(id);
    }

    public String generateItinerary(String userInput) {

        // destination name validation, if blank and not containing relevent data

        // check if exists in database
        Destination destination = destinationService.getDestinationByName(userInput);
        if (destination == null) {
            // create new destination
            destination = new Destination();
            destination.setName(userInput);
            destinationService.saveDestination(destination);
        }

        // generate plan
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("ada")
                .echo(true)
                .build();

        StringBuilder builder = new StringBuilder();
        openAiService.createCompletion(completionRequest)
                .getChoices().forEach(choice -> {
                    builder.append(choice.getText());
                });

        String response = builder.toString();
        return response;
    }

}
