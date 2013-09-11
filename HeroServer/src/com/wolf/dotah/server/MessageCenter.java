package com.wolf.dotah.server;

import java.util.Collection;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;
import com.wolf.dotah.server.util.l;

public class MessageCenter {
    
    private GamePlugin plugin;
    private TableModel table;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
    
        this.debug(tag, "sendMessageToSingleUser:  user: " + user + ",  msg: " + msg.toString());
        msg.setInteger(c.param_key.kParamRemainingCardCount, table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
    
        this.debug(tag, "sendMessageToAll: " + msg.toString());
        msg.setInteger(c.param_key.kParamRemainingCardCount, table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), msg);
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
    
        msg.setInteger(c.param_key.kParamRemainingCardCount, table.getRemainCardCount());
        this.debug(tag, "sendMessageToAllWithoutSpecificUser: exceptionUser: " + exceptionUser + ",  msg: " + msg.toString());
        Collection<UserValue> users = plugin.getApi().getUsersInRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId());
        for (UserValue userv : users) {
            if (!userv.getUserName().equals(exceptionUser)) {
                plugin.getApi().sendPluginMessageToUser(userv.getUserName(), msg);
            }
        }
    }
    
    public void broadcastMessage(Data data) {
    
        sendMessageToAll(data);
    }
    
    
    public void handleMessage(String user, EsObject msg) {
    
        int client_message = msg.getInteger(c.a, -1);
        this.debug(tag, "plugin: " + plugin);
        if (client_const.ACTION_START_GAME == client_message) {
            plugin.getApi().setGameLockState(true);
            this.debug(tag, "translateGameStartFromClient");
            if (table == null) {
                int playerCount = msg.getInteger(c.param_key.player_count, -1);
                int zone = plugin.getApi().getZoneId();
                int room = plugin.getApi().getRoomId();
                PlayerList playerList = new PlayerList();
                Collection<UserValue> users = plugin.getApi().getUsersInRoom(zone, room);
                this.debug(tag, " get users : " + users.toString());
                
                table = new TableModel(playerList, this);
                playerList.setTable(table);
                if (playerCount != -1) {
                    playerList.initWithUserCollectionAndPlayerCount(users, playerCount);
                } else {
                    playerList.initWithUserCollection(users);
                }
            }
            table.dispatchHeroCandidates();
        } else if (client_const.kActionChooseHeroId == client_message) {
            table.getPlayers().getPlayerByUserName(user).pickedHero(msg);
            
            
        } else if (client_const.kActionChooseCard == client_message) {
            //TODO 改成不要在这里写, 在player里写
            //TODO 简化player的state后, 使用player的state莱判断
            //现在先用table的state来判断
            table.choseCard(user, msg);
            
            
        } else if (client_const.kActionUseHandCard == client_message) {
            table.playerUseCard(user, msg);
        } else if (client_const.kActionStartRound == client_message) {
            table.startTurn(user);
        }
    }
    
    final String tag = "===>> MessageDispatcher ==>>  ";
    
    public MessageCenter(GamePlugin gamePlugin) {
    
        this.plugin = gamePlugin;
    }
    
    public TableModel getTable() {
    
        return table;
    }
    
    public void debug(String tag, String msg) {
    
        l.logger().d(tag, msg);
    }
    
    public int scheduleExecution(int i, int j, ScheduledCallback callback) {
    
        return plugin.getApi().scheduleExecution(i, j, callback);
    }
    
    public void cancelScheduledExecution(int callback_id) {
    
        plugin.getApi().cancelScheduledExecution(callback_id);
        
    }
    
    
}
