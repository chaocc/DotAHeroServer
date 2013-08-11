package com.wolf.dotah.server.cmpnt.table;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.player.player_const;


public class PlayerList implements player_const {
    
    List<Player> playerList;
    List<String> userList;
    
    
    public void initWithUserCollection(Collection<UserValue> input) {
    
        List<String> users = new ArrayList<String>();
        for (UserValue userv : input) {
            users.add(userv.getUserName());
        }
        this.userList = users;
        initPlayerList();
        
    }
    
    
    private void initPlayerList() {
    
        for (String userName : userList) {
            Player player = new Player(userName);
            playerList.add(player);
        }
    }
    
    
    public int getCount() {
    
        return playerList.size();
    }
    
    
    public static PlayerList getModel() {
    
        if (model == null) {
            model = new PlayerList();
        }
        return model;
    }
    
    
    private static PlayerList model;
    
    
    private PlayerList() {
    
        playerList = new ArrayList<Player>();
    }
}
