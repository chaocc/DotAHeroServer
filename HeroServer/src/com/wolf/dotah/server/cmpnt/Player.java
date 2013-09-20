package com.wolf.dotah.server.cmpnt;

import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.functioncon;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel.HandCardsChangeListener;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty.PlayerPropertyChangedListener;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.layer.dao.HeroParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class Player implements HandCardsChangeListener, PlayerPropertyChangedListener {
    
    private String tag = "Player ==>> ";
    public String stateAction;
    public String stateReason;
    public boolean godStrength = false;
    public int used_how_many_attacks = 0;
    public Data stateInfo;
    public PlayerProperty property;//player 属性的状态
    public PlayerHandCardsModel handCards;
    public TableModel table;
    
    public void updatePropertyToClient(Data result) {
    
        result.addString(c.param_key.player_name, userName);
        result.setAction(c.action.update_player_property);
        //        this.table.sendMessageToAll(result);
        table.sendMessageToSingleUser(userName, result);
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
        property = new PlayerProperty(heroInfo, this);
        handCards = new PlayerHandCardsModel(this, heroInfo.getHandcardLimit());
        handCards.registerHandcardChangeListener(table);
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
            updateToClient(msg);
        }
    }
    
    public void updateToClient(Data msg) {
    
        l.logger().d(tag, "updateToClient, stateInfo=");
        table.sendMessageToSingleUser(userName, msg);
        
    }
    
    
    public void sendPrivateMessage(String string_action) {
    
        Data data = new Data();
        data.setAction(string_action);
        addPrivateData(data, string_action);
        table.getMessenger().sendMessageToSingleUser(userName, data);
    }
    
    //TODO should deprecate, no need 不需要使用类似的操作
    private void addPrivateData(Data data, String string_action) {
    
        if (c.action.choosing_from_hand.equals(string_action)) {
            List<Integer> cardList = this.handCards.getCards();
            int[] cardArray = u.intArrayMapping(cardList.toArray(new Integer[] {}));
            data.setIntegerArray(c.param_key.id_list, cardArray);
            data.setInteger(c.param_key.available_count, 1);
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
    
    
    //    public void addHandcards(List<Integer> cards) {
    //    
    //        this.handCards.add(cards, true);
    //        
    //    }
    
    public void initHandCards(List<Integer> cards) {
    
        this.handCards.initPlayerHandcards(cards);
        
        
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
        
        int[] usedCards = info.getIntegerArray(c.param_key.id_list);
        //        this.handCards.remove(cardId, true);
        this.handCards.removeAll(usedCards, false);
        
        switch (functionId) {//主要是b, s, m三类
            case functioncon.b_normal_attack: {
                used_how_many_attacks++;
                
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
                used_how_many_attacks++;
                
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
                used_how_many_attacks++;
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "flame attacking targetName: " + targetName);
                String action = c.action.choosing_from_hand;
                String reason = c.reason.flame_attacked;
                l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName);
                targetPlayer.updateState(action, reason, info);
                
                
                break;
            }
            case functioncon.b_evasion: {
                if (this.stateReason == c.reason.s_LagunaBladed) {
                    int hitted = 3 - usedCards.length;
                    if (hitted > 0) {
                        this.property.hpDown(hitted);
                        this.property.spUp(hitted);
                    }
                }
                turnToTurnHolder();
                break;
            }
            case functioncon.b_heal: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                if (userName.equals(targetName)) {
                    this.property.hpUp(1);
                }
                
                
                break;
            }
            case functioncon.s_GodsStrength: {
                
                this.property.spDown(2);
                
                this.godStrength = true;
                this.drawHandCards(1);
                
                break;
            }
            case functioncon.s_LagunaBlade: {
                this.property.spDown(3);
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "s_Lagunaing Blade targetName: " + targetName);
                String action = c.action.choosing_from_hand;
                String reason = c.reason.s_LagunaBladed;
                l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName);
                targetPlayer.updateState(action, reason, info);
                
                break;
            }
            case functioncon.s_viper_raid: {
                this.property.spDown(2);
                
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
                l.logger().d(tag, "s_Lagunaing Blade targetName: " + targetName);
                
                PlayerHandCardsModel handcards = targetPlayer.handCards;
                if (handcards.getCardArray().length < 4) {
                    if (handcards.getCardArray().length < 2) {
                        targetPlayer.property.hpDown(1);
                    }
                    handcards.removeAll(u.intArrayMapping(handcards.getCardArray()), true);
                } else {
                    String action = c.action.choosing_from_hand;
                    String reason = c.reason.s_viper_raided;
                    l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName + " for s_viper_raid");
                    targetPlayer.updateState(action, reason, info);
                }
                
                
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
        }
    }
    
    //TODO 思考是用chose更好, 还是用respond as target更好, 或者改成chain的某个阶段之类的?
    public void respondAsTarget(EsObject msg) {
    
        if (stateReason.equals(c.reason.s_viper_raided)) {
            int[] cardsToBeDrop = msg.getIntegerArray(c.param_key.id_list);
            this.handCards.removeAll(cardsToBeDrop, false);
            this.turnToTurnHolder();
        }
        
    }
    
    private void updateState(String state, String reason, EsObject inputState) {
    
        this.stateAction = state;
        this.stateReason = reason;
        this.stateInfo.addAll(inputState);
        /* 
         * TODO 别忘了更新table state
         * 
         */
        l.logger().d(tag, "stateAction=" + stateAction + "stateAction, stateReason=" + stateReason);
        if (stateReason.equals(c.reason.normal_attacked)
            || stateReason.equals(c.reason.chaos_attacked)
            || stateReason.equals(c.reason.flame_attacked)
        
        ) {
            l.logger().d(tag + this.userName, "under attacking");
            stateInfo.removeVariable(c.param_key.target_player_list);
            
            table.tableState = new TableState(c.game_state.started.somebody_attacking, new String[] { userName });
            stateInfo.setAction(c.action.choosing_to_evade);
            Integer[] evasions = this.handCards.getCardsByFunction(functioncon.b_evasion).toArray(new Integer[] {});
            stateInfo.setInteger(c.param_key.available_count, 1);
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(evasions));
            this.updateToClient(stateInfo);
            table.getWaiter().waitForSingleChoosing(this, c.default_wait_time);
            
            
            Data publicData = new Data();
            publicData.setAction(c.action.choosing_to_evade);
            
            table.sendPublicMessage(publicData, userName);
        } else if (stateReason.equals(c.reason.s_LagunaBladed)) {
            l.logger().d(tag + this.userName, "s_LagunaBladed");
            stateInfo.removeVariable(c.param_key.target_player_list);
            
            table.tableState = new TableState(c.game_state.started.somebody_s_LagunaingBlade, new String[] { userName });
            stateInfo.setAction(c.action.choosing_to_evade);
            Integer[] evasions = this.handCards.getCardsByFunction(functioncon.b_evasion).toArray(new Integer[] {});
            stateInfo.setInteger(c.param_key.available_count, 3);
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(evasions));
            this.updateToClient(stateInfo);
            table.getWaiter().waitForSingleChoosing(this, c.default_wait_time);
            
            
            Data publicData = new Data();
            publicData.setAction(c.action.choosing_to_evade);
            table.sendPublicMessage(publicData, userName);
        } else if (stateReason.equals(c.reason.s_viper_raided)) {
            l.logger().d(tag + this.userName, "s_viper_raided");
            
            
            table.tableState = new TableState(c.game_state.started.somebody_is_s_viper_raiding, new String[] { userName });
            stateInfo.setAction(c.action.choosing_to_drop);
            Integer[] cards = this.handCards.getCardArray();
            stateInfo.setInteger(c.param_key.available_count, 3);
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(cards));
            this.updateToClient(stateInfo);
            table.getWaiter().waitForSingleChoosing(this, c.default_wait_time);
            
            
            Data publicData = new Data();
            publicData.setAction(c.action.choosing_to_drop);
            table.sendPublicMessage(publicData, userName);
            
        }
    }
    
    
    public void startTurn() {
    
        used_how_many_attacks = 0;
        this.drawHandCards(2);
        this.freePlay();
        
        
    }
    
    
    private void freePlay() {
    
        Data obj = new Data();
        obj.setAction(c.action.turn_to_player);//kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        //        obj.addBoolean(c.param_key.clear_showing_cards, true);
        int[] availableHandCards = this.getAvailableHandCards();
        obj.addIntegerArray(c.param_key.available_id_list, availableHandCards);
        obj.addInteger(c.param_key.available_count, c.selectable_count.default_value);
        
        
        table.sendMessageToSingleUser(userName, obj);
        
        obj = new Data();
        obj.setAction(c.action.turn_to_player);//kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        table.sendPublicMessage(obj, userName);
        
    }
    
    
    private void drawHandCards(int i) {
    
        List<Integer> cards = table.drawCardsFromDeck(i);
        
        Data animi = new Data();
        animi.setAction(client_const.kActionPlayerUpdateHandDrawing);
        animi.setInteger(c.param_key.hand_card_change_amount, i);
        table.sendPublicMessage(animi, userName);
        
        this.handCards.add(cards, true);
        
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
    
        table.cancelScheduledExecution();
        
        
        l.logger().d(tag, "cancel, stateReason=" + stateReason);
        if (this.stateReason == c.reason.normal_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            
            
            this.property.hpDown(1 + god_helped);
            this.property.spUp(1);
            
            //            Data result = new Data();
            //            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 1);
            //            this.updatePropertyToClient(result);
            
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.chaos_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            this.property.hpDown(1 + god_helped);
            this.property.spUp(1);
            
            //            Data result = new Data();
            //            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 1);
            //            this.updatePropertyToClient(result);
            //            
            //            Data spUpData = new Data();
            //            spUpData.addInteger(c.param_key.sp_changed, 1);
            //            table.players.turnHolder.updatePropertyToClient(spUpData);
            
            table.players.turnHolder.property.spUp(1);
            
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.flame_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            this.property.hpDown(1 + god_helped);
            this.property.spUp(2);
            //            Data result = new Data();
            //            result.addInteger(c.param_key.hp_changed, -1).addInteger(c.param_key.sp_changed, 2);
            //            this.updatePropertyToClient(result);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.s_LagunaBladed) {
            this.property.hpDown(3);
            this.property.spUp(3);
            
            turnToTurnHolder();
        }
        
        
        else if (this.stateAction.equals(c.action.free_play)) {
            //TODO 弃牌
            godStrength = false;
        }
    }
    
    
    private void turnToTurnHolder() {
    
        table.turnBackToTurnHolder(userName);
        
    }
    
    @Override
    public void onHandCardsAdded(List<Integer> newCards, String playerName, boolean updateToClient) {
    
        if (updateToClient && !this.isAi()) {
            Data obj = new Data();
            obj.setAction(c.action.update_hand_cards);//kActionUpdatePlayerHand,  2003
            obj.setIntegerArray(c.param_key.id_list, u.intArrayMapping(newCards.toArray(new Integer[] {})));
            this.updateToClient(obj);
        }
    }
    
    @Override
    public void onHandCardsDropped(int[] droppedCards, String playerName, boolean updateToClient) {
    
        if (updateToClient && !this.isAi()) {
            Data obj = new Data();
            obj.setAction(c.action.update_hand_cards);//kActionUpdatePlayerHand,  2003
            obj.setIntegerArray(c.param_key.id_list, droppedCards);
            this.updateToClient(obj);
        }
    }
    
    
    @Override
    public void onHpChanged(String playerName, int amount) {
    
        //        Data data = new Data();
        //        data.addInteger(c.param_key.hp_changed, amount);
        //        this.updatePropertyToClient(data);
        
    }
    
    
    @Override
    public void onSpChanged(String playerName, int amount) {
    
        //    
        //        Data data = new Data();
        //        data.addInteger(c.param_key.sp_changed, amount);
        //        this.updatePropertyToClient(data);
        
    }
    
    
    @Override
    public void onEquipChanged(String playerName) {
    
    }
    
    
}
