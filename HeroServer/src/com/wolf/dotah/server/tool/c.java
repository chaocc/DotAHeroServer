package com.wolf.dotah.server.tool;


public interface c {
    
    public static final String action = "action";
    
    
    public static interface server_action {
        
        public static final String free_play = "free_play";
        public static final String update_deck_info = "update_deck_info";
        public static final String update_player_info = "update_player_info";
        public static final String choosing = "choosing";
        
    }
    
    public static interface player_action {
        
        public final String use_card = "player_use_card";
        public final String use_hero_skill = "player_use_hero_skill";
        public final String choose = "player_choose";
        public final String cancel = "player_cancel";
    }
    
    public static interface game_state {
        
        public String none = "";
        public String waiting = "waiting";
    }
    
    
}
