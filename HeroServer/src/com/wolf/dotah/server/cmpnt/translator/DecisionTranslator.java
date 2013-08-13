package com.wolf.dotah.server.cmpnt.translator;


import com.wolf.dotah.server.GamePlugin;


public class DecisionTranslator {
    
    private static DecisionTranslator translator;
    private GamePlugin dispatcher;
    
    
    public static DecisionTranslator getTranslator(GamePlugin dispatcher) {
    
        if (translator == null) {
            translator = new DecisionTranslator();
        }
        if (!dispatcher.equals(translator.dispatcher)) {
            translator.dispatcher = dispatcher;
        }
        return translator;
    }
    
    
    private DecisionTranslator() {
    
    }
}
