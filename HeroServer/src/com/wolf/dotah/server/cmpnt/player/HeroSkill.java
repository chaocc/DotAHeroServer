package com.wolf.dotah.server.cmpnt.player;


import com.wolf.dotah.server.cmpnt.function.function_const;


public class HeroSkill implements function_const, hero_const {
    
    
    int functionId;
    int heroSkillId;
    
    int skillCategoryCode;
    String skillCategory;
    int skillTypeCode;
    String skillType;
    String name;// skillText
    boolean isMandatorySkill;
    boolean canBeDispelled;
    
    
    public HeroSkill(int heroSkillId, int category, int type, String name, boolean mandatory, boolean canDispell) {
    
        super();
        this.heroSkillId = heroSkillId;
        this.skillCategoryCode = category;
        this.skillTypeCode = type;
        this.name = name;
        this.isMandatorySkill = mandatory;
        this.canBeDispelled = canDispell;
    }
    
    
    public HeroSkill(int skillId) {
    
        this.heroSkillId = skillId;
    }
    
    
    public HeroSkill genInfo() {
    
        switch (skillTypeCode) {
            case skill.typecon.generalCode: {
                this.skillType = skill.typecon.general;
                break;
            }
            case skill.typecon.limitedCode: {
                this.skillType = skill.typecon.limited;
                break;
            }
        }
        
        switch (skillCategoryCode) {
            case skill.categorycon.activeCode: {
                this.skillCategory = skill.categorycon.active;
                break;
            }
            case skill.categorycon.passiveCode: {
                this.skillCategory = skill.categorycon.passive;
                break;
            }
        }
        return this;
        //TODO get function id from skill id
    }
    
    
    @Override
    public String toString() {
    
        return "HeroSkill [functionId=" + functionId + ", heroSkillId=" + heroSkillId + ", skillCategoryCode=" + skillCategoryCode
                + ", skillCategory=" + skillCategory + ", skillTypeCode=" + skillTypeCode + ", skillType=" + skillType + ", name=" + name
                + ", isMandatorySkill=" + isMandatorySkill + ", canBeDispelled=" + canBeDispelled + "]";
    }


    public int getFunctionId() {
    
        return functionId;
    }
    
    
    public void setFunctionId(int functionId) {
    
        this.functionId = functionId;
    }
    
    
    public int getHeroSkillId() {
    
        return heroSkillId;
    }
    
    
    public void setHeroSkillId(int heroSkillId) {
    
        this.heroSkillId = heroSkillId;
    }
    
    
    public String getName() {
    
        return name;
    }
    
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    
    public boolean isMandatorySkill() {
    
        return isMandatorySkill;
    }
    
    
    public void setMandatorySkill(boolean isMandatorySkill) {
    
        this.isMandatorySkill = isMandatorySkill;
    }
    
    
    public boolean isCanBeDispelled() {
    
        return canBeDispelled;
    }
    
    
    public void setCanBeDispelled(boolean canBeDispelled) {
    
        this.canBeDispelled = canBeDispelled;
    }
    
}
