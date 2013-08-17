package com.wolf.dotah.server.layer.translator;


public interface commands {
    
    public static interface action {
        
        // free play
        public static final String action_free_to_play = "playing_card";
        // room owner click start game
        public static final String client_message_game_start = "owner_game_start";
        public static final int action_user_ready = 1;
        
        public static final String action_choosing_card_id = "choosing_card_id";
        public static final String action_choosing_hero_id = "choosing_hero_id";
        public static final String action_choosing_color = "choosing_color";
        public static final String action_choosing_suits = "choosing_suits";
        public static final String action_choosing_yes_no = "choosing_yes_no";
        public static final String action_ordering = "arranging_card_id";
    }
    
    
    public static interface params {
        
        public static final String howmany = "selectable_count";
        
        public static final String remaining_count = "remaining_count";
        public static final String id_list = "id_list";
        public static final String index_list = "index_list";
        public static final String target_player_list = "target_player_list";
    }
    
}
