package com.wolf.dotah.server.cmpnt.player;


public interface player_const {
    
    interface statecon {
        
        interface stage {
            
            int unavailable = -1;
            int free_play = 1;
            int choosing = 2;
            int waiting = 3;//有进度条, 比如等待某人的驱散
            int idle = 4;//没进度条, 什么都没做, 比如有人在free_play
        }
    }
    
    
    String aiName = "AI Player ";
    String aiPlayerName = "I'm AI ";
}
