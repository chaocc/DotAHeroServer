package com.electrotank.examples.chatlogger.components;

public enum CardEnum {
    _0(0, "普通攻击", 1, 1 + CardEnum.suit_spade),
    _1(1, "普通攻击", 2, 2 + CardEnum.suit_spade),
    _2(2, "普通攻击", 3, 3 + CardEnum.suit_spade),
    _3(3, "普通攻击", 4, 4 + CardEnum.suit_spade),
    _4(4, "普通攻击", 5, 5 + CardEnum.suit_spade),
    _5(5, "普通攻击", 6, 6 + CardEnum.suit_spade),
    _6(6, "普通攻击", 7, 7 + CardEnum.suit_spade),
    _7(7, "普通攻击", 8, 8 + CardEnum.suit_spade),
    _8(8, "普通攻击", 9, 9 + CardEnum.suit_spade),
    _9(9, "混乱攻击", 10, 10 + CardEnum.suit_spade),
    _10(10, "混乱攻击", 1, 1 + CardEnum.suit_spade),
    _11(11, "混乱攻击", 2, 2 + CardEnum.suit_spade),
    _12(12, "混乱攻击", 3, 3 + CardEnum.suit_spade),
    _13(13, "混乱攻击", 4, 4 + CardEnum.suit_spade),
    _14(14, "混乱攻击", 5, 5 + CardEnum.suit_spade),
    _15(15, "混乱攻击", 6, 6 + CardEnum.suit_spade),
    _16(16, "神灭斩", 7, 7 + CardEnum.suit_spade),
    _17(17, "神灭斩", 8, 8 + CardEnum.suit_spade),
    _18(18, "蝮蛇突袭", 9, 9 + CardEnum.suit_spade),
    _19(19, "蝮蛇突袭", 10, 10 + CardEnum.suit_spade),
    _20(20, "误导", 1, 1 + CardEnum.suit_club),
    _21(21, "误导", 2, 2 + CardEnum.suit_club),
    _22(22, "贪婪", 3, 3 + CardEnum.suit_club),
    _23(23, "贪婪", 4, 4 + CardEnum.suit_club),
    _24(24, "缴械", 5, 5 + CardEnum.suit_club),
    _25(25, "缴械", 6, 6 + CardEnum.suit_club),
    _26(26, "缴械", 7, 7 + CardEnum.suit_club),
    _27(27, "缴械", 8, 8 + CardEnum.suit_club),
    _28(28, "缴械", 9, 9 + CardEnum.suit_club),
    _29(29, "查克拉", 10, 10 + CardEnum.suit_club),
    _30(30, "查克拉", 1, 1 + CardEnum.suit_club),
    _31(31, "能量转移", 2, 2 + CardEnum.suit_club),
    _32(32, "能量转移", 3, 3 + CardEnum.suit_club),
    _33(33, "月神之箭", 4, 4 + CardEnum.suit_club),
    _34(34, "月神之箭", 5, 5 + CardEnum.suit_club),
    _35(35, "狂热", 6, 6 + CardEnum.suit_club),
    _36(36, "狂热", 7, 7 + CardEnum.suit_club),
    _37(37, "速度之靴", 8, 8 + CardEnum.suit_club),
    _38(38, "菲丽丝之戒", 9, 9 + CardEnum.suit_club),
    _39(39, "闪避护符", 10, 10 + CardEnum.suit_club),
    _40(40, "火焰攻击", 1, 1 + CardEnum.suit_heart),
    _41(41, "火焰攻击", 2, 2 + CardEnum.suit_heart),
    _42(42, "火焰攻击", 3, 3 + CardEnum.suit_heart),
    _43(43, "火焰攻击", 4, 4 + CardEnum.suit_heart),
    _44(44, "火焰攻击", 5, 5 + CardEnum.suit_heart),
    _45(45, "火焰攻击", 6, 6 + CardEnum.suit_heart),
    _46(46, "治疗药膏", 7, 7 + CardEnum.suit_heart),
    _47(47, "治疗药膏", 8, 8 + CardEnum.suit_heart),
    _48(48, "治疗药膏", 9, 9 + CardEnum.suit_heart),
    _49(49, "治疗药膏", 10, 10 + CardEnum.suit_heart),
    _50(50, "驱散", 1, 1 + CardEnum.suit_heart),
    _51(51, "驱散", 2, 2 + CardEnum.suit_heart),
    _52(52, "驱散", 3, 3 + CardEnum.suit_heart),
    _53(53, "驱散", 4, 4 + CardEnum.suit_heart),
    _54(54, "驱散", 5, 5 + CardEnum.suit_heart),
    _55(55, "治疗药膏", 6, 6 + CardEnum.suit_heart),
    _56(56, "治疗药膏", 7, 7 + CardEnum.suit_heart),
    _57(57, "流浪法师斗篷", 8, 8 + CardEnum.suit_heart),
    _58(58, "神之力量", 9, 9 + CardEnum.suit_heart),
    _59(59, "神之力量", 10, 10 + CardEnum.suit_heart),
    _60(60, "闪避", 1, 1 + CardEnum.suit_diamond),
    _61(61, "闪避", 2, 2 + CardEnum.suit_diamond),
    _62(62, "闪避", 3, 3 + CardEnum.suit_diamond),
    _63(63, "闪避", 4, 4 + CardEnum.suit_diamond),
    _64(64, "闪避", 5, 5 + CardEnum.suit_diamond),
    _65(65, "闪避", 6, 6 + CardEnum.suit_diamond),
    _66(66, "闪避", 7, 7 + CardEnum.suit_diamond),
    _67(67, "闪避", 8, 8 + CardEnum.suit_diamond),
    _68(68, "闪避", 9, 9 + CardEnum.suit_diamond),
    _69(69, "闪避", 10, 10 + CardEnum.suit_diamond),
    _70(70, "散失之刃", 1, 1 + CardEnum.suit_diamond),
    _71(71, "洛萨之锋", 2, 2 + CardEnum.suit_diamond),
    _72(72, "冰魄之眼", 3, 3 + CardEnum.suit_diamond),
    _73(73, "黯灭之刃", 4, 4 + CardEnum.suit_diamond),
    _74(74, "散夜对剑", 5, 5 + CardEnum.suit_diamond),
    _75(75, "攻击之爪", 6, 6 + CardEnum.suit_diamond),
    _76(76, "恶魔刀锋", 7, 7 + CardEnum.suit_diamond),
    _77(77, "圣者遗物", 8, 8 + CardEnum.suit_diamond),
    _78(78, "刃甲", 9, 9 + CardEnum.suit_diamond),
    _79(79, "闪避", 10, 10 + CardEnum.suit_diamond)
    
    ;
    //==  basic  ==========================================
    //==  equipment  ==========================================
    //==  magic cards  ==========================================
    public static final int suit_spade   = 100;   //黑桃
    public static final int suit_club    = 1000;  //草花
    public static final int suit_heart   = 10000;
    public static final int suit_diamond = 100000;
    
    private int             cardId;
    private String          name;
    private int             pokerValue;
    private int             suit;                 //花色
                                                   
    private CardEnum(int cardId, String name, int pokerValue, int suit) {
        this.cardId = cardId;
        this.name = name;
        this.pokerValue = pokerValue;
        this.suit = suit;
    }
    
    public int getCardId() {
        return cardId;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPokerValue() {
        return pokerValue;
    }
    
    public String getColor() {
        return suit >= CardEnum.suit_heart ? "red" : "black";
    }
    
    public int getSuit() {
        return suit;
    }
    
    private CardEnum() {
        
    }
}
