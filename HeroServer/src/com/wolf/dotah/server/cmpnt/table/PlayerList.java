package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.layer.translator.MessageDispatcher;

public class PlayerList implements player_const {
    
    List<Player> playerList;
    List<String> userList;
    List<PlayerListListener> listeners = new ArrayList<PlayerListListener>();
    final String tag = "====>> PlayerList: ";
    
    public void initWithUserCollection(Collection<String> input) {
        
        //        MessageDispatcher.getDispatcher(null).debug(tag, "initWithUserCollection");
        initWithUserCollectionAndPlayerCount(input, defaultPlayerCount);
    }
    
    public void initWithUserCollectionAndPlayerCount(Collection<String> usersInRoom, int playerCount) {
        
        /**
         * 保证多次调用init 方法是不管用的
         */
        //        MessageDispatcher.getDispatcher(null).debug(tag, "initWithUserCollectionAndPlayerCount, playerList.size():" + playerList.size());
        if (playerList == null || playerList.size() == 0) {
            
            List<String> users = new ArrayList<String>();
            //            MessageDispatcher.getDispatcher(null).debug(tag, "initWithUserCollectionAndPlayerCount, usersInRoom:" + usersInRoom.toString());
            for (String userv : usersInRoom) {
                users.add(userv);
                //                MessageDispatcher.getDispatcher(null).debug(tag, "initWithUserCollectionAndPlayerCount, users.add():" + userv);
                
            }
            this.userList = users;
            initPlayerList(playerCount);
        }
    }
    
    private void initPlayerList(int playerCount) {
        
        for (String userName : userList) {
            Player player = new Player(userName);
            //            MessageDispatcher.getDispatcher(null).debug(tag, "adding player " + userName);
            playerList.add(player);
        }
        if (playerCount > userList.size()) {
            int aiCount = playerCount - userList.size();
            for (int i = 0; i < aiCount; i++) {
                
                Player player = new Player(aiName + i);
                player.setAi(new Ai());
                //                MessageDispatcher.getDispatcher(null).debug(tag, "adding ai " + aiName + i);
                playerList.add(player);
            }
        }
        for (PlayerListListener listener : listeners) {
            listener.didInitPlayerList();
        }
    }
    
    public interface PlayerListListener {
        public void didInitPlayerList();
    }
    
    public boolean registerPlayerListListener(PlayerListListener listener) {
        if (listeners.contains(listener)) {
            return false;
        } else {
            listeners.add(listener);
            return true;
        }
    }
    
    public int getCount() {
        
        return playerList.size();
    }
    
    //    public static PlayerList getModel() {
    //        
    //        if (model == null) {
    //            model = new PlayerList();
    //        }
    //        return model;
    //    }
    //    
    //    private static PlayerList model;
    
    public PlayerList() {
        
        playerList = new ArrayList<Player>();
    }
    
    public Player getPlayerByIndex(int i) {
        
        return playerList.get(i);
    }
    
    public Player getPlayerByUserName(String user) {
        
        //TODO 现在player 一定是在ai前边的, 所以player index和user index是一样的
        return playerList.get(userList.indexOf(user));
    }
    
    public String[] getNameList() {
        String[] names = new String[playerList.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = playerList.get(i).getUserName();
        }
        return names;
    }
    
    //    public void clearModel() {
    //        if (model != null) {
    //            model = null;
    //        }
    //    }
    
}
