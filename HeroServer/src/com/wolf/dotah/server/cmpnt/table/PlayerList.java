package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.player_const;

public class PlayerList implements player_const {
    
    List<Player> playerList;
    List<String> userList;
    final String tag = "====>> PlayerList: ";
    
    public void initWithUserCollection(Collection<UserValue> input) {
        System.out.println(tag + "initWithUserCollection");
        initWithUserCollectionAndPlayerCount(input, defaultPlayerCount);
    }
    
    public void initWithUserCollectionAndPlayerCount(Collection<UserValue> usersInRoom, int playerCount) {
        
        /**
         * 保证多次调用init 方法是不管用的
         */
        if (playerList == null || playerList.size() == 0) {
            
            List<String> users = new ArrayList<String>();
            for (UserValue userv : usersInRoom) {
                users.add(userv.getUserName());
            }
            this.userList = users;
            initPlayerList(playerCount);
        }
    }
    
    private void initPlayerList(int playerCount) {
        
        for (String userName : userList) {
            Player player = new Player(userName);
            System.out.println("adding player " + userName);
            playerList.add(player);
        }
        if (playerCount > userList.size()) {
            int aiCount = playerCount - userList.size();
            for (int i = 0; i < aiCount; i++) {
                
                Player player = new Player(aiName + i);
                player.setAi(new Ai());
                System.out.println("adding ai " + aiName + i);
                playerList.add(player);
            }
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
    
    public Player getPlayerByIndex(int i) {
        
        return playerList.get(i);
    }
    
}
