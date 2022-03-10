package com.example.genspark.java_conference.Domain;

import java.util.ArrayList;

public class Display {
    private ArrayList<ArrayList<Timeslot>> tracks = new ArrayList<>();

    public ArrayList<ArrayList<Timeslot>> getTracks() {
        return this.tracks;
    }

    public void setTracks(ArrayList<ArrayList<Timeslot>> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(ArrayList<Timeslot> track){
        this.tracks.add(track);
    }

    public void display() {
        for(int i = 0; i < this.tracks.size(); i++){
            System.out.println(String.format("Track %d: ", i + 1));
            ArrayList<Timeslot> track = this.tracks.get(i);
            for(int j = 0; j < track.size(); j++){
                System.out.println(String.format("%s     %s     %s", track.get(j).getTime().getTimeTwelveHourFormat(),
                        track.get(j).getSession().getTitle(), track.get(j).getSession().lengthString()));
            }
            System.out.println();
        }
    }
}
