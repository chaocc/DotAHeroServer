package com.wolf.dotah.server.layer.translator;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.util.c;
import com.wolf.tool.client_const;

public class MessageDispatcher {
    
    
    private TableTranslator tableTranslator;
    private PlayerTranslator playerTranslator;
    private DecisionTranslator decisionTranslator;
    private GamePlugin plugin;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
        
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
        
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
        
    }
    
    public MessageDispatcher waitingForEverybody() {
        // TODO start waiting
        return this;
    }
    
    public void becauseOf(String serverAction) {
        // TODO 根据becauseOf莱判断waiting结束条件
        
    }
    
    public void handleMessage(String user, EsObject msg) {
        String client_message = msg.getString(c.action, "");
        if (client_const.kActionStartGame.equals(client_message)) {
            tableTranslator.translateGameStartFromClient(plugin, msg);
        }
        //TODO 这个chose hero id的action, 就该交给decision translator?
        //        else if (c.client_constants.kActionChooseHeroId.equals(client_message)) {
        //            Player p = desk.getPlayerByUserName(user);
        //            p.setHeroId(message.getIntegerArray(c.choosing.id_list)[0]);
        //            //            desk.
        //        }
    }
    
    
    private static MessageDispatcher dispatcher;
    
    public static MessageDispatcher getDispatcher(GamePlugin gamePlugin) {
        if (dispatcher == null) {
            dispatcher = new MessageDispatcher(gamePlugin);
        }
        return dispatcher;
    }
    
    private MessageDispatcher(GamePlugin gamePlugin) {
        tableTranslator = TableTranslator.getTranslator();
        playerTranslator = PlayerTranslator.getTranslator();
        decisionTranslator = DecisionTranslator.getTranslator();
        playerTranslator.setDecisionTranslator(decisionTranslator);
    }
    
    
}
