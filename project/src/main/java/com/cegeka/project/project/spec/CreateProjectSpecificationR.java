package com.cegeka.project.project.spec;

import lombok.Value;

import java.util.Collection;
import java.util.UUID;

@Value
public class CreateProjectSpecificationR {

    private final UUID projectId;
    private final Collection<MatchingRuleR> matchingRules;
}
