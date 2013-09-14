package com.wolf.dotah.server.cmpnt.player;


import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.cardandskill.HeroSkill;


public class HeroSkills {
    
    List<HeroSkill> skills;
    
    
    public HeroSkills() {
    
        skills = new ArrayList<HeroSkill>();
    }
    
    
    @Override
    public String toString() {
    
        return "Skills [skills=" + skills + "]";
    }
    
    
    public List<HeroSkill> getSkills() {
    
        return skills;
    }
    
    
    public void setSkills(List<HeroSkill> skills) {
    
        this.skills = skills;
    }
    
    
    //    public void addSkillBySkillId(int i) {
    //    
    //        Skill skill = new Skill(i);
    //        skill.genInfo();
    //        skills.add(skill);
    //        
    //    }
    
    
    public void addSkill(HeroSkill skill) {
    
        skills.add(skill);
    }
    
    
}
