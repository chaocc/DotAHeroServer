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
    
    
    private int[] weapons;
    //    private HeroSkill[] skills;
    private int[] skills;
    public static final int
            function_id_p_fierySoul = 46,// 炽魂                                  强化不消耗怒气                 //Slayer   秀逗魔导士
            function_id_p_lagunaBlade = 47,// 神灭斩                       2张红色手牌当神灭斩
            function_id_p_fanaticismHeart = 48,// 狂热之心          魔法牌使用成功后本回合攻击次数+1
            function_id_p_netherSwap = 24,//移形换位                             抽对方一张牌, 给对方一张牌, 但不能是同一张牌               //VengefulSpirit   复仇之魂
            function_id_p_waveOfTerror = 25,//恐怖波动                      每获得1点怒气, 可以摸2张牌
            function_id_p_warpath = 4,//战意            每受到一次伤害, 可进行一次判定, 若为红色, 则可指定任何一个人弃置1闪或对其造成1伤害           //Bristleback   刚被兽
            function_id_p_bristlebackSkill = 5,//刚毛后背            自己每次受到伤害>1的话, 则它-1
            function_id_p_lifeBreak = 6,//牺牲                                      对自己造成1点伤害, 弃置指定角色2张手牌              //SacredWarrior    神灵武士
            function_id_p_burningSpear = 7,//沸血长矛,       若血量<=2,  则攻击造成的伤害+1
            function_id_p_illuminate = 61,//冲击波                            弃置3张不同花色的牌,  对指定非自己的1~2名角色造成1点伤害      //KeeperOfTheLight    光之守卫
            function_id_p_chakraMagic = 62,//查克拉                         可以将1张手牌当1张查克拉使用
            function_id_p_grace = 63,//恩惠                                             这个角色的查克拉可以对任一名角色使用
            function_id_p_manaBreak = 37,//法力损毁                             攻击成功后弃置对方一张手牌         //Antimage    敌法师
            function_id_p_blink = 38,//闪烁                                                黑色手牌当闪
            function_id_p_manaVoid = 39//法力虚空                                   3怒气,  造成X = 手牌上限-手牌数         点伤害
            
            
            ;
    
    
    class HeroSkill {
        
        private int id;
        private String name;
        private int skillType;
        private boolean isMandatorySkill;
        private boolean canBeDispelled;
        private int[] whenToUse;
        
        
        public HeroSkill(int id, String name, int skillType, boolean isMandatorySkill, boolean canBeDispelled, int[] whenToUse) {
        
            this.id = id;
            this.name = name;
            this.skillType = skillType;
            this.isMandatorySkill = isMandatorySkill;
            this.canBeDispelled = canBeDispelled;
            this.whenToUse = whenToUse;
        }
        
        
        public int getId() {
        
            return id;
        }
        
        
        public String getName() {
        
            return name;
        }
        
        
        public int getSkillType() {
        
            return skillType;
        }
        
        
        public boolean isMandatorySkill() {
        
            return isMandatorySkill;
        }
        
        
        public boolean isCanBeDispelled() {
        
            return canBeDispelled;
        }
        
        
        public int[] getWhenToUse() {
        
            return whenToUse;
        }
    }
    
    
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
        //        skills = new HeroSkill[fileSkills.length];
        skills = new int[fileSkills.length];
        for (int i = 0; i < skills.length; i++) {
            NSObject fileSkill = fileSkills[i];
            skills[i] = fileSkill.toInteger();
        }
    }
    
    
    /** only for test */
    // TODO comment out to go production
    public static void main(String... args) throws Exception {
    
        NSArray heroArray = (NSArray) PropertyListParser.parse(new File("doc/HeroCardArray.plist"));
        NSDictionary hero = (NSDictionary) heroArray.array()[0];
        System.out.println("heroName: " + hero.get("heroName").getValue().toString());
        System.out.println("heroType: " + hero.get("heroAttribute").getValue().toString());
        System.out.println("hpLimit: " + hero.get("healthPointLimit").getValue().toString());
        System.out.println("spLimit: " + hero.get("manaPointLimit").getValue().toString());
        System.out.println("handCardLimit: " + hero.get("handSizeLimit").getValue().toString());
        NSObject[] fileSkills = ((NSArray) hero.get("heroSkills")).array();
        for (NSObject fskill : fileSkills) {
            NSObject fileSkill = fskill;
            //            System.out.println("skill: " + fileSkill.getValue().firstKey());
            System.out.println(fileSkill);
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
    
    
    public void setHandCardArray(int[] handCardArray) {
    
        if (this.handCards == null) {
            this.handCards = new ArrayList<Integer>();
        }
        for (int i = 0; i < handCardArray.length; i++) {
            handCards.add(handCardArray[i]);
        }
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
    
    
}