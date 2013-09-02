package com.wolf.dotah.server.cmpnt.player;


public class PlayerProperty {
    HeroInfo hero;
    
    int hp;
    int sp;
    final int hpLimit;
    final int spLimit;
    PlayerEquipments equips;
    String force;
    
    public PlayerProperty(HeroInfo heroInfo) {
    
        hero = heroInfo;
        
        hpLimit = hero.getHpLimit();
        spLimit = hero.getSpLimit();
        equips = new PlayerEquipments();
        
        this.refresh();
    }
    
    public HeroInfo getHero() {
    
        return hero;
    }
    
    public void setHero(HeroInfo hero) {
    
        this.hero = hero;
        
    }
    
    
    public PlayerEquipments getEquips() {
    
        return equips;
    }
    
    public void setEquips(PlayerEquipments equips) {
    
        this.equips = equips;
    }
    
    
    private void refresh() {
    
        hp = hpLimit;
        sp = 0;
        
    }
    
    @Override
    public String toString() {
    
        return "PlayerProperty [hero=" + hero + ", hp=" + hp + ", sp=" + sp + ", equips=" + equips + ", force=" + force + "]";
    }
}
