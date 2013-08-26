package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerHandCardsModel {
    
    public PlayerHandCardsModel(int handcardLimit) {
        this.limit = handcardLimit;
    }
    
    int limit;
    List<Integer> cards = new ArrayList<Integer>();
    
    public void add(List<Integer> input) {
        cards.addAll(input);
    }
    
    public List<Integer> getCards() {
        return cards;
    }
    
    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }
    
}
