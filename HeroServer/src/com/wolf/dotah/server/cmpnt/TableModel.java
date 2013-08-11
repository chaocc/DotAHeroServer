package com.wolf.dotah.server.cmpnt;


import java.util.List;

import com.wolf.dotah.server.TablePlugin;
import com.wolf.dotah.server.cmpnt.table.CardDropStack;
import com.wolf.dotah.server.cmpnt.table.CardRemainStack;
import com.wolf.dotah.server.cmpnt.table.DeckModel;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.Ticker;

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
public class TableModel {
    
    TablePlugin dispatcher;
    TableState state; //TODO define states
    PlayerList players;
    List<Integer[]> heroCandidateList;
    CardRemainStack remainStack;
    CardDropStack dropStack;
    Ticker ticker;
    
    
    public TableModel(TablePlugin tablePlugin) {
    
        this.dispatcher = tablePlugin;
        initPlayerList();
        initHeroCandidates();
        initCardModels();
        //TODO init hero candidates,  parsing and model behaviors
        //TODO init player basic info, from plugin api
        //TODO design ticker
        // 21, 12, 2, 3, 28, 17
        
        // each give 3
        
        
    }
    
    
    private void initHeroCandidates() {
    
        heroCandidateList = HeroCandidateModel.getCandidateModel().getCandidateForAll(players.getCount());
        
    }
    
    
    private void initPlayerList() {
    
        int zone = dispatcher.getApi().getZoneId();
        int room = dispatcher.getApi().getRoomId();
        PlayerList.getModel().initWithUserCollection(dispatcher.getApi().getUsersInRoom(zone, room));
        
        
    }
    
    
    private void initCardModels() {
    
        DeckModel deck = DeckModel.getDeckModel();
        //remain stack should have the behavior of dispatching handcards
        CardRemainStack.getRemainStackModel().initWithCardList(deck.getSimpleDeck());
        CardDropStack.getDropStackModel().syncWithRemainStack();
        
    }
    
    //TODO 等选完hero了, 把player list 里的每个player更新好
    
    
}
