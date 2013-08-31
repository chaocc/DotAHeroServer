package com.wolf.dotah.server.cmpnt.table.schedule;


import java.util.Collection;
import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;


public class Ticker {
    
    
    private int countdownSeconds;
    private int callbackId = -1;
    private int turnCallback = -1;
    private int aiCallback = -1;
    private int tickCounter;
    
    BasePlugin plugin;
    
    
    public Ticker(BasePlugin plugin) {
    
        this.plugin = plugin;
    }
    
    
    private void startTicker() {
    
        callbackId = plugin.getApi().scheduleExecution(1000,
            -1,
            new ScheduledCallback() {
                
                public void scheduledCallback() {
                
                    tick();
                }
            });
    }
    
    
    public void tick() {
    
        //        if (!table.getGameState().isWaiting()) { return; }
        //        if (tickCounter < 1) {
        //            startRound();
        //        } else {
        //            sendCountDownSecondsLeftMessage();
        //        }
    }
    
    
    private void sendCountDownSecondsLeftMessage() {
    
        EsObject message = new EsObject();
        //        message.setString(PluginConstants.ACTION, PluginConstants.COUNTDOWN_SECONDS);
        //        message.setInteger(PluginConstants.COUNTDOWN_LEFT, tickCounter);
        //        sendAndLog("GoFishGame.sendCountDownSecondsLeftMessage", message);
        tickCounter--;
    }
    
    
    private void startRound() {
    
        plugin.getApi().cancelScheduledExecution(callbackId);
        
        // players can't join the game while it is in play
        plugin.getApi().setGameLockState(true);
        Collection<String> humansInRoom = plugin.getApi().getUsers();
        
    }
}
