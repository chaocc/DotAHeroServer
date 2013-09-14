package com.wolf.dotah.server.layer.dao;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.plist.NSArray;
import net.sf.plist.NSDictionary;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListException;
import net.sf.plist.io.PropertyListParser;

import com.wolf.dotah.server.cmpnt.cardandskill.HeroSkill;


public class SkillParser {
    
    final String path = "doc/HeroSkillArray.plist";
    private static SkillParser parser;
    
    
    public static SkillParser getParser() {
    
        if (parser == null) {
            parser = new SkillParser();
        }
        return parser;
    }
    
    
    public List<HeroSkill> getSkillList() {
    
        List<HeroSkill> heroSkillList = new ArrayList<HeroSkill>();
        NSObject[] skillArray = getNSSkillArray();
        for (int i = 0; i < skillArray.length; i++) {
            HeroSkill skill = getSkillById(i);
            heroSkillList.add(skill);
        }
        return heroSkillList;
    }
    
    
    public HeroSkill getSkillById(int id) {
    
        NSDictionary skillDict = (NSDictionary) getNSSkillArray()[id];
        HeroSkill skill = genSkillFromSkillDictWithId(skillDict, id);
        return skill;
    }
    
    
    private HeroSkill genSkillFromSkillDictWithId(NSDictionary skillDict, int id) {
    
        int category = skillDict.get("skillCategory").toInteger();
        int type = skillDict.get("skillType").toInteger();
        String name = skillDict.get("skillText").toString();
        boolean mandatory = skillDict.get("isMandatorySkill").toBoolean();
        boolean canDispell = skillDict.get("canBeDispelled").toBoolean();
        
        HeroSkill heroSkill = new HeroSkill(id, category, type, name, mandatory, canDispell);
        return heroSkill.genInfo();
    }
    
    
    private NSObject[] getNSSkillArray() {
    
        try {
            NSArray skillArray = (NSArray) PropertyListParser.parse(new File(path));
            NSObject[] skills = skillArray.array();
            return skills;
        } catch (PropertyListException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private SkillParser() {
    
    }
    
    
    public static void main(String... args) {
    
        for (HeroSkill skill : SkillParser.getParser().getSkillList()) {
            System.out.println(skill);
        }
    }
    
}
