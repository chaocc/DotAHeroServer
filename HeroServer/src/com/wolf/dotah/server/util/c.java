package com.wolf.dotah.server.util;

public interface c {
    
    public String action = "action";
    public String action_description = "action_description";
    public String action_category = "action_category";
    public int default_player_count = 2;
    public int default_wait_time = 10;
    
    public interface server_action {
        //TODO 分成很少的几个action, 比如choosing之类的, chose, 至于是chose hero, hero就用参数
        public String start_game = "start_game";
        public String free_play = "free_play";
        public String update_table_info = "update_table_info";
        public String update_player_info = "update_player_info";
        public String choosing = "choosing";
        public String count_down = "counting_down";
        public String update_player_list_info = "update_player_list_info";
        public String chose_hero = "chose_hero";
        
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
    
}
