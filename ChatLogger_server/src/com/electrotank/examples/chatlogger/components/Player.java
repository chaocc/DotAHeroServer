package com.electrotank.examples.chatlogger.components;

import java.util.List;

public class Player {
    private String        playerName;
    private int           order;
    private String        heroName;
    private int           heroId;
    private int           hp;
    private int           sp;
    private int[]         skills;
    private int[]         weapons;
    private int           cardLimit;
    private List<Integer> handCards;
    
    /**
     * 
     * @param fileName player's heroName, 
     *                 skills are read from configured file
     */
    public Player(String playerName, int order, String fileName) {
        
    }
    
    public Player(String playerName, String fileName) {
        
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
