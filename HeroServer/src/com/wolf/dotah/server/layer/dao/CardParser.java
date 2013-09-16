package com.wolf.dotah.server.layer.dao;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.util.l;

public class CardParser {
    
    private final String path = "doc/cards";
    private static CardParser parser;
    static String tag = " ===>> CardParser";
    
    public static CardParser getParser() {
    
        if (parser == null) {
            parser = new CardParser();
        }
        return parser;
    }
    
    public List<Card> getCardList() {
    
        List<Card> cardList = new ArrayList<Card>();
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new FileReader(path));
            
            Gson gson = new Gson();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                
                Card c = gson.fromJson(jsonReader, Card.class);
                c.genInfo();
                cardList.add(c);
            }
            jsonReader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cardList;
    }
    
    public Card getCardById(int cardId) {
        l.logger().d(tag, "getCardById, with card id: "+cardId);
        for (Card card : getCardList()) {
            if (card.getId() == cardId) { return card; }
        }
        return null;
    }
    
    private CardParser() {
    
    }
    
    public static void main(String... args) throws Exception {
    
        for (Card card : CardParser.getParser().getCardList()) {
            System.out.println(tag + card);
        }
    }
    
}
