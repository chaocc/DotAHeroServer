package com.electrotank.examples.chatlogger.components;

public class PluginConstants {
    public static final String ACTION                   = "action";
    public static final int    ACTION_START_GAME        = 1;
    public static final int    ACTION_USER_READY        = 0;
    public static final int    ACTION_CHOOSE_CHARACTER  = 2;
    public static final int    ACTION_CHOSE_CHARACTER   = 3;
    public static final int    ACTION_ALL_HEROS         = 4;
    
    public static final int    ACTION_DISPATCH_FORCE    = 5;
    public static final int    ACTION_DISPATCH_HANDCARD = 6;                    //should along with cards
                                                                                 
    public static final int    ACTION_START_TURN        = 7;
    public static final int    ACTION_DRAW_CARDS        = 8;                    //client send to server
                                                                                 
    public static final int    ACTION_STAKE             = 9;
    public static final int    ACTION_SEND_CARDS        = 10;                   //server send to client
                                                                                 
    private static final int   ACTION_BASE              = 100;
    public static final int    ACTION_NORMAL_ATTACK     = ACTION_BASE+1;
    public static final int    ACTION_ATTACKED          = ACTION_BASE+2;
    public static final int    ACTION_DAMAGED           = ACTION_BASE+3;
    public static final int    ACTION_EVASION           = ACTION_BASE+4;
    
    public static final String CHARACTORS_TO_CHOOSE     = "toBeSelectedHeroIds";
    public static final String SORTED_PLAYER_NAMES      = "sortedPlayerNames";
    public static final String STACK_CARD_COUNT         = "remainingCardCount";
    public static final String SELECTED_HERO_ID         = "heroId";
    public static final String ALL_HEROS                = "allHeroIds";
    public static final String ROLE_IDS                 = "roleIds";            //value for dispatch force action
    public static final String DISPATCH_CARDS           = "gotPlayingCardIds";
    public static final String USED_CARDS               = "UsedPlayingCardIds";
    public static final String PLAYER_NAME              = "playerName";
    public static final String TARGET_PLAYERS           = "targetPlayerNames";
    //    public static final String CHARACTER_CONFIRMATION        = "char_confirm";
    public static final String FORCE                    = "force";
    //    public static final String INIT_HAND_CARDS          = "init_handcards";
    public static final String CARD_STACK               = "card_stack";
    public static final String STAKE_CARD               = "staked_card";
    
}
