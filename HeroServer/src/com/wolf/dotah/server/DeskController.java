package com.wolf.dotah.server;


import com.wolf.dotah.server.component.DeskModel;
import com.wolf.dotah.server.component.PlayerModel;
import com.wolf.dotah.testframework.ClientRequest;
import com.wolf.dotah.testframework.User;


public class DeskController {
    
    private DeskModel desk;
    
    public void gotClientRequest(ClientRequest request, User user) {
    
        PlayerModel pm = desk.getPlayerByUser(user);
        pm.act(request);
        
        
        
    }
    
}
