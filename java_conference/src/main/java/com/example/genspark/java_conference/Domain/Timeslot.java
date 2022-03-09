package com.example.genspark.java_conference.Domain;

import java.util.ArrayList;

public class Timeslot {
    private Time time;
    private Session session;
    private ArrayList<Session> sessionArrs;

    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public ArrayList<Session> getSessionArrs() {
        return sessionArrs;
    }
    public void setSessionArrs(ArrayList<Session> sessionArrs) {
        this.sessionArrs = sessionArrs;
    }
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }

    public Timeslot(){
        super();
    }
//    public Timeslot(Time time, ArrayList<Session> sessionArrs){
//        this.time=time;
//        this.sessionArrs=sessionArrs;
//    }
    public Timeslot(Time time, Session session){
        this.time=time;
        this.session=session;
    }

//    @Override
//    public int compareTo(Timeslot timeslot) {
//        int compareTime= timeslot.getTime().getHour()*60+timeslot.getTime().getMinutes();
//        return (this.getTime().getHour()*60+this.getTime().getMinutes())-compareTime;
//    }

    @Override
    public String toString() {
        return "Timeslot{" +
                "Time=" + time +
                ", Session=" + session +
                '}';
    }
}
