package com.cegeka.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @RequestMapping("/")
    public String getAll() {
        return "Greetings from Spring Boot!";
    }
}
