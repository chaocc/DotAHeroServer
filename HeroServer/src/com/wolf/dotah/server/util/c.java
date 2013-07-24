package com.wolf.dotah.server.util;


public interface c {
    
    public final String //  server commands to player
            push_player_model = "update_player_model",
            push_desk_model = "update_desk_model"
            
            
            ;
    
    public final String //  client actions
            player_action_use_card = "player_use_card",
            player_action_choose = "player_choose",
            player_action_cancel = "player_cancel"
            
            
            ;
    
}
