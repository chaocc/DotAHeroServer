package com.wolf.dotah.server.cmpnt.player;


public interface hero_const {
    
    //  kHeroAttributeStrength = 1,             // 力量型
    //  kHeroAttributeAgility,                  // 敏捷型
    //  kHeroAttributeIntelligence              // 智力型
    //  kHeroSkillCategoryActive = 0,           // 主动技能
    //  kHeroSkillCategoryPassive,              // 被动技能
    //  kHeroSkillTypeGeneral = 0,              // 普通技
    //  kHeroSkillTypeRestricted,               // 限制技
    //  kHeroSkillTypeLimited                   // 限定技
    public interface typecon {
        
        int strengthCode = 1;
        int agilityCode = 2;
        int intelligenceCode = 3;
        
        String strength = "力量";
        String agility = "敏捷";
        String intelligence = "智力";
        
    }
    
    public interface skill {
        
        public interface categorycon {
            
            int activeCode = 0;
            int passiveCode = 1;
            String active = "主动技";
            String passive = "被动技";
        }
        
        public interface typecon {
            
            int generalCode = 0;
            int limitedCode = 1;
            String general = "普通技";
            String limited = "限制技";
        }
    }
}
