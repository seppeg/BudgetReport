package com.cegeka.project.project.spec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ProjectSpecificationServiceTest {

    private ProjectSpecificationService projectSpecificationService;

    @Mock
    private ProjectSpecificationRepository projectSpecificationRepository;


    @BeforeEach
    void setUp() {
        projectSpecificationService = new ProjectSpecificationService(new MatchingRuleFactory(), projectSpecificationRepository);
    }

    @Test
    void createProjectSpecification() {
        UUID projectId = randomUUID();
        CreateProjectSpecificationR createProjectSpecificationR = new CreateProjectSpecificationR(projectId, singletonList(new WorkOrderMatchingRuleR("workOrder")));

        projectSpecificationService.createProjectSpecification(createProjectSpecificationR);

        ProjectSpecification expectedSpecification = new ProjectSpecification(projectId);
        expectedSpecification.addMatchingRule(new WorkOrderMatchingRule("workOrder"));

        ArgumentCaptor<ProjectSpecification> captor = ArgumentCaptor.forClass(ProjectSpecification.class);
        verify(projectSpecificationRepository).save(captor.capture());
        ProjectSpecification result = captor.getValue();
        assertThat(result.getProjectId()).isEqualTo(projectId);
        assertThat(result.getMatchingRules()).hasSize(1);
        MatchingRule matchingRule = result.getMatchingRules().iterator().next();
        assertThat(matchingRule).isInstanceOf(WorkOrderMatchingRule.class);
        assertThat(((WorkOrderMatchingRule)matchingRule).getWorkOrder()).isEqualTo("workOrder");
    }
}