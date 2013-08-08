package com.wolf.dota.component;


import com.wolf.dota.component.constants.Params;


public enum CardModel implements Params {
    _0(0, "普通攻击", CardModel.function_id_normal_attack, "黑桃A", 1, 1 + CardModel.suit_spade),
    _1(1, "普通攻击", CardModel.function_id_normal_attack, "黑桃2", 2, 2 + CardModel.suit_spade),
    _2(2, "普通攻击", CardModel.function_id_normal_attack, "黑桃3", 3, 3 + CardModel.suit_spade),
    _3(3, "普通攻击", CardModel.function_id_normal_attack, "黑桃4", 4, 4 + CardModel.suit_spade),
    _4(4, "普通攻击", CardModel.function_id_normal_attack, "黑桃5", 5, 5 + CardModel.suit_spade),
    _5(5, "普通攻击", CardModel.function_id_normal_attack, "黑桃6", 6, 6 + CardModel.suit_spade),
    _6(6, "普通攻击", CardModel.function_id_normal_attack, "黑桃7", 7, 7 + CardModel.suit_spade),
    _7(7, "普通攻击", CardModel.function_id_normal_attack, "黑桃8", 8, 8 + CardModel.suit_spade),
    _8(8, "普通攻击", CardModel.function_id_normal_attack, "黑桃9", 9, 9 + CardModel.suit_spade),
    
