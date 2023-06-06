package com.we.travelplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.we.travelplanner.dao.DestinationRepository;
import com.we.travelplanner.dao.ItineraryRepository;
import com.we.travelplanner.model.Destination;
import com.we.travelplanner.model.Itinerary;
import com.we.travelplanner.service.DestinationService;
import com.we.travelplanner.service.ItineraryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItineraryServiceTest {

    @InjectMocks
    ItineraryService itineraryService;

    @Mock
    ItineraryRepository itineraryRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllItineraries() {
        Itinerary itinerary1 = new Itinerary();
        Itinerary itinerary2 = new Itinerary();
        when(itineraryRepository.findAll()).thenReturn(Arrays.asList(itinerary1, itinerary2));

        assertEquals(2, itineraryService.getAllItineraries().size());
        verify(itineraryRepository, times(1)).findAll();
    }

    @Test
    public void testGetItineraryById() {
        Itinerary itinerary = new Itinerary();
        when(itineraryRepository.findById(1)).thenReturn(Optional.of(itinerary));

        assertNotNull(itineraryService.getItineraryById(1));
        verify(itineraryRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveItinerary() {
        Itinerary itinerary = new Itinerary();
        when(itineraryRepository.save(itinerary)).thenReturn(itinerary);

        assertNotNull(itineraryService.saveItinerary(itinerary));
        verify(itineraryRepository, times(1)).save(itinerary);
    }

    @Test
    public void testDeleteItinerary() {
        doNothing().when(itineraryRepository).deleteById(1);
        itineraryService.deleteItinerary(1);

        verify(itineraryRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateItinerary() {
        Itinerary itinerary = new Itinerary();
        itinerary.setId(1);
        itinerary.setPlan("Old Plan");

        when(itineraryRepository.findById(1)).thenReturn(Optional.of(itinerary));
        when(itineraryRepository.save(itinerary)).thenAnswer(invocation -> invocation.getArgument(0));

        itinerary = itineraryService.updateItinerary(1, "New Plan");

        assertEquals("New Plan", itinerary.getPlan());
        verify(itineraryRepository, times(1)).findById(1);
        verify(itineraryRepository, times(1)).save(itinerary);
    }

    @Test
    public void testGetItinerariesByDestinationName() {
        Itinerary itinerary1 = new Itinerary();
        Itinerary itinerary2 = new Itinerary();
        when(itineraryRepository.findByDestinationName("Paris"))
                .thenReturn(Optional.of(Arrays.asList(itinerary1, itinerary2)));

        List<Itinerary> itineraries = itineraryService.getItineraryByDestinatioName("Paris");

        assertEquals(2, itineraries.size());
        verify(itineraryRepository, times(1)).findByDestinationName("Paris");
    }
}
