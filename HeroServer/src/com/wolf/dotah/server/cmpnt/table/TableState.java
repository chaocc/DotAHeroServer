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
    String[] targets;
    
    public int getState() {
    
        return state;
    }
    
    public TableState setState(int state) {
    
        this.state = state;
        return this;
    }
    
    public TableState(int input) {
    
        this.state = input;
    }
    
    public TableState(int inputState, String inputSubject) {
    
        this.state = inputState;
        this.subject = inputSubject;
    }
    
    public TableState(int inputState, String inputSubject, String[] inputTargets) {
    
        this.state = inputState;
        this.subject = inputSubject;
        this.targets = inputTargets;
    }
    
    public TableState(int inputState, String[] inputTargets) {
    
        this.state = inputState;
        this.targets = inputTargets;
    }
    
    public void setSubject(String name) {
    
        this.subject = name;
        
    }
    
    public String getSubject() {
    
        return subject;
    }
    
    
    public String[] getTargets() {
    
        return targets;
    }
    
    public void setTargets(String[] targets) {
    
        this.targets = targets;
    }
    
    public boolean isEqualToState(int inputState) {
    
        if (inputState == state) { return true; }
        return false;
    }
    
}
