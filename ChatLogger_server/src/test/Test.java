package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String... args) {
        testAddToEmptyList();
    }
    
    public static void testAddToEmptyList() {

    }
    
    public static void printStringArray() {
        String[] charsToChoose = { "1st", "2nd", "3rd", "4th" };
        System.out.println(Arrays.toString(charsToChoose));
        
    }
    
    public static void shuffle() {
        int[] x = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + ", ");
            list.add(x[i]);
        }
        System.out.println();
        
        Collections.shuffle(list);
        
        for (int number : list) {
            System.out.print(number + ", ");
        }
    }
    
    public static void testRandomNumber() {
        System.out.println(new Random().nextInt(8));
    }
}
