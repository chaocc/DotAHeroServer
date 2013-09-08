package com.wolf.dotah.server.cmpnt.player;

import java.util.Random;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

public class Ai {
    final boolean isAi = true;
    final String tag = "===>> Ai";
    
    public int chooseSingle(int[] candidates) {
//        MessageDispatcher.getDispatcher(null).debug(tag, "" + u.printArray(candidates));
        return candidates[new Random().nextInt(candidates.length)];
    }
    
    public boolean isAi() {
        return isAi;
    }

    @Override
    public String toString() {
    
        return "Ai [isAi=" + isAi + ", tag=" + tag + "]";
    }

    public void startTurn() {
    
        Data obj = new Data();
        obj.setAction(c.ac.turn_to_player);//kActionPlayingCard 出牌阶段
//        obj.addString(client_const.param_key.player_name, this.player.getUserName());
//        
//        int[] availableHandCards = pp.getAvailableHandCards();
//        obj.addIntegerArray(client_const.param_key.available_id_list, availableHandCards);
//        obj.addInteger(client_const.param_key.kParamSelectableCardCount, c.selectable_count.default_value);
//        
//        
//        disp.sendMessageToSingleUser(playerName, obj);
        //TODO 告诉玩家可以开始玩牌了, 
        //TODO 摸2张牌
        
    }
}
