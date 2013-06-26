/*
 * ChatLoggerClientApp.java
 */

package com.electrotank.electroserver5.examples.chatloggerclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ChatLoggerClientApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            show(new View(this));
        } catch (IOException ex) {
            Logger.getLogger(ChatLoggerClientApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ChatLoggerClientApp
     */
    public static ChatLoggerClientApp getApplication() {
        return Application.getInstance(ChatLoggerClientApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ChatLoggerClientApp.class, args);
    }
}
