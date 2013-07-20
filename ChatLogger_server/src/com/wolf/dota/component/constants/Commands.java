package com.wolf.dota.component.constants;


public interface Commands {
    
    public static final String
            code_action = "action",
            code_client_action_required = "playerState",
            error = "err",
            action = "command"
            
            
            ;
    
    public static final String action_hitted_by = "hitted_by",
            action_hit_success = "hit_success",
            action_turn_change = "turn_change",
            
            action_pick_for_using_gree = "pick_cards_for_you_are_using_gree",
            action_pick_for_targetted_by_gree = "pick_a_card_for_you_are_targetted_by_gree",
            
            action_continue_chakra = "last_guess_success_and_you_can_continue_guess",
            action_should_stake = "please_stake_after_got_initial_cards",
            
            
            action_game_finish = "game_finish"
            
            
            ;
}
