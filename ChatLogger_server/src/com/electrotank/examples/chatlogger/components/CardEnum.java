package com.electrotank.examples.chatlogger.components;

public enum CardEnum {
    _1(1, "闪", 1, 1 + CardEnum.suit_diamond),
    _2(2, "闪", 1, 1 + CardEnum.suit_diamond),
    _3(3, "闪", 1, 1 + CardEnum.suit_diamond),
    _4(4, "闪", 1, 1 + CardEnum.suit_diamond),
    _5(5, "闪", 1, 1 + CardEnum.suit_diamond),
    _6(6, "闪", 1, 1 + CardEnum.suit_diamond),
    _7(7, "闪", 1, 1 + CardEnum.suit_diamond),
    _8(8, "shan", 1, 1 + CardEnum.suit_diamond),
    _9(9, "shan", 1, 1 + CardEnum.suit_diamond),
    _10(10, "shan", 1, 1 + CardEnum.suit_diamond),
    _11(11, "shan", 1, 1 + CardEnum.suit_diamond),
    _12(12, "shan", 1, 1 + CardEnum.suit_diamond),
    _13(13, "shan", 1, 1 + CardEnum.suit_diamond),
    _14(14, "治疗药膏", 6, 1 + CardEnum.suit_diamond),
    _15(15, "shan", 7, 1 + CardEnum.suit_spade),
    _16(16, "普通攻击", 3, 1 + CardEnum.suit_heart),
    _17(17, "混乱攻击", 2, 1 + CardEnum.suit_heart),
    _18(18, "火焰攻击", 12, 1 + CardEnum.suit_spade),
    _19(19, "圣者遗物", 13, 1 + CardEnum.suit_club),
    _20(20, "菲利斯之戒", 11, 1 + CardEnum.suit_diamond),
    _21(21, "攻击之爪", 13, 1 + CardEnum.suit_spade),
    _22(22, "散夜对剑", 13, 1 + CardEnum.suit_spade),
    _23(23, "shan", 1, 1 + CardEnum.suit_diamond),
    _24(24, "shan", 1, 1 + CardEnum.suit_diamond),
    _25(25, "shan", 1, 1 + CardEnum.suit_diamond),
    _26(26, "shan", 1, 1 + CardEnum.suit_diamond),
    _27(27, "治疗药膏", 6, 1 + CardEnum.suit_diamond),
    _28(28, "shan", 7, 1 + CardEnum.suit_spade),
    _29(29, "普通攻击", 3, 1 + CardEnum.suit_heart),
    _30(30, "混乱攻击", 2, 1 + CardEnum.suit_heart),
    _31(31, "火焰攻击", 12, 1 + CardEnum.suit_spade),
    _32(32, "圣者遗物", 13, 1 + CardEnum.suit_club),
    _33(33, "菲利斯之戒", 11, 1 + CardEnum.suit_diamond),
    _34(34, "攻击之爪", 13, 1 + CardEnum.suit_spade),
    _35(35, "shan", 7, 1 + CardEnum.suit_spade),
    _36(36, "普通攻击", 3, 1 + CardEnum.suit_heart),
    _37(37, "混乱攻击", 2, 1 + CardEnum.suit_heart),
    _38(38, "火焰攻击", 12, 1 + CardEnum.suit_spade),
    _39(39, "圣者遗物", 13, 1 + CardEnum.suit_club),
    _40(40, "菲利斯之戒", 11, 1 + CardEnum.suit_diamond),
    _41(41, "攻击之爪", 13, 1 + CardEnum.suit_spade),
    _42(42, "散夜对剑", 13, 1 + CardEnum.suit_spade),
    _43(43, "shan", 1, 1 + CardEnum.suit_diamond),
    _44(44, "shan", 1, 1 + CardEnum.suit_diamond),
    _45(45, "shan", 1, 1 + CardEnum.suit_diamond),
    _46(46, "shan", 1, 1 + CardEnum.suit_diamond),
    _47(47, "治疗药膏", 6, 1 + CardEnum.suit_diamond),
    _48(48, "shan", 7, 1 + CardEnum.suit_spade),
    _49(49, "普通攻击", 3, 1 + CardEnum.suit_heart),
    _50(50, "混乱攻击", 2, 1 + CardEnum.suit_heart),
    _51(51, "火焰攻击", 12, 1 + CardEnum.suit_spade),
    _52(52, "圣者遗物", 13, 1 + CardEnum.suit_club),
    _53(53, "菲利斯之戒", 11, 1 + CardEnum.suit_diamond),
    _54(55, "散夜对剑", 13, 1 + CardEnum.suit_spade)
    
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
