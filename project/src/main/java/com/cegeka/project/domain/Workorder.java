package com.cegeka.project.domain;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@ToString
public class Workorder {

    @Id
    private UUID id;
    private String workorder;

    Workorder(){

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workorder)) return false;
        Workorder workorder = (Workorder) o;
        return Objects.equals(id, workorder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
