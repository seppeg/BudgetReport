package com.cegeka;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Booking {

    @Id
    private long id;

    Booking(){

    }

    public long getId() {
        return id;
    }
}
