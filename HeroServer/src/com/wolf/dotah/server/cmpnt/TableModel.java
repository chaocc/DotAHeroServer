package com.wolf.dotah.server.cmpnt;


import java.util.List;

import com.wolf.dotah.server.cmpnt.table.CardDropStack;
import com.wolf.dotah.server.cmpnt.table.CardRemainStack;
import com.wolf.dotah.server.cmpnt.table.DeckModel;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.Ticker;
import com.wolf.dotah.server.layer.translator.TableTranslator;
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
public class TableModel {
    
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
    
    
    TableTranslator translator;
    
    
    final String tag = "====>> TableModel: ";
    
    public TableModel() {
        players = PlayerList.getModel();
        initCardModels();
        //TODO init player basic info, from plugin api
        //TODO design ticker
        // 21, 12, 2, 3, 28, 17
        
        // each give 3
        
        System.out.println(tag + " table model inited. ");
        System.out.println(tag + "TableModel: " + this.toString());
    }
    
    /**
     * init完后的第一件事就是发待选英雄
     */
    public void dispatchHeroCandidates() {
        HeroCandidateModel heroModel = HeroCandidateModel.getCandidateModel();
        List<Integer[]> heroCandidateList = heroModel.getCandidateForAll(players.getCount());
        System.out.println(tag + "hero candidates inited. \n" + heroCandidateList);
        
        //TODO table 事件发给每个player都要做事的活动可以尝试叫table action, 
        //具体是否可行还有待思考
        for (int i = 0; i < players.getCount(); i++) {
            Integer[] candidatesForSingle = heroCandidateList.get(i);
            Player single = PlayerList.getModel().getPlayerByIndex(i);
            //updateState传进state?
            single.updateState("choosing_hero", new Data().addIntegerArray("", u.integerArrayToIntArray(candidatesForSingle)));
        }
    }
    
    private void initCardModels() {
        
        DeckModel deck = DeckModel.getDeckModel();
        //remain stack should have the behavior of dispatching handcards
        CardRemainStack.getRemainStackModel().initWithCardList(deck.getSimpleDeck());
        CardDropStack.getDropStackModel().syncWithRemainStack();
        
    }
    
    
    public void setTranslator(TableTranslator tableTranslator) {
        
        this.translator = tableTranslator;
        
    }
    
    //TODO 等选完hero了, 把player list 里的每个player更新好
    
    
}
