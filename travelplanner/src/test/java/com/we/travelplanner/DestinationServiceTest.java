package com.we.travelplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.we.travelplanner.dao.DestinationRepository;
import com.we.travelplanner.model.Destination;
import com.we.travelplanner.service.DestinationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DestinationServiceTest {

    @InjectMocks
    DestinationService destinationService;

    @Mock
    DestinationRepository destinationRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDestinations() {
        Destination destination1 = new Destination();
        Destination destination2 = new Destination();
        when(destinationRepository.findAll()).thenReturn(Arrays.asList(destination1, destination2));

        List<Destination> destinations = destinationService.getAllDestinations();

        assertEquals(2, destinations.size());
        verify(destinationRepository, times(1)).findAll();
    }

    @Test
    public void testGetDestinationById() {
        Destination destination = new Destination();
        destination.setId(1);
        when(destinationRepository.findById(1)).thenReturn(Optional.of(destination));

        Destination result = destinationService.getDestinationById(1);

        assertEquals(1, result.getId());
        verify(destinationRepository, times(1)).findById(anyInt());
    }

    @Test
    public void testSaveDestination() {
        Destination destination = new Destination();
        destination.setId(1);
        when(destinationRepository.save(any(Destination.class))).thenReturn(destination);

        destinationService.saveDestination(destination);

        verify(destinationRepository, times(1)).save(any(Destination.class));
    }

    @Test
    public void testDeleteDestination() {
        doNothing().when(destinationRepository).deleteById(1);

        destinationService.deleteDestination(1);

        verify(destinationRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void testGetDestinationByName() {
        Destination destination = new Destination();
        destination.setName("Paris");
        when(destinationRepository.findByName("Paris")).thenReturn(Optional.of(destination));

        Destination result = destinationService.getDestinationByName("Paris");

        assertEquals("Paris", result.getName());
        verify(destinationRepository, times(1)).findByName(anyString());
    }
}
