package com.wolf.dotah.server.cmpnt.player;


import java.util.ArrayList;
import java.util.List;


public class HeroSkills {
    
    List<Skill> skills;
    
    
    public HeroSkills() {
    
        skills = new ArrayList<Skill>();
    }
    
    
    @Override
    public String toString() {
    
        return "Skills [skills=" + skills + "]";
    }
    
    
    public List<Skill> getSkills() {
    
        return skills;
    }
    
    
    public void setSkills(List<Skill> skills) {
    
        this.skills = skills;
    }
    
    
//    public void addSkillBySkillId(int i) {
//    
//        Skill skill = new Skill(i);
//        skill.genInfo();
//        skills.add(skill);
//        
//    }
    
    
    public void addSkill(Skill skill) {
    
        skills.add(skill);
    }
    
    
    public class Skill {
        
        int functionId;
        int heroSkillId;
        
        int skillCategory;
        int skillType;
        String name;//skillText
        boolean isMandatorySkill;
        boolean canBeDispelled;
        
        
        public Skill(int skillId) {
        
            this.heroSkillId = skillId;
        }
        
        
        public void genInfo() {
        
            //TODO get function id from skill id
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
        
        
        public int getSkillCategory() {
        
            return skillCategory;
        }
        
        
        public void setSkillCategory(int skillCategory) {
        
            this.skillCategory = skillCategory;
        }
        
        
        public int getSkillType() {
        
            return skillType;
        }
        
        
        public void setSkillType(int skillType) {
        
            this.skillType = skillType;
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
    
    
}
