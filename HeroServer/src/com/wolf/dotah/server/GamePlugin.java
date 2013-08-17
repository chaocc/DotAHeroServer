package com.wolf.dotah.server;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.layer.translator.Dispatcher;

/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * @author Solomon
 *
 */
public class GamePlugin extends BasePlugin {
    
    private EsObject currentMessageObject;
    private String sender;
    
    @Override
    public void init(EsObjectRO parameters) {
        Dispatcher.getDispatcher(this);
        d.debug("DeskPlugin initialized " + d.version);
    }
    
    
    @Override
    public void request(String user, EsObjectRO message) {
        logMessage(user, message);
        this.currentMessageObject = new EsObject();
        currentMessageObject.addAll(message);
        sender = user;
        messageArrived();
        
        Dispatcher.getDispatcher(this).handleMessage(user, currentMessageObject);
    }
    
    //TODO only for test, need remove for production
    private void messageArrived() {
        EsObject obj = new EsObject();
        obj.addAll(currentMessageObject);
        sendMessageToSingleUser(sender, obj);
        
    }
    
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
        
        getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
        
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
        
    }
    
    void logMessage(String tag, EsObjectRO message) {
        EsObject eso = new EsObject();
        eso.addAll(message);
        d.debug(tag + " requests: " + message.toString());
    }
    
    private class D {
        
        private final String logprefix = "===== desk =>> ";
        public String version = "v 0.06";
        
        
        public void debug(String message) {
            
            getApi().getLogger().debug(logprefix + message);
        }
    }
    
    public void dlog(String message) {
        d.debug(message);
    }
    
    private D d = new D();
    
}
