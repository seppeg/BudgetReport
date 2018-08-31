package com.cegeka.project.project.spec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MatchingRuleFactoryTest {

    private MatchingRuleFactory factory;

    @BeforeEach
    void setUp() {
        factory = new MatchingRuleFactory();
    }

    @Test
    void createWorkOrderMatchingRule() {
        MatchingRule result = factory.create(new WorkOrderMatchingRuleR("workOrder"));

        assertThat(result).isInstanceOf(WorkOrderMatchingRule.class);
        WorkOrderMatchingRule workOrderMatchingRule = (WorkOrderMatchingRule) result;
        assertThat(workOrderMatchingRule.getWorkOrder()).isEqualTo("workOrder");
    }
}