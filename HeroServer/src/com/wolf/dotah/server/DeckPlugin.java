package com.wolf.dotah.server;


import java.util.Collection;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.cmpnt.DeskModel;
import com.wolf.dotah.server.tool.c;

/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * @author Solomon
 *
 */
public class DeckPlugin extends BasePlugin {
    
    private DeskModel desk;
    public EsObject currentMessageObject;
    private int countdownSeconds;
    private int callbackId = -1;
    private int turnCallback = -1;
    private int aiCallback = -1;
    private int tickCounter;
    
    
    @Override
    public void init(EsObjectRO parameters) {
    
        desk = new DeskModel(this);
        d.debug("DeskPlugin initialized " + d.version);
    }
    
    
    @Override
    public void request(String user, EsObjectRO message) {
        this.currentMessageObject = new EsObject();
        currentMessageObject.addAll(message);
        d.debug(user + " requests: " + currentMessageObject.toString());
        
//        String client_message = currentMessageObject.getString(c.action.client_message, "");
//        if (c.action.client_message_game_start.equals(client_message)) {
//            
//            //            startTicker();
//            for (Player p : desk.getPlayers()) {
//                
//                EsObject obj = new EsObject();
//                Integer[] heroIntegers = p.getHerosForChoosing().toArray(new Integer[] {});
//                int[] heroIds = new int[heroIntegers.length];
//                for (int i = 0; i < heroIntegers.length; i++) {
//                    heroIds[i] = heroIntegers[i];
//                }
//                obj.setString(c.action.server_message, c.choosing.action_choosing_hero_id);
//                obj.setIntegerArray(c.choosing.id_list, heroIds);
//                this.sendMessageToUser(p.getUserName(), obj);
//            }
//            
//            
//        } else if (c.client_constants.kActionChooseHeroId.equals(client_message)) {
//            Player p = desk.getPlayerByUserName(user);
//            p.setHeroId(message.getIntegerArray(c.choosing.id_list)[0]);
//            //            desk.
//            
//        }
    }
    
    
    private void startTicker() {
    
        callbackId = getApi().scheduleExecution(1000,
                -1,
                new ScheduledCallback() {
                    
                    public void scheduledCallback() {
                    
                        tick();
                    }
                });
    }
    
    
    public void tick() {
    
        if (!desk.game_state.equals(c.game_state.waiting)) { return; }
        if (tickCounter < 1) {
            startRound();
        } else {
            sendCountDownSecondsLeftMessage();
        }
    }
    
    
    private void sendCountDownSecondsLeftMessage() {
    
        EsObject message = new EsObject();
        //        message.setString(PluginConstants.ACTION, PluginConstants.COUNTDOWN_SECONDS);
        //        message.setInteger(PluginConstants.COUNTDOWN_LEFT, tickCounter);
        //        sendAndLog("GoFishGame.sendCountDownSecondsLeftMessage", message);
        tickCounter--;
    }
    
    
    private void startRound() {
    
        getApi().cancelScheduledExecution(callbackId);
        
        // players can't join the game while it is in play
        getApi().setGameLockState(true);
        Collection<String> humansInRoom = getApi().getUsers();
        
    }
    
    
    private void sendMessageToUser(String user, EsObject obj) {
    
        getApi().sendPluginMessageToUser(user, obj);
    }
    
    
    private class D {
        
        private final String logprefix = "===== desk =>> ";
        public String version = "v 0.06";
        
        
        public void debug(String message) {
        
            getApi().getLogger().debug(logprefix + message);
        }
    }
    
    
    private D d = new D();
    
}
