package com.wolf.dotah.server.cmpnt.card;

/**
 * 以下都属于这个类别
 * 基本牌    B
 * 魔法牌    M
 * S技能牌 S
 * 驱散
 * 装备牌    E
 * 
 * @author Solomon
 */
public class Card {
    int id;
    String name;
    int suits;
    int color;
    int faceNumber;
    
    int function;
    boolean enhancable;
    int enhancedFunction;
}
