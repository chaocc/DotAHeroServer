package com.wolf.dotah.server.cmpnt.player;

import java.util.List;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.layer.dao.HeroParser;

public class PlayerProperty {
    HeroInfo hero;
    PlayerHandCardsModel handCards;
    PlayerHpModel hp;
    PlayerSpModel sp;
    PlayerEquipments equips;
    PlayerForce force;
    Player player;
    
    public PlayerProperty(int heroId, Player p) {
        this.player = p;
        hero = HeroParser.getParser().getHeroInfoById(heroId);
        handCards = new PlayerHandCardsModel(p, hero.getHandcardLimit());
        hp = new PlayerHpModel(hero.getHpLimit());
        sp = new PlayerSpModel(hero.getSpLimit());
        equips = new PlayerEquipments();
        force = new PlayerForce();
        
        this.refresh();
    }
    
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
    
    private void refresh() {
        
        hp.setHp(hp.getHpLimit());
        sp.setSp(0);
        
    }
    
    public void addHandcards(List<Integer> cards) {
        this.handCards.add(cards);
        
    }

    @Override
    public String toString() {
    
        return "PlayerProperty [hero=" + hero + ", handCards=" + handCards + ", hp=" + hp + ", sp=" + sp + ", equips=" + equips
            + ", force=" + force + ", player=" + player + "]";
    }
}
