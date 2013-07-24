package com.wolf.dotah.server.component;


import com.wolf.dotah.server.DeskController;
import com.wolf.dotah.testframework.User;


public class DeskModel {
    
    private DeskController controller;
    
    
    public DeskModel(DeskController deskController) {
    
        this.controller = deskController;
    }
    
    
    public PlayerModel getPlayerByUser(User user) {
    
        
        return new PlayerModel(controller);
        
    }
    
}
