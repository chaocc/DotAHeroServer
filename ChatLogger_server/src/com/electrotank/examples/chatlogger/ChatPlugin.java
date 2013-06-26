package com.electrotank.examples.chatlogger;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.ChainAction;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserPublicMessageContext;

public class ChatPlugin extends BasePlugin {
    
    private ChatLogger chatLogger = null;
    
    @Override
    public void init(EsObjectRO parameters) {
        
        getApi().getLogger().debug("ChatPlugin initialized");
    }
    
    @Override
    public void request(String user, EsObjectRO message) {
        EsObject messageIn = new EsObject();
        messageIn.addAll(message);
        getApi().getLogger().debug(
                user + " requests: " + messageIn.toString());
        
        String action = messageIn.getString(PluginConstants.ACTION);
        
        if (action.equals("kill")) {
            EsObject obj = new EsObject();
            obj.setString(PluginConstants.ACTION, "shan");
            getApi().sendPluginMessageToUser(user, obj);
        } else if (action.equals(PluginConstants.ACTION_START_GAME)) {
            EsObject obj = new EsObject();
            obj.setString(PluginConstants.ACTION, "print_test");
            getApi().sendPluginMessageToUser(user, obj);
        } else {
            EsObject obj = new EsObject();
            obj.setString("action", "nan");
            getApi().sendPluginMessageToUser(user, obj);
        }
        
        /*
         * 
         * if (isQueued) {
         * getApi().sendQueuedPluginMessageToUsers(initializedPlayers, message);
         * } else { getApi().sendPluginMessageToUsers(initializedPlayers,
         * message); }
         * 
         * 
         * 
         * 
         * String action = messageIn.getString(PluginConstants.ACTION);
         * 
         * if (action.equals(PluginConstants.INIT_ME)) {
         * handlePlayerInitRequest(playerName); } else if
         * (action.equals(PluginConstants.HEADING_UPDATE)) {
         * handleHeadingUpdate(playerName, messageIn); } else if
         * (action.equals(PluginConstants.UPDATE_TURRET_ROTATION)) {
         * handleTurretRotationUpdate(playerName, messageIn); } else if
         * (action.equals(PluginConstants.SHOOT)) {
         * handleShootRequest(playerName, messageIn); } else if
         * (action.equals(PluginConstants.COLLECT_POWERUP)) {
         * handleCollectPowerup(playerName, messageIn); }
         */
    }
    
    @Override
    public void destroy() {
        // TODO: if you want to log that the room was destroyed, do it here
        getApi().getLogger().debug("ChatPlugin destroyed");
    }
    
    @Override
    public ChainAction userSendPublicMessage(UserPublicMessageContext message) {
        String chatLine = message.getMessage();
        getApi().getLogger().debug("chat message recd: {}", chatLine);
        String user = message.getUserName();
        getChatLogger().addChatEvent(user, chatLine, getApi().getRoomId(), getApi().getZoneId());
        return ChainAction.OkAndContinue;
    }
    
    private ChatLogger getChatLogger() {
        if (chatLogger == null) {
            chatLogger = (ChatLogger) getApi().acquireManagedObject("ChatLogger", null);
        }
        return chatLogger;
    }
}
