package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Test {
    
    public static void main(String... args) {
    
        
    }
    
    
    public static void consoleDesign() {
    
        System.out.print("Enter your name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String userName = null;
        try {
            userName = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your name!");
            System.exit(1);
        }
        
        System.out.println("Thanks for the name, " + userName);
    }
    
    
    class TestInt {
        
        static final int a = 1;
    }
    
    
    public static void printGbc() {
    
        String str = new String("abc");
        char[] ch = { 'a', 'b', 'c' };
        change(str, ch);
        
        System.out.print(str + " ");
        System.out.print(ch);
    }
    
    
    public static void change(String str, char[] ch) {
    
        str = "gbc";
        ch[0] = 'g';
    }
    
    
    public static void testIndexOf() {
    
        String message = "杀nnn";
        System.out.println(message.substring(message.indexOf("杀") + 1));
    }
    
    
    public static void testEnumValueOf() {
    
        System.out.println(TestEnum.valueOf("_1").faceNumber);
    }
    
    
    enum TestEnum {
        _0(0, "普通攻击", "黑桃A", 1),
        _1(1, "普通攻击", "黑桃2", 2);
        
        public int cardId;
        public String name;
        public String cardFace;
        public int faceNumber;
        
        
        private TestEnum(int cardId, String name, String face, int faceNumber) {
        
            this.cardId = cardId;
            this.name = name;
            this.cardFace = face;
            this.faceNumber = faceNumber;
        }
    }
    
    
    public static void getNumberAndSuit() {
    
        String source = "黑桃A";
        String faceNumberString = source.substring(2);
        System.out.println(faceNumberString);
        int faceNumber = -1;
        if (faceNumberString.equals("A")) {
            faceNumber = 1;
        } else {
            faceNumber = Integer.parseInt(faceNumberString);
        }
        System.out.println(faceNumber);
    }
    
    
    public static void testSubString() {
    
        String source = "普通攻击:";
        String result = source.substring(0, source.indexOf(':'));
        System.out.println(result);
    }
    
    
    public static void testIntEqualsString() {
    
        int[] ints = { 3, 5, 98 };
        String string = "5";
        for (int i : ints) {
            System.out.println((i == Integer.parseInt(string)));
        }
    }
    
    
    public static void testArrayToListShuffle() {
    
        List<String> forceList = Arrays.asList(new String[] { "a", "b" });
        Collections.shuffle(forceList);
        System.out.println(Arrays.toString(forceList.toArray(new String[] {})));
    }
    
    
    public static void testEmptyArrayValues() {
    
        String[] strings = new String[5];
        System.out.println(strings[4]);
    }
    
    
    public static void testAddToEmptyList() {
    
    }
    
    
    public static void printStringArray() {
    
        String[] charsToChoose = { "1st", "2nd", "3rd", "4th" };
        System.out.println(Arrays.toString(charsToChoose));
        
    }
    
    
    public static void shuffle() {
    
        int[] x = { 1, 2, 3, 4, 5, 6, 7, 8 };
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < x.length; i++) {
            // System.out.print(x[i] + ", ");
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
