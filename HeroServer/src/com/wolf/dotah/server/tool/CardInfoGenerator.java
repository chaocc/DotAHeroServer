package com.wolf.dotah.server.tool;


public class CardInfoGenerator {
    
    public static void main(String... args) {
    
        for (int i = 0; i < 80; i++) {
            int id = i;
            int faceNumber = getFaceNumber(i);
            String suits = getSuits(i);
            System.out.println("{\"id\":\"" + id + "\", \"suits\":\"" + suits
                    + "\", \"faceNumber\":\"" + faceNumber + "\", \"type\":\"b\", \"name\":\"普通攻击\" },");
        }
    }
    
    
    public static int getFaceNumber(int i) {
    
        int faceNumber = (i + 1) % 10;
        if (faceNumber == 0) {
            faceNumber = 10;
        }
        return faceNumber;
    }
    
    
    public static String getSuits(int i) {
    
        String face = null;
        if (i < 20) {
            face = "黑桃";
        } else if (i < 40) {
            face = "梅花";
        } else if (i < 60) {
            face = "红桃";
        } else {
            face = "方块";
        }
        
        return face;
        
    }
}
