package com.wolf.dotah.server;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;

/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * 核心逻辑在sequence和toData等地方
 * @author Solomon
 * 
 * Choosing, update player info,  等等, 这些 
 * 叫做server action, 
 * 又叫做action category, 
 * 也叫做state等in general\
 */
public class GamePlugin extends BasePlugin {
    
    private EsObject currentMessageObject;
//    private String sender;
    private MessageDispatcher messageDispatcher;
    
    @Override
    public void init(EsObjectRO parameters) {
        //TODO 别忘了init 时候也可以收EsObj! 可以带参数来用来init!
        messageDispatcher = new MessageDispatcher(this);
        d.debug("DeskPlugin initialized " + d.version);
    }
    
    @Override
    public void userExit(String userName) {
        super.userExit(userName);
        if (this.getApi().getUsersInRoom(this.getApi().getZoneId(), this.getApi().getRoomId()).size() < 1) {
            messageDispatcher.destroyTable();
        }
    }
    
    @Override
    public void request(String user, EsObjectRO message) {
        logMessage(user, message);
        this.currentMessageObject = new EsObject();
        currentMessageObject.addAll(message);
//        sender = user;
        //        messageArrived();
        
        messageDispatcher.handleMessage(user, currentMessageObject);
    }
    
//    //TODO only for test, need remove for production
//    private void messageArrived() {
//        EsObject obj = new EsObject();
//        //        obj.addAll(currentMessageObject);
//        obj.setInteger("message_arrived", currentMessageObject.getInteger(c.action, -100));
//        getApi().sendPluginMessageToUser(sender, obj);
//        
//    }
    
    void logMessage(String tag, EsObjectRO message) {
        EsObject eso = new EsObject();
        eso.addAll(message);
        d.debug(tag + " requests: " + message.toString());
    }
    
    public class D {
        
        private final String logprefix = "===== desk =>> ";
        public String version = "v 0.06";
        
        public void debug(String message) {
            
            getApi().getLogger().debug(logprefix + message);
        }
        
        public void debug(String tag, String message) {
            getApi().getLogger().debug(tag + message);
        }
    }
    
    public void dlog(String message) {
        d.debug(message);
    }
    
    public void dlog(String tag, String message) {
        d.debug(tag, message);
    }
    
    private D d = new D();
    
}
