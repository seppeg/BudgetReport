package com.cegeka.project.project.spec;

import com.cegeka.project.booking.BookingEvent;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@ToString
@Entity
public class ProjectSpecification {

    @Id
    private UUID id;

    private UUID projectId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "project_specification_id", nullable = false)
    private Collection<MatchingRule> matchingRules = new ArrayList<>();

    ProjectSpecification(){}

    public ProjectSpecification(UUID projectId){
        this.id = UUID.randomUUID();
        this.projectId = projectId;
    }

    public void addMatchingRule(MatchingRule matchingRule){
        this.matchingRules.add(matchingRule);
    }

    public boolean matches(BookingEvent bookingEvent) {
        return this.matchingRules.stream().allMatch(rule -> rule.matches(bookingEvent));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectSpecification that = (ProjectSpecification) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
