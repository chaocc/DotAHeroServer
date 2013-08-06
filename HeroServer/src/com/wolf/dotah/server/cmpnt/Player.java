package com.wolf.dotah.server.cmpnt;


import java.util.List;

import com.wolf.dotah.server.DeckPlugin;
import com.wolf.dotah.testframework.ClientRequest;


public class Player {
    
    //TODO attackable
    //TODO disarmable
    
    
    String userName;
    List<Integer> herosForChoosing;
    int heroId;
    
    StateModel state;
    HandCardsModel handCards;
    HeroModel hero;
    
    
    public int getHeroId() {
    
        return heroId;
    }
    
    
    public void setHeroId(int heroId) {
    
        this.heroId = heroId;
    }
    
    
    public String getUserName() {
    
        return userName;
    }
    
    
    public void setUserName(String userName) {
    
        this.userName = userName;
    }
    
    
    public List<Integer> getHerosForChoosing() {
    
        return herosForChoosing;
    }
    
    
    public void setHerosForChoosing(List<Integer> herosForChoosing) {
    
        this.herosForChoosing = herosForChoosing;
    }
    
    
    DeckPlugin controller;
    
    
    public Player(DeckPlugin deskController) {
    
        this.controller = deskController;
    }
    
    
    public void act(ClientRequest request) {
    
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
}
