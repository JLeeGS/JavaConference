package com.example.genspark.java_conference.Domain;

import java.util.ArrayList;

public class Session implements Comparable<Session> {
    private String title;
    private int length;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public Session(){
        super();
    }

    public Session (String title, int length){
        this.title=title;
        this.length=length;
    }

    @Override
    public int compareTo(Session sessionLength) {
        int compareTime=((Session) sessionLength).getLength();
        return compareTime-this.getLength(); //descending
    }

    @Override
    public String toString(){
        return "Title: "+ getTitle()+" Length: "+getLength()+"min";
    }
}
