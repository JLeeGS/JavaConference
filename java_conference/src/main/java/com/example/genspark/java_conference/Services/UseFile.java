package com.example.genspark.java_conference.Services;

import com.example.genspark.java_conference.Domain.Session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseFile {
    public ArrayList<Session> readSchedule(String path) {
        ArrayList<Session> arrs = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scan = new Scanner(file);
            int i = 0;
            while (scan.hasNext()) {
                String next = scan.nextLine(), stime = "";
                int index = 0, length = next.length(), time = 0;
                Matcher m = Pattern.compile("[0-9]+[0-9]+min").matcher(next);
                if (m.find()) {
                    stime = m.group(0);
                    time = Integer.parseInt(stime.substring(0, 2));
                    index=next.indexOf(stime);
                    arrs.add(new Session(next.substring(0,index), time));
                }
                else if (next.contains("lightning")) {
                index=next.indexOf("lightning");
                time=5;
                arrs.add(new Session(next.substring(0,index), time));
                }
                else if (time == 0) {
                    String endArr = "", s = scan.next(), min = "";
                    endArr = next + " " + s;
                    if (scan.hasNext("[0-9][0-9]+min")) {
                        min = scan.next("[0-9][0-9]+min");
                        scan.nextLine();
                        time = Integer.parseInt(min.substring(0, 2));
                    }
                    arrs.remove(endArr);
                    arrs.add(new Session(endArr, time));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrs;
    }
}

