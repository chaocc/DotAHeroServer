package com.electrotank.examples.chatlogger;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.ChainAction;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserPublicMessageContext;
import com.electrotank.examples.chatlogger.components.PluginConstants;

//import org.slf4j.Logger;

public class ChatPlugin extends BasePlugin {
    
    private static final String logprefix  = "======== room =>> ";
    
    private ChatLogger          chatLogger = null;
    
    @Override
    public void init(EsObjectRO parameters) {
        
        getApi().getLogger().debug("ChatPlugin initialized");
    }
    
    
    @Override
    public void request(String user, EsObjectRO message) {
        EsObject messageIn = new EsObject();
        messageIn.addAll(message);
        getApi().getLogger().debug(user + " requests: " + messageIn.toString());
        
        int action = messageIn.getInteger(PluginConstants.ACTION);
        if(action==100){
            messageIn.setInteger(PluginConstants.ACTION, 100);
            
        }
        
    }
    
    /**************** logic in game loop start ***************************/
    
    //    private synchronized void confirmGotHandcards(String user) {
    //        playerStates[players.indexOf(user)] = player_state_get_init_handcards_confirmed;
    //        for (int i = 0; i < players.size(); i++) {
    //            String state = playerStates[i];
    //            if (!state.equals(player_state_get_init_handcards_confirmed)) { return; }
    //            
    //            if (i == players.size() - 1) {
    //                gameTurn(players.get(0));
    //            }
    //        }
    //    }
    
    /**************** logic before game start end ***************************/
    @Override
    public void destroy() {
        // TODO: if you want to log that the room was destroyed, do it here
        d.debug("ChatPlugin destroyed");
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
    

    
    private class D {
        public void debug(String message) {
            getApi().getLogger().debug(message);
            ;
        }
    }
    
    private D d = new D();
    
    
}
