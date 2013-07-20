package com.wolf.dota.component;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.plist.NSArray;
import net.sf.plist.NSDictionary;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListParser;


public class Player {
    
    public static Player getPlayerById(int id, String userName) {
    
        Player player = players.get(id);
        if (player == null) {
            player = new Player(id, userName);
            players.put(id, player);
        }
        return player;
    }
    
    
    private static Map<Integer, Player> players = new HashMap<Integer, Player>();
    
    private String
            playerName,
            heroName;
    private int
            heroId,
            heroType,
            hpLimit,
            spLimit,
            handCardLimit,
            hp,
            sp;
    
    
    private int[]
            skills,
            weapons;
    private List<Integer> handCards;
    
    
    // 21, "kSlayer"),
    // 12, "kVengefulSpirit"),
    // 2, "kBristleback"),
    // 3, "kSacredWarrior"),
    // 28, "kKeeperOfTheLight"),
    // 17, "kAntimage")
    
    private Player(int id, String userName) {
    
        this.playerName = userName;
        initPlayerFromPlist(id);
    }
    
    
    private void initPlayerFromPlist(int id) {
    
        this.heroId = id;
        handCards = new ArrayList<Integer>();
        weapons = new int[2];
        NSArray heroArray;
        try {
            heroArray = (NSArray) PropertyListParser.parse(new File(
                    "doc/HeroCardArray.xml"));
            
        } catch (Exception e) {
            System.err.println("init hero failed");
            e.printStackTrace();
            return;
        }
        if (heroArray == null || heroArray.array().length == 0) { return; }
        NSDictionary hero = (NSDictionary) heroArray.array()[id];
        hp = hpLimit = Integer.parseInt(hero.get("healthPointLimit").getValue().toString());
        sp = spLimit = Integer.parseInt(hero.get("manaPointLimit").getValue().toString());
        handCardLimit = Integer.parseInt(hero.get("handSizeLimit").getValue().toString());
        heroName = hero.get("heroName").getValue().toString();
        heroType = Integer.parseInt(hero.get("heroAttribute").getValue().toString());
        NSObject[] fileSkills = ((NSArray) hero.get("heroSkills")).array();
        skills = new int[fileSkills.length];
        for (int i = 0; i < skills.length; i++) {
            NSDictionary fileSkill = (NSDictionary) fileSkills[i];
            skills[i] = Integer.parseInt(fileSkill.getValue().firstKey());
        }
    }
    
    
    /** only for test */
    // TODO comment out to go production
    public static void main(String... args) throws Exception {
    
        NSArray heroArray = (NSArray) PropertyListParser.parse(new File(
                "doc/HeroCardArray.xml"));
        NSDictionary hero = (NSDictionary) heroArray.array()[2];
        System.out.println("heroName: " + hero.get("heroName").getValue().toString());
        System.out.println("heroType: " + hero.get("heroAttribute").getValue().toString());
        System.out.println("hpLimit: " + hero.get("healthPointLimit").getValue().toString());
        System.out.println("spLimit: " + hero.get("manaPointLimit").getValue().toString());
        System.out.println("handCardLimit: "
                + hero.get("handSizeLimit").getValue().toString());
        NSObject[] fileSkills = ((NSArray) hero.get("heroSkills")).array();
        for (NSObject fskill : fileSkills) {
            NSDictionary fileSkill = (NSDictionary) fskill;
            System.out.println("skill: " + fileSkill.getValue().firstKey());
        }
        
    }
    
    
    /**
     * 
     * @param fileName
     *            player's heroName, skills are read from configured file
     */
    private Player(String playerName, int order, String fileName) {
    
    }
    
    
    private Player(String playerName, String fileName) {
    
    }
    
    
    public List<Integer> getHandCards() {
    
        return handCards;
    }
    
    
    public int[] getHandCardsArray() {
    
        int[] result = new int[handCards.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = handCards.get(i);
        }
        
        return result;
    }
    
    
    public void setHandCards(List<Integer> handCards) {
    
        this.handCards = handCards;
    }
    
    
    // dealing hand cards
    public List<Integer> addHandCard(int card) {
    
        return handCards;
    }
    
    
    public List<Integer> addHandCards(int[] cards) {
    
        if (cards != null) {
            for (int card : cards) {
                this.handCards.add(card);
            }
        }
        
        return handCards;
    }
    
    
    public List<Integer> removeCard(int card) {
    
        this.handCards.remove(card);
        return handCards;
    }
    
    
    public List<Integer> removeCards(int[] cards) {
    
        if (cards != null) {
            for (int card : cards) {
                if (this.handCards.contains(cards)) {
                    this.handCards.remove(card);
                }
            }
        }
        return handCards;
    }
    
    
    public int getHp() {
    
        return hp;
    }
    
    
    public int setHp(int hp) {
    
        this.hp = hp;
        return this.hp;
    }
    
    
    public int hpUp(int up) {
    
        this.hp += up;
        if (hp > hpLimit) {
            hp = hpLimit;
        }
        
        return this.hp;
    }
    
    
    public int hpDrop(int drop) {
    
        this.hp -= drop;
        if (hp < 0) {
            hp = 0;
        }
        return this.hp;
    }
    
    
    public int getSp() {
    
        return sp;
    }
    
    
    public int spUp(int up) {
    
        this.sp += up;
        if (sp > spLimit) {
            sp = spLimit;
        }
        return this.sp;
    }
    
    
    public int spDrop(int drop) {
    
        this.sp -= drop;
        if (sp < 0) {
            sp = 0;
        }
        return sp;
    }
    
    
    public void setSp(int sp) {
    
        if (sp >= spLimit) {
        
        return; }
        this.sp = sp;
    }
    
    
    public int[] getWeapons() {
    
        return weapons;
    }
    
    
    public void setWeapons(int[] weapons) {
    
        this.weapons = weapons;
    }
    
    
    public String getPlayerName() {
    
        return playerName;
    }
    
    
    public String getHeroName() {
    
        return heroName;
    }
    
    
    public int getHeroId() {
    
        return heroId;
    }
    
    
    public int getHeroType() {
    
        return heroType;
    }
    
    
    public int getHpLimit() {
    
        return hpLimit;
    }
    
    
    public int getSpLimit() {
    
        return spLimit;
    }
    
    
    public int getHandCardLimit() {
    
        return handCardLimit;
    }
    
    
    public int[] getSkills() {
    
        return skills;
    }
    
    
}