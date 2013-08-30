package com.wolf.dotah.server;

import java.util.Collection;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
import com.wolf.dotah.server.cmpnt.table.schedule.ChooseHero;
import com.wolf.dotah.server.cmpnt.table.schedule.CutCard;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

public class MessageDispatcher {
    
    private TableTranslator tableTranslator;
    private PlayerTranslator playerTranslator;
    //    private DecisionTranslator decisionTranslator;
    private GamePlugin plugin;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
        this.debug(tag, "sendMessageToSingleUser:  user: " + user + ",  msg: " + msg.toString());
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, tableTranslator.getTable().getRemainCardCount());
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
        this.debug(tag, "sendMessageToAll: " + msg.toString());
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, tableTranslator.getTable().getRemainCardCount());
        plugin.getApi().sendPluginMessageToRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), msg);
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
        msg.setInteger(client_const.param_key.kParamRemainingCardCount, tableTranslator.getTable().getRemainCardCount());
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
            this.choosing_hero = plugin.getApi().scheduleExecution(1000, 11, new ChooseHero(this, waitingType));
        } else if (waitReason.equals(c.server_action.choosing)) {
            this.cutting = plugin.getApi().scheduleExecution(1000, 11, new CutCard(this, waitingType));
        }
        
    }
    
    public void handleMessage(String user, EsObject msg) {
        
        int client_message = msg.getInteger(c.action, -1);
        if (client_const.ACTION_START_GAME == client_message) {
            this.debug(tag, "plugin: " + plugin);
            tableTranslator.translateGameStartFromClient(plugin, msg);
        } else if (client_const.kActionChooseHeroId == client_message) {
            playerTranslator.translateUpdate(tableTranslator.getPlayerList().getPlayerByUserName(user), msg);
        } else if (client_const.kActionChooseCard == client_message) {
            playerTranslator.translateChose(user, msg);
        }
    }
    
    final String tag = "===>> MessageDispatcher ==>>  ";
    
    public MessageDispatcher(GamePlugin gamePlugin) {
        
        this.plugin = gamePlugin;
        tableTranslator = new TableTranslator(this);
        playerTranslator = new PlayerTranslator(this);
        //        decisionTranslator = new DecisionTranslator(this);
        //        tableTranslator.setDecisionTranslator(decisionTranslator);
        //        playerTranslator.setDecisionTranslator(decisionTranslator);
    }
    
    public void debug(String tag, String msg) {
        plugin.dlog(tag, msg);
    }
    
    public void destroyTable() {
        tableTranslator.destroyTable();
        
    }
    
    public GamePlugin getPlugin() {
        return plugin;
    }
    
    public void setPlugin(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    public TableTranslator getTableTranslator() {
        return tableTranslator;
    }
    
    public PlayerTranslator getPlayerTranslator() {
        return playerTranslator;
    }
    
    public void cancelScheduledExecution(int callback_id) {
        plugin.getApi().cancelScheduledExecution(callback_id);
    }
    
    public int choosing_hero = -1, cutting = -1;
    
}
