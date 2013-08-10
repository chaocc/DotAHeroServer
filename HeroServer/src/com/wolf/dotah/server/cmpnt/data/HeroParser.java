package com.wolf.dotah.server.cmpnt.data;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.plist.NSArray;
import net.sf.plist.NSDictionary;
import net.sf.plist.NSInteger;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListParser;

import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.HeroSkills;
import com.wolf.dotah.server.cmpnt.player.HeroSkills.Skill;


public class HeroParser {
    
    private final String path = "doc/HeroCardArray.plist";
    private static HeroParser parser;
    
    
    public static HeroParser getParser() {
    
        if (parser == null) {
            parser = new HeroParser();
        }
        return parser;
    }
    
    
    public List<HeroInfo> getHeroInfoList() {
    
        List<HeroInfo> heroInfoList = new ArrayList<HeroInfo>();
        try {
            NSArray heroArray = (NSArray) PropertyListParser.parse(new File(path));
            NSObject[] heros = heroArray.array();
            for (int i = 0; i < heros.length; i++) {
                NSDictionary hero = (NSDictionary) heros[i];
                HeroInfo heroInfo = genHeroInfoFromNSDictWithId(hero, i);
                heroInfoList.add(heroInfo);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return heroInfoList;
    }
    
    
    private HeroInfo genHeroInfoFromNSDictWithId(NSDictionary hero, int id) {
    
        int hpLimit = Integer.parseInt(hero.get("bloodPointLimit").getValue().toString());
        int spLimit = Integer.parseInt(hero.get("angerPointLimit").getValue().toString());
        int handCardLimit = Integer.parseInt(hero.get("handSizeLimit").getValue().toString());
        String heroName = hero.get("heroName").getValue().toString();
        int heroType = Integer.parseInt(hero.get("heroAttribute").getValue().toString());
        NSObject[] skillIdArray = ((NSArray) hero.get("heroSkills")).array();
        
        HeroSkills heroSkills = new HeroSkills();
        for (int i = 0; i < skillIdArray.length; i++) {
            NSInteger skillId = (NSInteger) skillIdArray[i];
            Skill heroSkill = heroSkills.new Skill(skillId.toInteger());
            
            heroSkills.addSkill(heroSkill);
        }
        
        HeroInfo heroInfo = new HeroInfo(id, heroName, hpLimit, spLimit, handCardLimit, heroType, heroSkills);
        
        return heroInfo;
    }
    
    
    private HeroParser() {
    
    }
    
    
    public static void main(String... args) {
    
        for (HeroInfo hero : HeroParser.getParser().getHeroInfoList()) {
            System.out.println(hero);
        }
    }
}
