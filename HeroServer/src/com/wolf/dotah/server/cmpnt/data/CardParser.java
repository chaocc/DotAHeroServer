package com.wolf.dotah.server.cmpnt.data;


import java.io.FileReader;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class CardParser {
    
    private static String path = "doc/cards";
    
    
    public static void main(String... args) throws Exception {
    
        System.out.println(getAmount());
    }
    
    
    public static boolean getAmount() throws Exception {
    
        JsonReader jsonReader = new JsonReader(new FileReader(path));
        JsonParser parser = new JsonParser();
        return parser.parse(jsonReader).isJsonObject();
    }
    
    
    public static void getCard() {
    
    }
}
