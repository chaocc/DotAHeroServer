package com.wolf.dotah.server;


import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.wolf.dotah.server.cmpnt.TableModel;

/**
 * Plugin 只负责分发请求, 以及和客户端互发信息, 不处理任何逻辑
 * @author Solomon
 *
 */
public class TablePlugin extends BasePlugin {
    
    private TableModel table;
    public EsObject currentMessageObject;

    
    
    @Override
    public void init(EsObjectRO parameters) {
    
        table = new TableModel(this);
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
