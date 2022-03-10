package com.example.genspark.java_conference.Domain;

import com.example.genspark.java_conference.Services.CreateSchedule;

import java.util.ArrayList;
import java.util.Arrays;

public class TrackBuilder {
    ArrayList<Session> sessions;
    ArrayList<ArrayList<Session>> morningSessions = new ArrayList<>();
    ArrayList<ArrayList<Session>> afternoonSessions = new ArrayList<>();

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public TrackBuilder(ArrayList<Session> sessions){
        this.sessions = sessions;
    }

    private void sumUpRecursive(ArrayList<Session> sessions, int target, ArrayList<Session> partial) {
        int s = 0;
        for (Session sesh: partial) {
            int x = sesh.getLength();
            s += x;
        }
        if (s == target){
            ArrayList<Session> matched = new ArrayList<>();
            //String seshes = "";
            for(Session sesh : partial){
                //seshes += sesh.getTitle() + ", ";
                matched.add(sesh);
            }
            if(target == 180){
                this.morningSessions.add(matched);
            }
            else {
                this.afternoonSessions.add(matched);
            }
            //System.out.println("seshes[" + seshes + "] = " + target);
        }
        if (s >= target)
            return;
        for(int i=0;i<sessions.size();i++) {
            ArrayList<Session> remaining = new ArrayList<Session>();
            Session n = sessions.get(i);
            for (int j=i+1; j<sessions.size();j++) remaining.add(sessions.get(j));
            ArrayList<Session> partial_rec = new ArrayList<Session>(partial);
            partial_rec.add(n);
            sumUpRecursive(remaining,target,partial_rec);
        }

    }

    public void getMorningSessions(){
        this.sumUpRecursive(this.sessions, 180, new ArrayList<Session>());
        System.out.println(this.morningSessions.size());
    }

    public void getAfternoonSessions() {
        this.sumUpRecursive(this.sessions, 240, new ArrayList<Session>());
        this.sumUpRecursive(this.sessions, 300, new ArrayList<Session>());
        System.out.println(this.afternoonSessions.size());
    }

    public void buildTrack() {
        this.getMorningSessions();
        this.getAfternoonSessions();
        ArrayList<Session> mornSeshes = this.morningSessions.get(0);
        System.out.println(mornSeshes);
        ArrayList<Session> aftSeshes = new ArrayList<>();
        for(int i = 0; i < this.afternoonSessions.size(); i++){
            boolean invalid = this.afternoonSessions.get(i).stream().filter(x -> mornSeshes.contains(x)).count() > 0;
            if(!invalid){
                aftSeshes = this.afternoonSessions.get(i);
                break;
            }
        }
        System.out.println(mornSeshes.toString());
        System.out.println(aftSeshes.toString());
        System.out.println((this.sessions.containsAll(mornSeshes) ? "Found" : "Not Found"));
        this.sessions = this.takeaway(mornSeshes, aftSeshes);
    }

    private ArrayList<Session> takeaway(ArrayList<Session> monr, ArrayList<Session> aft){
        ArrayList<Session> blah = this.sessions;
        for(Session sesh : monr){
            if(blah.contains(sesh)){
                blah.remove(sesh);
            }
        }
        for(Session mesh : aft){
            if(blah.contains(mesh)){
                blah.remove(aft);
            }
        }
        return blah;
    }



}
