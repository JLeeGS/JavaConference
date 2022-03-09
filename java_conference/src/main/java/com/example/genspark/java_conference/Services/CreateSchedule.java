package com.example.genspark.java_conference.Services;

import com.example.genspark.java_conference.Domain.*;

import java.util.*;
import java.util.stream.Collectors;

public class CreateSchedule {
    //Afternoon Session begin 1pm and must finish in time for Networking event
    //All talk lengths are either in minutes or lightning (5mins)
    //Each Session contains multiple Tasks
    //Morning Sessions begin at 9am and must finish by 12 noon for lunch
    //No talk title has numbers in it
    //Presenters will be punctual, no gaps inbetween sessions
    //Conference has multiple tracks each of which has a morning and afternoon session
    //The networking event can start no earlier than 4 and no later than 5

    public boolean hasAllSession(ArrayList<Session> sessArrs1, ArrayList<Session> sessArrs2){
        Set<Session> set1=sessArrs1.stream().collect(Collectors.toSet());
        Set<Session> set2=sessArrs2.stream().collect(Collectors.toSet());
        return set1.stream().anyMatch(x->set2.contains(x));
    }

    public ArrayList<Session> getSessionByLength(ArrayList<Session> sessArrs, int timeLength){
        return sessArrs.stream().filter(s->s.getLength()==timeLength).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Session> compareArrs(ArrayList<Session> sess1, ArrayList<Session> sess2){
        Set<Session> set1=sess1.stream().collect(Collectors.toSet());
        Set<Session> set2=sess2.stream().collect(Collectors.toSet());
        return set1.stream().filter(x->set2.contains(x)).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean containsTime(ArrayList<Timeslot> timeslots, Time time){
        Set<Integer> timeSet1= timeslots.stream().map(Timeslot::getTime).mapToInt(x->x.getHour()*60+ x.getMinutes()).boxed().collect(Collectors.toSet());
        Integer timeInMins=time.getHour()*60+time.getMinutes();
        return timeSet1.stream().anyMatch(x->x.equals(timeInMins));
    }

//    public TreeMap<Time,ArrayList<Track>> createSchedule(TreeMap<Time,ArrayList<Track>> timeslots, ArrayList<Session> file) {
//        //start 9, end 17
//        int hour = 9, minutes = 0, prev = 0;
//            ArrayList<Track> track1 = timeslots.values().stream().collect(Collectors.toCollection(ArrayList::new)).get(0);
//                ArrayList<Track> track2 = timeslots.values().stream().collect(Collectors.toCollection(ArrayList::new)).get(1);
//                    ArrayList<Track> allTracks= track1; allTracks.addAll(track2);
//                    Session session=null;
//        //while ((compareArrs(allTracks.get(0).getSession(),file).isEmpty())){
//            for (Session sess : file) {
//                if (hour > 24) {
//                    hour = hour - 24;
//                }
//                int length = sess.getLength();
//                if (((getTotalTrackTime(track1.get(0)) + sess.getLength() <= 60) &
//                        (!hasSession(timeslots, sess)))) {
//                    timeslots.put(new Time(hour, 0),
//                            new ArrayList<>(Arrays.asList(
//                                    new Track(hour,
//                                            new ArrayList<Session>(Arrays.asList(sess))))));
//                } else if (((getTotalTrackTime(track2.get(1)) + sess.getLength() <= 60) &
//                        (!hasSession(timeslots, sess)))) {
//                    timeslots.put(new Time(hour, 0),
//                            new ArrayList<>(Arrays.asList(
//                                    new Track(hour,
//                                            new ArrayList<Session>(Arrays.asList(sess))))));
//                }
//                hour++;
//            }
//        //}
//        return timeslots;
//    }

}
