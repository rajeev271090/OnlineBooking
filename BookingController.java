package com.maersk.bookings.controller;

import com.maersk.bookings.entity.Booking;
import com.maersk.bookings.model.BookingRequest;
import com.maersk.bookings.service.AvailabilityService;
import com.maersk.bookings.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final AvailabilityService availabilityService;
    private final BookingService bookingService;

    @PostMapping("/checkAvailable")
    public Mono<Map<String, Boolean>> checkAvailability(@Valid @RequestBody BookingRequest request) {
        return availabilityService.checkAvailability(request)
                .map(available -> Map.of("available", available));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Map<String, String>> createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request)
                .map(saved -> Map.of("bookingRef", saved.getBookingRef()))
                .onErrorResume(ex -> {
                    System.err.println("Exception: " + ex.getMessage());
                    return Mono.error(new RuntimeException("Sorry there was a problem processing your request"));
                });
    }
}
