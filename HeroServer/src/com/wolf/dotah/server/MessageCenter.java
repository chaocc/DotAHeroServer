package com.wolf.dotah.server;

import java.util.Collection;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.Players;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;
import com.wolf.dotah.server.util.l;

public class MessageCenter {
    
    private GamePlugin plugin;
    private TableModel table;
    
    // public int actionCache = -1;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
        
        this.debug(tag, "sendMessageToSingleUser:  user: " + user + ",  msg: "
                + msg.toString());
        msg.setInteger(c.param_key.kParamRemainingCardCount,
                table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
        
        this.debug(tag, "sendMessageToAll: " + msg.toString());
        msg.setInteger(c.param_key.kParamRemainingCardCount,
                table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToRoom(plugin.getApi().getZoneId(),
                plugin.getApi().getRoomId(), msg);
        
    }
    
    public void sendPublicMessage(EsObject msg, String from) {
        
        this.debug(tag, from + "is sending public message : " + msg.toString());
        plugin.getApi().sendPublicMessageToRoomFromPlugin(from,
                plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), "",
                msg, false, false);
    }
    
    // public void broadcastCachedMessage(Data msg, String from) {
    //
    // this.debug(tag, "broadcastCachedMessage : " + msg.toString());
    // msg.setAction(this.actionCache);
    // plugin.getApi().sendPublicMessageToRoomFromPlugin(from,
    // plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), "", msg, false,
    // false);
    // actionCache = -1;
    //
    // }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg,
            String exceptionUser) {
        
        msg.setInteger(c.param_key.kParamRemainingCardCount,
                table.getRemainCardCount());
        
        // this.debug(tag,
        // "sendMessageToAllWithoutSpecificUser: exceptionUser: " +
        // exceptionUser + ",  msg: " + msg.toString());
        // Collection<UserValue> users =
        // plugin.getApi().getUsersInRoom(plugin.getApi().getZoneId(),
        // plugin.getApi().getRoomId());
        // for (UserValue userv : users) {
        // if (!userv.getUserName().equals(exceptionUser)) {
        // plugin.getApi().sendPluginMessageToUser(userv.getUserName(), msg);
        // }
        // }
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
                Players playerList = new Players();
                Collection<UserValue> users = plugin.getApi().getUsersInRoom(
                        zone, room);
                this.debug(tag, " get users : " + users.toString());
                
                table = new TableModel(playerList, this);
                playerList.setTable(table);
                if (playerCount != -1) {
                    playerList.initWithUserCollectionAndPlayerCount(users,
                            playerCount);
                } else {
                    playerList.initWithUserCollection(users);
                }
            }
            table.dispatchHeroCandidates();
        } else if (client_const.kActionChoseHero == client_message) {
            table.players.getPlayerByUserName(user).pickedHero(msg);
        } else if (client_const.kActionChoseCardToCut == client_message) {
            table.choseCard(user, msg);
        } else if (client_const.kActionChoseCardToUse == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
        } else if (client_const.kActionUseHandCard == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.playerUseCard(user, msg);
        } else if (client_const.kActionChoseCardToDrop == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
        }
        
        else if (client_const.kActionStartRound == client_message) {
            // TODO table.startTurn(user);
        }
        
        else if (client_const.kActionCancel == client_message) {
            // table.cancelScheduledExecution();
            table.players.getPlayerByPlayerName(user).cancel();
        } else if (client_const.kActionChoseColor == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
        } else if (client_const.kActionChoseSuits == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
        } else if (client_const.kActionChoseCardToGet == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
        } else if (client_const.kActionDiscard == client_message) {
            table.cancelScheduledExecution();
            this.sendPublicMessage(msg, user);
            table.choseCard(user, msg);
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
        
        table.waiter.execution_id = -1;
        l.logger().d(tag, "cancelling schedule execution ... ");
        plugin.getApi().cancelScheduledExecution(callback_id);
        
    }
    
}
