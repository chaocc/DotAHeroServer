package com.wolf.dotah.server.cmpnt.player;

import java.util.Random;

public class Ai {
    final boolean isAi = true;
    final String tag = "===>> Ai";
    
    public int chooseSingle(int[] candidates) {
//        MessageDispatcher.getDispatcher(null).debug(tag, "" + u.printArray(candidates));
        return candidates[new Random().nextInt(candidates.length)];
    }
    
    public boolean isAi() {
        return isAi;
    }

    @Override
    public String toString() {
    
        return "Ai [isAi=" + isAi + ", tag=" + tag + "]";
    }
}
