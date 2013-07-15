package com.electrotank.electroserver5.examples.chatloggerclient.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.primitives.Ints;

public class Player {
    
    public static Player getPlayerById(int id) {
        Player player = players.get(id);
        if (player == null) {
            player = new Player(id);
            players.put(id, player);
        }
        return player;
    }
    
    private static Map<Integer, Player> players = new HashMap<Integer, Player>();
    
    private String
            playerName,
            heroName;
    private int
            order,
            heroId,
            heroType,
            hp,
            hpLimit,
            spLimit,
            sp,
            handCardLimit;
    private int[]
            skills,
            weapons;
    private List<Integer> handCards;
    
    //    21, "kSlayer"),
    //    12, "kVengefulSpirit"),
    //    2, "kBristleback"),
    //    3, "kSacredWarrior"),
    //    28, "kKeeperOfTheLight"),
    //    17, "kAntimage")
    
    private Player(int id) {
        initPlayerFromPlist(id);
    }
    
    private void initPlayerFromPlist(int id) {
        this.heroId = id;
        handCards = new ArrayList<Integer>();
    }
    
    /**
     * 
     * @param fileName player's heroName, 
     *                 skills are read from configured file
     */
    private Player(String playerName, int order, String fileName) {
        
    }
    
    private Player(String playerName, String fileName) {
        
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public List<Integer> getHandCards() {
        return handCards;
    }
    
    public void setHandCards(List<Integer> handCards) {
        this.handCards = handCards;
    }
    
    //dealing hand cards
    public List<Integer> addHandCard(int card) {
        return handCards;
    }
    
    public List<Integer> addHandCards(int[] cards) {
//        this.handCards.addAll(Ints.asList(cards));
        return handCards;
    }
    
    public List<Integer> removeCard(int card) {
        this.handCards.remove(card);
        return handCards;
    }
    
    public List<Integer> removeCards(int[] cards) {
        this.handCards.removeAll(Ints.asList(cards));
        return handCards;
    }
    
}
