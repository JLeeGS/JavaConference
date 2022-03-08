package com.example.genspark.java_conference.Domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private int hour, minutes;

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Time(){
        super();
    }
    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }
    public String getTimeTwelveHourFormat(){
        return LocalTime.of(getHour(),getMinutes()).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    @Override
    public String toString(){return "{Time: "+getTimeTwelveHourFormat()+"}";
    }
}
