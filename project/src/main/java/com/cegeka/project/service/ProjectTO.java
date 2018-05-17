package com.cegeka.project.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ProjectTO {

    private long id;
    private String workorder;
    private LocalDate date;
    private String description;
    private double hours;
    private String employee;


    public ProjectTO() {
    }
}
