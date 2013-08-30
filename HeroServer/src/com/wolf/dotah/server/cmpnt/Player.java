package com.wolf.dotah.server.cmpnt;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.PlayerAvailableTargetModel;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.player.PlayerState;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.cmpnt.table.table_const.tablecon;
import com.wolf.dotah.server.layer.translator.MessageDispatcher;
import com.wolf.dotah.server.layer.translator.PlayerTranslator;
import com.wolf.dotah.server.layer.translator.ServerUpdateSequence;
import com.wolf.dotah.server.layer.translator.TableTranslator;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;
import com.wolf.dotah.testframework.ClientRequest;

public class Player implements player_const {
    
    //TODO attackable
    //TODO disarmable
    String tag = "Player: ";
    /*
     * TODO 考虑把state 弄简单点儿,  像table的state那样, 就是state和subject之类的.
     * 倒也不是那么简单
     */
    private PlayerState state;//hero 在干嘛, 可以干嘛
    
    private PlayerProperty property;//player 属性的状态
    
    private PlayerAvailableTargetModel targets;
    private TableModel table;
    private PlayerTranslator translator;
    
    public void act(ClientRequest request) {
        
        //TODO 这个还要吗? 不知道是否有用
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
    /**
     * 每一个public的update方法, 都要把update的过程加入到update steps里, 供translate时候用
     */
    public void updateState(String state, Data params, ServerUpdateSequence sequence) {
        
        //如果sequence是空的, 就可以抛出update sequence not start exception
        updateState(this.getState().setStateDesp(state), params, sequence);
    }
    
    /**
     * 每一个public的update方法, 都要把update的过程加入到update steps里, 供translate时候用
     */
    public void updateProperty(String propertyName, Data result, ServerUpdateSequence sequence) {
        
        // TODO 先要把update property 翻译成server action, 然后把server action放到step里, 而不是property name
        // sequence.add(some server action,  and data);
        // TODO Auto-generated method stub
    }
    
    private void updateState(PlayerState state, Data params, ServerUpdateSequence sequence) {
        
        this.state = state;
        state.updateDetail(params);
        sequence.add(state.getStateDesp(), this.getState().toData());
    }
    
    public Data toData() {
        
        Data result = new Data();
        //TODO 加state
        //TODO 加property
        //TODO 加PlayerAvailableTargetModel
        return result;
        //TODO 是否要加username 和ai?
    }
    
    public Player(String name, TableModel inputTable) {
        this.table = inputTable;
        this.userName = name;
        this.tag += name + ", ";
        this.translator = table.getTranslator().getDispatcher().getPlayerTranslator();
        state = new PlayerState();
    }
    
    public PlayerProperty getProperty() {
        
        return property;
    }
    
    public void setProperty(PlayerProperty property) {
        
        this.property = property;
    }
    
    public PlayerState getState() {
        
        return state;
    }
    
    public void setState(PlayerState state) {
        
        this.state = state;
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
    
    /**
     * 相当于客户端拿来了新消息, 在做判断
     * @param action
     */
    public void performAiAction(String action, String from) {
        
        if (action.equals(c.server_action.free_play)) {
        } else {// choosing
            if (state.getStateDesp().equals(playercon.state.desp.choosing.choosing_hero)) {
                int[] pickResult = state.toData().getIntegerArray(playercon.state.param_key.general.id_list, new int[] {});
                int heroId = ai.chooseSingle(pickResult);
                this.initPropertyWithHeroId(heroId);
            } else if (from.equals(c.param_key.id_list)) {
                Integer[] pickResult = property.getHandCards().getCards().toArray(new Integer[] {});
                int resultId = ai.chooseSingle(u.intArrayMapping(pickResult));
                this.translator.getDispatcher().debug(tag, this.userName + " chose from handcard " + resultId);
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
            int[] idList = state.toData().getIntegerArray(playercon.state.param_key.general.id_list, new int[] {});
            if (idList.length > 0) {
                if (state.getStateDesp().equals(playercon.state.desp.choosing.choosing_hero)) {
                    this.initPropertyWithHeroId(idList[0]);
                }
            }
        }
    }
    
    public void getResult(int[] pickResult) {
        
        if (this.getState().getStateDesp().equals(playercon.state.desp.choosing.choosing_hero)) {
            int heroId = pickResult[0];
            this.initPropertyWithHeroId(heroId);
        }
    }
    
    public PlayerTranslator getTranslator() {
        return translator;
    }
    
    public void setTranslator(PlayerTranslator translator) {
        this.translator = translator;
    }
    
    private void initPropertyWithHeroId(int heroId) {
        
        property = new PlayerProperty(heroId, this);
        //TODO 不是在这里, 而是在全都收到选择了英雄后broadcast chose property
        state.setStateDesp(playercon.state.desp.confirmed.hero);
        if (this.getAi() == null || !this.getAi().isAi()) {
            String[] keys = { "heroId" };
            int[] values = { heroId };
            TableTranslator trans = table.getTranslator();
            MessageDispatcher disp = trans.getDispatcher();
            PlayerTranslator playerTrans = disp.getPlayerTranslator();
            String userName = this.getUserName();
            playerTrans.updatePlayerInfo(userName, keys, values);
        }
    }
    
    public void getHandcards(List<Integer> cards) {
        // TODO 先给player发这些个卡, 
        this.property.addHandcards(cards);
        if (this.ai == null || !this.ai.isAi()) {
            translator.sendPrivateMessage(c.ac.init_hand_cards, this);
        }
        //        translator.sendPublicMessage(c.ac.init_hand_cards, this);
        // TODO 然后再调用 player translator的send plugin message之类的方法
        
    }
    
    public void cutting() {
        state.setStateDesp(c.server_action.choosing);
        state.setUsableCardContext(property.getHandCards().getCards());
        if (ai != null && ai.isAi()) {
            performAiAction(c.server_action.choosing, c.param_key.id_list);
        } else {
            translator.sendPrivateMessage(c.ac.choosing_from_hand, this);
        }
        //TODO 如果是普通的ai, 就让它选好, 如果是一般的player, 就发update state
    }
    
    public int[] getAvailableHandCards() {
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
        return u.intArrayMapping(availableList.toArray(new Integer[] {}));
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
    
    @Override
    public String toString() {
        return "Player [state=" + state + ", property=" + property + ", targets=" + targets + ", table=" + table + ", translator=" + translator + ", userName=" + userName + ", ai=" + ai + "]";
    }
}
