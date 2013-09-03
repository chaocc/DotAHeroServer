package com.wolf.dotah.server;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.util.l;

/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * 核心逻辑在sequence和toData等地方
 * @author Solomon
 * 
 * Choosing, update player info,  等等, 这些 
 * 叫做server action, 
 * 又叫做action category, 
 * 也叫做state等in general
 */
public class GamePlugin extends BasePlugin {
    
    private EsObject currentMessageObject;
    //    private String sender;
    private MessageCenter messageDispatcher;
    
    @Override
    public void init(EsObjectRO parameters) {
    
        l.init(this);
        //TODO 别忘了init 时候也可以收EsObj! 可以带参数来用来init!
        messageDispatcher = new MessageCenter(this);
        this.getApi().getLogger().debug("DeskPlugin initialized 0.1");
    }
    
    @Override
    public void userExit(String userName) {
    
        super.userExit(userName);
        if (this.getApi().getUsersInRoom(this.getApi().getZoneId(), this.getApi().getRoomId()).size() < 1) {
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
        this.getApi().getLogger().debug(tag + " requests: " + message.toString());
    }
    
}
