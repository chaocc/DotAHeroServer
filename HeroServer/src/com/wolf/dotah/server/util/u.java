package com.wolf.dotah.server.util;

import com.wolf.dotah.server.cmpnt.player.player_const.playercon;

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
            result = 0;
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
}
