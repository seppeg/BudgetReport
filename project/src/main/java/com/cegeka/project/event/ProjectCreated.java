package com.cegeka.project.event;

import com.cegeka.project.domain.Workorder;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreated {

    private UUID id;
    private String description;
    private List<Workorder> workorders;
    private double budget;
}
