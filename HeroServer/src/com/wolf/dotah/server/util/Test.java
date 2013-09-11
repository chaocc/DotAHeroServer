package com.wolf.dotah.server.util;


public class Test {
    public static void main(String... args) {
    
        int i = 10;
        Integer j =10;
        A a = new A();
        System.out.println();
    }
}


class A {
    int state = 10;
    
    @Override
    public boolean equals(Object obj) {
    
        if (obj.equals(state)) { return true; }
        return false;
    }
    
}