package com.cegeka.project.project.spec;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectSpecificationService {

    private final MatchingRuleFactory matchingRuleFactory;
    private final ProjectSpecificationRepository projectSpecificationRepository;

    public void createProjectSpecification(CreateProjectSpecificationR createProjectSpecificationR){
        ProjectSpecification specification = new ProjectSpecification(createProjectSpecificationR.getProjectId());
        addMatchingRules(specification, createProjectSpecificationR);
        projectSpecificationRepository.save(specification);
    }

    private void addMatchingRules(ProjectSpecification specification, CreateProjectSpecificationR createProjectSpecificationR) {
        createProjectSpecificationR.getMatchingRules()
                .stream()
                .<MatchingRule>map(matchingRuleFactory::create)
                .forEach(specification::addMatchingRule);
    }
}
