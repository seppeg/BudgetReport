package com.cegeka.util;

import com.cegeka.api.BookingR;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.time.LocalDate.of;
import static java.time.LocalDate.ofEpochDay;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class BookingDummyDataGenerator {

    public static void main(String[] args) throws JsonProcessingException {
        List<BookingR> bookings = getBookings(100);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String s = objectMapper.writeValueAsString(bookings);
        System.out.println(s);
    }

    private static List<BookingR> getBookings(int amount) {
        Random random = new Random();
        List<String> workOrders = asList("COCFL871.004", "COCFL874.004", "COCFL860.004");
        List<String> employees = IntStream.range(1, 100).mapToObj(String::valueOf).collect(toList());
        List<BookingR> bookings = new ArrayList<>(amount);
        for(int i = 0; i < amount; i++){
            String randomWorkOrder = workOrders.get(random.nextInt(workOrders.size()));
            String randomEmployee = employees.get(random.nextInt(employees.size()));
            double randomHours = (int) (random.nextDouble() * 5)+1;
            LocalDate randomDate = randomDate(of(2018, 1, 1), LocalDate.now());
            bookings.add(new BookingR(randomWorkOrder, randomDate, "Description", randomHours, randomEmployee));
        }
        return bookings;
    }

    private static LocalDate randomDate(LocalDate start, LocalDate end){
        return ofEpochDay(ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay()));
    }
}
