package com.wolf.dotah.server.cmpnt.card;


public interface card_const {
    
    final int card_code_base = 10000;
    
    
    public interface typecon {
        
        final char e = 'e';// none weapon equipment
        final char w = 'w';// weapon, also equipment
        final char d = 'd';// dual hand weapon, also equipment
        final char m = 'm';// magic, no distance limited
        final char b = 'b';// basic, attacks, heal, and evasion
        final char s = 's';// super skills
        
        
        final int bCode = card_code_base + 100;
        final int mCode = card_code_base + 200;
        final int sCode = card_code_base + 300;
        final int eCode = card_code_base + 400;
        final int wCode = card_code_base + eCode + 10;
        final int dCode = card_code_base + eCode + 20;
        
    }
    
    public interface colorcon {
        
        final String red = "red";
        final String black = "black";
        
        final int red_code = card_code_base + 50;
        final int black_code = card_code_base + 60;
    }
    
    public interface suitscon {
        
        final String heart = "红桃";
        final String diamond = "方块";
        final String spade = "黑桃";
        final String club = "梅花";
        
        final int heart_code = card_code_base + 10;
        final int diamond_code = card_code_base + 20;
        final int spade_code = card_code_base + 30;
        final int club_code = card_code_base + 40;
    }
    
    public interface namecon {
        
        String normal_attack = "普通攻击";
        String flame_attack = "火焰攻击";
        String chaos_attack = "混乱攻击";
        String heal = "治疗药膏";
        String evasion = "闪避";
        
        String god_strength = "神之力量";
        String viper_raid = "蝮蛇突袭";
        String laguna_blade = "神灭斩";
        
        String Fanaticism = "狂热";
        String Mislead = "误导";
        String Chakra = "查克拉";
        String Dispel = "驱散";
        String Disarm = "缴械";
        String ElunesArrow = "月神之剑";
        String EnergyTransport = "能量转移";
        String Greed = "贪婪";
    }
    
}
