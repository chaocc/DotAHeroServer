package com.wolf.dotah.server.cmpnt.player;


public class HeroInfo implements hero_const {
    
    int id;
    String name;
    int hpLimit;
    int spLimit;
    int handcardLimit;
    String heroType;
    int heroTypeCode;
    HeroSkills skills;
    
    
    public HeroInfo(int id, String name, int hpLimit, int spLimit, int handcardLimit, int heroType, HeroSkills skills) {
    
        super();
        this.id = id;
        this.name = name;
        this.hpLimit = hpLimit;
        this.spLimit = spLimit;
        this.handcardLimit = handcardLimit;
        this.heroTypeCode = heroType;
        this.skills = skills;
    }
    
    
    public HeroInfo genInfo() {
    
        switch (heroTypeCode) {
            case typecon.agilityCode: {
                this.heroType = typecon.agility;
                break;
            }
            case typecon.intelligenceCode: {
                this.heroType = typecon.intelligence;
                break;
            }
            case typecon.strengthCode: {
                this.heroType = typecon.strength;
                break;
            }
        }
        return this;
    }
    
    
    @Override
    public String toString() {
    
        return "HeroInfo [id=" + id + ", name=" + name + ", hpLimit=" + hpLimit + ", spLimit=" + spLimit + ", handcardLimit="
                + handcardLimit + ", heroType=" + heroType + ", heroTypeCode=" + heroTypeCode + ", skills=" + skills + "]";
    }
    
    
    public int getId() {
    
        return id;
    }
    
    
    public String getName() {
    
        return name;
    }
    
    
    public int getHpLimit() {
    
        return hpLimit;
    }
    
    
    public int getSpLimit() {
    
        return spLimit;
    }
    
    
    public int getHandcardLimit() {
    
        return handcardLimit;
    }
    
    
    public HeroSkills getSkills() {
    
        return skills;
    }
    
    
    public void setId(int id) {
    
        this.id = id;
    }
    
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    
    public void setHpLimit(int hpLimit) {
    
        this.hpLimit = hpLimit;
    }
    
    
    public void setSpLimit(int spLimit) {
    
        this.spLimit = spLimit;
    }
    
    
    public void setHandcardLimit(int handcardLimit) {
    
        this.handcardLimit = handcardLimit;
    }
    
    
    public void setSkills(HeroSkills skills) {
    
        this.skills = skills;
    }
    
    
    public String getHeroType() {
    
        return heroType;
    }
    
    
    public int getHeroTypeCode() {
    
        return heroTypeCode;
    }
    
    
    public void setHeroType(String heroType) {
    
        this.heroType = heroType;
    }
    
    
    public void setHeroTypeCode(int heroTypeCode) {
    
        this.heroTypeCode = heroTypeCode;
    }
    
    
}
