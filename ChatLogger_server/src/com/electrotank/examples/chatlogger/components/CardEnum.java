package com.electrotank.examples.chatlogger.components;

public enum CardEnum {
    闪避(1, "shan", 1, 1 + CardEnum.suit_diamond),
    治疗药膏(2, "shan", 6, 1 + CardEnum.suit_diamond),
    普通攻击(3, "shan", 7, 1 + CardEnum.suit_spade),
    混乱攻击(4, "shan", 3, 1 + CardEnum.suit_heart),
    火焰攻击(5, "shan", 2, 1 + CardEnum.suit_heart),
    暗灭之刃(6, "shan", 12, 1 + CardEnum.suit_spade),
    圣者遗物(7, "shan", 13, 1 + CardEnum.suit_club),
    菲利斯之戒(8, "shan", 11, 1 + CardEnum.suit_diamond),
    攻击之爪(9, "shan", 13, 1 + CardEnum.suit_spade),
    散夜对剑(10, "shan", 13, 1 + CardEnum.suit_spade)
    
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
