package com.cegeka.project.project;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Log4j2
public class ProjectReadModel {

    private final ProjectRepository projectRepository;

    @EventListener
    public void on(BookingCreated event){
        Optional<Project> project = projectRepository.findByWorkOrdersWorkOrderContains(event.getWorkOrder());
        project.ifPresent(p -> addHoursSpent(p, event.getHours()));
    }

    @EventListener
    public void on(BookingDeleted event){
        Optional<Project> project = projectRepository.findByWorkOrdersWorkOrderContains(event.getWorkOrder());
        project.ifPresent(p -> removeHoursSpent(p, event.getHours()));
    }

    private void addHoursSpent(Project p, double hours) {
        p.addHoursSpent(hours);
        log.info("updated hours spent +");
    }

    private void removeHoursSpent(Project p, double hours) {
        p.removeHoursSpent(hours);
        log.info("updated hours spent -");
    }
}
