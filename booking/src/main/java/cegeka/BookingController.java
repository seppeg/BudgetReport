package cegeka;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking")
//    @PutMapping("/booking")
    public Mono<String> createBooking(){
        log.info("creating booking");
        bookingService.createBooking(new BookingCreated(5L));
        return Mono.just("created");
    }
}
