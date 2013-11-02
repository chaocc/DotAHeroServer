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
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class PlayerHandCardsModel {
    
    private List<HandCardsChangeListener> changeListeners;
    final String tag = PlayerHandCardsModel.class.getSimpleName();
    
    public PlayerHandCardsModel(Player p, int handcardLimit) {
    
        this.limit = handcardLimit;
        this.player = p;
        changeListeners = new ArrayList<HandCardsChangeListener>();
    }
    
    private Player player;
    public int limit;
    List<Integer> cards = new ArrayList<Integer>();
    
    public void add(int[] input, boolean sendPrivate) {
    
        // int original_size = cards.size();
        //        Integer[] inputArray = input.toArray(new Integer[] {});
        for (int i : input) {
            cards.add(i);
        }
        
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsAdded(input, player.userName, sendPrivate);
        }
    }
    
    public void add(List<Integer> input, boolean sendPrivate) {
    
        Integer[] inputArray = input.toArray(new Integer[] {});
        cards.addAll(input);
        
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsAdded(u.intArrayMapping(inputArray), player.userName, sendPrivate);
        }
    }
    
    public void add(int input, boolean sendPrivate, Data effectData) {
    
        if (effectData != null) {
            effectData.setIntegerArray(c.param_key.id_list, new int[] { input });
            player.table.sendPublicMessage(effectData, player.userName);
        }
        cards.add(input);
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsAdded(new int[] { input }, player.userName, sendPrivate);
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
    
    public void remove(int card, boolean sendPrivate, String reason) {
    
        // int origin_size = cards.size();
        l.logger().d("[HandCards, " + player.userName + "] ", "removing card=" + card + " from=" + this.getCards());
        this.getCards().remove(this.getCards().indexOf(card));
        // Data data = new Data();
        // if (sendPrivate) {
        // // send update player handcards to self
        // data.setAction(c.action.update_hand_cards);
        // data.setIntegerArray(c.param_key.id_list, new int[] { card });
        // player.updateMyStateToClient(data);
        // }
        // //send update player handcard count to other players
        // data = new Data();
        // data.setAction(c.action.update_hand_cards);
        // // data.setInteger(c.param_key.hand_card_change_amount,
        // cards.size());
        // data.setInteger(c.param_key.hand_card_change_amount, cards.size() -
        // origin_size);
        // // data.setString(c.param_key.who, player.userName);
        // //
        // player.getTable().getMessenger().sendMessageToAllWithoutSpecificUser(data,
        // player.userName);
        // player.table.sendPublicMessage(data, player.userName);
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsDropped(new int[] { card }, player.userName, sendPrivate, reason);
        }
    }
    
    public void removeAll(int[] usedCards, boolean sendPrivate, String reason) {
    
        // int origin_size = cards.size();
        l.logger().d(tag, "removeAll, removing cards=" + u.printArray(usedCards) + " from " + this.getCards());
        for (int usedCard : usedCards) {
            this.getCards().remove(this.getCards().indexOf(usedCard));
        }
        
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsDropped(usedCards, player.userName, sendPrivate, reason);
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
            for (int card : cards) {
                Card c = CardParser.getParser().getCardById(card);
                l.logger().d("playerHandCards ===>> ", "getCardsByProperty, card=" + c.toString());
                if (c.getColorCode() == value) {
                    result.add(card);
                }
            }
        } else if (property.equals(card_const.suits)) {
            for (int card : cards) {
                Card c = CardParser.getParser().getCardById(card);
                if (c.getSuitsCode() == value) {
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
                
                if (negativeCard(card) || attack_and_can_NOT_use(card)) {
                    continue;
                } else {
                    result.add(card);
                }
            }
        } else if (usage.equals(card_const.color)) {
            for (int card : getCards()) {
                
                if (negativeCard(card) || attack_and_can_NOT_use(card)) {
                    continue;
                } else {
                    result.add(card);
                }
            }
        } else if (usage.equals(card_const.suits)) {
            
        }
        return result;
    }
    
    private boolean attack_and_can_NOT_use(int card) {
    
        if (!player.m_Fanaticismed && player.used_how_many_attacks > 0) {
            int function = CardParser.getParser().getCardById(card).getFunction();
            if (function == functioncon.b_chaos_attack
                || function == functioncon.b_flame_attack
                || function == functioncon.b_normal_attack) { return true; }
        }
        return false;
    }
    
    private boolean negativeCard(int card) {
    
        boolean firstCase = card > 49 && card < 55;
        if (player.property.hp >= player.property.hpLimit) {
            //heal: 46~49, 55, 56
            firstCase = card > 45 && card < 57;
        }
        boolean secondCase = card > 59 && card < 70;
        boolean thirdCase = card == 79;
        return firstCase || secondCase || thirdCase;
    }
    
    public interface HandCardsChangeListener {
        public void onHandCardsAdded(int[] newCards, String playerName, boolean sendPrivate);
        
        public void onHandCardsDropped(int[] droppedCards, String playerName, boolean sendPrivate, String reason);
    }
    
    public void registerHandcardChangeListener(HandCardsChangeListener input) {
    
        this.changeListeners.add(input);
    }
    
    public boolean hasDispell() {
    
        if (cards.size() > 0) {
            for (int i : cards) {
                switch(i){
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:{
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
}
