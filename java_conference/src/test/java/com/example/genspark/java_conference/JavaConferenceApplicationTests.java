package com.example.genspark.java_conference;

import com.example.genspark.java_conference.Domain.Session;
import com.example.genspark.java_conference.Domain.Time;
import com.example.genspark.java_conference.Domain.Timeslot;
import com.example.genspark.java_conference.Services.CreateSchedule;
import com.example.genspark.java_conference.Services.UseFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class JavaConferenceApplicationTests {

	@BeforeEach
	void setUp(){
		System.out.println("Starting Test");
	}

	@Test
	void useFile() {
		ArrayList<Session> arrs=new ArrayList<>();
		try{
			File file=new File("src/main/resources/JavaConference2020.txt");
			Scanner scan= new Scanner(file);
			while(scan.hasNextLine()){
				String next=scan.nextLine(), stime="";
				int index=0, length=next.length(), time=0;
				Matcher m= Pattern.compile("[0-9]+[0-9]+min").matcher(next);
				if(m.find()) {
					stime=m.group(0).toString();
					index=next.indexOf(stime);
					time=Integer.parseInt(stime.substring(0,2));
				}
				else{
					index=next.indexOf("lightning");
					time=5;
				}
				arrs.add(new Session(next.substring(0,index), time));
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		Collections.sort(arrs);
	}

	@Test
	void generateTimeslots(){
		UseFile useFile=new UseFile(); CreateSchedule createSchedule=new CreateSchedule();
		ArrayList<Session> file=useFile.readSchedule("src/main/resources/JavaConference2020.txt");
		ArrayList<Timeslot> timeslots=new ArrayList<>();
			Session lunchSess=new Session("Lunch",60); Session netSess=new Session("Networking",60);
		timeslots.add(new Timeslot(
						new Time(12, 0),
							new ArrayList<Session>(
									Arrays.asList(lunchSess, lunchSess))));
		timeslots.add(new Timeslot(
				new Time(17, 0),
				new ArrayList<Session>(
						Arrays.asList(netSess, netSess))));

		int[] hours= {9,10,11,12,13,14,15,16,17};
		for (int i=0; i< file.size(); i++) {
			Set<Time> timeSet=timeslots.stream().map(Timeslot::getTime).collect(Collectors.toSet());
				ArrayList<Session> sessionSet=timeslots.stream().map(Timeslot::getSessionArrs).flatMap(x->x.stream()).collect(Collectors.toCollection(ArrayList::new));
				Time sessPrevLength=new Time(9,0);
					if(timeslots.size()>2){
						sessPrevLength= timeslots.get(i-1).getTime();
					}
					if((!createSchedule.containsTime(timeslots,sessPrevLength))|createSchedule.hasAllSession(file,sessionSet)) {
								//need latest session length of the track
								timeslots.add(new Timeslot(sessPrevLength,
												new ArrayList<Session>(
													Arrays.asList(file.get(i)))));
							}
					//need to add constraints
			}
		Collections.sort(timeslots);
		timeslots.stream().forEach(System.out::println);
	}

	@Test
	void containsTime(){
		CreateSchedule createSchedule=new CreateSchedule();
		ArrayList<Timeslot> timeslots=new ArrayList<>();
		Session lunchSess=new Session("Lunch",60); Session netSess=new Session("Networking",60);
		timeslots.add(new Timeslot(
				new Time(12, 0),
				new ArrayList<Session>(
						Arrays.asList(lunchSess, lunchSess))));
		Time time=new Time(12,0);
		System.out.println(createSchedule.containsTime(timeslots,time));
	}

	@Test
	void getSessionByLengthTest(){
		UseFile useFile=new UseFile(); CreateSchedule createSchedule=new CreateSchedule();
		ArrayList<Session> file=useFile.readSchedule("src/main/resources/JavaConference2020.txt");
		Collections.sort(file);
		ArrayList<Session> fourtyFive=createSchedule.getSessionByLength(file,45);
		file.removeAll(fourtyFive); //file.addAll(fourtyFive);
			ArrayList<Session> newSort=new ArrayList<>(); newSort.addAll(file); newSort.addAll(fourtyFive);
			newSort.stream().forEach(System.out::println);
	}

	@Test
	void hasAllSessions(){
		Session session=new Session("TestSession",10);
		ArrayList<Session> sessArrs1= new ArrayList<>();sessArrs1.add(session);
		ArrayList<Session> sessArrs2= new ArrayList<>(Arrays.asList(session, new Session("Test2",5)));
			System.out.println(sessArrs1.stream().anyMatch(x->sessArrs2.contains(x)));
	}

	@Test
	void getSessions(){
		UseFile useFile=new UseFile(); CreateSchedule createSchedule=new CreateSchedule();
		ArrayList<Session>file=useFile.readSchedule("src/main/resources/JavaConference2020.txt");
		//file.removeAll(currSess);
		//file.forEach(System.out::println);
		//timeslots.forEach((k,v)->System.out.println(k+"\n"+v.get(0)+"\n"+v.get(1)+""));
		//timeslots.forEach((k,v)->System.out.println(k+" "+v));
		//timeslots.forEach((k,v)->System.out.println(v.get(0).getTitle()+"\n"+v.get(1).getTitle()));
	}


	@Test
	void hasSession(){
		TreeMap<Time,ArrayList<ArrayList<Session>>> timeslots=new TreeMap<>();
		Session sess=new Session("Test", 12);
		timeslots.put(new Time(1,0),new ArrayList<>());timeslots.get(new Time(1,0)).add(new ArrayList<>(Arrays.asList(sess)));
		ArrayList<ArrayList<Session>> sessArrs= timeslots.values().stream().collect(Collectors.toCollection(ArrayList::new)).get(0);
		System.out.println(sessArrs.stream().anyMatch(x->x.contains(sess)));
	}
	@Test
	void getTotalSessionTime(){
		UseFile useFile=new UseFile();
		ArrayList<Session> sessions=useFile.readSchedule("src/main/resources/JavaConference2020.txt");
		TreeMap<Time,ArrayList<Session>> trackMap=new TreeMap<>();
		ArrayList<Session> trackArrs=new ArrayList<Session>(Arrays.asList(new Session("Test1",10),new Session("Test2", 15)));
			trackMap.put(new Time(12,0), trackArrs);
		System.out.println(sessions.stream().mapToInt(Session::getLength).sum());
		System.out.println(trackMap.get(new Time(12,0)).stream().mapToInt(Session::getLength).sum());
	}

	@Test
	void getSessionByLength(){
		UseFile useFile=new UseFile(); CreateSchedule createSchedule=new CreateSchedule();
		ArrayList<Session>file=useFile.readSchedule("src/main/resources/JavaConference2020.txt");
		ArrayList<Session> thirties=createSchedule.getSessionByLength(file,30);
			System.out.println(thirties);
	}

	@AfterEach
	void tearDown(){
		System.out.println("Tests Complete");
	}

}
