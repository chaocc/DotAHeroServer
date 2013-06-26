package com.electrotank.examples.chatlogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * An Event that is sent as a User is chatting
 */
public class ChatEvent {

    public static final SimpleDateFormat formatter =
            new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
    
    private final String userName;
    private final String roomName;
    private final String message;
    private final Date date;
    private final String filename;

    public ChatEvent(String userName, String message, int roomId, int zoneId) {
        this.userName = userName;
        roomName = "" + zoneId + "_" + roomId;
        this.message = message;
        this.date = new Date();
        filename = setFilename();
    }

    public String getUserName() {
        return userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        String logLine = date + "\t" + getUserName() + "\t" + getMessage() + "\n";

        return logLine;
    }

    private String setFilename() {
        return getRoomName() + "_" + formatter.format(date) + ".txt";
    }

    public String getFilename() {
        return filename;
    }
}
