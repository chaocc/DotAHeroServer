package com.wolf.dotah.server.cmpnt.translator;


import com.wolf.dotah.server.GamePlugin;


public class PlayerTranslator {
    
    private static PlayerTranslator translator;
    private DecisionTranslator decisionTranslator;
    GamePlugin dispatcher;
    
    
    public static PlayerTranslator getTranslator(GamePlugin dispatcher) {
    
        if (translator == null) {
            translator = new PlayerTranslator();
        }
        if (!dispatcher.equals(translator.dispatcher)) {
            translator.dispatcher = dispatcher;
        }
        return translator;
    }
    
    
    private PlayerTranslator() {
    
    }
    
    
    public void setDecisionTranslator(DecisionTranslator decisionTranslator) {
    
        this.decisionTranslator = decisionTranslator;
        
    }
    
}
