package com.wolf.dotah.server.cmpnt.player;

import java.util.Random;

public class Ai {
    final boolean isAi = true;
    
    public int chooseSingle(int[] candidates) {
        return candidates[new Random().nextInt(candidates.length)];
    }

    public boolean isAi() {
        return isAi;
    }
}
