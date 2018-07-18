package com.codecool.shop.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminLog {
    public static AdminLog instance = null;

    private AdminLog(){};

    public static AdminLog getInstance() {
        if (instance == null) {
            instance = new AdminLog();
        }
        return instance;
    }

    private String readLatestOrderId() {
        String id = "";
        FileReader reader = null;
        try
        {reader = new FileReader(System.getProperty("user.dir") + "\\log\\LatestOrderId.txt");}
        catch(IOException ex) {System.err.println("Caught IOException: " + ex.getMessage());}


        try(BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            id = sb.toString().substring(0, id.length() - 4);
        } catch(IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        return id;
    }


    public void createLogFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        String id = readLatestOrderId();
        File dir = new File(System.getProperty("user.dir") + "/log");
        File logFile = new File(dir, id + "-" + timeStamp + ".txt");
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        }
        catch(IOException ex){
            System.err.println("Caught IOException: " + ex.getMessage());
        }
    }


}
