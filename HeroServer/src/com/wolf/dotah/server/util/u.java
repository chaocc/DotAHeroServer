package com.wolf.dotah.server.util;

public class u {
    public static int[] integerArrayToIntArray(Integer[] integerArray) {
        int length = integerArray.length;
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = integerArray[i];
        }
        return result;
    }
}
