package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.layer.translator.MessageDispatcher;
import com.wolf.dotah.server.util.u;

/**
 * 每个Player 里都有一个 state, 来表示当前处于的play状态,
 * free play 或者 choosing 等
 * @author Solomon
 *
 */
public class PlayerState implements player_const {
    
    String stateDesp;//which choosing  / which waiting
    //    int stateCode;//choosing  /waiting  / free play
    boolean currentPlayer;
    List<Integer> choosingCardContext = new ArrayList<Integer>();//choosing 的卡片列表
    //    List<List<Integer>> targetCardContext = new ArrayList<List<Integer>>();// 有多个目标时候, 再加
    List<Integer> usableCardContext = new ArrayList<Integer>();//?
    List<Integer> heroSkillContext = new ArrayList<Integer>();//正在使用哪个技能
    List<Integer> equipmentContext = new ArrayList<Integer>();//正在使用哪个装备
    final String tag = "===>> PlayerState: ";
    
    public PlayerState() {
        
        //        this.stateCode = playercon.state.state_code.unavailable;
        setStateDesp(playercon.state.desp.unavailable);
    }
    
    public PlayerState(String state) {
        setStateDesp(state);
    }
    
    public void updateDetail(Data detail) {
        //        MessageDispatcher.getDispatcher(null).debug(tag, "update player state to detail: \n" + detail.toString());
        Integer[] choosingCards = u.integerArrayMapping(detail.getIntegerArray(playercon.state.param_key.general.id_list, new int[] {}));
        //        MessageDispatcher.getDispatcher(null).debug(tag, "adding choosing id list to context: " + u.printArray(choosingCards));
        choosingCardContext.addAll(Arrays.asList(choosingCards));
        
    }
    
    public Data toData() {
        /**
         * to data的时候就要根据当前的state desp来判断了, 如果是不同的state, 要加不同的param key
         */
        Data data = new Data();
        if (stateDesp.equals(playercon.state.desp.choosing.choosing_hero)) {
            int[] heroCandidates = u.intArrayMapping(choosingCardContext.toArray(new Integer[] {}));
            //            MessageDispatcher.getDispatcher(null).debug(tag, "fetching choosing id list from context: " + u.printArray(heroCandidates));
            data.setIntegerArray(playercon.state.param_key.general.id_list, heroCandidates);
            
        }
        return data;
    }
    
    public String getStateDesp() {
        return stateDesp;
    }
    
    public PlayerState setStateDesp(String stateDesp) {
        this.stateDesp = stateDesp;
        return this;
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
