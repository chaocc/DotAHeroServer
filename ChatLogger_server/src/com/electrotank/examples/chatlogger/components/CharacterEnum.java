package com.electrotank.examples.chatlogger.components;

public enum CharacterEnum {
    秀逗魔导士(21, "kSlayer"),
    复仇之魂(12, "kVengefulSpirit"),
    刚被兽(2, "kBristleback"),
    神灵武士(3, "kSacredWarrior"),
    光之守卫(28, "kKeeperOfTheLight"),
    敌法师(17, "kAntimage")
    
    ;
    private final String value;
    private final int    id;
    
    CharacterEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public int getId() {
        return this.id;
    }
}
