package com.codecool.shop.model;

import org.json.simple.JSONObject;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AdminLog {
    private static AdminLog instance = null;
    private final String LOG_DIR_PATH = System.getProperty("user.dir") + "\\log\\";
    private final String LATEST_ID_FILE_PATH = System.getProperty("user.dir") + "\\log\\LatestOrderId.txt";

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
        {reader = new FileReader(LATEST_ID_FILE_PATH);}
        catch(IOException ex) {System.err.println("Caught IOException: " + ex.getMessage());}

        try(BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            id = sb.toString().trim();
        } catch(IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        return id;
    }

    private void increaseLatestOrderId() {
        int latestId = Integer.parseInt(readLatestOrderId());

        try {
            FileWriter fw = new FileWriter(LATEST_ID_FILE_PATH);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(Integer.toString(++latestId));
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }

    }

    public File createLogFile() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String id = readLatestOrderId();
        increaseLatestOrderId();
        File logFile = new File(LOG_DIR_PATH + id + "--" + timeStamp + ".txt");

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
                System.out.println("Successfully created logfile: " + LOG_DIR_PATH + logFile.toString());
            }
        }catch(IOException ex){
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        return logFile;
    }

    public void writeLogToFile(File file, JSONObject obj) {
        String fileName = file.toString();

        try {
            FileWriter fw = new FileWriter(LOG_DIR_PATH + fileName);
            fw.write(obj.toJSONString());

            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);

        }catch(IOException ex){
            System.err.println("Caught IOException: " + ex.getMessage());
        }
    }

    public static void addLog(JSONObject obj, String key, String value) {
        obj.put(key, value);
    }
}
