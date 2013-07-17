package com.electrotank.electroserver5.examples.chatloggerclient;

public interface ClientConstants {
	public static final String
			ac = "action",
			ac_required = "playerState",
			error = "err";
	public static final int
			ACTION_BASE = 1,
			ACTION_USER_READY = ACTION_BASE + 0,
			ACTION_START_GAME = ACTION_BASE + 1,
			ACTION_CHOOSE_CHARACTER = ACTION_BASE + 2,
			ACTION_CHOSE_CHARACTER = ACTION_BASE + 3,
			ACTION_ALL_HEROS = ACTION_BASE + 4,
			ACTION_DISPATCH_FORCE = ACTION_BASE + 5,
			ACTION_DISPATCH_HANDCARD = ACTION_BASE + 6, // should along with
														// cards
			ACTION_START_TURN = ACTION_BASE + 7,
			ACTION_DRAW_CARDS = ACTION_BASE + 8, // client send to server
			ACTION_STAKE = ACTION_BASE + 9,
			ACTION_SEND_CARDS = ACTION_BASE + 10, // server send to client
			ACTION_USED_CARD = ACTION_BASE + 11,
			
			ACTION_HP_DAMAGED = ACTION_BASE + 13,
			ACTION_HP_RESTORE = ACTION_BASE + 14,
			ACTION_SP_UP = ACTION_BASE + 15,
			ACTION_SP_LOST = ACTION_BASE + 16,
			ACTION_GET_SPECIFIC_CARD = ACTION_BASE + 17,
			ACTION_LOOSE_EQUIPMENT = ACTION_BASE + 18,
			
			ACTION_FUNCTION_BASE = 100,
			ACTION_NORMAL_ATTACK = ACTION_FUNCTION_BASE + 1,
			ACTION_EVASION = ACTION_FUNCTION_BASE + 2,
			// ===>> 2013.07.14 task
			ACTION_CHAOS_ATTACK = ACTION_FUNCTION_BASE + 3,
			ACTION_FLAME_ATTACK = ACTION_FUNCTION_BASE + 4,
			ACTION_HEAL = ACTION_FUNCTION_BASE + 5,
			
			ACTION_S_LagunaBlade = ACTION_FUNCTION_BASE + 6,
			ACTION_S_GodsStrength = ACTION_FUNCTION_BASE + 7,
			ACTION_S_ViperRaid = ACTION_FUNCTION_BASE + 8,
			ACTION_Mislead = ACTION_FUNCTION_BASE + 9,
			ACTION_Dispel = ACTION_FUNCTION_BASE + 10,
			ACTION_Disarm = ACTION_FUNCTION_BASE + 11
			// ===>> 2013.07.14 task end
			
			;
	
	public static final int
			ac_required_base = 100,
			ac_require_turn_start = 1,
			ac_require_determing = 2, // 判定阶段
			ac_require_draw = 3, // 摸牌阶段
			ac_require_play = 4, // 出牌阶段
			ac_require_turn_end_drop = 5, // 弃牌阶段
			ac_require_turn_end = 6, // 回合结束阶段
			
			ac_require_attacked = 7, // 被攻击生效前
			ac_require_before_magic_happen = 8, // 魔法牌生效前
			ac_require_targetted_by_magic_card = 9, // 成为任意1张魔法牌的目标时
			ac_required_damaged = 10, // 受到1次伤害时
			ac_require_dying = 11, // 濒死状态
			ac_require_dead = 12, // 死亡
			
			ac_require_attack_hitted = 13, // 使用攻击命中后
			ac_require_made_damage = 14, // 造成一次伤害
			ac_require_damaged_x = 15 // 受到伤害大于1
			
			;
	
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
			STRENGTHED = "isStrengthed"
			
			;
	
	/** client message */
	
	public static final String USER_INPUT_MESSAGE_CHOSE_CHARACTER = "chose_character";
	public static final String USER_INPUT_MESSAGE_START = "start";
}
