package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.layer.dao.CardParser;

public class DeckModel {
    
    List<Card> deck;
    private TableModel table;
    List<Integer> remainStack;
    List<Integer> dropStack;
    
    public DeckModel(TableModel tableModel) {
    
        this.table = tableModel;
        deck = CardParser.getParser().getCardList();
        Collections.shuffle(deck);
        remainStack = this.getSimpleDeck();
        dropStack = new ArrayList<Integer>();
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
    
    //    public Card getCardById(Integer id) {
    //    
    //        for (Card c : deck) {
    //            if (c.getId() == id) { return c; }
    //        }
    //        return null;
    //    }
    
    public List<Integer> fetchCards(int count) {
    
        List<Integer> cards = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
            int card = remainStack.get(0);
            cards.add(card);
            remainStack.remove(0);
        }
        return cards;
    }
    
    public int fetchOneCard() {
    
        int card = remainStack.get(0);
        remainStack.remove(0);
        return card;
    }
    
    public int syncWithRemainStack() {
    
        int synced = 0;
        
        List<Integer> deckCardList = this.getSimpleDeck();
        for (Integer card : deckCardList) {
            if (!remainStack.contains(card)) {
                dropStack.add(card);
                synced++;
            }
        }
        return synced;
    }
    
    public void setDeck(List<Card> deck) {
    
        this.deck = deck;
    }
    
    
    public int getRemainCount() {
    
        return remainStack.size();
    }
    
    @Override
    public String toString() {
    
        return "DeckModel [deck=" + deck + ", table=" + table + ", remainStack=" + remainStack + ", dropStack=" + dropStack + "]";
    }
    
    
}
