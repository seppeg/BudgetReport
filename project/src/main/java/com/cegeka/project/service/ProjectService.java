package com.cegeka.project.service;

import com.cegeka.project.controller.ProjectR;
import com.cegeka.project.controller.WorkOrderR;
import com.cegeka.project.domain.daybooking.DayBookingReadModel;
import com.cegeka.project.domain.project.Project;
import com.cegeka.project.domain.project.ProjectRepository;
import com.cegeka.project.domain.workorder.WorkOrder;
import com.cegeka.project.domain.workorder.WorkOrderRepository;
import com.cegeka.project.event.BookingCreated;
import com.cegeka.project.event.BookingDeleted;
import com.cegeka.project.event.ProjectCreated;
import com.cegeka.project.infrastructure.ProjectStreams;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectStreams projectStreams;
    private final WorkOrderTracker workOrderTracker;
    private final WorkOrderRepository workOrderRepository;
    private final DayBookingReadModel dayBookingReadModel;

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingCreated'")
    public void onBookingCreated(@Payload BookingCreated bookingCreated) {
        Optional<Project> project = projectRepository.findByWorkOrdersWorkOrderContains(bookingCreated.getWorkOrder());
        project.ifPresent(p -> addHoursSpent(p, bookingCreated.getHours()));
        dayBookingReadModel.on(bookingCreated);
    }

    private void addHoursSpent(Project p, double hours) {
        p.addHoursSpent(hours);
        log.info("updated hours spent +");
    }

    @StreamListener(value = ProjectStreams.INPUT, condition = "headers['type']=='BookingDeleted'")
    public void onBookingDeleted(@Payload BookingDeleted bookingDeleted) {
        Optional<Project> project = projectRepository.findByWorkOrdersWorkOrderContains(bookingDeleted.getWorkOrder());
        project.ifPresent(p -> removeHoursSpent(p, bookingDeleted.getHours()));
    }

    private void removeHoursSpent(Project p, double hours) {
        p.removeHoursSpent(hours);
        log.info("updated hours spent -");
    }

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectR createProject(ProjectR projectR) throws ProjectAlreadyExistsException {
        if (projectRepository.existsProjectByName(projectR.getName())) {
            throw new ProjectAlreadyExistsException(projectR.getName());
        }
        List<WorkOrder> workOrders = projectR.getWorkOrders()
                .stream()
                .map(WorkOrderR::getWorkOrder)
                .map(workOrder -> workOrderRepository.findByWorkOrder(workOrder)
                        .orElseGet(() -> new WorkOrder(workOrder)))
                .collect(toList());
        Project project = projectRepository.save(new Project(projectR.getName(), workOrders, projectR.getBudget()));
        ProjectCreated projectCreated = new ProjectCreated(projectR);
        raiseEvent(projectCreated);
        workOrderTracker.trackWorkOrders(projectCreated.getWorkOrders());
        return new ProjectR(project);
    }

    private void raiseEvent(Object event) {
        log.info("Raising event " + event);
        MessageChannel messageChannel = projectStreams.outboundProjects();
        Message<Object> message = MessageBuilder.withPayload(event).setHeader("type", event.getClass().getSimpleName()).build();
        messageChannel.send(message);
    }

    public Collection<String> findProjectWorkOrders(UUID projectId) {
        return projectRepository.findById(projectId)
                .stream()
                .map(Project::getWorkOrders)
                .flatMap(Collection::stream)
                .map(WorkOrder::getWorkOrder)
                .collect(toList());
    }
}
