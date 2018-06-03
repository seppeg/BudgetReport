package com.cegeka.project.project;

import com.cegeka.project.infrastructure.UnexistingResourceException;
import com.cegeka.project.workorder.WorkOrder;
import com.cegeka.project.workorder.WorkOrderR;
import com.cegeka.project.workorder.WorkOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final WorkOrderRepository workOrderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public UUID createProject(ProjectR projectR) throws ProjectAlreadyExistsException {
        validateProjectName(projectR);
        List<WorkOrder> workOrders = findOrCreateWorkOrders(projectR);
        Set<ProjectYearBudget> budgets = createBudgets(projectR);
        Project project = projectRepository.save(new Project(projectR.getName(), workOrders, budgets));
        eventPublisher.publishEvent(new ProjectCreated(projectR));
        return project.getId();
    }

    public void updateProject(UUID projectId, ProjectR projectR) throws UnexistingResourceException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new UnexistingResourceException("project", projectId));
        List<WorkOrder> workOrders = findOrCreateWorkOrders(projectR);
        Set<ProjectYearBudget> budgets = createBudgets(projectR);
        project.update(projectR.getName(), workOrders, budgets);
    }

    private void validateProjectName(ProjectR projectR) throws ProjectAlreadyExistsException {
        if (projectRepository.existsProjectByName(projectR.getName())) {
            throw new ProjectAlreadyExistsException(projectR.getName());
        }
    }

    private List<WorkOrder> findOrCreateWorkOrders(ProjectR projectR) {
        return projectR.getWorkOrders()
                .stream()
                .map(WorkOrderR::getWorkOrder)
                .map(workOrder -> workOrderRepository.findByWorkOrder(workOrder)
                        .orElseGet(() -> new WorkOrder(workOrder)))
                .collect(toList());
    }

    private Set<ProjectYearBudget> createBudgets(ProjectR projectR) {
        return projectR.getBudgets()
                .stream()
                .map(b -> new ProjectYearBudget(b.getYear(), b.getBudget()))
                .collect(toCollection(TreeSet::new));
    }

    public Collection<String> findProjectWorkOrders(UUID projectId) {
        return projectRepository.findById(projectId)
                .stream()
                .map(Project::getWorkOrders)
                .flatMap(Collection::stream)
                .map(WorkOrder::getWorkOrder)
                .collect(toList());
    }

    public Optional<ProjectR> findProject(UUID projectId){
        return projectRepository.findById(projectId).map(ProjectR::new);
    }
}
