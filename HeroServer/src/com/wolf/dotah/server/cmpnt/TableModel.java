package com.wolf.dotah.server.cmpnt;

import java.util.List;
import java.util.Map;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.MessageCenter;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.cmpnt.table.DeckModel;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.PlayerList.PlayerListListener;
import com.wolf.dotah.server.cmpnt.table.TableShowingCards;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.table_const;
import com.wolf.dotah.server.cmpnt.table.schedule.Waiter;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.client_const;
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
public class TableModel implements table_const, player_const, PlayerListListener {
    
    /*
     * TODO wait 有几种,  所有人等待特定的人, 所有人等待未知的人, 一个人等待特定的一个人, 一个列表的人等待特定的人
     * TODO 发message有几种: 发给一个人, 发给几个人, 发给所有人, 还要区分是否只有自己可见的(好像不发给所有人的都是只有自己可见)
     * TODO decision 有几种
     * TODO action分成3种, table action, all player action, 和 specific player action
     *                   table action 直接给table处理.
     *                   all player action 交给 player list,  由player list交给每个player 来处理
     *                   specific player action 交给player list, 由player list 交给指定player 来处理
     */
    
    TableState state; //TODO define states
    PlayerList players;
    DeckModel deck;
    private TableShowingCards showingCards;
    MessageCenter disp;
    Waiter waiter;
    
    final String tag = "====>> TableModel: ";
    
    public TableModel(PlayerList playerList, MessageCenter dispatcher) {
    
        state = new TableState();
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
    
        this.state.setSubject(this.getClass().getSimpleName());
        this.state.setState(tablecon.state.not_started.chooing_hero);
        HeroCandidateModel heroModel = new HeroCandidateModel();
        List<Integer[]> heroCandidateList = heroModel.getCandidateForAll(players.getCount());
        
        for (int i = 0; i < players.getCount(); i++) {
            Integer[] candidatesForSingle = heroCandidateList.get(i);
            Player single = players.getPlayerByIndex(i);
            
            Data state = new Data().addIntegerArray(playercon.state.param_key.general.id_list, u.intArrayMapping(candidatesForSingle));
            single.setAction(playercon.state.desp.choosing.choosing_hero);
            single.setState(state);
            state.setAction(playercon.state.desp.choosing.choosing_hero);
            if (single.isAi()) {
                single.performAiAction(c.param_key.hero_candidates);
            } else {
                disp.sendMessageToSingleUser(single.getUserName(), state);
            }
            //            updateSequence.submitServerUpdateByTable(this);
        }
        //TODO waiting for everybody to choose
        waiter.waitingForEverybody().becauseOf(playercon.state.desp.choosing.choosing_hero);
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
        data.setAction(c.server_action.start_game);
        data.addStringArray("player_list", players.getNameList());
        disp.broadcastMessage(data);
    }
    
    public PlayerList getPlayers() {
    
        return players;
    }
    
    public void setPlayers(PlayerList players) {
    
        this.players = players;
    }
    
    
    public interface tablevar {
        public int wait_time = c.default_wait_time;
    }
    
    public void broadcastHeroInited() {
    
        Data data = new Data();
        data.setAction(c.server_action.update_player_list_info);//kActionInitPlayerHero = 1004
        //TODO 先只加hero, 以后再改;
        data.addAll(this.getPlayers().toSubtleData());
        disp.broadcastMessage(data);
    }
    
    public List<Integer> getCardsFromRemainStack(int count) {
    
        List<Integer> cards = deck.fetchCards(count);
        
        return cards;
    }
    
    public void dispatchHandcards() {
    
        // TODO 给每个人发手牌, 每发1个, 就发2个plugin message
        for (Player p : this.getPlayers().getPlayerList()) {
            List<Integer> cards = this.getCardsFromRemainStack(c.default_draw_count);
            p.getHandcards(cards);
        }
        
        updatePlayersToCutting();
    }
    
    
    public void updatePlayersToCutting() {
    
        this.state.setState(tablecon.state.not_started.cutting);
        showingCards.startUsing(1);
        for (Player p : this.getPlayers().getPlayerList()) {
            p.cutting();
        }
        waiter.waitingForEverybody().becauseOf(c.server_action.choosing);
    }
    
    
    public TableState getState() {
    
        return state;
    }
    
    public void setState(TableState state) {
    
        this.state = state;
    }
    
    public Map<String, Integer> showingCards() {
    
        return showingCards.getAllPlayerChoosingOrUsingCardMap();
    }
    
    public void addResultForShowing(String user, Integer card) {
    
        showingCards.addResultToShow(user, card);
    }
    
    public void startTurn(String playerName) {
    
        Data data = new Data();
        data.setAction(c.ac.turn_to_player);//kActionPlayingCard 出牌阶段
        data.addString(client_const.param_key.player_name, playerName);
        //TODO table 里要保存current player, 
        disp.sendMessageToAllWithoutSpecificUser(data, playerName);
        
        
        Player pp = players.getPlayerByPlayerName(playerName);
        if (pp.getAi() != null && pp.isAi()) {
            pp.getAi().startTurn();
        } else {
            pp.startTurn();
            
        }
        
        
        //        data = new Data();
        //        data.setAction(c.server_action.free_play);//3001
        //        int[] availableHandCards = players.getPlayerByPlayerName(playerName).getAvailableHandCards();
        //        data.setIntegerArray(client_const.param_key.available_id_list, availableHandCards);
        //        data.setInteger(client_const.param_key.kParamSelectableCardCount, 1);//selectable count
        //        this.getTranslator().getDispatcher().sendMessageToSingleUser(playerName, data);
        
    }
    
    public DeckModel getDeck() {
    
        return deck;
    }
    
    public void setDeck(DeckModel deck) {
    
        this.deck = deck;
    }
    
    public int getRemainCardCount() {
    
        return this.deck.getRemainCount();
    }
    
    @Override
    public String toString() {
    
        return "TableModel [state=" + state + ", players=" + players + ", deck=" + deck + ", cutCards=" + showingCards
            + ", disp=" + disp + ", tag=" + tag + "]";
    }
    
    public void sendMessageToSingleUser(String userName, Data msg) {
    
        disp.sendMessageToSingleUser(userName, msg);
    }
    
    public MessageCenter getMessenger() {
    
        return this.disp;
    }
    
    public void choseCard(String user, EsObject msg) {
    
        int[] id = msg.getIntegerArray(c.param_key.id_list, new int[] {});
        l.logger().d(user, "table.getState().getState() :  " + state.getState());
        if (state.getState() == tablecon.state.not_started.cutting) {
            this.addResultForShowing(user, id[0]);
        }
        
    }
    
    public void playerUpdateInfo(String userName, Data customData) {
    
        this.sendMessageToAllWithoutSpecificUser(customData, userName);
        
        
    }
    
    private void sendMessageToAllWithoutSpecificUser(Data customData, String userName) {
    
        disp.sendMessageToAllWithoutSpecificUser(customData, userName);
        
    }
    
    public List<Integer> drawCardsFromDeck(int i) {
    
        
        return deck.fetchCards(2);
    }
}
