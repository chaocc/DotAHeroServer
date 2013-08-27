package com.wolf.dotah.server.layer.translator;

import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.table.table_const.tablecon;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;
import com.wolf.tool.client_const;

public class PlayerTranslator {
    
    private DecisionTranslator decisionTranslator;
    private MessageDispatcher dispatcher;
    final String tag = "===>> PlayerTranslator: ";
    
    public PlayerTranslator(MessageDispatcher input) {
        this.dispatcher = input;
    }
    
    public void setDecisionTranslator(DecisionTranslator decisionTranslator) {
        
        this.decisionTranslator = decisionTranslator;
        
    }
    
    public void translateUpdate(Player player, EsObject msg) {
        //TODO 不是根据action, 而是根据player 状态来判断choose了什么
        //        Player decisionMaker = players.getPlayerByUserName(user);
        int[] pickResult = msg.getIntegerArray(c.param_key.id_list, new int[] {});
        player.getResult(pickResult);
    }
    
    public void updatePlayerInfo(String subject, String[] keys, int[] values) {
        Data msg = new Data();
        this.dispatcher.debug(tag, "keys.length: " + keys.length);
        if (keys.length == 1) {
            if (keys[0].equals("heroId")) {
                msg.setAction(c.server_action.chose_hero);
                msg.setInteger("id", values[0]);
            }
        } else if (keys.length > 1) {
            //TODO action可以直接设成update xxx
        }
        dispatcher.sendMessageToSingleUser(subject, msg);
        
    }
    
    public void dispatchHandcards(Player p, List<Integer> cards) {
        // TODO Auto-generated method stub
        p.getHandcards(cards);
    }
    
    public void sendPrivateMessage(String string_action, Player player) {
        Data data = new Data();
        data.setAction(string_action);
        addPrivateData(data, string_action, player);
        dispatcher.sendMessageToSingleUser(player.getUserName(), data);
    }
    
    private void addPrivateData(Data data, String string_action, Player player) {
        if (c.ac.init_hand_cards.equals(string_action)) {
            Integer[] cardArray = player.getProperty().getHandCards().getCards().toArray(new Integer[] {});
            data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cardArray));
        } else if (c.ac.choosing_from_hand.equals(string_action)) {
            List<Integer> cardList = player.getProperty().getHandCards().getCards();
            int[] cardArray = u.intArrayMapping(cardList.toArray(new Integer[] {}));
            data.setIntegerArray(client_const.param_key.kParamCardIdList, cardArray);
            data.setInteger(client_const.param_key.kParamSelectableCardCount, 1);
        }
    }
    
    public void sendPublicMessage(String string_action, Player player) {
        Data data = new Data();
        data.setAction(string_action);
        addPublicData(data, string_action, player);
        dispatcher.sendMessageToAllWithoutSpecificUser(data, player.getUserName());
    }
    
    private void addPublicData(Data data, String string_action, Player player) {
        if (c.ac.init_hand_cards.equals(string_action)) {
            data.setInteger(client_const.param_key.kParamHandCardCount, player.getProperty().getHandCards().getCards().size());
        }
        
    }
    
    public MessageDispatcher getDispatcher() {
        return dispatcher;
    }
    
    public void translateChose(String user, EsObject msg) {
        //TODO 改成不要在这里写, 在player里写
        //TODO 简化player的state后, 使用player的state莱判断
        //现在先用table的state来判断
        TableModel table = dispatcher.getTableTranslator().getTable();
        int[] id = msg.getIntegerArray(c.param_key.id_list, new int[] {});
        dispatcher.debug(user, "table.getState().getState() :  " + table.getState().getState());
        if (table.getState().getState() == tablecon.state.not_started.cutting) {
            table.getCutCards().put(user, id[0]);
            this.getDispatcher().debug(user, "translateChose: " + table.getCutCards().toString());
        }
    }
    
}
