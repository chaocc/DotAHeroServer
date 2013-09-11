package com.wolf.dotah.server;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.util.c;

public class RoomPlugin extends BasePlugin {
    @Override
    public void init(EsObjectRO parameters) {
        
        d.debug("RoomController initialized");
    }
    
    @Override
    public void request(String user, EsObjectRO message) {
        
        EsObject messageIn = new EsObject();
        messageIn.addAll(message);
        d.debug(user + " requests: " + messageIn.toString());
        //TODO 这个逻辑还需要理清楚, 这样太简陋了
        int action = messageIn.getInteger(c.a);
        if (action == c.action_user_ready) {
            getApi().sendPluginMessageToUser(user, messageIn);
        }
    }
    
    @Override
    public void destroy() {
        d.debug("ChatPlugin destroyed");
    }
    
    private class D {
        private final String logprefix = "===== room =>> ";
        
        public void debug(String message) {
            
            getApi().getLogger().debug(logprefix + message);
        }
    }
    
    private D d = new D();
}
