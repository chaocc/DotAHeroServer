package com.wolf.dota.component;

import java.util.List;

/**
 * This is a class simulate the desk, 
 * there should be 
 * a card stack, 
 * a drop stack 
 * a circle of players with cards in their hands
 * a circle of players' states, dead, or something else
 * a circle of players' force
 * etc.
 * anything changed may affect desk will appear in desk model, 
 * player's hp or has what skills wouldn't affect the desk, so write in player model
 * @author Solomon
 *
 */
public class DeskModel {
    List<Integer>   cardStack;
    List<Integer>[] playerHandCards; //array is players, list is handcard for everybody
    int[]           playerStates;   //dead=-1, waiting=0, playing=1, etc.
    int[]           playerForces;
    List<Integer>   dropStack;
    
    public DeskModel(int playerAmount) {
        
    }
    
}
