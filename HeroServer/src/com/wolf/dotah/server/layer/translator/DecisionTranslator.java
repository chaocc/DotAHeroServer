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
    
    private void translateSingleStepAction(ServerUpdateSequence sequence) {
        String serverAction = sequence.get(0).getStepDesp();
        Data obj = new Data();
        
        //add action
        obj.setInteger(c.action, u.actionMapping(serverAction));
        obj.setString(c.action_category, sequence.getSequenceType());
        //add param
        obj.addAll(sequence.get(0).getData());
        
        MessageDispatcher.getDispatcher(null).sendMessageToSingleUser(sequence.getPlayerAsSubject().getUserName(), obj);
        //                MessageDispatcher.getDispatcher(null).
    }
    
    public void translate(ServerUpdateSequence sequence) {
        if (sequence.isSingleStepSequence()) {
            translateSingleStepAction(sequence);
        } else {
            translateMultiStepAction(sequence);
        }
    }
    
    
    private void translateMultiStepAction(ServerUpdateSequence sequence) {
        //TODO 比如又掉血啦, 又涨怒气啦什么的, 
        // 就要一步一步的sequence step 都加到esobject里
        
    }
    
    
}
