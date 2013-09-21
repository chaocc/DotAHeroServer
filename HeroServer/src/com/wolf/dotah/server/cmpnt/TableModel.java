package com.wolf.dotah.server.cmpnt;

import java.util.List;
import java.util.Map;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.MessageCenter;
import com.wolf.dotah.server.cmpnt.cardandskill.Card;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel.HandCardsChangeListener;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty.PlayerPropertyChangedListener;
import com.wolf.dotah.server.cmpnt.table.DeckModel;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.Players;
import com.wolf.dotah.server.cmpnt.table.Players.PlayerListListener;
import com.wolf.dotah.server.cmpnt.table.TableShowingCards;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.schedule.Waiter;
import com.wolf.dotah.server.layer.dao.CardParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

/** 
 * 牌桌行为主要有:
 * 
 * 1, 轮换turn
 * 2, 发牌,
 * 3, 洗牌
 * 4, 将用过的牌扔到弃牌堆
 * 
 * 生成牌, 洗牌
 * 发英雄牌
 * 分发手牌
 * 切牌, 
 * 决定势力, 先出的为近卫, 然后往下交替
 * 5, 切牌后即开始进入6个阶段的循环, 每6个阶段完成后进入下个角色的6个阶段
 * 6, 每个阶段都有时间限制, 10秒或者15秒什么的
 * @author Solomon
 *
 */
public class TableModel implements PlayerListListener, HandCardsChangeListener, PlayerPropertyChangedListener {
    
    /*
     * TODO wait 有几种,  所有人等待特定的人, 所有人等待未知的人, 一个人等待特定的一个人, 一个列表的人等待特定的人
     * TODO action分成3种, table action, all player action, 和 specific player action
     *                   table action 直接给table处理.
     *                   all player action 交给 player list,  由player list交给每个player 来处理
     *                   specific player action 交给player list, 由player list 交给指定player 来处理
     */
    
    public TableState tableState; //TODO define states
    public Players players;
    public DeckModel deck;
    private TableShowingCards showingCards;
    MessageCenter disp;
    public Waiter waiter;
    
    final String tag = "====>> TableModel: ";
    
    public TableModel(Players playerList, MessageCenter dispatcher) {
    
        tableState = new TableState(c.game_state.none);
        players = playerList;
        players.registerPlayerListListener(this);
        showingCards = new TableShowingCards();
        initCardModels();
        this.disp = dispatcher;
        waiter = new Waiter(disp);
        //TODO init player basic info, from plugin api
        //TODO design ticker
        // 21, 12, 2, 3, 28, 17
        
        // each give 3
    }
    
    /**
     * init完后的第一件事就是发待选英雄
     */
    public void dispatchHeroCandidates() {
    
        this.tableState.setSubject(this.getClass().getSimpleName());
        this.tableState.setState(c.game_state.not_started.chooing_hero);
        HeroCandidateModel heroModel = new HeroCandidateModel();
        List<Integer[]> heroCandidateList = heroModel.getCandidateForAll(players.getCount());
        
        for (int i = 0; i < players.getCount(); i++) {
            Integer[] candidatesForSingle = heroCandidateList.get(i);
            Player single = players.getPlayerByIndex(i);
            
            Data state = new Data().addIntegerArray(c.playercon.state.param_key.general.id_list, u.intArrayMapping(candidatesForSingle));
            single.stateAction = c.playercon.state.choosing.choosing_hero;
            single.stateInfo = state;
            state.setAction(c.playercon.state.choosing.choosing_hero);
            if (single.isAi()) {
                single.performAiAction(c.param_key.hero_candidates);
            } else {
                disp.sendMessageToSingleUser(single.userName, state);
            }
            //            updateSequence.submitServerUpdateByTable(this);
        }
        waiter.waitingForEverybody().becauseOf(c.playercon.state.choosing.choosing_hero);
    }
    
    private void initCardModels() {
    
        deck = new DeckModel(this);
        
    }
    
    
    @Override
    public void didInitPlayerList() {
    
        this.broadcastGameStarted();
    }
    
