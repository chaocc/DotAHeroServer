package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.List;

public class CardRemainStack {
    
    List<Integer> remainStack;
    private DeckModel deck;
    
    public CardRemainStack initWithCardList(List<Integer> list) {
        
        for (Integer card : list) {
            remainStack.add(card);
        }
        return this;
    }
    
    public void initWithDeck() {
        
        List<Integer> deckCards = deck.getSimpleDeck();
        for (Integer card : deckCards) {
            remainStack.add(card);
        }
    }
    
    public List<Integer> getRemainStack() {
        
        return remainStack;
    }
    
    public void setRemainStack(List<Integer> remainStack) {
        
        this.remainStack = remainStack;
    }
    
    public CardRemainStack(DeckModel deckModel) {
        this.deck = deckModel;
        deck.setRemainStack(this);
        remainStack = new ArrayList<Integer>();
    }
    
}
