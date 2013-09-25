package com.wolf.dotah.server.cmpnt;

import java.util.List;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.colorcon;
import com.wolf.dotah.server.cmpnt.cardandskill.card_const.functioncon;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel.HandCardsChangeListener;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty.PlayerPropertyChangedListener;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.layer.dao.CardParser;
import com.wolf.dotah.server.layer.dao.HeroParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

//TODO 在写个on targeted listener, 
public class Player implements HandCardsChangeListener, PlayerPropertyChangedListener {
    
    private String              tag                   = "Player ==>> ";
    public String               stateAction;
    public String               stateReason;
    public boolean              godStrength           = false;
    public boolean              m_Fanaticismed        = false;
    public int                  used_how_many_attacks = 0;
    public Data                 stateInfo;
    public PlayerProperty       property;                              // player
                                                                        // 属性的状态
    public PlayerHandCardsModel handCards;
    public TableModel           table;
    
    public void updatePropertyToClient(Data result) {
        
        result.addString(c.param_key.player_name, userName);
        result.setAction(c.action.update_player_property);
        // this.table.sendMessageToAll(result);
        table.sendMessageToSingleUser(userName, result);
    }
    
    /**
     * 相当于客户端拿来了新消息, 在做判断 TODO 这个方法应并进auto decide里
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
        
        if (table.tableState.getState() == c.game_state.not_started.cutting) {// cutting
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
     * TODO 要并到choose from showing里
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
        // TODO 不是在这里, 而是在全都收到选择了英雄后broadcast chose property
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
                // TODO action可以直接设成update xxx
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
    
    // TODO should deprecate, no need 不需要使用类似的操作
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
        // TODO 判断哪些available
        /*
         * 1, 根据当前round的player, 是自己还是被人target等 2, 根据当前的技能或者其他context, 3,
         */
        List<Integer> availableList = this.handCards.getCardsByUsage("active");
        return u.intArrayMapping(availableList.toArray(new Integer[] {}));
    }
    
    public void initHandCards(List<Integer> cards) {
        
        // TODO TODO only for test, need remove when production
        // cards.clear();
        
        this.handCards.initPlayerHandcards(cards);
        
    }
    
    public Player(String name, TableModel inputTable) {
        
        this.table = inputTable;
        this.userName = name;
        this.tag += name + ", ";
    }
    
    public String userName;
    public Ai     ai;
    
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
        // this.handCards.remove(cardId, true);
        this.handCards.removeAll(usedCards, false);
        
        
        
        // 驱散要在这之前
        boolean isStrengthened = info.getBoolean(c.param_key.is_strengthened, false);
        l.logger().d(tag, "using card with strengthened=" + isStrengthened);
        if (isStrengthened) {
            this.property.spDown(1);
        }
        switch (functionId) {// 主要是b, s, m三类
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
                        this.property.hpDown(hitted, true);
                    }
                }
                turnToTurnHolder();
                break;
            }
            case functioncon.b_heal: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list, new String[] { userName })[0];
                if (userName.equals(targetName)) {
                    this.property.hpUp(1);
                } else {
                    Player target = table.players.getPlayerByPlayerName(targetName);
                    target.property.hpUp(1);
                }
                this.turnToTurnHolder();
                break;
            }
            case functioncon.s_GodsStrength: {
                
                this.property.spDown(2);
                
                this.godStrength = true;
                this.drawHandCards(1);
                
                this.freePlay(false);
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
                        targetPlayer.property.hpDown(1, true);
                    }
                    
                    Data animiData = new Data();
                    animiData.setAction(client_const.kActionChoseCardToDrop);
                    animiData.setInteger(c.param_key.hand_card_change_amount, handcards.getCardArray().length);
                    animiData.setIntegerArray(c.param_key.id_list, u.intArrayMapping(handcards.getCardArray()));
                    table.sendPublicMessage(animiData, targetPlayer.userName);
                    
                    handcards.removeAll(u.intArrayMapping(handcards.getCardArray()), true);
                    this.turnToTurnHolder();
                } else {
                    String action = c.action.choosing_from_hand;
                    String reason = c.reason.s_viper_raided;
                    l.logger().d(tag + userName, "turn to player: " + targetPlayer.userName + " for s_viper_raid");
                    targetPlayer.updateState(action, reason, info);
                }
                
                break;
            }
            case functioncon.m_Chakra: {
                this.drawHandCards(1);
                
                int i = table.drawOneCard();
                String action = c.action.choosing_from_color;
                String reason = c.reason.m_Chakraing;
                
                Data publicMessage = new Data();
                publicMessage.setAction(client_const.kActionGuessCard);
                publicMessage.setInteger(c.param_key.server_internal.how_many, 1);
                table.sendPublicMessage(publicMessage, userName);
                
                Data data = new Data();
                data.addInteger(c.param_key.id, i);
                this.updateState(action, reason, data);
                
                break;
            }
            case functioncon.m_Dispel: {
                /*
                 * 驱散：在魔法牌或英雄技能生效前，抵消其全部效果。
                 */
                break;
            }
            case functioncon.m_Disarm: {
                /*
                 * 缴械：出牌阶段，对任意一名装备区里有装备牌的角色使用。
                 * 选择目标角色装备区里的一张装备牌弃置。
                 * 强化：你将目标角色装备区里的装备牌收为自己的手牌。
                 */
                break;
            }
            case functioncon.m_ElunesArrow: {
                
                String action = c.action.choosing_from_showing;
                String reason = c.reason.m_ElunesArrowing;
                this.updateState(action, reason, info);
                
                break;
            }
            case functioncon.m_EnergyTransport: {
                /*
                 * 能量转移：出牌阶段，你查看牌堆顶的X张牌（X为存活角色的数量），
                 * 按行动顺序均分给所有角色。
                 * 强化效果：按行动顺序进行分配，其中一名角色获得两张牌，
                 * 另一名角色不能获得牌，其他角色各获得一张。
                 */
                break;
            }
            case functioncon.m_Fanaticism: {
                this.m_Fanaticismed = true;
                this.property.hpDown(1, true);
                this.freePlay(false);
                break;
            }
            case functioncon.m_Greed: {
                /*
                 * 贪婪：出牌阶段，对另一名角色使用。
                 * 你抽取并暗置目标角色的两张手牌或一张装备区里的装备牌，
                 * 然后该角色抽取你的一张手牌，最后你将暗置的牌归入手牌。
                 * 强化：该角色不能抽取你的一张手牌而是由你交给该角色一张手牌。
                 */
                if (isStrengthened) {
                    
                    /*
                     * 如果强化了
                     * 先选对方手牌
                     * 再选自己的
                     * 等待选完
                     * 如果超时了就帮他选
                     * 然后俩人都得到牌.
                     */
                    stateAction = c.action.choosing_from_another;
                    stateReason = c.reason.m_greeding;
                    stateInfo = new Data();
                    
                    Data d = new Data();
                    d.setAction(c.action.choosing_from_another);
                    String targetPlayerName = info.getStringArray(c.param_key.target_player_list)[0];
                    int targetHandcardAmount = table.players.getPlayerByPlayerName(targetPlayerName).handCards.getCardArray().length;
                    stateInfo.setString(c.param_key.server_internal.target_player_name, targetPlayerName);
                    
                    
                    
                    
                } else {
                    this.stateAction = c.action.choosing_from_another;
                    this.stateReason = c.reason.m_greeding;
                    /*
                     * 两个人同时做的效果
                     * 发起人先设置state, reason什么的, 
                     * 然后updateState给target, target设置state和reason什么的, 
                     * 然后等待2个人选
                     * 都选完的话就互相得到牌.
                     * 有人超时了就帮他选
                     * 
                    */}
                break;
            }
            case functioncon.m_Mislead: {
                String[] targetPlayers = info.getStringArray(c.param_key.target_player_list);
                String spProviderName = targetPlayers[0];
                String spReceiverName = targetPlayers[1];
                
                Player spProvider = table.players.getPlayerByPlayerName(spProviderName);
                spProvider.property.spDown(1);
                spProvider.drawHandCards(1);
                
                Player spReceiver = table.players.getPlayerByPlayerName(spReceiverName);
                spReceiver.property.spUp(1);
                
                this.turnToTurnHolder();
                break;
            }
        }
    }
    
    // TODO 思考是用chose更好, 还是用respond as target更好, 或者改成chain的某个阶段之类的?
    public void respondAsTarget(EsObject msg) {
        
        if (stateReason.equals(c.reason.s_viper_raided)) {
            int[] cardsToBeDrop = msg.getIntegerArray(c.param_key.id_list);
            this.handCards.removeAll(cardsToBeDrop, false);
            this.turnToTurnHolder();
        } else if (stateReason.equals(c.reason.m_ElunesArrowed)) {
            int[] cardsToBeDrop = msg.getIntegerArray(c.param_key.id_list);
            this.handCards.removeAll(cardsToBeDrop, false);
            this.turnToTurnHolder();
        }
    }
    
    public void updateState(String state, String reason, EsObject inputState) {
        
        this.stateAction = state;
        this.stateReason = reason;
        this.stateInfo.addAll(inputState);
        /*
         * TODO 别忘了更新table state
         */
        l.logger().d(tag, "stateAction=" + stateAction + "stateAction, stateReason=" + stateReason);
        if (stateReason.equals(c.reason.normal_attacked)
                || stateReason.equals(c.reason.chaos_attacked)
                || stateReason.equals(c.reason.flame_attacked)
        
        ) {
            l.logger().d(tag + this.userName, "under attacking");
            
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
            
            table.tableState = new TableState(c.game_state.started.somebody_s_LagunaingBlade, new String[] { userName });
            stateInfo.setAction(c.action.choosing_to_evade);
            Integer[] evasions = this.handCards.getCardsByFunction(functioncon.b_evasion).toArray(new Integer[] {});
            stateInfo.setInteger(c.param_key.available_count, 3);
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(evasions));
            this.updateToClient(stateInfo);
            table.getWaiter().waitForSingleChoosing(this, c.default_wait_time);// .becauseOf(reason);
            
            Data publicData = new Data();
            publicData.setAction(c.action.choosing_to_evade);
            table.sendPublicMessage(publicData, userName);
            this.stateReason = c.reason.s_LagunaBladed;
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
            
        } else if (stateReason.equals(c.reason.m_ElunesArrowed)) {
            table.tableState = new TableState(c.game_state.started.somebody_is_m_ElunesArrowing, new String[] { userName });
            boolean strenghened = stateInfo.getBoolean(c.param_key.is_strengthened, false);
            stateInfo.setInteger(c.param_key.available_count, 1);
            stateInfo.setAction(c.action.choosing_to_drop);
            Integer[] availableList = null;
            int select_result = stateInfo.getIntegerArray(c.param_key.id_list)[0];
            if (strenghened) {
                availableList = this.handCards.getCardsByProperty(card_const.suits, select_result).toArray(new Integer[] {});
            } else {
                availableList = this.handCards.getCardsByProperty(card_const.color, select_result).toArray(new Integer[] {});
            }
            stateInfo.isCancelEnabled(true);
            stateInfo.setIntegerArray(c.param_key.available_id_list, u.intArrayMapping(availableList));
            this.updateToClient(stateInfo);
            
            Data publicData = new Data();
            publicData.setAction(c.action.choosing_to_drop);
            table.waiter.waitForSingleChoosing(this, c.default_wait_time);
            
            table.sendPublicMessage(publicData, userName);
        } else if (stateReason.equals(c.reason.m_ElunesArrowing)) {
            table.tableState = new TableState(c.game_state.started.somebody_is_m_ElunesArrowing, new String[] { userName });
            boolean strenghened = stateInfo.getBoolean(c.param_key.is_strengthened, false);
            stateInfo.setInteger(c.param_key.available_count, 1);
            Data publicData = new Data();
            
            if (strenghened) {
                stateInfo.setAction(c.action.choosing_from_suits);
                publicData.setAction(c.action.choosing_from_suits);
            } else {
                stateInfo.setAction(c.action.choosing_from_color);
                publicData.setAction(c.action.choosing_from_color);
            }
            this.updateToClient(stateInfo);
            table.waiter.waitForSingleChoosing(this, c.default_wait_time);
            table.sendPublicMessage(publicData, userName);
        } else if (stateReason.equals(c.reason.m_Chakraing)) {
            table.tableState = new TableState(c.game_state.started.somebody_is_m_Chakraing, new String[] { userName });
            Data data = new Data();
            data.setAction(c.action.choosing_from_color);
            data.setInteger(c.param_key.available_count, 1);
            table.waiter.waitForSingleChoosing(this, c.default_wait_time);
            table.sendPublicMessage(data, userName);
            this.updateToClient(data);
        }
    }
    
    public void startTurn() {
        
        used_how_many_attacks = 0;
        this.drawHandCards(2);
        this.freePlay(this.isAi());
        
    }
    
    private void freePlay(boolean ai) {
        
        Data obj = new Data();
        obj.setAction(c.action.turn_to_player);// kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        // obj.addBoolean(c.param_key.clear_showing_cards, true);
        int[] availableHandCards = this.getAvailableHandCards();
        obj.addIntegerArray(c.param_key.available_id_list, availableHandCards);
        obj.addInteger(c.param_key.available_count, c.selectable_count.default_value);
        
        table.sendMessageToSingleUser(userName, obj);
        
        obj = new Data();
        obj.setAction(c.action.turn_to_player);// kActionPlayingCard 出牌阶段
        obj.addString(c.param_key.player_name, userName);
        table.sendPublicMessage(obj, userName);
        
        if (this.isAi()) {
            this.cancel();
        } else {
            table.waiter.waitForSingleChoosing(this, c.default_wait_time);// .becauseOf(c.action.free_play);
        }
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
        
        l.logger().d(tag, "autoDecise, stateAction=" + stateAction + ", stateReason=" + stateReason);
        if (stateAction.equals(c.action.choosing_from_hand)) {
            if (this.isAi()) {
                int result = -1;
                if (this.stateReason.equals(c.reason.s_LagunaBladed)) {
                    int[] id_list = stateInfo.getIntegerArray(c.param_key.id_list);
                    result = this.ai.chooseSingle(id_list);
                } else {
                    int[] available_id_list = stateInfo.getIntegerArray(c.param_key.available_id_list);
                    result = this.ai.chooseSingle(available_id_list);
                }
                this.updateState(c.playercon.state.idle, c.action.decided, new Data().addInteger(c.param_key.single_result, result));
                l.logger().d(tag, "clearing stateInfo");
                this.stateInfo.removeAll();
            } else {
                this.cancel();
            }
            
        } else if (stateAction.equals(c.action.choosing_from_color) || stateAction.equals(c.action.choosing_from_suits)) {
            
            this.cancel();
        }
        this.stateAction = c.action.none;
    }
    
    public void cancel() {
        
        table.cancelScheduledExecution();
        
        l.logger().d(tag, "cancel, stateReason=" + stateReason);
        if (this.stateReason == c.reason.normal_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            
            int hit_amount = 1 + god_helped;
            this.property.hpDown(hit_amount, true);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.chaos_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            int hit_amount = 1 + god_helped;
            this.property.hpDown(hit_amount, true);
            
            table.players.turnHolder.property.spUp(1);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.flame_attacked) {
            int god_helped = table.players.turnHolder.godStrength ? 1 : 0;
            this.property.hpDown(1 + god_helped, false);
            this.property.spUp(2 + god_helped);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.s_LagunaBladed) {
            this.property.hpDown(3, true);
            
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.m_ElunesArrowed) {
            this.property.hpDown(1, true);
            turnToTurnHolder();
        } else if (this.stateReason == c.reason.m_ElunesArrowing) {
            int result = 1;// auto chose suits or color
            
            String targetName = stateInfo.getStringArray(c.param_key.target_player_list)[0];
            Player targetPlayer = table.players.getPlayerByPlayerName(targetName);
            String action = c.action.choosing_from_hand;
            String reason = c.reason.m_ElunesArrowed;
            stateInfo.addIntegerArray(c.param_key.id_list, new int[] { result });
            targetPlayer.updateState(action, reason, stateInfo);
        } else if (this.stateReason.equals(c.reason.m_Chakraing)) {
            colorResult(colorcon.red_client);
        }
        
        else if (this.stateAction.equals(c.action.free_play) && table.players.turnHolder.equals(this)) {
            // TODO 弃牌
            godStrength = false;
            m_Fanaticismed = false;
        }
    }
    
    public void colorResult(int choseResult) {
        if (this.stateReason.equals(c.reason.m_Chakraing)) {
            int realCardId = stateInfo.getInteger(c.param_key.id);
            Card card = CardParser.getParser().getCardById(realCardId);
            int realColorId = card.getColorCode();
            
            showCoveredCard(realCardId);
            
            if (realColorId == choseResult) {
                this.handCards.add(realCardId, false, new Data().setAction(c.action.table_card_to_get));
                
                stateAction = c.action.choosing_from_color;
                stateReason = c.reason.m_Chakraing;
                int i = table.drawOneCard();
                stateInfo.setInteger(c.param_key.id, i);
                table.tableState = new TableState(c.game_state.started.somebody_is_m_Chakraing, new String[] { userName });
                
                Data guessCardAnimi = new Data();
                guessCardAnimi.setAction(client_const.kActionGuessCard);
                guessCardAnimi.setInteger(c.param_key.server_internal.how_many, 1);
                table.sendPublicMessage(guessCardAnimi, userName);
                
                
                Data data = new Data();
                data.setAction(c.action.choosing_from_color);
                table.waiter.waitForSingleChoosing(this, c.default_wait_time);
                table.sendPublicMessage(data, userName);
                this.updateToClient(data);
            } else {
                this.turnToTurnHolder();
            }
        }
    }
    
    private void showCoveredCard(int realCardId) {
        // TODO drop stack . add realCardId
        // table.deck
        Data data = new Data();
        data.setAction(client_const.kActionUpdateDeckUsedCard);
        data.addIntegerArray(c.param_key.id_list, new int[] { realCardId });
        table.sendPublicMessage(data, userName);
    }
    
    private void turnToTurnHolder() {
        
        table.turnBackToTurnHolder(userName);
        
    }
    
    @Override
    public void onHandCardsAdded(int[] newCards, String playerName, boolean updateToClient) {
        
        if (updateToClient && !this.isAi()) {
            Data obj = new Data();
            obj.setAction(c.action.update_hand_cards);// kActionUpdatePlayerHand,
                                                      // 2003
            obj.setIntegerArray(c.param_key.id_list, newCards);
            this.updateToClient(obj);
        }
    }
    
    @Override
    public void onHandCardsDropped(int[] droppedCards, String playerName, boolean updateToClient) {
        
        if (updateToClient && !this.isAi()) {
            Data obj = new Data();
            obj.setAction(c.action.update_hand_cards);// kActionUpdatePlayerHand,
                                                      // 2003
            obj.setIntegerArray(c.param_key.id_list, droppedCards);
            this.updateToClient(obj);
        }
    }
    
    @Override
    public void onHpChanged(String playerName, int amount) {
        
        // Data data = new Data();
        // data.addInteger(c.param_key.hp_changed, amount);
        // this.updatePropertyToClient(data);
        
    }
    
    @Override
    public void onSpChanged(String playerName, int amount) {
        
        //
        // Data data = new Data();
        // data.addInteger(c.param_key.sp_changed, amount);
        // this.updatePropertyToClient(data);
        
    }
    
    @Override
    public void onEquipChanged(String playerName) {
        
    }
    
}
