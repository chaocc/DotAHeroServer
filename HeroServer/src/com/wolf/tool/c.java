package com.wolf.tool;


public interface c {
    
    public String action = "action";
    int default_player_count = 6;
    
    
    public static interface server_action {
        
        public String free_play = "free_play";
        public String update_deck_info = "update_deck_info";
        public String update_player_info = "update_player_info";
        public String choosing = "choosing";
        
    }
    
    public static interface player_action {
        
        public String use_card = "player_use_card";
        public String use_hero_skill = "player_use_hero_skill";
        public String choose = "player_choose";
        public String cancel = "player_cancel";
    }
    
    public static interface game_state {
        
        public String none = "";
        public String waiting = "waiting";
    }
    
    public interface param {
        
        String player_count = "player_count";
    }
    
    
}
