package com.wolf.dotah.server.tool;


public interface c {
    public static interface action {
        public static final String server_message = "action";
        
        public final String //  client actions
                client_message = "action",
                client_message_game_start = "owner_game_start",
                player_action_use_card = "player_use_card",
                player_action_use_hero_skill = "player_use_hero_skill",
                player_action_choose = "player_choose",
                player_action_cancel = "player_cancel"
                
                
                ;
    }
    
    public static interface free_play {
        // actions
        public static final String action_free_to_play = "playing_card";
        // params
        public static final String target_player_list = "target_player_list";
        
    }
    
    public static interface player_info {
        public static final String action_update_player_info = "update_player_info";
    }
    
    public static interface deck_info {
        // action
        public static final String action_update_deck_info = "update_deck_info";
        // params
        public static final String remaining_count = "remaining_count";
    }
    
    public static interface ordering {
        public static final String action_ordering = "arranging_card_id";
    }
    
    public static interface choosing {
        //actions
        public static final String action_choosing_card_id = "choosing_card_id";
        public static final String action_choosing_hero_id = "choosing_hero_id";
        public static final String action_choosing_color = "choosing_color";
        public static final String action_choosing_suits = "choosing_suits";
        public static final String action_choosing_yes_no = "choosing_yes_no";
        
        //params
        public static final String howmany = "selectable_count";
        
        public static final String id_list = "id_list";
        public static final String index_list = "index_list";
        
    }
    
    public static interface client_constants {
        public static final String kActionStartGame = "startGame"; // 开始游戏
        public static final String kActionUseCard = "useCard"; // 使用卡牌
        public static final String kActionUseHeroSkill = "useHeroSkill"; // 使用英雄技能
        public static final String kActionCancel = "cancel"; // 取消
        //        public static final String kActionDiscard = "discard"; // 确定弃牌
        
        public static final String kActionHeroList = "heroList"; // 所有玩家选中的英雄
        
        public static final String kActionChooseHeroId = "chooseHeroId"; // 选择英雄牌
        public static final String kActionChooseCardId = "chooseCardId"; // 选择卡牌ID
        public static final String kActionChooseColor = "chooseColor"; // 选择卡牌颜色
        public static final String kActionChooseSuits = "chooseSuits"; // 选择卡牌花色
        public static final String kActionChooseYesNo = "chooseYesNo"; // 选择Yes/No
        
        public static final String kActionArrangeCardId = "arrangeCardId";
    }
    
    
    public static interface game_state {
        public String game_state_none = "";
        public String game_state_waiting = "waiting";
    }
    
    public static interface legacy {
        public static final String action = "action";
        public static final int action_user_ready = 1;
    }
    
}
