package com.cegeka.timeprovider;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class Timeprovider {

    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDate getNow(){
        return LocalDate.now(clock);
    }

    public static void freezeTime(LocalDateTime now){
        clock = Clock.fixed(now.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }

}
