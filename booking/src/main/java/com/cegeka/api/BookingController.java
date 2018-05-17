package com.cegeka.api;

import com.cegeka.service.BookingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

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
}
