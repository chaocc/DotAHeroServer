package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.List;

public class CardDropStack {
    
    List<Integer> dropStack;
    private DeckModel deck;
    
    public CardDropStack(DeckModel deckModel) {
        this.deck = deckModel;
        deck.setDropStack(this);
        dropStack = new ArrayList<Integer>();
    }
    
    public int syncWithRemainStack() {
        
        int synced = 0;
        
        List<Integer> deckCardList = deck.getSimpleDeck();
        List<Integer> remainStack = deck.getRemainStack().getRemainStack();
        for (Integer card : deckCardList) {
            if (!remainStack.contains(card)) {
                dropStack.add(card);
                synced++;
            }
        }
        return synced;
    }
    
    public List<Integer> getDropStack() {
        
        return dropStack;
    }
    
    public void setDropStack(List<Integer> dropStack) {
        
        this.dropStack = dropStack;
    }
    
}
