package com.wolf.dotah.server.util;

import java.util.Arrays;

public class u {
    public static int[] intArrayMapping(Integer[] integerArray) {
    
        int length = integerArray.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = integerArray[i];
        }
        return result;
    }
    
    public static Integer[] integerArrayMapping(int[] intArray) {
    
        int length = intArray.length;
        Integer[] result = new Integer[length];
        for (int i = 0; i < length; i++) {
            result[i] = intArray[i];
        }
        return result;
    }
    
    /*
     * use action+reason to check is correct.
     * only actions have limitations
     */
    public static int actionMapping(String action) {
    
        int result = -1;
        if (action.equals(c.playercon.state.choosing.choosing_hero)) {
            result = client_const.kActionUpdateDeckHero;
        } else if (action.equals(c.action.update_table_info)) {
            // result = client_const.action_update_table_player_list;
        } else if (action.equals(c.action.count_down)) {
            result = client_const.action.count_down;
        } else if (action.equals(c.action.update_player_list_info)) {
            result = client_const.action_update_table_confirmed_heros;
        } else if (action.equals(c.action.start_game)) {
            result = client_const.ACTION_START_GAME;
        } else if (action.equals(c.action.chose_hero)) {
            result = client_const.kActionInitPlayerHero;
        } else if (action.equals(c.action.init_hand_cards)) {
            result = client_const.kActionInitPlayerCard;
        } else if (action.equals(c.action.choosing_from_hand)) {// TODO 这个要改
            result = client_const.kActionChooseCardToCompare;
        } else if (action.equals(c.action.cutted)) {
            result = client_const.action_cutted;
        } else if (action.equals(c.action.update_hand_cards)) {
            result = client_const.kActionPlayerUpdateHand;
        }
        // else if (action.equals(c.action.update_hand_cards_count)) {
        // result = client_const.kActionUpdatePlayerHand;
        // }
        else if (action.equals(c.action.turn_to_player)) {
            result = client_const.kActionPlayingCard;
        } else if (action.equals(c.action.free_play)) {
            result = client_const.kActionPlayingCard;
        } else if (action.equals(c.action.choosing_to_evade)) {
            result = client_const.kActionChooseCardToUse;
        } else if (action.equals(c.action.update_player_property)) {
            result = client_const.kActionUpdatePlayerHero;
        } else if (action.equals(c.action.choosing_to_drop)) {
            result = client_const.kActionChooseCardToDiscard;
        } else if (action.equals(c.action.choosing_from_color)) {
            result = client_const.kActionChoosingColor;
        } else if (action.equals(c.action.choosing_from_suits)) {
            result = client_const.kActionChoosingSuits;
        } else if (action.equals(c.action.table_card_to_get)) {
            result = client_const.update_player_hand_get_card_from_table;
        } else if (action.equals(c.action.choosing_from_another)) {
            
        } else if (action.equals(c.action.choosing_from_list)) {
            result = client_const.choosing_from_list;
        }
        
        return result;
        
    }
    
    public static int actionMapping(String action, String reason) {
    
        int result = -1;
        if (action.equals(c.action.choosing_from_another)) {
            if (reason.equals(c.reason.m_greeding)) {
                result = client_const.kActionChooseCardToExtract;
            } else if (reason.equals(c.reason.m_greeded)) {
                result = client_const.kActionChooseCardToExtract;
            }
        } else if (action.equals(c.action.choosing_from_hand)) {
            if (reason.equals(c.reason.m_greeding)) {
                result = client_const.kActionChooseCardToGive;
            } else if (reason.equals(c.reason.turn_end)) {
                result = client_const.kActionChooseCardToDiscard;
            }
        }
        return result;
    }
    
    public static String actionMapping(int action) {
    
        String result = "";
        switch (action) {
            case -1: {
                result = "";
                break;
            }
        }
        return result;
    }
    
    public static String printArray(Object[] choosingCards) {
    
        String result = Arrays.toString(choosingCards);
        // MessageDispatcher.getDispatcher(null).debug(tag, "printArray: " +
        // result);
        return result;
    }
    
    public static String printArray(int[] choosingCards) {
    
        String result = Arrays.toString(choosingCards);
        // MessageDispatcher.getDispatcher(null).debug(tag, "printArray: " +
        // result);
        return result;
    }
    
    final String tag = "util: ";
    
    public static int[] getIndexList(int amount) {
    
        int[] result = new int[amount];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }
    
}
