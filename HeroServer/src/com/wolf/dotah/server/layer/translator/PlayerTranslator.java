package com.wolf.dotah.server.layer.translator;




public class PlayerTranslator {
    
    private static PlayerTranslator translator;
    private DecisionTranslator decisionTranslator;
    
    
    public static PlayerTranslator getTranslator() {
        
        if (translator == null) {
            translator = new PlayerTranslator();
        }
        
        return translator;
    }
    
    
    private PlayerTranslator() {
        
    }
    
    
    public void setDecisionTranslator(DecisionTranslator decisionTranslator) {
        
        this.decisionTranslator = decisionTranslator;
        
    }
    
}
