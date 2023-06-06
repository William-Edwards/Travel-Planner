package com.we.travelplanner.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.we.travelplanner.dao.ItineraryRepository;
import com.we.travelplanner.model.Destination;
import com.we.travelplanner.model.Itinerary;

/**
 * Service for handling operations related to itineraries.
 */
@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private OpenAiService openAiService;

    private static final String SYSTEM_MESSAGE = " You are a sophisticated AI travel planner. Your task is to create a detailed travel itinerary based on the destination provided by the user. Please make sure to include recommendations for attractions, activities, local cuisine, and lodging where appropriate. Do not provide anything else.";

    /**
     * Get all itineraries.
     *
     * @return A list of all Itinerary objects.
     */
    public List<Itinerary> getAllItineraries() {
        // get all
        return itineraryRepository.findAll();
    }

    /**
     * Get a specific itinerary by its ID.
     *
     * @param id The ID of the itinerary.
     * @return The Itinerary object with the given ID, or null if no such itinerary
     *         exists.
     */
    public Itinerary getItineraryById(Integer id) {
        // get by id
        return itineraryRepository.findById(id).orElse(null);
    }

    /**
     * Save an itinerary. This method can be used for creating a new itinerary or
     * updating an existing one.
     *
     * @param itinerary The Itinerary object to save.
     * @return The saved Itinerary object.
     */
    public Itinerary saveItinerary(Itinerary itinerary) {
        // save to database
        return itineraryRepository.save(itinerary);
    }

    /**
     * Delete an itinerary.
     *
     * @param id The ID of the itinerary to delete.
     */
    public void deleteItinerary(Integer id) {
        // delete from database
        itineraryRepository.deleteById(id);
    }

    /**
     * Update an existing itinerary.
     *
     * @param id      The ID of the itinerary to update.
     * @param newPlan The new plan for the itinerary.
     * @return The updated Itinerary object.
     */
    public Itinerary updateItinerary(int id, String newPlan) {
        Itinerary itinerary = itineraryRepository.findById(id).orElse(null);
        itinerary.setPlan(newPlan);
        itinerary.setUpdatedAt(new Date());
        return itineraryRepository.save(itinerary);
    }

    /**
     * Generate a new itinerary based on user input.
     *
     * @param userInput The user's input (destination).
     * @return The generated Itinerary object.
     */
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

        // generate plan, list model and and init prompt and user input
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
        // format for html
        itinerary.setPlan(formatPlan(plan));
        return itineraryRepository.save(itinerary);

    }

    // formatter for putting in html tags, so dsiplay is nice on screen

    private String formatPlan(String plan) {
        String[] days = plan.split("Day \\d+:");
        StringBuilder formattedPlan = new StringBuilder();

        for (int i = 1; i < days.length; i++) {
            formattedPlan.append("<h3>Day ").append(i).append(":</h3><ul>");

            String[] activities = days[i].split(" - ");
            for (String activity : activities) {
                formattedPlan.append("<li>").append(activity.trim()).append("</li>");
            }

            formattedPlan.append("</ul>");
        }

        return formattedPlan.toString();

    }

    /**
     * Get all itineraries for a specific destination name.
     *
     * @param name The name of the destination.
     * @return A list of Itinerary objects for the given destination name, or null
     *         if no such itineraries exist.
     */
    public List<Itinerary> getItineraryByDestinatioName(String name) {
        return itineraryRepository.findByDestinationName(name).orElse(null);
    }

    /**
     * Get a sample of itineraries.
     *
     * @return A list of the latest 3 Itinerary objects.
     */
    public List<Itinerary> getSampleItineraries() {
        // get the latest 3 itineraries
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdAt"));
        return itineraryRepository.findAll(pageable).getContent();
    }

}
