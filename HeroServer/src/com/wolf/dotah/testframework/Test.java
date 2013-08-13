package com.wolf.dotah.testframework;


import java.io.File;
import java.io.IOException;


public class Test {
    
    public static void main(String... args) {
    
        System.out.println(Integer.valueOf('a'));
    }
    
    
    class TestThread extends Thread {
        
        @Override
        public void run() {
        
            super.run();
            if (true) {
                File f = new File("dd");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    interface testI {
        
        int b = 20;
    }
}
