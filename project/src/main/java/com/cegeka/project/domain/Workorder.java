package com.cegeka.project.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Workorder {

    @Id
    private UUID id;
    private String workorder;

    public Workorder(String workorder) {
        this.workorder = workorder;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getWorkorder() {
        return workorder;
    }
}
