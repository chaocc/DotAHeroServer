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
			PLAYER_NAME = "playerName",
			TARGET_PLAYERS = "targetPlayerNames",
			FORCE = "force",
			ALL_STAKE_CARDS = "allCuttingCardIds",
			HP_CHANGED = "hpChanged",
			SP_CHANGED = "spChanged",
			STRENGTHED = "isStrengthed",
			TARGET_COLOR = "targetColor",
			TARGET_SUITS = "targetSuits"
			;
	
	public static final int
			kCardSuitsHearts = 1, // 红桃
			kCardSuitsDiamonds = 2, // 方块
			kCardSuitsSpades = 3, // 黑桃
			kCardSuitsClubs = 4, // 梅花
			kCardColorRed = 1, // 红色
			kCardColorBlack = 2 // 黑色
			
			;
}
