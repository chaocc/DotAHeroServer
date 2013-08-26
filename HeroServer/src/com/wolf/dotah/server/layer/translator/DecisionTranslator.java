package com.wolf.dotah.server.layer.translator;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.util.c;

public class DecisionTranslator {
    
    private MessageDispatcher msgDispatcher;
    
    public DecisionTranslator(MessageDispatcher dispatcher) {
        this.msgDispatcher = dispatcher;
    }
    
    /*
     * ==========  translations from server
     */
    private void translateSingleStepAction(ServerUpdateSequence sequence, final Data obj) {
        
        String serverAction = sequence.get(0).getStepDesp();
        
        //add action
        obj.setAction(serverAction);
        obj.setString(c.action_category, sequence.getFinalTarget());
        //add param
        obj.addAll(sequence.get(0).getData());
    }
    
    public void translate(ServerUpdateSequence sequence) {
        
        Data obj = new Data();
        if (sequence.isSingleStepSequence()) {
            translateSingleStepAction(sequence, obj);
        } else {
            translateMultiStepAction(sequence, obj);
        }
        // TODO 每个translate 方法做更多的事情, 
        // 比如把 table 的状态和各个player的属性和状态, 该加的都加进来!
        // TODO 比如 which property updated, 是个例子
        // TODO 加上了table上有哪几个player, 应该要移到
        msgDispatcher.sendMessageToSingleUser(sequence.getSubjectPlayer().getUserName(), obj);
    }
    
    private void translateMultiStepAction(ServerUpdateSequence sequence, final Data obj) {
        
        //TODO 比如又掉血啦, 又涨怒气啦什么的, 
        // 就要一步一步的sequence step 都加到esobject里
        
    }
    
    /*
     * =============  translations from client
     */
    
    public void translateChose(Player decisionMaker, EsObject msg) {
        

    }
    
}
