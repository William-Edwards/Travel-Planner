package com.we.travelplanner.service;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.we.travelplanner.dao.ItineraryRepository;
import com.we.travelplanner.model.Destination;
import com.we.travelplanner.model.Itinerary;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ItineraryService {

    private static final String SYSTEM_MESSAGE = " You are a sophisticated AI travel planner. Your task is to create a detailed travel itinerary based on the destination provided by the user. Please make sure to include recommendations for attractions, activities, local cuisine, and lodging where appropriate. Do not provide anything else.";
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

        // added timeout duration of 60s
        this.openAiService = new OpenAiService(token, Duration.ofSeconds(60));
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

    public Itinerary generateItinerary(String userInput) {

        // article and api docs used for reference
        // https://dzone.com/articles/creating-scalable-openai-gpt-applications-in-java
        // https://github.com/TheoKanning/openai-java
        // https://platform.openai.com/docs

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
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        new ChatMessage("system", SYSTEM_MESSAGE),
                        new ChatMessage("user", userInput)))
                .build();

        // build to string

        StringBuilder builder = new StringBuilder();
        openAiService.createChatCompletion(chatCompletionRequest)
                .getChoices().forEach(choice -> {
                    builder.append(choice.getMessage().getContent());
                });

        String plan = builder.toString();

        // create new itinerary
        Itinerary itinerary = new Itinerary();
        itinerary.setDestination(destination);
        itinerary.setPlan(plan);
        return itineraryRepository.save(itinerary);

    }

    // get by name
    public List<Itinerary> getiItineraryByDestinatioName(String name) {
        return itineraryRepository.findByDestinationName(name).orElse(null);
    }

}
