package com.wolf.dotah.server.layer.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.plist.NSArray;
import net.sf.plist.NSDictionary;
import net.sf.plist.NSInteger;
import net.sf.plist.NSObject;
import net.sf.plist.io.PropertyListParser;
import com.wolf.dotah.server.cmpnt.player.HeroInfo;
import com.wolf.dotah.server.cmpnt.player.HeroSkill;
import com.wolf.dotah.server.cmpnt.player.HeroSkills;

public class HeroParser {
    
    private final String path = "doc/HeroCardArray.plist";
    private static HeroParser parser;
    //    private final String tag = "===>> HeroParser ";
    private int[] fake_data_hero_ids = { 2, 17, 21, 3, 12, 28 };
    
    public static HeroParser getParser() {
    
        if (parser == null) {
            parser = new HeroParser();
        }
        return parser;
    }
    
    //    public List<HeroInfo> getHeroInfoList() {
    //        
    //        List<HeroInfo> heroInfoList = new ArrayList<HeroInfo>();
    //        NSObject[] heros = getNSHeroArray();
    //        for (int i = 0; i < heros.length; i++) {
    //            NSDictionary hero = (NSDictionary) heros[i];
    //            HeroInfo heroInfo = genHeroInfoFromNSDictWithId(hero, i);
    //            heroInfoList.add(heroInfo);
    //        }
    //        return heroInfoList;
    //    }
    
    public HeroInfo getHeroInfoById(int id) {
    
        NSDictionary hero = (NSDictionary) getNSHeroArray()[id];
        HeroInfo heroInfo = genHeroInfoFromNSDictWithId(hero, id);
        return heroInfo;
    }
    
    public List<Integer> getHeroIdList() {
    
        List<Integer> heroIdList = new ArrayList<Integer>();
        int size = getNSHeroArray().length;
        for (int i = 0; i < size; i++) {
            heroIdList.add(i);
        }
        //        return heroIdList;
        return this.getList(fake_data_hero_ids);
    }
    
    private List<Integer> getList(int[] fake_data_hero_ids) {
    
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < fake_data_hero_ids.length; i++) {
            result.add(fake_data_hero_ids[i]);
        }
        return result;
    }
    
    private NSObject[] getNSHeroArray() {
    
        try {
            File file = new File(path);
            NSArray heroArray = (NSArray) PropertyListParser.parse(file);
            NSObject[] heros = heroArray.array();
            return heros;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //        return null;
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
            HeroSkill heroSkill = SkillParser.getParser().getSkillById(skillId.toInteger());
            heroSkills.addSkill(heroSkill);
        }
        
        HeroInfo heroInfo = new HeroInfo(id, heroName, hpLimit, spLimit, handCardLimit, heroType, heroSkills);
        return heroInfo.genInfo();
    }
    //    
    //    private HeroParser() {
    //        
    //    }
    //    
    //    public static void main(String... args) {
    //        
    //        for (HeroInfo hero : HeroParser.getParser().getHeroInfoList()) {
    //            System.out.println(hero);
    //        }
    //    }
}
