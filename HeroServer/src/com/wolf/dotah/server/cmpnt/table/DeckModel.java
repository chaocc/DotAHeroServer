package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.card.Card;
import com.wolf.dotah.server.layer.data.CardParser;

public class DeckModel {
    
    List<Card> deck;
    private TableModel table;
    private CardRemainStack remainStack;
    private CardDropStack dropStack;
    
    public DeckModel(TableModel tableModel) {
        this.table = tableModel;
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
    
    public CardRemainStack getRemainStack() {
        return remainStack;
    }
    
    public void setRemainStack(CardRemainStack remainStack) {
        this.remainStack = remainStack;
    }
    
    public CardDropStack getDropStack() {
        return dropStack;
    }
    
    public void setDropStack(CardDropStack dropStack) {
        this.dropStack = dropStack;
    }
    
}