    _9(9, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃10", 10, 10 + CardModel.suit_spade),
    _10(10, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃A", 1, 1 + CardModel.suit_spade),
    _11(11, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃2", 2, 2 + CardModel.suit_spade),
    _12(12, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃3", 3, 3 + CardModel.suit_spade),
    _13(13, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃4", 4, 4 + CardModel.suit_spade),
    _14(14, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃5", 5, 5 + CardModel.suit_spade),
    _15(15, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃6", 6, 6 + CardModel.suit_spade),
    
    _16(16, "神灭斩_S", CardModel.function_id_s_LagunaBlade, "黑桃7", 7, 7 + CardModel.suit_spade),
    _17(17, "神灭斩_S", CardModel.function_id_s_LagunaBlade, "黑桃8", 8, 8 + CardModel.suit_spade),
    _18(18, "蝮蛇突袭_S", CardModel.function_id_s_viper_raid, "黑桃9", 9, 9 + CardModel.suit_spade),
    _19(19, "蝮蛇突袭_S", CardModel.function_id_s_viper_raid, "黑桃10", 10, 10 + CardModel.suit_spade),
    
    _20(20, "误导_M_1", CardModel.function_id_m_Mislead, "梅花A", 1, 1 + CardModel.suit_club),
    _21(21, "误导_M", CardModel.function_id_m_Mislead, "梅花2", 2, 2 + CardModel.suit_club),
    _22(22, "贪婪_M_2", CardModel.function_id_m_Greed, "梅花3", 3, 3 + CardModel.suit_club),
    _23(23, "贪婪_M", CardModel.function_id_m_Greed, "梅花4", 4, 4 + CardModel.suit_club),
    _24(24, "缴械_M_3", CardModel.function_id_m_Disarm, "梅花5", 5, 5 + CardModel.suit_club),
    _25(25, "缴械_M", CardModel.function_id_m_Disarm, "梅花6", 6, 6 + CardModel.suit_club),
    _26(26, "缴械_M", CardModel.function_id_m_Disarm, "梅花7", 7, 7 + CardModel.suit_club),
    _27(27, "缴械_M", CardModel.function_id_m_Disarm, "梅花8", 8, 8 + CardModel.suit_club),
    _28(28, "缴械_M", CardModel.function_id_m_Disarm, "梅花9", 9, 9 + CardModel.suit_club),
    _29(29, "查克拉_M_4", CardModel.function_id_m_Chakra, "梅花10", 10, 10 + CardModel.suit_club),
    _30(30, "查克拉_M_4", CardModel.function_id_m_Chakra, "梅花A", 1, 1 + CardModel.suit_club),
    _31(31, "能量转移_M_5", CardModel.function_id_m_EnergyTransport, "梅花2", 2, 2 + CardModel.suit_club),
    _32(32, "能量转移_M", CardModel.function_id_m_EnergyTransport, "梅花3", 3, 3 + CardModel.suit_club),
    _33(33, "月神之箭_M_6", CardModel.function_id_m_ElunesArrow, "梅花4", 4, 4 + CardModel.suit_club),
    _34(34, "月神之箭_M_6", CardModel.function_id_m_ElunesArrow, "梅花5", 5, 5 + CardModel.suit_club),
    _35(35, "狂热_M_7", CardModel.function_id_m_Fanaticism, "梅花6", 6, 6 + CardModel.suit_club),
    _36(36, "狂热_M_7", CardModel.function_id_m_Fanaticism, "梅花7", 7, 7 + CardModel.suit_club),
    
    _37(37, "速度之靴_W", CardModel.function_id_e_BootsOfSpeed, "梅花8", 8, 8 + CardModel.suit_club),
    _38(38, "菲丽丝之戒_W", CardModel.function_id_e_PhyllisRing, "梅花9", 9, 9 + CardModel.suit_club),
    _39(39, "闪避护符_W", CardModel.function_id_e_TalismanOfEvasion, "梅花10", 10, 10 + CardModel.suit_club),
    
    _40(40, "火焰攻击", CardModel.function_id_flame_attack, "红桃A", 1, 1 + CardModel.suit_heart),
    _41(41, "火焰攻击", CardModel.function_id_flame_attack, "红桃2", 2, 2 + CardModel.suit_heart),
    _42(42, "火焰攻击", CardModel.function_id_flame_attack, "红桃3", 3, 3 + CardModel.suit_heart),
    _43(43, "火焰攻击", CardModel.function_id_flame_attack, "红桃4", 4, 4 + CardModel.suit_heart),
    _44(44, "火焰攻击", CardModel.function_id_flame_attack, "红桃5", 5, 5 + CardModel.suit_heart),
    _45(45, "火焰攻击", CardModel.function_id_flame_attack, "红桃6", 6, 6 + CardModel.suit_heart),
    _46(46, "治疗药膏", CardModel.function_id_heal, "红桃7", 7, 7 + CardModel.suit_heart),
    _47(47, "治疗药膏", CardModel.function_id_heal, "红桃8", 8, 8 + CardModel.suit_heart),
    _48(48, "治疗药膏", CardModel.function_id_heal, "红桃9", 9, 9 + CardModel.suit_heart),
    _49(49, "治疗药膏", CardModel.function_id_heal, "红桃10", 10, 10 + CardModel.suit_heart),
    
    _50(50, "驱散_M_8", CardModel.function_id_m_Dispel, "红桃A", 1, 1 + CardModel.suit_heart),
    _51(51, "驱散_M_8", CardModel.function_id_m_Dispel, "红桃2", 2, 2 + CardModel.suit_heart),
    _52(52, "驱散_M_8", CardModel.function_id_m_Dispel, "红桃3", 3, 3 + CardModel.suit_heart),
    _53(53, "驱散_M_8", CardModel.function_id_m_Dispel, "红桃4", 4, 4 + CardModel.suit_heart),
    _54(54, "驱散_M_8", CardModel.function_id_m_Dispel, "红桃5", 5, 5 + CardModel.suit_heart),
    
    _55(55, "治疗药膏", CardModel.function_id_heal, "红桃6", 6, 6 + CardModel.suit_heart),
    _56(56, "治疗药膏", CardModel.function_id_heal, "红桃7", 7, 7 + CardModel.suit_heart),
    
    _57(57, "流浪法师斗篷", CardModel.function_id_e_PlaneswalkersCloak, "红桃8", 8, 8 + CardModel.suit_heart),
    
    _58(58, "神之力量S", CardModel.function_id_s_GodsStrength, "红桃9", 9, 9 + CardModel.suit_heart),
    _59(59, "神之力量S", CardModel.function_id_s_GodsStrength, "红桃10", 10, 10 + CardModel.suit_heart),
    
    _60(60, "闪避", CardModel.function_id_evasion, "方块A", 1, 1 + CardModel.suit_diamond),
    _61(61, "闪避", CardModel.function_id_evasion, "方块2", 2, 2 + CardModel.suit_diamond),
    _62(62, "闪避", CardModel.function_id_evasion, "方块3", 3, 3 + CardModel.suit_diamond),
    _63(63, "闪避", CardModel.function_id_evasion, "方块4", 4, 4 + CardModel.suit_diamond),
    _64(64, "闪避", CardModel.function_id_evasion, "方块5", 5, 5 + CardModel.suit_diamond),
    _65(65, "闪避", CardModel.function_id_evasion, "方块6", 6, 6 + CardModel.suit_diamond),
    _66(66, "闪避", CardModel.function_id_evasion, "方块7", 7, 7 + CardModel.suit_diamond),
    _67(67, "闪避", CardModel.function_id_evasion, "方块8", 8, 8 + CardModel.suit_diamond),
    _68(68, "闪避", CardModel.function_id_evasion, "方块9", 9, 9 + CardModel.suit_diamond),
    _69(69, "闪避", CardModel.function_id_evasion, "方块10", 10, 10 + CardModel.suit_diamond),
    
    _70(70, "散失之刃_W", CardModel.function_id_e_DiffusalBlade, "方块A", 1, 1 + CardModel.suit_diamond),
    _71(71, "洛萨之锋_W", CardModel.function_id_e_LotharsEdge, "方块2", 2, 2 + CardModel.suit_diamond),
    _72(72, "冰魄之眼_W", CardModel.function_id_e_EyeOfSkadi, "方块3", 3, 3 + CardModel.suit_diamond),
    _73(73, "黯灭之刃_W", CardModel.function_id_e_StygianDesolator, "方块4", 4, 4 + CardModel.suit_diamond),
    _74(74, "散夜对剑_W", CardModel.function_id_e_SangeAndYasha, "方块5", 5, 5 + CardModel.suit_diamond),
    _75(75, "攻击之爪_W", CardModel.function_id_e_BladesOfAttack, "方块6", 6, 6 + CardModel.suit_diamond),
    _76(76, "恶魔刀锋_W", CardModel.function_id_e_DemonEdge, "方块7", 7, 7 + CardModel.suit_diamond),
    _77(77, "圣者遗物_W", CardModel.function_id_e_SacredRelic, "方块8", 8, 8 + CardModel.suit_diamond),
    _78(78, "刃甲_W", CardModel.function_id_e_BladeMail, "方块9", 9, 9 + CardModel.suit_diamond),
    
    _79(79, "闪避", CardModel.function_id_evasion, "方块10", 10, 10 + CardModel.suit_diamond)
    
    ;
    
    // == basic ==========================================
    // == equipment ==========================================
    // == magic cards ==========================================
    public static final int suit_club = 100; // 草花
    public static final int suit_spade = 1000; // 黑桃
    public static final int suit_diamond = 10000;
    public static final int suit_heart = 100000;
    
    public static final int
            function_id_base = -100,
            function_id_normal_attack = function_id_base - 20,
            function_id_flame_attack = function_id_base - 21,
            function_id_chaos_attack = function_id_base - 22,
            function_id_heal = function_id_base - 23,
            function_id_evasion = function_id_base - 24,
            function_id_s_GodsStrength = function_id_base - 25,
            function_id_s_viper_raid = function_id_base - 26,
            kPlayingCardTimeLock = function_id_base - 27,
            kPlayingCardSunder = function_id_base - 28,
            function_id_s_LagunaBlade = function_id_base - 29,
            function_id_m_Fanaticism = function_id_base - 30,
            function_id_m_Mislead = function_id_base - 31,
            function_id_m_Chakra = function_id_base - 32,
            kPlayingCardWildAxe = function_id_base - 33,
            function_id_m_Dispel = function_id_base - 34,
            function_id_m_Disarm = function_id_base - 35,
            function_id_m_ElunesArrow = function_id_base - 36,
            function_id_m_EnergyTransport = function_id_base - 37,
            function_id_m_Greed = function_id_base - 38,
            kPlayingCardSirenSong = function_id_base - 39,
            function_id_e_EyeOfSkadi = function_id_base - 40,
            function_id_e_BladesOfAttack = function_id_base - 41,
            function_id_e_SacredRelic = function_id_base - 42,
            function_id_e_DemonEdge = function_id_base - 43,
            function_id_e_DiffusalBlade = function_id_base - 44,
            function_id_e_LotharsEdge = function_id_base - 45,
            function_id_e_StygianDesolator = function_id_base - 46,
            function_id_e_SangeAndYasha = function_id_base - 47,
            function_id_e_PlunderAxe = function_id_base - 48,
            function_id_e_MysticStaff = function_id_base - 49,
            function_id_e_Eaglehorn = function_id_base - 50,
            function_id_e_QuellingBlade = function_id_base - 51,
            function_id_e_PhyllisRing = function_id_base - 52,
            function_id_e_BladeMail = function_id_base - 53,
            function_id_e_BootsOfSpeed = function_id_base - 54,
            function_id_e_PlaneswalkersCloak = function_id_base - 55,
            function_id_e_TalismanOfEvasion = function_id_base - 56
            
            ;
    
    //    kPlayingCardEyeOfSkadi,             // 冰魄之眼
    //    kPlayingCardBladesOfAttack,         // 攻击之爪
    //    kPlayingCardSacredRelic,            // 圣者遗物
    //    kPlayingCardDemonEdge,              // 恶魔刀锋
    //    kPlayingCardDiffusalBlade,          // 散失之刃
    //    kPlayingCardLotharsEdge,            // 洛萨之锋
    //    kPlayingCardStygianDesolator,       // 黯灭之刃
    //    kPlayingCardSangeAndYasha,          // 散夜对剑
    //    kPlayingCardPlunderAxe,             // 掠夺之斧
    //    kPlayingCardMysticStaff,            // 神秘法杖
    //    kPlayingCardEaglehorn,              // 鹰角弓
    //    kPlayingCardQuellingBlade,          // 补刀斧
    //    
    //    kPlayingCardPhyllisRing,            // 菲丽丝之戒
    //    kPlayingCardBladeMail,              // 刃甲
    //    kPlayingCardBootsOfSpeed,           // 速度之靴
    //    kPlayingCardPlaneswalkersCloak,     // 流浪法师斗篷
    //    kPlayingCardTalismanOfEvasion       // 闪避护符
    
    
    private int cardId;
    private String name;
    private int functionId;
    
    private String cardFace;
    private int pokerValue;
    private int suit; // 花色
    
    
    private CardModel(int cardId, String name, int functionId, String face, int pokerValue,
            int suit) {
    
        this.cardId = cardId;
        this.name = name;
        this.functionId = functionId;
        this.cardFace = face;
        this.pokerValue = pokerValue;
        this.suit = suit;
    }
    
    
    public int getCardId() {
    
        return cardId;
    }
    
    
    public String getName() {
    
        return name;
    }
    
    
    public int getFunctionId() {
    
        return functionId;
    }
    
    
    public String getCardFace() {
    
        return cardFace;
    }
    
    
    public int getPokerValue() {
    
        return pokerValue;
    }
    
    
    public String getColor() {
    
        return suit >= CardModel.suit_heart ? "red" : "black";
    }
    
    
    public int getColorCode() {
    
        return suit >= CardModel.suit_heart ? kCardColorRed : kCardColorBlack;
    }
    
    
    public int getSuit() {
    
        return suit;
    }
    
    
    public static int getFunctionById(int id) {
    
        if (id == -1) { return -1; }
        return CardModel.valueOf("_" + id).getFunctionId();
        
    }
    
    
    public int[] getObjectByFieldValue(String fieldValue) {
    
        int[] result = {};
        if (fieldValue.equals("普通攻击")) {
            result = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        } else if (fieldValue.equals("闪避")) {
            result = new int[] { 60, 61, 62, 63, 64, 65, 66, 67, 68, 69 };
        }
        return result;
    }
    
    
    private CardModel() {
    
    }
    
    
    public static boolean isEquipment(int i) {
    
        switch (i) {
            case CardModel.function_id_e_BootsOfSpeed:
            case CardModel.function_id_e_PhyllisRing:
            case CardModel.function_id_e_TalismanOfEvasion:
            case CardModel.function_id_e_BladeMail:
            case CardModel.function_id_e_BladesOfAttack:
            case CardModel.function_id_e_DemonEdge:
            case CardModel.function_id_e_DiffusalBlade:
            case CardModel.function_id_e_Eaglehorn:
            case CardModel.function_id_e_EyeOfSkadi:
            case CardModel.function_id_e_LotharsEdge:
            case CardModel.function_id_e_MysticStaff:
            case CardModel.function_id_e_PlaneswalkersCloak:
            case CardModel.function_id_e_PlunderAxe:
            case CardModel.function_id_e_QuellingBlade:
            case CardModel.function_id_e_SacredRelic:
            case CardModel.function_id_e_SangeAndYasha:
            case CardModel.function_id_e_StygianDesolator:
                return true;
            default:
                return false;
        }
    }
}
