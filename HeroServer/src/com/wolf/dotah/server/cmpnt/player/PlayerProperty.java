package com.wolf.dotah.server.cmpnt.player;

public class PlayerProperty {
    HeroInfo hero;
    PlayerHandCardsModel handCards;
    PlayerHpModel hp;
    PlayerSpModel sp;
    PlayerEquipments equips;
    PlayerForce force;
    
    public HeroInfo getHero() {
        
        return hero;
    }
    
    
    public void setHero(HeroInfo hero) {
        
        this.hero = hero;
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
}
