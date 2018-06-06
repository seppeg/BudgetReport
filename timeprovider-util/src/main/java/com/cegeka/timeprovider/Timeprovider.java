package com.cegeka.timeprovider;

import java.time.*;

public class Timeprovider {

    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDate getNow(){
        return LocalDate.now(clock);
    }


    public static Year getCurrentYear(){
        return Year.now(clock);
    }

    public static YearMonth getCurrentYearMonth(){
        return YearMonth.now(clock);
    }

    public static void freezeTime(LocalDateTime now){
        clock = Clock.fixed(now.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
    }

}
