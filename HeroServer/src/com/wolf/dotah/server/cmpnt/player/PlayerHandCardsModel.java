package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

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
            data.setAction(c.ac.update_hand_cards);
            data.setIntegerArray(c.param_key.id_list, new int[] { card });
            player.updateMyStateToClient(data);
        }
        //send update player handcard count to other players
        data = new Data();
        data.setAction(c.ac.update_hand_cards);
        data.setInteger(client_const.param_key.hand_card_count, cards.size());
        data.setString(c.param_key.who, player.getUserName());
        player.getTable().getMessenger().sendMessageToAllWithoutSpecificUser(data, player.getUserName());
    }
    public interface HandCardsChangeListener {
        public void onHandCardsAdded(List<Integer> newCards);
    }
    
    public void registerHandcardChangeListener(HandCardsChangeListener input) {
    
        this.changeListeners.add(input);
    }
}
