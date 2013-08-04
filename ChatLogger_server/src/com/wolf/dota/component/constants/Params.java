package com.wolf.dota.component.constants;


public interface Params {
    
    public static final String
            CHARACTORS_TO_CHOOSE = "toBeSelectedHeroIds",
            SORTED_PLAYER_NAMES = "sortedPlayerNames",
            STACK_CARD_COUNT = "remainingCardCount",
            SELECTED_HERO_ID = "heroId",
            ALL_HEROS = "allHeroIds",
            ROLE_IDS = "roleIds", // value for dispatch force action
            DISPATCH_CARDS = "gotPlayingCardIds",
            USED_CARDS = "usedPlayingCardIds",
            TARGET_CARD = "targetCard",
            TARGET_EQUIPMENTS = "targetEquipments",
            TARGET_CARD_COUNT = "targetCardCount",
            PLAYER_NAME = "playerName",
            TARGET_PLAYERS = "targetPlayerNames",
            FORCE = "force",
            ALL_STAKE_CARDS = "allCuttingCardIds",
            HP_CHANGED = "hpChanged",
            SP_CHANGED = "spChanged",
            STRENGTHED = "isStrengthed",
            TARGET_COLOR = "targetColor",
            TARGET_SUITS = "targetSuits",
            MISGUESSED_CARD = "misGuessedCardIds",
            GREED_TYPE = "greedType",
            INDEX = "extractedCardIdxes",
            GREED_LOSE_CARDS = "greedLoseCardIds",
            GREED_SEND_CARDS = "transferedCardIds",
            HAND_CARDS = "handCardIds",
            USED_SKILL = "usedSkillId",
            HP = "heroHP"
            
            
            ;
    
    public static final int
            kCardSuitsHearts = 1, // 红桃
            kCardSuitsDiamonds = 2, // 方块
            kCardSuitsSpades = 3, // 黑桃
            kCardSuitsClubs = 4, // 梅花
            kCardColorRed = 1, // 红色
            kCardColorBlack = 2, // 黑色
            type_quipment = 4,
            type_hand = 5,
            
            
            hero_id_kSlayer = 21,//秀逗魔导士
            hero_id_kVengefulSpirit = 12,//复仇之魂
            hero_id_kBristleback = 2,//刚被兽
            hero_id_kSacredWarrior = 3,//神灵武士
            hero_id_kKeeperOfTheLight = 28,//光之守卫
            hero_id_kAntimage = 17//敌法师
            
            
            ;
}
