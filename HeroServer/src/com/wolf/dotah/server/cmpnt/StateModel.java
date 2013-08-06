package com.wolf.dotah.server.cmpnt;


import java.util.List;


/**
 * 每个Player 里都有一个 state, 来表示当前的状态
 * @author Solomon
 *
 */
public class StateModel {
    
    int stage;//choosing    / free play
    boolean currentPlayer;
    List<Integer> usableCardContext;
    List<Integer> heroSkillContext;
    List<Integer> equipmentContext;
    
    
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
