package com.wolf.dotah.server.cmpnt.cardandskill;


/**
 * 以下都属于这个类别
 * 基本牌       B
 * 魔法牌       M
 * S技能牌    S
 * 驱散
 * 装备牌       E
 * 武器           W
 * 双手武器   D
 * 
 * @author Solomon
 */
public class Card implements card_const {
    
    // basic fields
    int id;
    String name;
    String suits;
    char type;
    int faceNumber;
    
    // gen basic fiedlds
    int typeCode;
    int suitsCode;
    String color;
    int colorCode;
    
    
    // type specific fields
    boolean distanceLimited;
    boolean dispellable;
    boolean equipable;
    
    int distance;
    
    int function;
    boolean enhancable;
    int enhancedFunction;
    
    
    public Card genInfo() {
    
        genTypeCode();
        genSuitsCode();
        genColor();
        genColorCode();
        
        switch (this.type) {
            case card_type.e: {
                genEinfo();
                break;
            }
            case card_type.w: {
                genWinfo();
                break;
            }
            case card_type.d: {
                genDinfo();
                break;
            }
            case card_type.b: {
                genBinfo();
                break;
            }
            case card_type.s: {
                genSinfo();
                break;
            }
            case card_type.m: {
                genMinfo();
                break;
            }
        }
        
        return this;
    }
    
    
    private void genMinfo() {
    
        this.dispellable = true;
        
        if (name.equals(func_name.Fanaticism)) {
            this.function = functioncon.m_Fanaticism;
        } else if (name.equals(func_name.Mislead)) {
            this.function = functioncon.m_Mislead;
        } else if (name.equals(func_name.Chakra)) {
            this.function = functioncon.m_Chakra;
        } else if (name.equals(func_name.Dispel)) {
            this.function = functioncon.m_Dispel;
        }
        
        // enhancable magics 
        else if (name.equals(func_name.Disarm)) {
            this.function = functioncon.m_Disarm;
            this.enhancedFunction = functioncon.m_enhanced_Disarm;
        } else if (name.equals(func_name.ElunesArrow)) {
            this.function = functioncon.m_ElunesArrow;
            this.enhancedFunction = functioncon.m_enhanced_ElunesArrow;
        } else if (name.equals(func_name.EnergyTransport)) {
            this.function = functioncon.m_EnergyTransport;
            this.enhancedFunction = functioncon.m_enhanced_EnergyTransport;
        } else if (name.equals(func_name.Greed)) {
            this.function = functioncon.m_Greed;
            this.enhancedFunction = functioncon.m_enhanced_Greed;
        }
    }
    
    
    private void genSinfo() {
    
        if (name.equals(func_name.laguna_blade)) {
            this.function = functioncon.s_LagunaBlade;
        } else if (name.equals(func_name.god_strength)) {
            this.function = functioncon.s_GodsStrength;
        } else if (name.equals(func_name.viper_raid)) {
            this.function = functioncon.s_viper_raid;
        }
    }
    
    
    private void genBinfo() {
    
        if (id >= 0 && id < 9) {
            this.function = functioncon.b_normal_attack;
        } else if (id > 8 && id < 16) {
            this.function = functioncon.b_chaos_attack;
        } else if (id > 39 && id < 46) {
            this.function = functioncon.b_flame_attack;
        }
        
        
        //        if (name.equals(func_name.normal_attack)) {
        //            this.function = functioncon.b_normal_attack;
        //        } else if (name.equals(func_name.chaos_attack)) {
        //            this.function = functioncon.b_chaos_attack;
        //        } else if (name.equals(func_name.flame_attack)) {
        //            this.function = functioncon.b_flame_attack;
        //        } else if (name.equals(func_name.heal)) {
        //            this.function = functioncon.b_heal;
        //        } else if (name.equals(func_name.evasion)) {
        //            this.function = functioncon.b_evasion;
        //        }
    }
    
    
    private void genDinfo() {
    
        this.equipable = true;
    }
    
    
    private void genWinfo() {
    
        this.equipable = true;
    }
    
    
    private void genEinfo() {
    
        this.equipable = true;
    }
    
    
    private void genTypeCode() {
    
        switch (this.type) {
            case card_type.e: {
                this.typeCode = card_type.eCode;
                break;
            }
            case card_type.w: {
                this.typeCode = card_type.wCode;
                break;
            }
            case card_type.b: {
                this.typeCode = card_type.bCode;
                break;
            }
            case card_type.s: {
                this.typeCode = card_type.sCode;
                break;
            }
            case card_type.m: {
                this.typeCode = card_type.mCode;
                break;
            }
            case card_type.d: {
                this.typeCode = card_type.dCode;
                break;
            }
        }
        
    }
    
    
    private void genSuitsCode() {
    
        if (suits.equals(suitscon.club)) {
            suitsCode = suitscon.club_code;
        } else if (suits.equals(suitscon.spade)) {
            suitsCode = suitscon.spade_code;
        } else if (suits.equals(suitscon.diamond)) {
            suitsCode = suitscon.diamond_code;
        } else if (suits.equals(suitscon.heart)) {
            suitsCode = suitscon.heart_code;
        }
        
    }
    
    
    private void genColor() {
    
        switch (this.suitsCode) {
            case suitscon.heart_code:
            case suitscon.diamond_code: {
                this.color = colorcon.red;
                break;
            }
            case suitscon.spade_code:
            case suitscon.club_code: {
                this.color = colorcon.black;
                break;
            }
        }
    }
    
    
    private void genColorCode() {
    
        switch (this.suitsCode) {
            case suitscon.heart_code:
            case suitscon.diamond_code: {
                this.colorCode = colorcon.red_code;
                break;
            }
            case suitscon.spade_code:
            case suitscon.club_code: {
                this.colorCode = colorcon.black_code;
                break;
            }
        }
    }
    
    
    public Card(int id, String name, String suits, char type, int faceNumber) {
    
        super();
        this.id = id;
        this.name = name;
        this.suits = suits;
        this.type = type;
        this.faceNumber = faceNumber;
    }
    
    
    public Card(int id, String name, String suits, char type, int faceNumber, boolean distanceLimited, boolean dispellable, int distance,
        boolean enhancable) {
    
        super();
        this.id = id;
        this.name = name;
        this.suits = suits;
        this.type = type;
        this.faceNumber = faceNumber;
        this.distanceLimited = distanceLimited;
        this.dispellable = dispellable;
        this.distance = distance;
        this.enhancable = enhancable;
    }
    
    
    public Card(int id, String name, String suits, int suitsCode, String color, int colorCode, char type, int typeCode, int faceNumber,
        boolean distanceLimited, boolean dispellable, boolean equipable, int distance, int function, boolean enhancable,
        int enhancedFunction) {
    
        super();
        this.id = id;
        this.name = name;
        this.suits = suits;
        this.suitsCode = suitsCode;
        this.color = color;
        this.colorCode = colorCode;
        this.type = type;
        this.typeCode = typeCode;
        this.faceNumber = faceNumber;
        this.distanceLimited = distanceLimited;
        this.dispellable = dispellable;
        this.equipable = equipable;
        this.distance = distance;
        this.function = function;
        this.enhancable = enhancable;
        this.enhancedFunction = enhancedFunction;
    }
    
    
    @Override
    public String toString() {
    
        return "Card [id=" + id + ", name=" + name + ", suits=" + suits + ", suitsCode=" + suitsCode + ", color=" + color + ", colorCode="
            + colorCode + ", type=" + type + ", typeCode=" + typeCode + ", faceNumber=" + faceNumber + ", distanceLimited="
            + distanceLimited + ", dispellable=" + dispellable + ", equipable=" + equipable + ", distance=" + distance + ", function="
            + function + ", enhancable=" + enhancable + ", enhancedFunction=" + enhancedFunction + "]";
    }
    
    
    public int getId() {
    
        return id;
    }
    
    
    public String getName() {
    
        return name;
    }
    
    
    public String getSuits() {
    
        return suits;
    }
    
    
    public int getSuitsCode() {
    
        return suitsCode;
    }
    
    
    public String getColor() {
    
        return color;
    }
    
    
    public int getColorCode() {
    
        return colorCode;
    }
    
    
    public char getType() {
    
        return type;
    }
    
    
    public int getTypeCode() {
    
        return typeCode;
    }
    
    
    public int getFaceNumber() {
    
        return faceNumber;
    }
    
    
    public boolean isDistanceLimited() {
    
        return distanceLimited;
    }
    
    
    public boolean isDispellable() {
    
        return dispellable;
    }
    
    
    public boolean isEquipable() {
    
        return equipable;
    }
    
    
    public int getDistance() {
    
        return distance;
    }
    
    
    public int getFunction() {
    
        return function;
    }
    
    
    public boolean isEnhancable() {
    
        return enhancable;
    }
    
    
    public int getEnhancedFunction() {
    
        return enhancedFunction;
    }
    
    
    public void setId(int id) {
    
        this.id = id;
    }
    
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    
    public void setSuits(String suits) {
    
        this.suits = suits;
    }
    
    
    public void setSuitsCode(int suitsCode) {
    
        this.suitsCode = suitsCode;
    }
    
    
    public void setColor(String color) {
    
        this.color = color;
    }
    
    
    public void setColorCode(int colorCode) {
    
        this.colorCode = colorCode;
    }
    
    
    public void setType(char type) {
    
        this.type = type;
    }
    
    
    public void setTypeCode(int typeCode) {
    
        this.typeCode = typeCode;
    }
    
    
    public void setFaceNumber(int faceNumber) {
    
        this.faceNumber = faceNumber;
    }
    
    
    public void setDistanceLimited(boolean distanceLimited) {
    
        this.distanceLimited = distanceLimited;
    }
    
    
    public void setDispellable(boolean dispellable) {
    
        this.dispellable = dispellable;
    }
    
    
    public void setEquipable(boolean equipable) {
    
        this.equipable = equipable;
    }
    
    
    public void setDistance(int distance) {
    
        this.distance = distance;
    }
    
    
    public void setFunction(int function) {
    
        this.function = function;
    }
    
    
    public void setEnhancable(boolean enhancable) {
    
        this.enhancable = enhancable;
    }
    
    
    public void setEnhancedFunction(int enhancedFunction) {
    
        this.enhancedFunction = enhancedFunction;
    }
    
    
    public int getFunctionId() {
    
        return 0;
    }
}
