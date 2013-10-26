package com.wolf.dotah.server.cmpnt.player;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Player;


public class PlayerProperty {
    HeroInfo hero;
    
    int hp;
    int sp;
    final int hpLimit;
    final int spLimit;
    public PlayerEquipments equips;
    String force;
    
    Player player;
    List<PlayerPropertyChangedListener> listeners;
    
    public PlayerProperty(HeroInfo heroInfo, Player inputPlayer) {
    
        hero = heroInfo;
        
        hpLimit = hero.getHpLimit();
        spLimit = hero.getSpLimit();
        equips = new PlayerEquipments();
        this.player = inputPlayer;
        this.listeners = new ArrayList<PlayerPropertyChangedListener>();
        listeners.add(inputPlayer.table);
        listeners.add(inputPlayer);
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
    
    public void hpUp(int i) {
    
        this.hp += i;
        for (PlayerPropertyChangedListener listener : listeners) {
            listener.onHpChanged(player.userName, i);
        }
    }
    
    public void hpDown(int i, boolean spUp) {
    
        this.hp -= i;
        for (PlayerPropertyChangedListener listener : listeners) {
            listener.onHpChanged(player.userName, -i);
        }
        
        if (spUp) {
            spUp(i);
        }
    }
    
    
    public void spUp(int i) {
    
        int spChanged = i;
        this.sp += i;
        if (sp >= spLimit) {
            spChanged = i - (sp - spLimit);
            sp = spLimit;
        }
        
        for (PlayerPropertyChangedListener listener : listeners) {
            listener.onSpChanged(player.userName, spChanged);
        }
    }
    
    public void spDown(int i) {
    
        this.sp -= i;
        for (PlayerPropertyChangedListener listener : listeners) {
            listener.onSpChanged(player.userName, -i);
        }
    }
    public interface PlayerPropertyChangedListener {
        void onHpChanged(String playerName, int amount);
        
        void onSpChanged(String playerName, int amount);
        
        void onEquipChanged(String playerName);
    }
}
