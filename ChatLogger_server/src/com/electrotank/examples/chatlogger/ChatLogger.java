package com.electrotank.examples.chatlogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatLogger extends Thread {
    private static final Logger logger = LoggerFactory.getLogger( ChatLogger.class    );
    private final BlockingQueue<ChatEvent> chatEvents = new LinkedBlockingQueue<ChatEvent>();
    private static final String DIRNAME = "./chatLogs/";
    private boolean shuttingDown = false;

    
    public ChatLogger()  {
        setName( getClass().getSimpleName() );
        
        logger.warn("Managed object ChatLogger created");
    }
    
    public void addChatEvent( String user, String chatLine, 
            int roomId, int zoneId ) {
        ChatEvent event = new ChatEvent(user, chatLine, roomId, zoneId);
        chatEvents.add( event );
    }
    
    
    @Override
    public void run() {
        logger.warn( "Chat logger starting" );

        while (!Thread.interrupted() && !shuttingDown) {
            List<ChatEvent> events = new ArrayList<ChatEvent>();

            try {
                events.add( this.chatEvents.take() );
            } catch( InterruptedException ignored ) {
                break;
            }

            this.chatEvents.drainTo( events );

            for ( ChatEvent event : events ) {
                log( event );
            }
        }

        logger.debug( "Chat logger terminating" );
    }

    private void log( ChatEvent event ) {
        String logLine = event.toString();
        logger.debug("ChatLogger.log: {}", logLine);
        String fileName = DIRNAME + event.getFilename();

        Writer writer = null;
        
        File dir = new File(DIRNAME);
        if (!dir.exists()) {
            boolean success = dir.mkdir();
            if (!success) {
                logger.error("ChatLogger unable to find or create {}", DIRNAME);
                return;
            }
        }
        File file = new File(fileName);
        if (!file.exists()) {
            boolean success;
            try {
                success = file.createNewFile();
                if (!success) {
                    logger.error("ChatLogger unable to find or create {}", fileName);
                    return;
            }
            } catch (IOException ex) {
                logger.error("ChatLogger unable to find or create {}", fileName, ex);
                return;
            }
        }

        try {
            writer = new OutputStreamWriter( new FileOutputStream( file, true ), "UTF-8" );

            writer.write( logLine );
            writer.flush();
        } catch( IOException e ) {
            logger.error( "Unable to write chat event to log. File: " + fileName + " Event: " + logLine, e );
        } finally {
            if ( null != writer ) {
                try {
                    writer.close();
                } catch( IOException e ) {
                    logger.warn( "Error closing " + fileName, e );
                }
            }
        }
    }

    public void dispose() throws Exception {
        logger.warn("ChatLogger.dispose invoked");
        shuttingDown = true;
    }

}
