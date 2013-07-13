package com.wolf.dota.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private String playerName;
    private int order;
    private String heroName;
    private int heroId;
    private int hp;
    private int hpLimit;
    private int spLimit;
    private int sp;
    private int[] skills;
    private int[] weapons;
    private int cardLimit;
    private List<Integer> handCards;
    
    //    21, "kSlayer"),
    //    12, "kVengefulSpirit"),
    //    2, "kBristleback"),
    //    3, "kSacredWarrior"),
    //    28, "kKeeperOfTheLight"),
    //    17, "kAntimage")
    
    private Player(int id) {
        switch (id) {
            case 2: {
                
                break;
            }
            case 3: {
                break;
            }
            case 12: {
                break;
            }
            case 17: {
                break;
            }
            case 21: {
                break;
            }
            case 28: {
                break;
            }
        }
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
    
    public List<Integer> addHandCard() {
        return handCards;
    }
}
