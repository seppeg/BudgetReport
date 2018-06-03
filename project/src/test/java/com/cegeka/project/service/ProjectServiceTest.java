package com.cegeka.project.service;

import com.cegeka.project.project.*;
import com.cegeka.project.workorder.WorkOrder;
import com.cegeka.project.workorder.WorkOrderR;
import com.cegeka.project.workorder.WorkOrderRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;

import java.util.Optional;

import static com.cegeka.project.controller.ProjectRTestBuilder.projectR;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class ProjectServiceTest {

    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MessageChannel messageChannel;

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository, workOrderRepository, eventPublisher);
        when(projectRepository.save(isA(Project.class))).thenAnswer(returnsFirstArg());
        when(workOrderRepository.save(isA(WorkOrder.class))).thenAnswer(returnsFirstArg());
    }

    @Test
    void createProject() throws ProjectAlreadyExistsException {
        when(workOrderRepository.findByWorkOrder("workorder")).thenReturn(Optional.of(new WorkOrder("workorder")));
        ProjectR project = projectR()
                .budget(Sets.newTreeSet(new ProjectYearBudgetR(2018, 5)))
                .name("desc")
                .workorder(singletonList(new WorkOrderR("workorder")))
                .build();

        projectService.createProject(project);

        ArgumentCaptor<Project> projectArgumentCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projectArgumentCaptor.capture());
        Project savedProject = projectArgumentCaptor.getValue();
        assertThat(savedProject.getBudgets()).extracting(ProjectYearBudget::getYear, ProjectYearBudget::getBudget).containsExactly(tuple(2018, 5D));
        assertThat(savedProject.getName()).isEqualTo("desc");
        assertThat(savedProject.getWorkOrders()).extracting(WorkOrder::getWorkOrder).containsExactly("workorder");

        ArgumentCaptor<ProjectCreated> messageArgumentCaptor = ArgumentCaptor.forClass(ProjectCreated.class);
        verify(eventPublisher).publishEvent(messageArgumentCaptor.capture());
        ProjectCreated projectCreated = messageArgumentCaptor.getValue();
        assertThat(projectCreated.getName()).isEqualTo("desc");
        assertThat(projectCreated.getBudgets()).extracting(ProjectYearBudgetR::getYear, ProjectYearBudgetR::getBudget).containsExactly(tuple(2018, 5D));
        assertThat(projectCreated.getWorkOrders()).containsExactly("workorder");
    }
}