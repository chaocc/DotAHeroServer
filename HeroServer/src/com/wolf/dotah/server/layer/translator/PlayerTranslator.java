package com.wolf.dotah.server.layer.translator;

public class PlayerTranslator {
    
    private DecisionTranslator decisionTranslator;
    private MessageDispatcher dispatcher;
    
    public PlayerTranslator(MessageDispatcher input) {
        this.dispatcher = input;
    }
    
    public void setDecisionTranslator(DecisionTranslator decisionTranslator) {
        
        this.decisionTranslator = decisionTranslator;
        
    }
    
}
