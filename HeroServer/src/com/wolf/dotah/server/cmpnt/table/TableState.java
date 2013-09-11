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
public class TableState {
    
    int state;
    String subject;
    
    public int getState() {
    
        return state;
    }
    
    public TableState setState(int state) {
    
        this.state = state;
        return this;
    }
    
    public void setSubject(String name) {
    
        this.subject = name;
        
    }
    
    public String getSubject() {
    
        return subject;
    }
    
    public boolean isEqualToState(int inputState) {
    
        if (inputState == state) { return true; }
        return false;
    }
    
}
