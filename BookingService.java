package com.maersk.bookings.service;

import com.maersk.bookings.entity.Booking;
import com.maersk.bookings.model.BookingRequest;
import com.maersk.bookings.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private static final AtomicLong counter = new AtomicLong(957000000);

    public Mono<Booking> createBooking(BookingRequest request) {
        String bookingRef = String.valueOf(counter.incrementAndGet());

        Booking booking = Booking.builder()
                .bookingRef(bookingRef)
                .containerSize(request.getContainerSize())
                .containerType(request.getContainerType())
                .origin(request.getOrigin())
                .destination(request.getDestination())
                .quantity(request.getQuantity())
                .timestamp(request.getTimestamp())
                .build();

        return bookingRepository.save(booking)
                .onErrorResume(e -> {
                    System.err.println("Error saving booking: " + e.getMessage());
                    return Mono.error(new RuntimeException("Sorry there was a problem processing your request"));
                });
    }
}
