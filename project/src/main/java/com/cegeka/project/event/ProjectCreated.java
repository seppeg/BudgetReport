package com.cegeka.project.event;

import lombok.*;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreated {

    private UUID id;
    private String description;
    private String workorder;
    private double budget;
}
