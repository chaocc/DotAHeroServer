package com.wolf.dotah.server.cmpnt.data;

import com.wolf.dotah.server.cmpnt.Data;

public class CustomData extends Data {
    private static final long serialVersionUID = -7974744822062137070L;
    
    public CustomData(String action, String sourcePlayer, String[] targetPlayers) {
    
        this.setAction(action);

    }
}
