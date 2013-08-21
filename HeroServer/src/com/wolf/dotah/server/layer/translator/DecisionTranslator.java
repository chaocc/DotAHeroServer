package com.wolf.dotah.server.layer.translator;

import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;

public class DecisionTranslator {
    
    private static DecisionTranslator translator;
    
    public static DecisionTranslator getTranslator() {
        
        if (translator == null) {
            translator = new DecisionTranslator();
        }
        return translator;
    }
    
    private DecisionTranslator() {
        
    }
    
    private void translateSingleStepAction(ServerUpdateSequence sequence, final Data obj) {
        
        String serverAction = sequence.get(0).getStepDesp();
        
        //add action
        obj.setInteger(c.action, u.actionMapping(serverAction));
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
        MessageDispatcher.getDispatcher(null).sendMessageToSingleUser(sequence.getSubjectPlayer().getUserName(), obj);
    }
    
    private void translateMultiStepAction(ServerUpdateSequence sequence, final Data obj) {
        
        //TODO 比如又掉血啦, 又涨怒气啦什么的, 
        // 就要一步一步的sequence step 都加到esobject里
        
    }
    
}
