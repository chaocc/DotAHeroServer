package com.wolf.dotah.server.layer.translator;


import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.util.c;


public class TableTranslator {
    
    private static TableTranslator translator;
    private TableModel table;
    
    final String tag = "====>> TableTranslator:";
    
    
    public static TableTranslator getTranslator() {
        
        if (translator == null) {
            translator = new TableTranslator();
        }
        return translator;
    }
    
    
    private TableTranslator() {
        
    }
    
    
    public void translate(EsObject msg) {
        
        
    }
    
    
    public void translateGameStartFromClient(GamePlugin gamePlugin, EsObject currentMessageObject) {
        if (table == null) {
            int playerCount = currentMessageObject.getInteger(c.param_key.player_count, -1);
            //TODO start game不要携带player count,  而是player name列表, 使用player name来新建player
            int zone = gamePlugin.getApi().getZoneId();
            int room = gamePlugin.getApi().getRoomId();
            if (playerCount != -1) {
                PlayerList.getModel().initWithUserCollectionAndPlayerCount(gamePlugin.getApi().getUsersInRoom(zone, room), playerCount);
            } else {
                PlayerList.getModel().initWithUserCollection(gamePlugin.getApi().getUsersInRoom(zone, room));
            }
            table = new TableModel();
            table.setTranslator(this);
            System.out.println(tag + " table translator inited");
            table.dispatchHeroCandidates();
        }
    }
    
    
}
