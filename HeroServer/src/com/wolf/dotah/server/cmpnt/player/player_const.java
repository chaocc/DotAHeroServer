package com.wolf.dotah.server.cmpnt.player;

import com.wolf.dotah.server.util.c;

public interface player_const {
    final int defaultPlayerCount = c.default_player_count;
    
    interface playercon {
        interface property {
            
        }
        
        interface state {
            
            //            interface state_code {
            //                
            //                int unavailable = -10;
            //                int free_play = 10;
            //                int idle = 20;//没进度条, 什么都没做, 比如有人在free_play
            //                int waiting = 30;//有进度条, 比如等待某人的驱散
            //                int choosing = 40;
            //            }
            
            interface desp {
                String unavailable = "unavailable";
                String free_play = "free_play";
                String idle = "idle";//没进度条, 什么都没做, 比如有人在free_play
                
                interface waiting {
                    String waiting = "waiting"; //有进度条, 比如等待某人的驱散
                    
                }
                
                interface choosing {
                    String choosing = "choosing";
                    String choosing_hero = "choosing_hero";
                }
            }
            
            interface param_key {
                interface general {
                    String choosing_card = "choosing_card";
                    String id_list = c.param_key.id_list;
                }
                
                interface detail {
                    String hero_candidates = "hero_candidates";
                }
            }
        }
        
        interface sequence {
            interface step {
                interface type {
                    String state = "state";
                    String property = "property";
                }
            }
        }
    }
    
    String aiName = "AI Player ";
    String aiPlayerName = "I'm AI ";
}
