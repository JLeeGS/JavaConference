package com.example.genspark.java_conference.Services;

import com.example.genspark.java_conference.Domain.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseFile {
    public ArrayList<Session> readSchedule(String path){
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
        return arrs;
    }
}
