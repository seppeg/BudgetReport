package com.cegeka.camis.booking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Component
public class BookingConnection {

    private String bookingUrl;

    BookingConnection(@Value("${booking.url}")String bookingUrl){
        this.bookingUrl = bookingUrl;
    }

    public void sendDataToBookingDomain(List<CamisBooking> camisBookings){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(URI.create(bookingUrl + "/booking"), camisBookings);
    }
}
