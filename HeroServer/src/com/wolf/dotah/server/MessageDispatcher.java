package com.wolf.dotah.server;

import java.util.Collection;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.TableModel.tablevar;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.schedule.ChooseHero;
import com.wolf.dotah.server.cmpnt.table.schedule.CutCard;
import com.wolf.dotah.server.cmpnt.table.table_const.tablecon;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

public class MessageDispatcher {
    
    private GamePlugin plugin;
    private TableModel table;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
    
        this.debug(tag, "sendMessageToSingleUser:  user: " + user + ",  msg: " + msg.toString());
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
    
        this.debug(tag, "sendMessageToAll: " + msg.toString());
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, table.getRemainCardCount());
        plugin.getApi().sendPluginMessageToRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), msg);
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
    
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, table.getRemainCardCount());
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
    
    //TODO 想想能不能抽出来waiter之类的 组件
    public MessageDispatcher waitingForEverybody() {
    
        this.debug(tag, "start waiting");
        waitingType = c.game_state.waiting_type.everybody;
        return this;
    }
    
    private String waitReason;
    private int waitingType;
    
    public void becauseOf(String serverAction) {
    
        debug(tag, "because of " + serverAction);
        waitReason = serverAction;
        if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
            this.choosing_hero = plugin.getApi().scheduleExecution(1000, tablevar.wait_time + 1, new ChooseHero(this, waitingType));
        } else if (waitReason.equals(c.server_action.choosing)) {
            CutCard cc = new CutCard(this, waitingType);
            this.cutting = plugin.getApi().scheduleExecution(1000, tablevar.wait_time + 1, cc);
        }
        
    }
    
    public void handleMessage(String user, EsObject msg) {
    
        int client_message = msg.getInteger(c.action, -1);
        this.debug(tag, "plugin: " + plugin);
        if (client_const.ACTION_START_GAME == client_message) {
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
            
            int[] pickResult = msg.getIntegerArray(c.param_key.id_list, new int[] {});
            table.getPlayers().getPlayerByUserName(user).getResult(pickResult);
            
            
        } else if (client_const.kActionChooseCard == client_message) {
            //TODO 改成不要在这里写, 在player里写
            //TODO 简化player的state后, 使用player的state莱判断
            //现在先用table的state来判断
            int[] id = msg.getIntegerArray(c.param_key.id_list, new int[] {});
            this.debug(user, "table.getState().getState() :  " + table.getState().getState());
            if (table.getState().getState() == tablecon.state.not_started.cutting) {
                table.getCutCards().put(user, id[0]);
            }
        }
    }
    
    final String tag = "===>> MessageDispatcher ==>>  ";
    
    public MessageDispatcher(GamePlugin gamePlugin) {
    
        this.plugin = gamePlugin;
    }
    
    public void debug(String tag, String msg) {
    
        plugin.dlog(tag, msg);
    }
    
    
    public GamePlugin getPlugin() {
    
        return plugin;
    }
    
    public void setPlugin(GamePlugin plugin) {
    
        this.plugin = plugin;
    }
    
    public void dspatchHandcards() {
    
        table.dispatchHandcards();
        table.updatePlayersToCutting();
    }
    
    public void destroyTable() {
    
        table = null;
    }
    
    public TableModel getTable() {
    
        return table;
    }
    
    public void cancelScheduledExecution(int callback_id) {
    
        debug(tag, "cancelScheduledExecution " + callback_id);
        plugin.getApi().cancelScheduledExecution(callback_id);
    }
    
    public int choosing_hero = -1, cutting = -1;
    
}
