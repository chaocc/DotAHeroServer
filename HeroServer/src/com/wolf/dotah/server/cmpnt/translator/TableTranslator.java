package com.wolf.dotah.server.cmpnt.translator;


import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;


public class TableTranslator {
    
    private GamePlugin dispatcher;
    private static TableTranslator translator;
    private TableModel table;
    
    
    public void initTable() {
    
        if (table == null) {
            int zone = dispatcher.getApi().getZoneId();
            int room = dispatcher.getApi().getRoomId();
            PlayerList.getModel().initWithUserCollection(dispatcher.getApi().getUsersInRoom(zone, room));
            table = new TableModel();
            table.setTranslator(this);
        }
    }
    
    
    public void initTable(int playerCount) {
    
        if (table == null) {
            int zone = dispatcher.getApi().getZoneId();
            int room = dispatcher.getApi().getRoomId();
            PlayerList.getModel().initWithUserCollection(dispatcher.getApi().getUsersInRoom(zone, room));
            table = new TableModel();
            table.setTranslator(this);
        }
    }
    
    
    public void initTableWithPlayerCount(int playerCount) {
    
        if (table == null) {
            int zone = dispatcher.getApi().getZoneId();
            int room = dispatcher.getApi().getRoomId();
            PlayerList.getModel().initWithUserCollectionAndPlayerCount(dispatcher.getApi().getUsersInRoom(zone, room), playerCount);
            table = new TableModel();
            table.setTranslator(this);
        }
    }
    
    
    public static TableTranslator getTranslator(GamePlugin dispatcher) {
    
        if (translator == null) {
            translator = new TableTranslator();
        }
        if (!dispatcher.equals(translator.dispatcher)) {
            translator.dispatcher = dispatcher;
        }
        return translator;
    }
    
    
    private TableTranslator() {
    
    }
    
    
    public void translate(EsObject msg) {
    
        
    }
    
    
}
