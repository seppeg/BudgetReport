package com.cegeka.project.project;

import com.cegeka.project.booking.BookingEvent;
import com.cegeka.project.project.spec.ProjectSpecification;
import com.cegeka.project.project.spec.ProjectSpecificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
public class BookingEventProjectSpecificationMatcher {

    private final ProjectSpecificationRepository projectSpecificationRepository;
    private final ProjectRepository projectRepository;

    public Collection<Project> getProjectsMatchingEvent(BookingEvent event) {
        Collection<ProjectSpecification> matchedSpecifications = matchEvent(event);
        return getProjectsBySpecification(matchedSpecifications);
    }

    private Collection<Project> getProjectsBySpecification(Collection<ProjectSpecification> matchedSpecifications) {
        return matchedSpecifications.stream()
                .map(ProjectSpecification::getProjectId)
                .map(projectRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSet());
    }

    private Collection<ProjectSpecification> matchEvent(BookingEvent bookingEvent){
        return projectSpecificationRepository.findAll()
                .stream()
                .filter(spec -> spec.matches(bookingEvent))
                .collect(toList());
    }
}
