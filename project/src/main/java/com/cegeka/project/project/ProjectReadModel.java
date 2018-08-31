package com.cegeka.project.project;

import com.cegeka.project.booking.BookingCreated;
import com.cegeka.project.booking.BookingDeleted;
import com.cegeka.project.booking.BookingEvent;
import com.cegeka.project.project.spec.ProjectSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
@Log4j2
public class ProjectReadModel {

    private final BookingEventProjectSpecificationMatcher matcher;

    @EventListener
    public void on(BookingCreated event){
        Collection<Project> matchedProjects = matcher.getProjectsMatchingEvent(event);
        matchedProjects.forEach(project -> addHoursSpent(project, event.getHours()));
    }

    @EventListener
    public void on(BookingDeleted event){
        Collection<Project> matchedProjects = matcher.getProjectsMatchingEvent(event);
        matchedProjects.forEach(project -> removeHoursSpent(project, event.getHours()));
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
