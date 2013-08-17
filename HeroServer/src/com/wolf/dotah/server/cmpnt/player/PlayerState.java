package com.wolf.dotah.server.cmpnt.player;


import java.util.List;

import com.wolf.dotah.server.cmpnt.Data;


/**
 * 每个Player 里都有一个 state, 来表示当前处于的play状态,
 * free play 或者 choosing 等
 * @author Solomon
 *
 */
public class PlayerState implements player_const {
    
    int stage;//choosing    / free play
    boolean currentPlayer;
    List<Integer> usableCardContext;
    List<Integer> heroSkillContext;
    List<Integer> equipmentContext;
    
    
    public PlayerState() {
        
        this.stage = playercon.state.stage.unavailable;
    }
    
    
    public PlayerState(String state) {
        // TODO Auto-generated constructor stub
    }
    
    public void updateDetail(Data detail) {
        
    }
    
    public int getStage() {
        
        return stage;
    }
    
    
    public boolean isCurrentPlayer() {
        
        return currentPlayer;
    }
    
    
    public List<Integer> getUsableCardContext() {
        
        return usableCardContext;
    }
    
    
    public List<Integer> getHeroSkillContext() {
        
        return heroSkillContext;
    }
    
    
    public List<Integer> getEquipmentContext() {
        
        return equipmentContext;
    }
    
    
    public void setStage(int stage) {
        
        this.stage = stage;
    }
    
    
    public void setCurrentPlayer(boolean currentPlayer) {
        
        this.currentPlayer = currentPlayer;
    }
    
    
    public void setUsableCardContext(List<Integer> usableCardContext) {
        
        this.usableCardContext = usableCardContext;
    }
    
    
    public void setHeroSkillContext(List<Integer> heroSkillContext) {
        
        this.heroSkillContext = heroSkillContext;
    }
    
    
    public void setEquipmentContext(List<Integer> equipmentContext) {
        
        this.equipmentContext = equipmentContext;
    }
    
    
}
