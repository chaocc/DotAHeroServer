package com.wolf.dotah.server.util;

import java.util.Arrays;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
import com.wolf.tool.client_const;

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
    
    public static int actionMapping(String action) {
        int result = -1;
        if (action.equals(playercon.state.desp.choosing.choosing_hero)) {
            result = client_const.kActionUpdateDeckHero;
        } else if (action.equals(c.server_action.update_table_info)) {
            //                        result = client_const.action_update_table_player_list;
        } else if (action.equals(c.server_action.count_down)) {
            result = client_const.action.count_down;
        } else if (action.equals(c.server_action.update_player_list_info)) {
            result = client_const.action_update_table_confirmed_heros;
        } else if (action.equals(c.server_action.start_game)) {
            result = client_const.ACTION_START_GAME;
        } else if (action.equals(c.server_action.chose_hero)) {
            result = client_const.kActionInitPlayerHero;
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
    
    public static String printArray(Integer[] choosingCards) {
        String result = Arrays.toString(choosingCards);
        //        MessageDispatcher.getDispatcher(null).debug(tag, "printArray: " + result);
        return result;
    }
    
    public static String printArray(int[] choosingCards) {
        String result = Arrays.toString(choosingCards);
        //        MessageDispatcher.getDispatcher(null).debug(tag, "printArray: " + result);
        return result;
    }
    
    final static String tag = "util: ";
}
