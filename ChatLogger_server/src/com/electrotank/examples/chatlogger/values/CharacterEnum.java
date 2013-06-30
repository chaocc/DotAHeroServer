package com.electrotank.examples.chatlogger.values;

public enum CharacterEnum {
    秀逗魔导士("kSlayer"), 复仇之魂("kVengefulSpirit"), 刚被兽("kBristleback"),
    神灵武士("kSacredWarrior"), 光之守卫("kKeeperOfTheLight"), 敌法师("kAntimage");
    private final String value;
    
    CharacterEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
}
