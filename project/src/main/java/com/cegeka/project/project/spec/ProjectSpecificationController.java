package com.cegeka.project.project.spec;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class ProjectSpecificationController {

    private final ProjectSpecificationService projectSpecificationService;

    @PutMapping("/projectspec")
    public void createProjectSpecification(@RequestBody CreateProjectSpecificationR projectR) {
        projectSpecificationService.createProjectSpecification(projectR);
    }
}
