package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;

public class PlayerList {
    
    private List<Player> playerList = new ArrayList<Player>();
    private List<String> userList;
    private List<PlayerListListener> listeners = new ArrayList<PlayerListListener>();
    final String tag = "====>> PlayerList: ";
    private TableModel table;
    
    public void initWithUserCollection(Collection<UserValue> input) {
    
        initWithUserCollectionAndPlayerCount(input, c.default_player_count);
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
            Player player = new Player(userName, table);
            debug(tag, "adding player " + userName);
            playerList.add(player);
        }
        if (playerCount > userList.size()) {
            int aiCount = playerCount - userList.size();
            for (int i = 0; i < aiCount; i++) {
                
                Player player = new Player(c.playercon.aiName + i, table);
                player.setAi(new Ai());
                debug(tag, "adding ai " + c.playercon.aiName + i);
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
    
    public PlayerList() {
    
    }
    
    public Player getPlayerByIndex(int i) {
    
        return playerList.get(i);
    }
    
    public Player getPlayerByUserName(String user) {
    
        //TODO 现在player 一定是在ai前边的, 所以player index和user index是一样的
        return playerList.get(userList.indexOf(user));
    }
    
    public Player getPlayerByPlayerName(String name) {
    
        debug(tag, "getPlayerByPlayerName, name: " + name + " from list " + playerList.toString());
        for (Player p : playerList) {
            if (p.getUserName().equals(name)) {
                debug(tag, "returning " + p.toString());
                return p;
            }
        }
        return null;
    }
    
    
    public String[] getNameList() {
    
        String[] names = new String[playerList.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = playerList.get(i).getUserName();
        }
        return names;
    }
    
    public TableModel getTable() {
    
        return table;
    }
    
    public void setTable(TableModel table) {
    
        this.table = table;
    }
    
    public List<Player> getPlayerList() {
    
        return playerList;
    }
    
    public List<String> getUserList() {
    
        return userList;
    }
    
    public Data toSubtleData() {
    
        Data data = new Data();
        int[] idList = new int[playerList.size()];
        for (int i = 0; i < idList.length; i++) {
            Player p = playerList.get(i);
            idList[i] = p.getProperty().getHero().getId();
        }
        data.setIntegerArray(c.param_key.id_list, idList);
        return data;
    }
    
    private void debug(String tag, String log) {
    
        if (table != null) {
            l.logger().d(tag, log);
        } else {
            System.out.println(tag + " table is null in line 159");
        }
        
    }
    
    //    @Override
    //    public String toString() {
    //    
    //        return "PlayerList [playerList=" + playerList + ", userList=" + userList + ", listeners=" + listeners + ", tag=" + tag + "]";
    //    }
    
}
