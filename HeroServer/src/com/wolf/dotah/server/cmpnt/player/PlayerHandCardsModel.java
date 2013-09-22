package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.functioncon;
import com.wolf.dotah.server.layer.dao.CardParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;

public class PlayerHandCardsModel {
    
    private List<HandCardsChangeListener> changeListeners;
    
    public PlayerHandCardsModel(Player p, int handcardLimit) {
    
        this.limit = handcardLimit;
        this.player = p;
        changeListeners = new ArrayList<HandCardsChangeListener>();
    }
    
    private Player player;
    int limit;
    List<Integer> cards = new ArrayList<Integer>();
    
    public void add(List<Integer> input, boolean sendPrivate) {
    
        //        int original_size = cards.size();
        
        cards.addAll(input);
        
        
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsAdded(input, player.userName, sendPrivate);
        }
    }
    
    public void initPlayerHandcards(List<Integer> input) {
    
        cards.addAll(input);
        if (!player.isAi()) {
            Data data = new Data();
            data.setAction(c.action.init_hand_cards);
            Integer[] cardArray = getCards().toArray(new Integer[] {});
            data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cardArray));
            player.updateToClient(data);
        }
    }
    
    public List<Integer> getCards() {
    
        return cards;
    }
    
    public Integer[] getCardArray() {
    
        return cards.toArray(new Integer[] {});
    }
    
    public void setCards(List<Integer> cards) {
    
        this.cards = cards;
    }
    
    public void remove(int card, boolean sendPrivate) {
    
        //        int origin_size = cards.size();
        this.getCards().remove(this.getCards().indexOf(card));
        //        Data data = new Data();
        //        if (sendPrivate) {
        //            // send update player handcards to self
        //            data.setAction(c.action.update_hand_cards);
        //            data.setIntegerArray(c.param_key.id_list, new int[] { card });
        //            player.updateMyStateToClient(data);
        //        }
        //        //send update player handcard count to other players
        //        data = new Data();
        //        data.setAction(c.action.update_hand_cards);
        //        //        data.setInteger(c.param_key.hand_card_change_amount, cards.size());
        //        data.setInteger(c.param_key.hand_card_change_amount, cards.size() - origin_size);
        //        //        data.setString(c.param_key.who, player.userName);
        //        //        player.getTable().getMessenger().sendMessageToAllWithoutSpecificUser(data, player.userName);
        //        player.table.sendPublicMessage(data, player.userName);
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsDropped(new int[] { card }, player.userName, sendPrivate);
        }
    }
    
    public void removeAll(int[] usedCards, boolean sendPrivate) {
    
        //        int origin_size = cards.size();
        
        for (int usedCard : usedCards) {
            this.getCards().remove(this.getCards().indexOf(usedCard));
        }
        
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsDropped(usedCards, player.userName, sendPrivate);
        }
    }
    
    public List<Integer> getCardsByFunction(int functionId) {
    
        List<Integer> result = new ArrayList<Integer>();
        switch (functionId) {
            case functioncon.b_evasion: {
                for (int cardId : cards) {
                    if ((cardId > 59 && cardId < 70) || cardId == 79) {
                        result.add(cardId);
                    }
                }
                break;
            }
        }
        return result;
    }
    
    public List<Integer> getCardsByProperty(String property, int value) {
    
        List<Integer> result = new ArrayList<Integer>();
        if (property.equals(card_const.color)) {
            for (int card : getCards()) {
              Card c=  CardParser.getParser().getCardById(card);
                if(c.getColorCode()==value){
                    result.add(card);
                }
            }
        } else if (property.equals(card_const.suits)) {
            for (int card : getCards()) {
                Card c=  CardParser.getParser().getCardById(card);
                  if(c.getSuitsCode()==value){
                      result.add(card);
                  }
              }
        }
        return result;
    }
    
    public List<Integer> getCardsByUsage(String usage) {
    
        List<Integer> result = new ArrayList<Integer>();
        if (usage.equals("active")) {
            for (int card : getCards()) {
                
                if (negativeCard(card) || attach_and_can_NOT_use(card)) {
                    continue;
                } else {
                    result.add(card);
                }
            }
        } else if (usage.equals(card_const.color)) {
            for (int card : getCards()) {
                
                if (negativeCard(card) || attach_and_can_NOT_use(card)) {
                    continue;
                } else {
                    result.add(card);
                }
            }
        } else if (usage.equals(card_const.suits)) {
            
        }
        return result;
    }
    
    private boolean attach_and_can_NOT_use(int card) {
    
        if (!player.m_Fanaticismed && player.used_how_many_attacks > 0) {
            int function = CardParser.getParser().getCardById(card).getFunction();
            if (function == functioncon.b_chaos_attack
                || function == functioncon.b_flame_attack
                || function == functioncon.b_normal_attack) { return true; }
        }
        return false;
    }
    
    private boolean negativeCard(int card) {
    
        boolean firstCase = card > 45 && card < 57;
        boolean secondCase = card > 59 && card < 70;
        boolean thirdCase = card == 79;
        return firstCase || secondCase || thirdCase;
    }
    public interface HandCardsChangeListener {
        public void onHandCardsAdded(List<Integer> newCards, String playerName, boolean sendPrivate);
        
        public void onHandCardsDropped(int[] droppedCards, String playerName, boolean sendPrivate);
    }
    
    public void registerHandcardChangeListener(HandCardsChangeListener input) {
    
        this.changeListeners.add(input);
    }
    
    
}
