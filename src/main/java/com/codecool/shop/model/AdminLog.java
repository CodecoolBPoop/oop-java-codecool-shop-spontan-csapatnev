package com.codecool.shop.model;

import com.google.gson.Gson;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;


public class AdminLog {

    private static AdminLog instance = null;
    private final String LOG_DIR_PATH = System.getProperty("user.dir") + "/log";
    private final String LATEST_ID_FILE_PATH = System.getProperty("user.dir") + "/log/LatestOrderId.txt";
    private static int idx = 0;

    private AdminLog() {
    }


    public static AdminLog getInstance() {

        if (instance == null) {
            instance = new AdminLog();
        }
        return instance;
    }

    /**
     * Reads and returns the latest Order ID from /log/LatestOrderId.txt
     *
     * @return Latest order ID in string.
     */
    String readLatestOrderId() {

        String id = "";
        FileReader reader = null;

        try {
            reader = new FileReader(LATEST_ID_FILE_PATH);
        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }

        try (BufferedReader br = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            id = sb.toString().trim();
        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        return id;
    }

    /**
     * Increases the latest order ID by 1 in /log/LatestOrderId.txt
     */
    void increaseLatestOrderId() {

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
     * Creates a log .json file with name: orderId--yyyy-MM-dd, writeLogToFile() uses it
     *
     * @return name of that file
     */
    private String createLogFile(HttpSession session) {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        Order order = (Order) session.getAttribute("currentOrder");
        String id = Integer.toString(order.getId());
        File logFile = new File(LOG_DIR_PATH + id + "--" + timeStamp + ".json");

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
                System.out.println("Successfully created logfile: " + logFile.toString());
            }
        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        return logFile.toString();
    }

    /**
     * Retrieves all the final logs from the session that is stored in the linked hash map
     * and writes it to the corresponding .json file
     *
     * @param session the HttpSession in which the linked hash map can be found
     */
    @SuppressWarnings("unchecked")
    public void writeLogsToFile(HttpSession session) {

        LinkedHashMap<Long, String> log = (LinkedHashMap) session.getAttribute("AdminLog");
        session.removeAttribute("AdminLog");

        try {
            String json = new Gson().toJson(log);
            FileWriter fw = new FileWriter(createLogFile(session));
            fw.write(json);
            fw.flush();
            fw.close();

            System.out.println("Successfully Copied log from session to File...");
            System.out.println("\nlog: " + log);

        } catch (IOException ex) {
            System.err.println("Caught IOException: " + ex.getMessage());
        }
        idx = 0;
    }

    /**
     * Retrieves a linked hash map ("AdminLog") from the session and adds ONE log to it.
     *
     * @param session the HttpSession in which the linked hash map can be found
     * @param value   value of the log to add
     */
    @SuppressWarnings("unchecked")
    public void addLog(HttpSession session, String value) {
        Long time = idx++ + System.currentTimeMillis();
        LinkedHashMap<Long, String> log = (LinkedHashMap) session.getAttribute("AdminLog");
        log.put(time, Integer.toString(idx) + " - " + value);

    }

}
