package com.wolf.dotah.server.cmpnt.table;

public interface table_const {
    
    public interface candidates {
        
        int default_count_for_each_player = 3;
    }
    
    public interface tablecon {
        
        public interface state {
            
            public interface not_started {
                
                int chooing_hero = 1;
                int cutting = 5;
                int determing_destiny = 2;
            }
            
            public interface started {
                
                int free_playing = 3;
                int deciding = 4;
            }
            
            int ended = 5;
        }
        
    }
}
