package com.wolf.dotah.server.cmpnt.table;


import java.util.ArrayList;
import java.util.List;


public class CardDropStack {
    
    List<Integer> dropStack;
    private static CardDropStack dropStackModel;
    
    
    public static CardDropStack getDropStackModel() {
    
        if (dropStackModel == null) {
            dropStackModel = new CardDropStack();
        }
        return dropStackModel;
    }
    
    
    private CardDropStack() {
    
        dropStack = new ArrayList<Integer>();
    }
    
    
    public int syncWithRemainStack() {
    
        int synced = 0;
        
        List<Integer> deck = DeckModel.getDeckModel().getSimpleDeck();
        List<Integer> remainStack = CardRemainStack.getRemainStackModel().getRemainStack();
        for (Integer card : deck) {
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
