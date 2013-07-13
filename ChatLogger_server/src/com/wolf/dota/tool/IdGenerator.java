package com.wolf.dota.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IdGenerator {
    static String sourceFilePath = "doc/CardIds";
    static String targetFilePath = "doc/formatted_ids";
    
    public static void main(String... args) {
        try {
            new IdGenerator()
                    .formatOutput(new File(sourceFilePath), new File(targetFilePath));
        } catch (IOException e) {
            System.err.println("ioe");
            e.printStackTrace();
        }
        //        System.out.println(f.getAbsolutePath());
    }
    
    public void formatOutput(File source, File target) throws IOException {
        if (!source.exists()) {
            System.err.println("source file not exist");
            return;
        }
        FileWriter fWriter = new FileWriter(target, true);
        BufferedWriter writer = new BufferedWriter(fWriter);
        
        BufferedReader reader = new BufferedReader(new FileReader(source));
        startNewSectionInFile(writer);
        String line;
        while ((line = reader.readLine()) != null) {
            writeLineToFile(line, reader, writer);
            
        }
        reader.close();
        writer.close();
    }
    
    String format       = "";
    String cardFunction = "";
    
    private void writeLineToFile(String line, BufferedReader reader,
            BufferedWriter writer)
            throws IOException {
        System.out.println("line: " + line);
        if (line.trim().equals("")) { return; }
        
        boolean containsCardInfo = line.contains("黑桃")
                || line.contains("方块")
                || line.contains("方片")
                || line.contains("草花")
                || line.contains("梅花")
                || line.contains("红桃")
                || line.contains("红心");
        
        if (!containsCardInfo) {
            System.out.println("cardFunction: " + cardFunction);
            if (line.contains("：")) {
                cardFunction = line.substring(0, line.indexOf("："));
            } else if (line.contains(":")) {
                cardFunction = line.substring(0, line.indexOf(":"));
            }
            
        } else if (containsCardInfo) {
            String functionName = cardFunction;
            String separater = "-";
            if (line.contains("－")) {
                separater = "－";
            } else if (line.contains("-")) {
                separater = "-";
            } else {
                return;
            }
            String cardFace = line.substring(0, line.indexOf(separater)).trim();
            int cardId = Integer.parseInt(line.substring(line.indexOf(separater) + 1).trim());
            String suit = "";
            if (cardFace.contains("方")) {
                suit = "suit_diamond";
            } else if (cardFace.contains("红")) {
                suit = "suit_heart";
            } else if (cardFace.contains("黑")) {
                suit = "suit_spade";
            } else if (cardFace.contains("花")) {
                suit = "suit_club";
            }
            String faceNumberString = cardFace.substring(2);
            int faceNumber = -1;
            if (faceNumberString.equals("A")) {
                faceNumber = 1;
            } else if (faceNumberString.equals("J")) {
                faceNumber = 11;
            } else if (faceNumberString.equals("Q")) {
                faceNumber = 12;
            } else if (faceNumberString.equals("K")) {
                faceNumber = 13;
            } else {
                faceNumber = Integer.parseInt(faceNumberString);
            }
            line = "_" + cardId + "(" + cardId + ", \"" + functionName + "\", " + faceNumber
                    + ", " + faceNumber + " + CardEnum." + suit + "),";
            
            writer.append(line);
            writer.newLine();
        }
    }
    
    private void startNewSectionInFile(BufferedWriter writer) throws IOException {
        
        writer.newLine();
        writer.newLine();
        writer.newLine();
        writer.append("=========>> section start at " + t.getCurrentTime()
                + " <<============");
        writer.newLine();
    }
    
}
