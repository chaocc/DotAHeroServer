package com.wolf.dotah.server.cmpnt;

import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.functioncon;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel.HandCardsChangeListener;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.layer.dao.HeroParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class Player implements HandCardsChangeListener {
    
    private String tag = "Player ==>> ";
    public String stateAction;
    public String stateReason;
    public Data stateInfo;
    public PlayerProperty property;//player 属性的状态
    public PlayerHandCardsModel handCards;
    private TableModel table;
    
    public void updatePropertyToClient(Data result) {
    
        result.setAction(c.action.update_player_property);
        this.table.sendMessageToAll(result);
    }
    
    
    /**
     * 相当于客户端拿来了新消息, 在做判断
     * TODO 这个方法应并进auto decide里
     */
    public void performAiAction(String fromParamKey) {
    
        if (stateAction.equals(c.playercon.state.choosing.choosing_hero)) {
            int[] pickResult = stateInfo.getIntegerArray(c.playercon.state.param_key.general.id_list, new int[] {});
            int heroId = ai.chooseSingle(pickResult);
            this.initPropertyWithHeroId(heroId);
        } else if (stateAction.equals(c.action.choosing_from_hand)) {
            Integer[] pickResult = handCards.getCards().toArray(new Integer[] {});
            int resultId = ai.chooseSingle(u.intArrayMapping(pickResult));
            this.table.addResultForShowing(this.userName, resultId);
        }
    }
    
    public void performSimplestChoice() {
    
        if (table.tableState.getState() == c.game_state.not_started.cutting) {//cutting
            int id = this.handCards.getCards().get(0);
            
            table.addResultForShowing(this.userName, id);
        } else {
            // choosing hero
            int[] idList = stateInfo.getIntegerArray(c.playercon.state.param_key.general.id_list, new int[] {});
            if (idList.length > 0) {
                if (stateAction.equals(c.playercon.state.choosing.choosing_hero)) {
                    this.initPropertyWithHeroId(idList[0]);
                }
            }
        }
    }
    
    
    /*
     * TODO  要并到choose from showing里
     */
    public void pickedHero(EsObject msg) {
    
        l.logger().d(tag, "picking hero, stateAction = " + stateAction);
        if (stateAction.equals(c.playercon.state.choosing.choosing_hero)) {
            int[] pickResult = msg.getIntegerArray(c.param_key.id_list, new int[] {});
            int heroId = pickResult[0];
            this.initPropertyWithHeroId(heroId);
        }
    }
    
    
    private void initPropertyWithHeroId(int heroId) {
    
        HeroInfo heroInfo = HeroParser.getParser().getHeroInfoById(heroId);
        property = new PlayerProperty(heroInfo);
        handCards = new PlayerHandCardsModel(this, heroInfo.getHandcardLimit());
        handCards.registerHandcardChangeListener(this);
        //TODO 不是在这里, 而是在全都收到选择了英雄后broadcast chose property
        stateAction = c.playercon.state.confirmed.hero;
        if (!this.isAi()) {
            String[] keys = { "heroId" };
            int[] values = { heroId };
            
            Data msg = new Data();
            debug(tag, "keys.length: " + keys.length);
            if (keys.length == 1) {
                if (keys[0].equals("heroId")) {
                    msg.setAction(c.action.chose_hero);
                    msg.setInteger("id", values[0]);
                }
            } else if (keys.length > 1) {
                //TODO action可以直接设成update xxx
            }
            updateMyStateToClient(msg);
        }
    }
    
    public void updateMyStateToClient(Data msg) {
    
        l.logger().d(tag, "updateMyStateToClient, stateInfo=" + msg);
        table.sendMessageToSingleUser(userName, msg);
        
    }
    
    
    public void getHandcards(List<Integer> cards) {
    
        addHandcards(cards);
        if (this.ai == null || !this.ai.isAi()) {
            sendPrivateMessage(c.action.init_hand_cards);
        }
        //        this.sendPublicMessage(c.ac.init_hand_cards);
        
    }
    
    private void sendPrivateMessage(String string_action) {
    
        Data data = new Data();
        data.setAction(string_action);
        addPrivateData(data, string_action);
        table.getMessenger().sendMessageToSingleUser(userName, data);
    }
    
    private void addPrivateData(Data data, String string_action) {
    
        if (c.action.init_hand_cards.equals(string_action)) {
            Integer[] cardArray = this.handCards.getCards().toArray(new Integer[] {});
            data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cardArray));
        } else if (c.action.choosing_from_hand.equals(string_action)) {
            List<Integer> cardList = this.handCards.getCards();
            int[] cardArray = u.intArrayMapping(cardList.toArray(new Integer[] {}));
            data.setIntegerArray(c.param_key.id_list, cardArray);
            data.setInteger(c.param_key.kParamSelectableCardCount, 1);
        }
    }
    
    public void cutting() {
    
        stateAction = c.action.choosing_from_hand;
        if (ai != null && ai.isAi()) {
            performAiAction(c.param_key.id_list);
        } else {
            sendPrivateMessage(c.action.choosing_from_hand);
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
        List<Integer> availableList = this.handCards.getCardsByUsage("active");
        return u.intArrayMapping(availableList.toArray(new Integer[] {}));
    }
    
    
    public void addHandcards(List<Integer> cards) {
    
        this.handCards.add(cards);
        
    }
    
    public Player(String name, TableModel inputTable) {
    
        this.table = inputTable;
        this.userName = name;
        this.tag += name + ", ";
    }
    
    
    public String userName;
    public Ai ai;
    
    public boolean isAi() {
    
        if (ai == null) { return false; }
        return ai.isAi();
    }
    
    
    public TableModel getTable() {
    
        return table;
    }
    
    private void debug(String tag, String log) {
    
        if (table != null) {
            l.logger().d(tag, log);
        }
    }
    
    @Override
    public String toString() {
    
        return "Player [userName=" + userName + "]";
    }
    public interface playerListener {
        public void usedCard();
        
    }
    
    public void useCard(EsObject info, int functionId) {
    
        l.logger().d(tag, "using card with function " + functionId);
        
        switch (functionId) {//主要是b, s, m三类
            case functioncon.b_normal_attack: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "normal attacking targetName: " + targetName);
                String action = c.action.choosing_from_hand;
                String reason = c.reason.normal_attacked;
                l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName);
                targetPlayer.updateState(action, reason, info);
                
                
                break;
            }
            case functioncon.b_chaos_attack: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                l.logger().d(tag, "chaos attacking targetName: " + targetName);
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "targetPlayer= " + targetPlayer.toString());
                String action = c.action.choosing_from_hand;
                String reason = c.reason.chaos_attacked;
                targetPlayer.updateState(action, reason, info);
                
                
                break;
            }
            case functioncon.b_flame_attack: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "flame attacking targetName: " + targetName);
                String action = c.action.choosing_from_hand;
                String reason = c.reason.flame_attacked;
                l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName);
                targetPlayer.updateState(action, reason, info);
                
                
                break;
            }
            case functioncon.b_heal: {
                
                break;
            }
            case functioncon.s_GodsStrength: {
                
                break;
            }
            case functioncon.s_LagunaBlade: {
                
                break;
            }
            case functioncon.s_viper_raid: {
                
                break;
            }
            case functioncon.m_Chakra: {
                
                break;
            }
            case functioncon.m_Dispel: {
                
                break;
            }
            case functioncon.m_Disarm: {
                
                break;
            }
            case functioncon.m_ElunesArrow: {
                
                break;
            }
            case functioncon.m_EnergyTransport: {
                
                break;
            }
            case functioncon.m_Fanaticism: {
                
                break;
            }
            case functioncon.m_Greed: {
                
                break;
            }
            case functioncon.m_Mislead: {
                
                break;
            }
            case functioncon.m_enhanced_Disarm: {
                
                break;
            }
            case functioncon.m_enhanced_ElunesArrow: {
                
                break;
            }
            case functioncon.m_enhanced_EnergyTransport: {
                
                break;
            }
            case functioncon.m_enhanced_Greed: {
                
                break;
            }
        }
        
        
    }
    
    
    private void updateState(String state, String reason, EsObject inputState) {
    
        this.stateAction = state;
        this.stateReason = reason;
        this.stateInfo.addAll(inputState);
        /* 
         * TODO 每次先根据reason 来判断哪些牌或者操作是允许的, 
         * 把这些牌或者操作添加到data里, 
         * 把action和允许的牌和操作更新给客户端, 让客户端选择.
         * 
         * 别忘了更新table state
         * 
         */
        l.logger().d(tag, "stateAction=" + stateAction + "stateAction, stateReason=" + stateReason);
        if (stateReason.equals(c.reason.normal_attacked)
            || stateReason.equals(c.reason.chaos_attacked)
        
        ) {
            l.logger().d(tag, "attacked");
            table.tableState = new TableState(c.game_state.started.somebody_attacking, new String[] { userName });
            stateInfo.setAction(c.action.choosing_to_evade);
            Integer[] evasions = this.handCards.getCardsByFunction(functioncon.b_normal_attack).toArray(new Integer[] {});
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(evasions));
            this.updateMyStateToClient(stateInfo);
            table.getWaiter().waitForSingleChoosing(this, c.default_wait_time);
        }
        
        
    }
    
    
    public void startTurn() {
    
        this.drawHandCards(2);
        this.freePlay();
        
        
    }
    
    
    private void freePlay() {
    
        Data obj = new Data();
        obj.setAction(c.action.turn_to_player);//kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        obj.addBoolean(c.param_key.clear_showing_cards, true);
        int[] availableHandCards = this.getAvailableHandCards();
        obj.addIntegerArray(c.param_key.available_id_list, availableHandCards);
        obj.addInteger(c.param_key.kParamSelectableCardCount, c.selectable_count.default_value);
        
        
        table.sendMessageToSingleUser(userName, obj);
        
        obj = new Data();
        obj.setAction(c.action.turn_to_player);//kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        table.playerUpdateInfo(userName, obj);
        
    }
    
    
    private void drawHandCards(int i) {
    
        List<Integer> cards = table.drawCardsFromDeck(2);
        this.handCards.add(cards);
        
    }
    
    
    @Override
    public void onHandCardsAdded(List<Integer> newCards) {
    
        
        Data obj = new Data();
        obj.setAction(c.action.update_hand_cards);//kActionUpdatePlayerHand,  2003
        obj.setIntegerArray(c.param_key.id_list, u.intArrayMapping(newCards.toArray(new Integer[] {})));
        this.updateMyStateToClient(obj);
        
        obj = new Data();
        obj.setAction(c.action.update_hand_cards);
        obj.setInteger(c.param_key.how_many, newCards.size());
        obj.setString(c.param_key.who, userName);
        table.playerUpdateInfo(userName, obj);
        
    }
    
    
    public void autoDecise() {
    
        if (stateAction.equals(c.action.choosing_from_hand)) {
            int[] available_id_list = stateInfo.getIntegerArray(c.param_key.available_id_list);
            if (this.isAi()) {
                int result = this.ai.chooseSingle(available_id_list);
                this.updateState(c.playercon.state.idle, c.action.decided, new Data().addInteger(c.param_key.single_result, result));
            } else {
                this.cancel();
            }
            
        }
        this.stateAction = c.action.none;
    }
    
    
    public void cancel() {
    
        l.logger().d(tag, "cancel, stateReason=" + stateReason);
        if (this.stateReason == c.reason.normal_attacked) {
            this.property.hpDown(1);
            this.property.spUp(1);
            Data result = new Data();
            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 1);
            
            this.updatePropertyToClient(result);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.chaos_attacked) {
            this.property.hpDown(1);
            this.property.spUp(1);
            Data result = new Data();
            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 1);
            this.updatePropertyToClient(result);
            
            
            //TODO 要给发杀的人sp+1
            
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.flame_attacked) {
            this.property.hpDown(1);
            this.property.spUp(2);
            Data result = new Data();
            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 1);
            
            this.updatePropertyToClient(result);
            
            turnToTurnHolder();
        } else if (this.stateAction.equals(c.action.free_play)) {
            //TODO 弃牌
        }
    }
    
    
    private void turnToTurnHolder() {
    
        table.turnBackToTurnHolder();
        
    }
    
    
    @Override
    public void onHandCardsDropped(List<Integer> droppedCards) {
    
        // TODO Auto-generated method stub
        
    }
}
