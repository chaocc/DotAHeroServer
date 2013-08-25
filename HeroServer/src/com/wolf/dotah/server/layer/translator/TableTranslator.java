package com.wolf.dotah.server.layer.translator;

import java.util.Collection;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.util.c;

public class TableTranslator {
    
    private TableModel table;
    private DecisionTranslator decisionTranslator;
    final String tag = "====>> TableTranslator: ";
    private MessageDispatcher msgDispatcher;
    
    public TableTranslator(MessageDispatcher dispatcher) {
        this.msgDispatcher = dispatcher;
    }
    
    public void translate(EsObject msg) {
        
    }
    
    public void translateGameStartFromClient(GamePlugin gamePlugin, EsObject currentMessageObject) {
        msgDispatcher.debug(tag, "translateGameStartFromClient");
        if (table == null) {
            int playerCount = currentMessageObject.getInteger(c.param_key.player_count, -1);
            int zone = gamePlugin.getApi().getZoneId();
            int room = gamePlugin.getApi().getRoomId();
            PlayerList playerList = new PlayerList();
            Collection<UserValue> users = gamePlugin.getApi().getUsersInRoom(zone, room);
            msgDispatcher.debug(tag, " get users : " + users.toString());
            if (playerCount != -1) {
                playerList.initWithUserCollectionAndPlayerCount(users, playerCount);
            } else {
                playerList.initWithUserCollection(users);
            }
            table = new TableModel(playerList);
            table.setTranslator(this);
            playerList.setTable(table);
            msgDispatcher.debug(tag, " table translator inited");
        }
        table.dispatchHeroCandidates();
    }
    
    public void destroyTable() {
        table = null;
    }
    
    public PlayerList getPlayerList() {
        return table.getPlayers();
    }
    
    public DecisionTranslator getDecisionTranslator() {
        return decisionTranslator;
    }
    
    public void setDecisionTranslator(DecisionTranslator input) {
        this.decisionTranslator = input;
    }
    
    public TableModel getTable() {
        return table;
    }
    
    public void setTable(TableModel input) {
        this.table = input;
    }
    
    public MessageDispatcher getDispatcher() {
        return msgDispatcher;
    }
    
    public void setDispatcher(MessageDispatcher input) {
        this.msgDispatcher = input;
    }
    
}
