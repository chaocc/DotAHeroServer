package com.wolf.dotah.server;


import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.cmpnt.translator.DecisionTranslator;
import com.wolf.dotah.server.cmpnt.translator.PlayerTranslator;
import com.wolf.dotah.server.cmpnt.translator.TableTranslator;
import com.wolf.dotah.server.tool.c;
import com.wolf.dotah.server.tool.client_const;


/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * @author Solomon
 *
 */
public class GamePlugin extends BasePlugin {
    
    //TODO 问题, 如何来判断该给哪个translator呢, 如何把拿来的信息翻译成有用的信息呢
    // 需要有一个完美的翻译流程
    
    
    private TableTranslator tableTranslator;
    private PlayerTranslator playerTranslator;
    private DecisionTranslator decisionTranslator;
    public EsObject currentMessageObject;
    
    
    @Override
    public void init(EsObjectRO parameters) {
    
        tableTranslator = TableTranslator.getTranslator(this);
        
        
        d.debug("DeskPlugin initialized " + d.version);
    }
    
    
    @Override
    public void request(String user, EsObjectRO message) {
    
        this.currentMessageObject = new EsObject();
        currentMessageObject.addAll(message);
        d.debug(user + " requests: " + currentMessageObject.toString());
        
        String client_message = currentMessageObject.getString(c.action, "");
        if (client_const.kActionStartGame.equals(client_message)) {
            int playerCount = currentMessageObject.getInteger(c.param.player_count, -1);
            if (playerCount != -1) {
                tableTranslator.initTableWithPlayerCount(playerCount);
            } else {
                tableTranslator.initTable();
            }
            playerTranslator = PlayerTranslator.getTranslator(this);
            decisionTranslator = DecisionTranslator.getTranslator(this);
            playerTranslator.setDecisionTranslator(decisionTranslator);
            
            //TODO start game 需要translate吗?
            tableTranslator.translate(currentMessageObject);
        }
        
        
        //TODO 这个chose hero id的action, 就该交给decision translator?
        //        else if (c.client_constants.kActionChooseHeroId.equals(client_message)) {
        //            Player p = desk.getPlayerByUserName(user);
        //            p.setHeroId(message.getIntegerArray(c.choosing.id_list)[0]);
        //            //            desk.
        //            
        //        }
    }
    
    
    public void sendMessageToUser(String user, EsObject obj) {
    
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
