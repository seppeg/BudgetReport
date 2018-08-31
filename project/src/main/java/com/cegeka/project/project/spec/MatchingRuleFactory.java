package com.cegeka.project.project.spec;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class MatchingRuleFactory {

    private final Map<String, MatchingRuleFactoryMethod> factoryMethods;

    public MatchingRuleFactory() {
        this.factoryMethods = new HashMap<>();
        this.factoryMethods.put(WorkOrderMatchingRuleR.class.getSimpleName(), matchingRuleR -> createWorkOrderMatchingRule((WorkOrderMatchingRuleR) matchingRuleR));
    }

    @SuppressWarnings("unchecked")
    public <R extends MatchingRuleR, T extends MatchingRule> T create(R matchingRuleR) {
        MatchingRuleFactoryMethod factoryMethod = checkNotNull(factoryMethods.get(matchingRuleR.getClass().getSimpleName()));
        return (T) factoryMethod.create(matchingRuleR);
    }

    @FunctionalInterface
    interface MatchingRuleFactoryMethod<R extends MatchingRuleR, T extends MatchingRule> {
        T create(R matchingRule);
    }

    private WorkOrderMatchingRule createWorkOrderMatchingRule(WorkOrderMatchingRuleR matchingRuleR) {
        return new WorkOrderMatchingRule(matchingRuleR.getWorkOrder());
    }
}
