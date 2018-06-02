package com.cegeka.project.domain.workorder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class WorkOrder {

    @Id
    private UUID id;

    private String workOrder;

    public WorkOrder(String workOrder) {
        this.workOrder = workOrder;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkOrder)) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return Objects.equals(id, workOrder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
