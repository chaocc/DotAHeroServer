package com.wolf.dotah.server.cmpnt;


import com.wolf.dotah.server.TablePlugin;
import com.wolf.dotah.server.cmpnt.player.AvailableTargetModel;
import com.wolf.dotah.server.cmpnt.player.Equipments;
import com.wolf.dotah.server.cmpnt.player.HandCardsModel;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.HpModel;
import com.wolf.dotah.server.cmpnt.player.PlayerState;
import com.wolf.dotah.server.cmpnt.player.SpModel;
import com.wolf.dotah.testframework.ClientRequest;


public class Player {
    
    //TODO attackable
    //TODO disarmable
    
    
    String userName;
    HeroInfo hero;
    
    PlayerState state;
    HandCardsModel handCards;
    HpModel hp;
    SpModel sp;
    AvailableTargetModel targets;
    Equipments equips;
    
    public String getUserName() {
    
        return userName;
    }
    
    
    public void setUserName(String userName) {
    
        this.userName = userName;
    }
    
    
    TablePlugin controller;
    
    
    public Player(TablePlugin deskController) {
    
        this.controller = deskController;
    }
    
    
    public void act(ClientRequest request) {
    
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
}
