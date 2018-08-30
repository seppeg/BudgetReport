package com.cegeka.project.project.spec;

import com.cegeka.project.booking.BookingEvent;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.UUID;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MatchingRule {

    @Id
    private UUID id;

    MatchingRule(){}

    public MatchingRule(UUID id){
        this.id = id;
    }

    public abstract boolean matches(BookingEvent bookingEvent);

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchingRule that = (MatchingRule) o;

        return id.equals(that.id);
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }
}
