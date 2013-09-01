package com.wolf.dotah.server.cmpnt;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.PlayerAvailableTargetModel;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.cmpnt.table.table_const.tablecon;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;

public class Player implements player_const {
    
    //TODO attackable
    //TODO disarmable
    private String tag = "Player ==>> ";
    private String action;
    private Data state;
    private PlayerProperty property;//player 属性的状态
    
    private PlayerAvailableTargetModel targets;
    private TableModel table;
    
    public void updateProperty(String propertyName, Data result) {
    
        // TODO 先要把update property 翻译成server action, 然后把server action放到step里, 而不是property name
        // sequence.add(some server action,  and data);
    }
    
    
    /**
     * 相当于客户端拿来了新消息, 在做判断
     * @param action
     */
    public void performAiAction(String fromParamKey) {
    
        if (action.equals(c.server_action.free_play)) {
        } else {// choosing
            if (action.equals(playercon.state.desp.choosing.choosing_hero)) {
                int[] pickResult = state.getIntegerArray(playercon.state.param_key.general.id_list, new int[] {});
                int heroId = ai.chooseSingle(pickResult);
                this.initPropertyWithHeroId(heroId);
            } else if (action.equals(c.ac.choosing_from_hand)) {
                Integer[] pickResult = property.getHandCards().getCards().toArray(new Integer[] {});
                int resultId = ai.chooseSingle(table.u.intArrayMapping(pickResult));
                this.table.getCutCards().put(this.getUserName(), resultId);
            }
        }
    }
    
    public void performSimplestChoice() {
    
        if (table.getState().getState() == tablecon.state.not_started.cutting) {//cutting
            int id = this.getProperty().getHandCards().getCards().get(0);
            table.getCutCards().put(this.getUserName(), id);
        } else {
            //choosing hero
            int[] idList = state.getIntegerArray(playercon.state.param_key.general.id_list, new int[] {});
            if (idList.length > 0) {
                if (action.equals(playercon.state.desp.choosing.choosing_hero)) {
                    this.initPropertyWithHeroId(idList[0]);
                }
            }
        }
    }
    
    public void getResult(int[] pickResult) {
    
        if (action.equals(playercon.state.desp.choosing.choosing_hero)) {
            int heroId = pickResult[0];
            this.initPropertyWithHeroId(heroId);
        }
    }
    
    
    private void initPropertyWithHeroId(int heroId) {
    
        property = new PlayerProperty(heroId, this);
        //TODO 不是在这里, 而是在全都收到选择了英雄后broadcast chose property
        action = playercon.state.desp.confirmed.hero;
        if (this.getAi() == null || !this.getAi().isAi()) {
            String[] keys = { "heroId" };
            int[] values = { heroId };
            
            Data msg = new Data(table.u);
            debug(tag, "keys.length: " + keys.length);
            if (keys.length == 1) {
                if (keys[0].equals("heroId")) {
                    msg.setAction(c.server_action.chose_hero);
                    msg.setInteger("id", values[0]);
                }
            } else if (keys.length > 1) {
                //TODO action可以直接设成update xxx
            }
            table.getDispatcher().sendMessageToSingleUser(this.getUserName(), msg);
        }
    }
    
    public void getHandcards(List<Integer> cards) {
    
        this.property.addHandcards(cards);
        if (this.ai == null || !this.ai.isAi()) {
            sendPrivateMessage(c.ac.init_hand_cards);
        }
        this.sendPublicMessage(c.ac.init_hand_cards);
        
    }
    
    public void sendPublicMessage(String string_action) {
    
        Data data = new Data(table.u);
        data.setAction(string_action);
        addPublicData(data, string_action);
        table.getDispatcher().sendMessageToAllWithoutSpecificUser(data, this.getUserName());
    }
    
    private void addPublicData(Data data, String string_action) {
    
        if (c.ac.init_hand_cards.equals(string_action)) {
            data.setInteger(client_const.param_key.hand_card_count, property.getHandCards().getCards().size());
        }
    }
    
    private void sendPrivateMessage(String string_action) {
    
        Data data = new Data(table.u);
        data.setAction(string_action);
        addPrivateData(data, string_action);
        table.getDispatcher().sendMessageToSingleUser(this.getUserName(), data);
    }
    
    private void addPrivateData(Data data, String string_action) {
    
        if (c.ac.init_hand_cards.equals(string_action)) {
            Integer[] cardArray = property.getHandCards().getCards().toArray(new Integer[] {});
            data.setIntegerArray(c.param_key.id_list, table.u.intArrayMapping(cardArray));
        } else if (c.ac.choosing_from_hand.equals(string_action)) {
            List<Integer> cardList = property.getHandCards().getCards();
            int[] cardArray = table.u.intArrayMapping(cardList.toArray(new Integer[] {}));
            data.setIntegerArray(client_const.param_key.id_list, cardArray);
            data.setInteger(client_const.param_key.kParamSelectableCardCount, 1);
        }
    }
    
    public void cutting() {
    
        action = c.ac.choosing_from_hand;
        if (ai != null && ai.isAi()) {
            performAiAction(c.param_key.id_list);
        } else {
            sendPrivateMessage(c.ac.choosing_from_hand);
        }
    }
    
    public int[] getAvailableHandCards() {
    
        debug(tag, "getAvailableHandCards()");
        //TODO 判断哪些available
        /*
         * 1, 根据当前round的player, 是自己还是被人target等
         * 2, 根据当前的技能或者其他context, 
         * 3, 
         */
        List<Integer> availableList = new ArrayList<Integer>();
        for (int card : property.getHandCards().getCards()) {
            if (active(card)) {
                availableList.add(card);
                continue;
            }
            
        }
        return table.u.intArrayMapping(availableList.toArray(new Integer[] {}));
    }
    
    
    private boolean active(int card) {
    
        boolean firstCase = card > 45 && card < 57;
        boolean secondCase = card > 59 && card < 70;
        boolean thirdCase = card == 79;
        if (firstCase || secondCase || thirdCase) {
            return false;
        } else {
            return true;
        }
    }
    
    public Player(String name, TableModel inputTable) {
    
        this.table = inputTable;
        this.userName = name;
        this.tag += name + ", ";
    }
    
    public PlayerProperty getProperty() {
    
        return property;
    }
    
    public void setProperty(PlayerProperty property) {
    
        this.property = property;
    }
    
    private String userName;
    private Ai ai;
    
    public boolean isAi() {
    
        if (ai == null) { return false; }
        return ai.isAi();
    }
    
    public void setAi(Ai ai) {
    
        this.ai = ai;
    }
    
    public Ai getAi() {
    
        return ai;
    }
    
    public String getUserName() {
    
        return userName;
    }
    
    public void setUserName(String userName) {
    
        this.userName = userName;
    }
    
    public String getAction() {
    
        return action;
    }
    
    public void setAction(String action) {
    
        this.action = action;
    }
    
    public Data getState() {
    
        return state;
    }
    
    public void setState(Data state) {
    
        this.state = state;
    }
    
    public TableModel getTable() {
    
        return table;
    }
    
    private void debug(String tag, String log) {
    
        if (table != null) {
            table.getDispatcher().debug(tag, log);
        }
        
    }
    
    @Override
    public String toString() {
    
        return "Player [property=" + property + ", targets=" + targets + ", table=" + table + ", userName="
            + userName + ", ai=" + ai + "]";
    }
}
