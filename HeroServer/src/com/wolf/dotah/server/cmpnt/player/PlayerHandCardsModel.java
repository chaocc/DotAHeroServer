package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.functioncon;
import com.wolf.dotah.server.util.c;

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
    
    public void add(List<Integer> input) {
    
        cards.addAll(input);
        for (HandCardsChangeListener listener : changeListeners) {
            listener.onHandCardsAdded(input);
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
    
    public void remove(int card, boolean sendPrivateMessage) {
    
        this.getCards().remove(this.getCards().indexOf(card));
        
        
        
        Data data = new Data();
        if (sendPrivateMessage) {
            // send update player handcards to self
            data.setAction(c.action.update_hand_cards);
            data.setIntegerArray(c.param_key.id_list, new int[] { card });
            player.updateMyStateToClient(data);
        }
        //send update player handcard count to other players
        data = new Data();
        data.setAction(c.action.update_hand_cards);
        data.setInteger(c.param_key.hand_card_count, cards.size());
        data.setString(c.param_key.who, player.userName);
        player.getTable().getMessenger().sendMessageToAllWithoutSpecificUser(data, player.userName);
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
                    result.add(card);
                }
            }
        }
        return result;
    }
    public interface HandCardsChangeListener {
        public void onHandCardsAdded(List<Integer> newCards);
        
        public void onHandCardsDropped(List<Integer> droppedCards);
    }
    
    public void registerHandcardChangeListener(HandCardsChangeListener input) {
    
        this.changeListeners.add(input);
    }
}
