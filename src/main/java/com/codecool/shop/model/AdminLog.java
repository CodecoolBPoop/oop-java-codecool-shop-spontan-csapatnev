package com.codecool.shop.model;

import org.json.simple.JSONObject;
import javax.servlet.http.HttpSession;
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

    /**
     * Reads and returns the latest Order ID from /log/LatestOrderId.txt
     * @return Latest order ID in string.
     */
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

    /**
     * Increases the latest order ID by 1 in /log/LatestOrderId.txt
     */
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

    /**
     * Creates a log .txt file with name: orderId--yyyy-MM-dd, writeLogToFile() uses it
     * @return name of that file
     */
    public String createLogFile() {

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
        return logFile.toString();
    }

    /**
     * Retrieves all the final logs from the session that is stored in a JSONObject
     * and writes it to the corresponding log file
     * @param session the HttpSession in which the JSONObject can be found
     */
    public void writeLogToFile(HttpSession session) {

        JSONObject log = (JSONObject)session.getAttribute("AdminLog");
        session.removeAttribute("AdminLog");

        try {
            FileWriter fw = new FileWriter(LOG_DIR_PATH + createLogFile());
            fw.write(log.toJSONString());

            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + log);

        }catch(IOException ex){
            System.err.println("Caught IOException: " + ex.getMessage());
        }
    }

    /**
     * Retrieves the JSONObject from the session and adds ONE log to it.
     * @param session the HttpSession in which the JSONObject can be found
     * @param key key of the log to add eg.: "Username"
     * @param value value of the log to add eg.: "John Smith"
     */
    public static void addLog(HttpSession session, String key, String value) {

        JSONObject log = (JSONObject)session.getAttribute("AdminLog");
        session.removeAttribute("AdminLog");
        log.put(key, value);
        session.setAttribute("AdminLog", log);

    }

}
