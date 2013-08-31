package com.wolf.dotah.server.util;

public interface c {
    
    public String action = "action";
    public String action_description = "action_description";
    public String action_category = "action_category";
    public int action_user_ready = 1;
    public int default_player_count = 5;
    public int default_wait_time = 10;
    public int default_draw_count = 5;
    
    public interface server_action {
        //TODO 分成很少的几个action, 比如choosing之类的, chose, 至于是chose hero, hero就用参数
        public String none = "";
        public String start_game = "start_game";
        public String free_play = "free_play";
        public String update_table_info = "update_table_info";
        public String update_player_info = "update_player_info";
        public String choosing = "choosing";
        public String count_down = "counting_down";
        public String update_player_list_info = "update_player_list_info";
        
        public String chose_hero = "chose_hero";
        
    }
    
    public interface ac {
        public String init_hand_cards = "init_player_info_hand_cards";
        public String update_hand_cards = "update_player_info_hand_cards";
        public String choosing_from_hand = "choosing_from_hand";
        public String cutted = "update_table_cutted";
        public String turn_to_player = "turn_to_player";
    }
    
    public interface player_action {
        
        public String use_card = "player_use_card";
        public String use_hero_skill = "player_use_hero_skill";
        public String choose = "player_choose";
        public String cancel = "player_cancel";
    }
    
    public interface param_key {
        
        public String left = "left";
        String player_count = "player_count";
        String id_list = "id_list";
        public String how_many = "how_many";
        //        public String hand_card = "id_list";
        public String hero_candidates = "hero_candidates";
        public String who = "who";
    }
    
    public interface game_state {
        
        public String none = "";
        public String waiting = "waiting";
        
        interface waiting_type {
            int none = -1;
            int everybody = 10;
            int single = 20;
        }
    }
    
    public interface selectable_count {
        
        int default_value = 1;
        
    }
    
}
