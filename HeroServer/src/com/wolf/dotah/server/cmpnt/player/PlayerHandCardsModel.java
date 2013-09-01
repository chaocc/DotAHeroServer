package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

public class PlayerHandCardsModel {
    
    public PlayerHandCardsModel(Player p, int handcardLimit) {
    
        this.limit = handcardLimit;
        this.player = p;
    }
    
    private Player player;
    int limit;
    List<Integer> cards = new ArrayList<Integer>();
    
    public void add(List<Integer> input) {
    
        cards.addAll(input);
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
        Data data = new Data(player.getTable().u);
        if (sendPrivateMessage) {
            // send update player handcards to self
            data.setAction(c.ac.update_hand_cards);
            data.setIntegerArray(c.param_key.id_list, new int[] { card });
            player.getTable().getDispatcher().sendMessageToSingleUser(this.player.getUserName(), data);
        }
        //send update player handcard count to other players
        data = new Data(player.getTable().u);
        data.setAction(c.ac.update_hand_cards);
        data.setInteger(client_const.param_key.hand_card_count, cards.size());
        data.setString(c.param_key.who, player.getUserName());
        player.getTable().getDispatcher().sendMessageToAllWithoutSpecificUser(data, player.getUserName());
    }
}
