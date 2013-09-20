package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
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
        
        
        //        Data data = new Data();//use to update count
        //        data.addHandCardSize(original_size, cards.size());
        //        data.setAction(c.action.update_hand_cards);
        //        player.table.sendPublicMessage(data, player.userName);
        //        
        //        if(sendUpdateMessage){
        //            data=new Data();
        //            data.setAction(c.action.update_hand_cards);
        //            data.addHandCardSize(original_size, cards.size());
        //            data.addHandCardState(input, cards);
        //            player.updateMyHandCardsToClient(data);
        //        }
        
        
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
        
        //        Data data = new Data();
        //        if (sendPrivateMessage) {
        //            // send update player handcards to self
        //            data.setAction(c.action.update_hand_cards);
        //            data.setIntegerArray(c.param_key.id_list, usedCards);
        //            player.updateMyStateToClient(data);
        //        }
        //        //send update player handcard count to other players
        //        data = new Data();
        //        data.setAction(c.action.update_hand_cards);
        //        data.setInteger(c.param_key.hand_card_change_amount, cards.size() - origin_size);
        //        player.table.sendPublicMessage(data, player.userName);
        
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
    
    public List<Integer> getCardsByUsage(String usage) {
    
        List<Integer> result = new ArrayList<Integer>();
        if (usage.equals("active")) {
            for (int card : getCards()) {
                boolean firstCase = card > 45 && card < 57;
                boolean secondCase = card > 59 && card < 70;
                boolean thirdCase = card == 79;
                
                
                if (firstCase || secondCase || thirdCase) {
                    continue;
                } else {
                    if (player.used_how_many_attacks > 0) {
                        int function = CardParser.getParser().getCardById(card).getFunction();
                        if (function == functioncon.b_chaos_attack
                            || function == functioncon.b_flame_attack
                            || function == functioncon.b_normal_attack) {
                            continue;
                        }
                    }
                    result.add(card);
                }
            }
        }
        return result;
    }
    public interface HandCardsChangeListener {
        public void onHandCardsAdded(List<Integer> newCards, String playerName, boolean sendPrivate);
        
        public void onHandCardsDropped(int[] droppedCards, String playerName, boolean sendPrivate);
    }
    
    public void registerHandcardChangeListener(HandCardsChangeListener input) {
    
        this.changeListeners.add(input);
    }
    
    
}