    private void broadcastGameStarted() {
    
        Data data = new Data();
        data.setAction(c.action.start_game);
        data.addStringArray("player_list", players.getNameList());
        disp.broadcastMessage(data);
    }
    
    
    public void broadcastHeroInited() {
    
        Data data = new Data();
        data.setAction(c.action.update_player_list_info);//kActionInitPlayerHero = 1004
        //TODO 先只加hero, 以后再改;
        data.addAll(this.players.toSubtleData());
        disp.broadcastMessage(data);
    }
    
    public List<Integer> getCardsFromRemainStack(int count) {
    
        List<Integer> cards = deck.fetchCards(count);
        
        return cards;
    }
    
    public void initHandcards() {
    
        // TODO 给每个人发手牌, 每发1个, 就发2个plugin message
        for (Player p : this.players.getPlayerList()) {
            List<Integer> cards = this.getCardsFromRemainStack(c.default_draw_count);
            p.initHandCards(cards);
        }
        
        updatePlayersToCutting();
    }
    
    
    public void updatePlayersToCutting() {
    
        this.tableState.setState(c.game_state.not_started.cutting);
        showingCards.startUsing(1);
        for (Player p : this.players.getPlayerList()) {
            p.cutting();
        }
        waiter.waitingForEverybody().becauseOf(c.action.choosing);
    }
    
    
    public Map<String, Integer> showingCards() {
    
        return showingCards.getAllPlayerChoosingOrUsingCardMap();
    }
    
    public void addResultForShowing(String user, Integer card) {
    
        showingCards.addResultToShow(user, card);
    }
    
    public void startTurn(String biggestPlayer, int delaySec) {
    
        this.players.turnHolder = this.players.getPlayerByPlayerName(biggestPlayer);
        waiter.waitingForEverybody().becauseOf(c.reason.animating, delaySec);
    }
    
    public void startTurn(String playerName) {
    
        l.logger().d(tag, "startTurn, playerName=" + playerName + ", tableState=" + tableState.getState());
        if (tableState.isEqualToState(c.game_state.not_started.can_start_turn)) {
            this.cancelScheduledExecution();
            
            
            Data data = new Data();
            data.setAction(c.action.turn_to_player);//kActionPlayingCard 出牌阶段
            data.addString(c.param_key.player_name, playerName);
            //            data.addBoolean(c.param_key.clear_showing_cards, true);
            data.addIntegerArray(c.param_key.available_id_list, new int[] {});
            this.sendPublicMessage(data, playerName);
            
            
            Player pp = players.getPlayerByPlayerName(playerName);
            players.turnHolder = pp;
            pp.startTurn();
            
        }
        
        //                data = new Data();
        //                data.setAction(c.action.free_play);//3001
        //                int[] availableHandCards = players.getPlayerByPlayerName(playerName).getAvailableHandCards();
        //                data.setIntegerArray(client_const.param_key.available_id_list, availableHandCards);
        //                data.setInteger(client_const.param_key.kParamSelectableCardCount, 1);//selectable count
        //                this.getTranslator().getDispatcher().sendMessageToSingleUser(playerName, data);
        
    }
    
    public void cancelScheduledExecution() {
    
        l.logger().d(tag, "cancel from id=" + waiter.execution_id);
        waiter.cancelScheduledExecution();
        
    }
    
    
    public int getRemainCardCount() {
    
        return this.deck.getRemainCount();
    }
    
    @Override
    public String toString() {
    
        return "TableModel [state=" + tableState + ", players=" + players + ", deck=" + deck + ", cutCards=" + showingCards
            + ", disp=" + disp + ", tag=" + tag + "]";
    }
    
    public void sendMessageToSingleUser(String userName, Data msg) {
    
        disp.sendMessageToSingleUser(userName, msg);
    }
    
    public MessageCenter getMessenger() {
    
        return this.disp;
    }
    
    
    //    
    //    public void playerUpdateInfo(String userName, Data customData) {
    //    
    //        //        this.sendMessageToAllWithoutSpecificUser(customData, userName);
    //        this.sendPublicMessage(customData, userName);
    //        
    //        
    //    }
    
    //    private void sendMessageToAllWithoutSpecificUser(Data customData, String userName) {
    //    
    //        disp.sendMessageToAllWithoutSpecificUser(customData, userName);
    //        
    //    }
    
    public void sendMessageToAll(Data msg) {
    
        disp.sendMessageToAll(msg);
    }
    
