package com.cegeka.project.booking;

import com.cegeka.project.project.BookingEventProjectSpecificationMatcher;
import com.cegeka.project.project.Project;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@AllArgsConstructor
@Log4j2
public class DayBookingReadModel {

    private final BookingEventProjectSpecificationMatcher bookingEventProjectSpecificationMatcher;
    private final DayBookingRepository dayBookingRepository;

    @EventListener
    public void on(BookingCreated event) {
        Collection<Project> projects = bookingEventProjectSpecificationMatcher.getProjectsMatchingEvent(event);
        for (Project project : projects) {
            Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndProjectId(event.getDate(), project.getId());
            if (dayBooking.isPresent()) {
                dayBooking.get().addHours(event.getHours());
            } else {
                dayBookingRepository.save(new DayBooking(event.getDate(), project.getId(), event.getHours()));
            }
        }
    }

    @EventListener
    public void on(BookingDeleted event) {
        Collection<Project> projects = bookingEventProjectSpecificationMatcher.getProjectsMatchingEvent(event);
        for (Project project : projects) {
            Optional<DayBooking> dayBooking = dayBookingRepository.findByDateAndProjectId(event.getDate(), project.getId());
            if (dayBooking.isPresent()) {
                dayBooking.get().subtractHours(event.getHours());
            } else {
                log.warn(() -> "BookingDeleted event for unexisting booking: " + event);
            }
        }
    }
}
