package com.wolf.dotah.server.cmpnt.table;


import java.util.ArrayList;
import java.util.List;


public class CardRemainStack {
    
    
    List<Integer> remainStack;
    private static CardRemainStack remainStackModel;
    
    
    public static CardRemainStack getRemainStackModel() {
    
        if (remainStackModel == null) {
            remainStackModel = new CardRemainStack();
        }
        return remainStackModel;
    }
    
    
    private CardRemainStack() {
    
        remainStack = new ArrayList<Integer>();
    }
    
    
    public void initWithCardList(List<Integer> list) {
    
        for (Integer card : list) {
            remainStack.add(card);
        }
    }
    
    
    public void initWithDeck() {
    
        List<Integer> deck = DeckModel.getDeckModel().getSimpleDeck();
        for (Integer card : deck) {
            remainStack.add(card);
        }
    }
    
    
    public List<Integer> getRemainStack() {
    
        return remainStack;
    }
    
    
    public void setRemainStack(List<Integer> remainStack) {
    
        this.remainStack = remainStack;
    }
    
    
}
