package com.wolf.dotah.server.cmpnt;


import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.PlayerAvailableTargetModel;
import com.wolf.dotah.server.cmpnt.player.PlayerEquipments;
import com.wolf.dotah.server.cmpnt.player.PlayerForce;
import com.wolf.dotah.server.cmpnt.player.PlayerHandCardsModel;
import com.wolf.dotah.server.cmpnt.player.PlayerHpModel;
import com.wolf.dotah.server.cmpnt.player.PlayerSpModel;
import com.wolf.dotah.server.cmpnt.player.PlayerState;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.testframework.ClientRequest;


public class Player implements player_const {
    
    //TODO attackable
    //TODO disarmable
    
    
    String userName;
    HeroInfo hero;
    
    PlayerState state;
    PlayerHandCardsModel handCards;
    PlayerHpModel hp;
    PlayerSpModel sp;
    PlayerAvailableTargetModel targets;
    PlayerEquipments equips;
    PlayerForce force;
    
    
    public Player(String name) {
    
        this.userName = name;
        state = new PlayerState();
        equips = new PlayerEquipments();
    }
    
    
    public HeroInfo getHero() {
    
        return hero;
    }
    
    
    public void setHero(HeroInfo hero) {
    
        this.hero = hero;
        
        
        
        
    }
    
    
    public PlayerState getState() {
    
        return state;
    }
    
    
    public void setState(PlayerState state) {
    
        this.state = state;
    }
    
    
    public PlayerHandCardsModel getHandCards() {
    
        return handCards;
    }
    
    
    public void setHandCards(PlayerHandCardsModel handCards) {
    
        this.handCards = handCards;
    }
    
    
    public PlayerHpModel getHp() {
    
        return hp;
    }
    
    
    public void setHp(PlayerHpModel hp) {
    
        this.hp = hp;
    }
    
    
    public PlayerSpModel getSp() {
    
        return sp;
    }
    
    
    public void setSp(PlayerSpModel sp) {
    
        this.sp = sp;
    }
    
    
    public PlayerAvailableTargetModel getTargets() {
    
        return targets;
    }
    
    
    public void setTargets(PlayerAvailableTargetModel targets) {
    
        this.targets = targets;
    }
    
    
    public PlayerEquipments getEquips() {
    
        return equips;
    }
    
    
    public void setEquips(PlayerEquipments equips) {
    
        this.equips = equips;
    }
    
    
    public PlayerForce getForce() {
    
        return force;
    }
    
    
    public void setForce(PlayerForce force) {
    
        this.force = force;
    }
    
    
    String getUserName() {
    
        return userName;
    }
    
    
    public void setUserName(String userName) {
    
        this.userName = userName;
    }
    
    
    //    TablePlugin controller;
    //    
    //    
    //    public Player(TablePlugin deskController) {
    //    
    //        this.controller = deskController;
    //    }
    
    
    public void act(ClientRequest request) {
    
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
}
