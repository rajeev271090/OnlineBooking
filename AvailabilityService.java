package com.maersk.bookings.service;

import com.maersk.bookings.model.BookingRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class AvailabilityService {

    public Mono<Boolean> checkAvailability(BookingRequest request) {
        int availableSpace = new Random().nextInt(10); // simulate response 0-9
        return Mono.just(availableSpace > 0);
    }
}
