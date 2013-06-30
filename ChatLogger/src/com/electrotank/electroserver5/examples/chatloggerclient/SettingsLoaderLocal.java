package com.electrotank.electroserver5.examples.chatloggerclient;

import com.electrotank.electroserver5.client.server.Server;
import com.electrotank.electroserver5.client.util.SettingsXMLParser;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import java.io.*;
import java.util.List;

public class SettingsLoaderLocal {

    private ClassLoader loader;
    private String xmlPath = "./settings.xml";
    
    public List<Server> readServerSettings() throws IOException, Exception {
        return readServerSettings(xmlPath);
    }
    
    public String lookForFileHere(String filename) {
        InputStream in = null;
        File file = new File(filename);
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                return filename + " worked!";
            } catch (FileNotFoundException ex) {
                return "File exists but FileNotFoundException thrown";
            }finally{
                IOUtils.closeQuietly(in);
            }
        } else {
            return null;
        }
        
    }
    
    public String getXMLasString(String path) {
        loader = this.getClass().getClassLoader();
        InputStream in = null;
        String results = "about to try";
        String filename = "settings.xml";
        
        // Try and find the file on the file system
        File file = new File(filename);
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                return filename + " worked!";
            } catch (FileNotFoundException ex) {
                return "File exists but FileNotFoundException thrown";
            }finally{
                IOUtils.closeQuietly(in);
            }
        } else {
            // Try and get the file stream via the classpath
            in = loader.getResourceAsStream(filename);

            // If we failed, error
            if (in == null) {
                return "Unable to locate file '" + filename + "'";
            }
        }
        try {

            results = IOUtils.toString(in, "UTF-8");
        } catch (IOException ex) {
            return "Error converting stream to string";
        }
        
//        try {
//            loader.
//            in = loader.getResourceAsStream(path);
//            results = IOUtils.toString(in, "UTF-8");
//        } catch (IOException iOException) {
//            return "Unable to locate settings '" + xmlPath + "'";
//        } catch (Exception e) {
//            return "Exception: " + e.getMessage();
//        } finally {
//            // Release the connection.
//            IOUtils.closeQuietly(in);
//        }
        return results;
    }
    
    public List<Server> readServerSettings(String filename) throws IOException, Exception {
        // read the XML file
        loader = this.getClass().getClassLoader();
        InputStream in = null;
        String results = null;

        // Try and find the file on the file system
        File file = new File(filename);
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                results = IOUtils.toString(in, "UTF-8");
            } catch (FileNotFoundException ex) {
                throw new FileNotFoundException("File exists but FileNotFoundException thrown");
            } finally {
                // Release the connection.
                IOUtils.closeQuietly(in);
            }
        } else {
            // Try and get the file stream via the classpath
            try {
                in = loader.getResourceAsStream(filename);
                results = IOUtils.toString(in, "UTF-8");
            } catch (IOException iOException) {
                throw new IOException("Unable to locate settings '" + xmlPath + "'");
            } catch (Exception e) {
                throw new Exception("Exception: " + e.getMessage());
            } finally {
                // Release the connection.
                IOUtils.closeQuietly(in);
            }

        }

        // convert from string to XML
        Document doc = SettingsXMLParser.parseStringToDocument(results);
        List<Server> servers = SettingsXMLParser.parse(doc);
        
        return  servers;
    }



}