    public List<Integer> drawCardsFromDeck(int i) {
    
        return deck.fetchCards(i);
    }
    
    public void updateTableInfoToOtherFromPlayer(String userName, int action, EsObject msg) {
    
        /*
         * 其实就是转发广播
         * 可能有目标, 
         * 也可能有card id
         * 之类的信息
         */
        Data data = new Data();
        data.addAll(msg);
        
        /*
         * 但是加工一下
         * 设上正确的action
         * 设上source
         */
        data.setAction(action);
        String source = userName;
        data.addString(c.param_key.player_name, source);
        
        //            this.sendMessageToAllWithoutSpecificUser(data, userName);
        this.sendPublicMessage(data, userName);
    }
    
    public void playerUseCard(String user, EsObject msg) {
    
        int cardId = msg.getIntegerArray(c.param_key.id_list)[0];
        Card card = CardParser.getParser().getCardById(cardId);
        int functionId = card.getFunction();
        
        l.logger().d(tag, "playerUseCard, cardId=" + cardId + ", card=" + card.toString() + ", functionId=" + functionId);
        Player p = players.getPlayerByUserName(user);
        l.logger().d(tag, "playerUseCard, Player=" + p.toString());
        
        p.useCard(msg, functionId);
        
    }
    
    public void choseCard(String user, EsObject msg) {
    
        int[] id = msg.getIntegerArray(c.param_key.id_list, new int[] {});
        l.logger().d(user, "table.getState().getState() :  " + tableState.getState());
        if (tableState.isEqualToState(c.game_state.not_started.cutting)) {
            this.addResultForShowing(user, id[0]);
        } else if (tableState.isEqualToState(c.game_state.started.somebody_attacking)) {
            playerUseCard(user, msg);
        } else if (tableState.isEqualToState(c.game_state.started.somebody_is_s_viper_raiding)) {
            Player p = players.getPlayerByUserName(user);
            if (p.stateReason.equals(c.reason.s_viper_raided)) {
                p.respondAsTarget(msg);
            }
        }else if(tableState.isEqualToState(c.game_state.started.somebody_s_LagunaingBlade)){
            playerUseCard(user, msg);
        }
        
    }
    
    public Waiter getWaiter() {
    
        return waiter;
    }
    
    public void turnBackToTurnHolder(String from) {
    
        Data data = new Data();
        data.setAction(c.action.free_play);
        this.sendPublicMessage(data, players.turnHolder.userName);
        
        
        data.addIntegerArray(c.param_key.available_id_list, players.turnHolder.getAvailableHandCards());
        data.addInteger(c.param_key.available_count, 1);
        this.sendMessageToSingleUser(players.turnHolder.userName, data);
        
    }
    
    public void sendPublicMessage(Data msg, String from) {
    
        msg.setInteger(c.param_key.kParamRemainingCardCount, this.getRemainCardCount());
        disp.sendPublicMessage(msg, from);
        
    }
    
    @Override
    public void onHandCardsAdded(List<Integer> newCards, String playerName, boolean sendPrivate) {
    
        Data obj = new Data();
        obj.setAction(c.action.update_hand_cards);
        obj.setInteger(c.param_key.hand_card_change_amount, newCards.size());
        this.sendPublicMessage(obj, playerName);
    }
    
    @Override
    public void onHandCardsDropped(int[] droppedCards, String playerName, boolean sendPrivate) {
    
        Data obj = new Data();
        obj.setAction(c.action.update_hand_cards);
        obj.setInteger(c.param_key.hand_card_change_amount, -droppedCards.length);
        this.sendPublicMessage(obj, playerName);
    }
    
    @Override
    public void onHpChanged(String playerName, int amount) {
    
        Data data = new Data();
        data.addInteger(c.param_key.hp_changed, amount);
        data.addString(c.param_key.player_name, playerName);
        data.setAction(c.action.update_player_property);
        this.sendPublicMessage(data, playerName);
    }
    
    @Override
    public void onSpChanged(String playerName, int amount) {
    
        Data data = new Data();
        data.addInteger(c.param_key.sp_changed, amount);
        data.addString(c.param_key.player_name, playerName);
        data.setAction(c.action.update_player_property);
        this.sendPublicMessage(data, playerName);
    }
    
    @Override
    public void onEquipChanged(String playerName) {
    
    }
    
}
