package com.cegeka.api;

import com.cegeka.service.BookingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Flux.fromIterable;

@RestController
@Log4j2
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PutMapping("/booking")
    public Mono<String> putBookingData(@RequestBody List<BookingR> bookings){
        log.info("creating booking "+bookings);
        bookingService.processBookingData(bookings);
        return Mono.just("created");
    }

    @GetMapping("/booking")
    public Flux<BookingR> getBookings(@RequestParam(name = "page", defaultValue = "0") int page){
        page = Math.max(0, page);
        return fromIterable(bookingService.getBookings(page).getContent()).map(BookingR::new);
    }
}
