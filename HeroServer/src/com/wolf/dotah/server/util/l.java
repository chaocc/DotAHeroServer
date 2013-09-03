package com.wolf.dotah.server.util;

import com.wolf.dotah.server.GamePlugin;

public class l {
    private static l logger;
    private GamePlugin gp;
    
    private l(GamePlugin input) {
    
        gp = input;
    }
    
    public static void init(GamePlugin input) {
    
        logger = new l(input);
    }
    
    public static l logger() {
    
        return logger;
    }
    
    public void d(String msg) {
    
        if (gp != null) {
            gp.getApi().getLogger().debug(msg);
        }
    }
    
    public void d(String tag, String msg) {
    
        if (gp != null) {
            gp.getApi().getLogger().debug(tag + msg);
        }
    }
    
}
