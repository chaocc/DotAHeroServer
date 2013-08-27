package com.wolf.dotah.server.cmpnt.table;


/**
 * 
 * not_start// 比如 choosing hero
 * playing_somebody_deciding
 * playing_somebody_free_play
 * 
 * game over
 * 
 * 宿命
 * @author Solomon
 *
 */
public class TableState implements table_const {
    
    int state;
    String subject;
    
    //    Player currentPlayer;
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }
    
    public void setSubject(String name) {
        this.subject = name;
        
    }
    
}
