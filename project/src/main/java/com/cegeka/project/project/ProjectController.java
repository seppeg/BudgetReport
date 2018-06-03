package com.cegeka.project.project;

import com.cegeka.project.booking.MonthlyWorkOrderBookingView;
import com.cegeka.project.booking.ProjectBookingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectBookingService projectBookingService;

    @GetMapping("/project")
    public Collection<ProjectR> getAll() {
        return projectService.getAllProjects()
                .stream()
                .map(ProjectR::new)
                .collect(toList());
    }

    @PutMapping("/project")
    public ProjectR createProject(@RequestBody ProjectR projectR) throws ProjectAlreadyExistsException {
        log.info(() -> "creating project "+projectR);
        return projectService.createProject(projectR);
    }

    @GetMapping("/projectbooking/{projectId}")
    public Collection<MonthlyWorkOrderBookingView> getMonthlyProjectBookings(@PathVariable("projectId") UUID projectId){
        Collection<String> projectWorkOrders = projectService.findProjectWorkOrders(projectId);
        return projectBookingService.getMonthlyWorkOrderBookings(projectWorkOrders);
    }
}
