package com.wolf.dotah.server.util;


public class Test {
    public static void main(String... args) {
    
        A a = new A();
        A c = new A();
        System.out.println(a == c);
        

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