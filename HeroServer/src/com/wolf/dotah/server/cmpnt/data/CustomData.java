package com.wolf.dotah.server.cmpnt.data;

import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.util.client_const;

public class CustomData extends Data {
    private static final long serialVersionUID = -7974744822062137070L;
    
    public CustomData(String action, String sourcePlayer, String[] targetPlayers) {
    
        this.setAction(action);
        this.setString(client_const.param_key.player_name, sourcePlayer);
        this.setStringArray(client_const.param_key.target_player_list, targetPlayers);
    }
}
