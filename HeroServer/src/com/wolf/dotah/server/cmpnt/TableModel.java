package com.wolf.dotah.server.cmpnt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wolf.dotah.server.cmpnt.player.PlayerState;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.cmpnt.table.CardDropStack;
import com.wolf.dotah.server.cmpnt.table.CardRemainStack;
import com.wolf.dotah.server.cmpnt.table.DeckModel;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.PlayerList.PlayerListListener;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.Ticker;
import com.wolf.dotah.server.cmpnt.table.table_const;
import com.wolf.dotah.server.layer.translator.ServerUpdateSequence;
import com.wolf.dotah.server.layer.translator.TableTranslator;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;

/**
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
    
    /**
     * 牌桌行为主要有:
     * 
     * 1, 轮换turn
     * 2, 发牌,
     * 3, 洗牌
     * 4, 将用过的牌扔到弃牌堆
     * 
     * 
     * 
     */
    
    /*
     * TODO wait 有几种,  所有人等待特定的人, 所有人等待未知的人, 一个人等待特定的一个人, 一个列表的人等待特定的人
     * TODO 发message有几种: 发给一个人, 发给几个人, 发给所有人, 还要区分是否只有自己可见的(好像不发给所有人的都是只有自己可见)
     * TODO decision 有几种
     */
    
    TableState state; //TODO define states
    PlayerList players;
    CardRemainStack remainStack;
    CardDropStack dropStack;
    Ticker ticker;
    Map<String, Integer> cutCards;
    
    TableTranslator translator;
    //    MessageDispatcher msgDispatcher;
    
    final String tag = "====>> TableModel: ";
    
    public TableModel(PlayerList playerList) {
        players = playerList;
        players.registerPlayerListListener(this);
        initCutCardMap();
        initCardModels();
        //TODO init player basic info, from plugin api
        //TODO design ticker
        // 21, 12, 2, 3, 28, 17
        
        // each give 3
    }
    
    private void initCutCardMap() {
        cutCards = new HashMap<String, Integer>();
    }
    
    /**
     * init完后的第一件事就是发待选英雄
     */
    public void dispatchHeroCandidates() {
        HeroCandidateModel heroModel = new HeroCandidateModel();
        List<Integer[]> heroCandidateList = heroModel.getCandidateForAll(players.getCount());
        //        MessageDispatcher.getDispatcher(null).debug(tag, "hero candidates inited. \n" + heroCandidateList);
        
        for (int i = 0; i < players.getCount(); i++) {
            Integer[] candidatesForSingle = heroCandidateList.get(i);
            Player single = players.getPlayerByIndex(i);
            /**
             * 所以从一开始消息dispatch 进来的时候,  就知道是要choosing了!
             * 中间一系列过程只是为了责任分离, 让代码更易理解, 更易维护!
             */
            ServerUpdateSequence updateSequence = new ServerUpdateSequence(c.server_action.choosing, single);
            
            Data stateDetail = new Data().addIntegerArray(playercon.state.param_key.general.id_list, u.intArrayMapping(candidatesForSingle));
            single.updateState(playercon.state.desp.choosing.choosing_hero, stateDetail, updateSequence);
            
            updateSequence.submitServerUpdateByTable(this);
        }
        //TODO waiting for everybody to choose
        //TODO 从translator来做
        translator.getDispatcher().waitingForEverybody().becauseOf(playercon.state.desp.choosing.choosing_hero);
    }
    
    private void initCardModels() {
        
        DeckModel deck = new DeckModel(this);
        //remain stack should have the behavior of dispatching handcards
        remainStack = new CardRemainStack(deck).initWithCardList(deck.getSimpleDeck());
        dropStack = new CardDropStack(deck);
        dropStack.syncWithRemainStack();
        
    }
    
    public void setTranslator(TableTranslator tableTranslator) {
        this.translator = tableTranslator;
        
    }
    
    @Override
    public void didInitPlayerList() {
        this.broadcastGameStarted();
    }
    
    private void broadcastGameStarted() {
        Data data = new Data();
        data.setAction(c.server_action.start_game);
        data.addStringArray("player_list", players.getNameList());
        //TODO 从translator broadcast
        this.getTranslator().getDispatcher().broadcastMessage(data);
    }
    
    public PlayerList getPlayers() {
        return players;
    }
    
    public void setPlayers(PlayerList players) {
        this.players = players;
    }
    
    public TableTranslator getTranslator() {
        return translator;
    }
    
    //TODO 等选完hero了, 把player list 里的每个player更新好
    public interface tablevar {
        public int wait_time = c.default_wait_time;
    }
    
    public void broadcastHeroInited() {
        Data data = new Data();
        data.setAction(c.server_action.update_player_list_info);//kActionInitPlayerHero = 1004
        //TODO先只加hero, 以后再改;
        data.addAll(this.getPlayers().toSubtleData());
        this.getTranslator().getDispatcher().broadcastMessage(data);
    }
    
    public void startTurn(Player playerByIndex) {
        // TODO 所有人都更新完手牌后, start turn
        
    }
    
    public List<Integer> getCardsFromRemainStack(int count) {
        List<Integer> cards = remainStack.fetchCards(count);
        
        return cards;
    }
    
    public void dispatchHandcards() {
        // TODO 给每个人发手牌, 每发1个, 就发2个plugin message
        for (Player p : this.getPlayers().getPlayerList()) {
            //            ServerUpdateSequence updateSequence = new ServerUpdateSequence(c.server_action.update_player_info, p);
            List<Integer> cards = this.getCardsFromRemainStack(c.default_draw_count);
            translator.getDispatcher().getPlayerTranslator().dispatchHandcards(p, cards);
            
            //            updateSequence.submitServerUpdateByTable(this);
            
        }
        this.startTurn(this.getPlayers().getPlayerByIndex(0));
    }
    
    public void updatePlayersToCutting() {
        for (Player p : this.getPlayers().getPlayerList()) {
            p.cutting();

        }
        
        this.getTranslator().getDispatcher().waitingForEverybody().becauseOf(c.server_action.choosing);
    }
}
