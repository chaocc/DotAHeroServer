package com.wolf.dotah.server.layer.translator;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.util.c;

public class PlayerTranslator {
    
    private DecisionTranslator decisionTranslator;
    private MessageDispatcher dispatcher;
    final String tag = "===>> PlayerTranslator: ";
    
    public PlayerTranslator(MessageDispatcher input) {
        this.dispatcher = input;
    }
    
    public void setDecisionTranslator(DecisionTranslator decisionTranslator) {
        
        this.decisionTranslator = decisionTranslator;
        
    }
    
    public void translateUpdate(Player player, EsObject msg) {
        //TODO 不是根据action, 而是根据player 状态来判断choose了什么
        //        Player decisionMaker = players.getPlayerByUserName(user);
        int[] pickResult = msg.getIntegerArray(c.param_key.id_list, new int[] {});
        player.getResult(pickResult);
    }
    
    public void updatePlayerInfo(String subject, String[] keys, int[] values) {
        Data msg = new Data();
        this.dispatcher.debug(tag, "keys.length: " + keys.length);
        if (keys.length == 1) {
            if (keys[0].equals("heroId")) {
                msg.setAction(c.server_action.chose_hero);
                msg.setInteger("id", values[0]);
            }
        } else if (keys.length > 1) {
            //TODO action可以直接设成update xxx
        }
        dispatcher.sendMessageToSingleUser(subject, msg);
        
    }
}
