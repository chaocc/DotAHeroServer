package com.wolf.dotah.server.cmpnt;

import java.util.ArrayList;
import java.util.List;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.cmpnt.card.card_const.functioncon;
import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel.HandCardsChangeListener;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.layer.dao.HeroParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class Player implements HandCardsChangeListener {
    
    //TODO attackable
    //TODO disarmable
    private String tag = "Player ==>> ";
    private String action;
    private Data state;
    private PlayerProperty property;//player 属性的状态
    PlayerHandCardsModel handCards;
    private TableModel table;
    
    public void updateProperty(String propertyName, Data result) {
    
        // TODO 先要把update property 翻译成server action, 然后把server action放到step里, 而不是property name
    }
    
    
    /**
     * 相当于客户端拿来了新消息, 在做判断
     * @param a
     */
    public void performAiAction(String fromParamKey) {
    
        if (action.equals(c.action_string.free_play)) {
        } else {// choosing
            if (action.equals(c.playercon.state.desp.choosing.choosing_hero)) {
                int[] pickResult = state.getIntegerArray(c.playercon.state.param_key.general.id_list, new int[] {});
                int heroId = ai.chooseSingle(pickResult);
                this.initPropertyWithHeroId(heroId);
            } else if (action.equals(c.action.choosing_from_hand)) {
                Integer[] pickResult = handCards.getCards().toArray(new Integer[] {});
                int resultId = ai.chooseSingle(u.intArrayMapping(pickResult));
                this.table.addResultForShowing(this.getUserName(), resultId);
            }
        }
    }
    
    public void performSimplestChoice() {
    
        if (table.getState().getState() == c.game_state.not_started.cutting) {//cutting
            int id = getHandCards().getCards().get(0);
            
            table.addResultForShowing(this.getUserName(), id);
        } else {
            //choosing hero
            int[] idList = state.getIntegerArray(c.playercon.state.param_key.general.id_list, new int[] {});
            if (idList.length > 0) {
                if (action.equals(c.playercon.state.desp.choosing.choosing_hero)) {
                    this.initPropertyWithHeroId(idList[0]);
                }
            }
        }
    }
    
    public void pickedHero(EsObject msg) {
    
        if (action.equals(c.playercon.state.desp.choosing.choosing_hero)) {
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
        action = c.playercon.state.desp.confirmed.hero;
        if (this.getAi() == null || !this.getAi().isAi()) {
            String[] keys = { "heroId" };
            int[] values = { heroId };
            
            Data msg = new Data();
            debug(tag, "keys.length: " + keys.length);
            if (keys.length == 1) {
                if (keys[0].equals("heroId")) {
                    msg.setAction(c.action_string.chose_hero);
                    msg.setInteger("id", values[0]);
                }
            } else if (keys.length > 1) {
                //TODO action可以直接设成update xxx
            }
            updateMyStateToClient(msg);
            
        }
    }
    
    public void updateMyStateToClient(Data msg) {
    
        table.sendMessageToSingleUser(userName, msg);
        
    }
    
    
    public void getHandcards(List<Integer> cards) {
    
        addHandcards(cards);
        if (this.ai == null || !this.ai.isAi()) {
            sendPrivateMessage(c.action.init_hand_cards);
        }
        //        this.sendPublicMessage(c.ac.init_hand_cards);
        
    }
    
    //    public void sendPublicMessage(String string_action) {
    //    
    //        Data data = new Data();
    //        data.setAction(string_action);
    //        addPublicData(data, string_action);
    //        table.getMessenger().sendMessageToAllWithoutSpecificUser(data, this.getUserName());
    //    }
    //    
    //    private void addPublicData(Data data, String string_action) {
    //    
    //        if (c.ac.init_hand_cards.equals(string_action)) {
    //            data.setInteger(client_const.param_key.hand_card_count, getHandCards().getCards().size());
    //        }
    //    }
    
    private void sendPrivateMessage(String string_action) {
    
        Data data = new Data();
        data.setAction(string_action);
        addPrivateData(data, string_action);
        table.getMessenger().sendMessageToSingleUser(userName, data);
    }
    
    private void addPrivateData(Data data, String string_action) {
    
        if (c.action.init_hand_cards.equals(string_action)) {
            Integer[] cardArray = getHandCards().getCards().toArray(new Integer[] {});
            data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cardArray));
        } else if (c.action.choosing_from_hand.equals(string_action)) {
            List<Integer> cardList = getHandCards().getCards();
            int[] cardArray = u.intArrayMapping(cardList.toArray(new Integer[] {}));
            data.setIntegerArray(c.param_key.id_list, cardArray);
            data.setInteger(c.param_key.kParamSelectableCardCount, 1);
        }
    }
    
    public void cutting() {
    
        action = c.action.choosing_from_hand;
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
        List<Integer> availableList = new ArrayList<Integer>();
        for (int card : getHandCards().getCards()) {
            if (active(card)) {
                availableList.add(card);
                continue;
            }
            
        }
        return u.intArrayMapping(availableList.toArray(new Integer[] {}));
    }
    
    public PlayerHandCardsModel getHandCards() {
    
        return handCards;
    }
    
    public void setHandCards(PlayerHandCardsModel handCards) {
    
        this.handCards = handCards;
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
    
    public void addHandcards(List<Integer> cards) {
    
        this.handCards.add(cards);
        
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
    
        
        switch (functionId) {//主要是b, s, m三类
            case functioncon.b_normal_attack: {
                
                String targetName = info.getStringArray(c.param_key.target_player_list)[0];
                Player targetPlayer = table.getPlayers().getPlayerByPlayerName(targetName);
                action = c.action.choosing_from_hand;
                String reason = c.reason.attacked;
                targetPlayer.updateState(action, reason, info);
                
                //                Data dataObj = new Data();
                //                dataObj.setInteger(name, value);
                //                requireAction.setInteger(code_client_action_required, ac_require_attacked);
                //                requireAction.setIntegerArray(USED_CARDS, cards);
                //                requireAction.setString(PLAYER_NAME, user);
                //                for (String target : obj.getStringArray(TARGET_PLAYERS)) {
                //                    sendGamePluginMessageToUser(target, requireAction);
                //                }
                
                // 2, update target player to require react 
                // with choosing from handcards, 
                // evisions are available.
                
                // 3, if attack hitted, then update target's sp, hp
                
                // 4, if attack evaded, then update drop card stack
                
                break;
            }
            case functioncon.b_chaos_attack: {
                
                break;
            }
            case functioncon.b_flame_attack: {
                
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
    
    
    private void updateState(String state, String reason, EsObject stateInfo) {
    
        this.action = state;
        this.state.addAll(stateInfo);
        /* 
         * TODO 每次先根据reason 来判断哪些牌或者操作是允许的, 
         * 把这些牌或者操作添加到data里, 
         * 把action和允许的牌和操作更新给客户端, 让客户端选择.
         * 
         */
        
        if (reason.equals(c.reason.attacked)) {
            
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
}
