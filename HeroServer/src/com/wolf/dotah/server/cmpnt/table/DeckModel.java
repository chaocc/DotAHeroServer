package com.wolf.dotah.server.cmpnt.table;


import java.util.ArrayList;
import java.util.List;

import com.wolf.dotah.server.cmpnt.card.Card;
import com.wolf.dotah.server.layer.data.CardParser;


public class DeckModel {
    
    List<Card> deck;
    private static DeckModel deckModel;
    
    
    public static DeckModel getDeckModel() {
    
        if (deckModel == null) {
            deckModel = new DeckModel();
        }
        return deckModel;
    }
    
    
    private DeckModel() {
    
        deck = CardParser.getParser().getCardList();
    }
    
    
    public List<Card> getDeck() {
    
        return deck;
    }
    
    
    public List<Integer> getSimpleDeck() {
    
        List<Integer> cardIdList = new ArrayList<Integer>();
        for (Card card : deck) {
            cardIdList.add(card.getId());
        }
        return cardIdList;
    }
    
    
    public void setDeck(List<Card> deck) {
    
        this.deck = deck;
    }
    
    
}
