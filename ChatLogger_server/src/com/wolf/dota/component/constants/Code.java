package com.wolf.dota.component.constants;


/**
 * s_ means s skills m_ means normal magic/skills h_ means hero skills w_ means
 * weapon skills
 * 
 * @author Solomon
 * 
 */
public interface Code {
    
    public static final int
            ACTION_BASE = 0,
            ACTION_USER_READY = ACTION_BASE + 1,
            ACTION_START_GAME = ACTION_BASE + 2,
            ACTION_CHOOSE_CHARACTER = ACTION_BASE + 3,
            ACTION_CHOSE_CHARACTER = ACTION_BASE + 4,
            ACTION_ALL_HEROS = ACTION_BASE + 5,
            ACTION_DISPATCH_FORCE = ACTION_BASE + 6,
            ACTION_DISPATCH_HANDCARD = ACTION_BASE + 7, // should along with
                                                        // cards
            ACTION_START_TURN = ACTION_BASE + 8,
            ACTION_DRAW_CARDS = ACTION_BASE + 9, // client send to server
            ACTION_STAKE = ACTION_BASE + 10,
            ACTION_SEND_CARDS = ACTION_BASE + 11, // server send to client
            ACTION_USED_CARD = ACTION_BASE + 12,
            ACTION_DROP_CARDS = ACTION_BASE + 13,
            ACTION_CANCEL = ACTION_BASE + 14,
            ACTION_HP_RESTORE = ACTION_BASE + 15,
            ACTION_SP_UP = ACTION_BASE + 16,
            ACTION_SP_LOST = ACTION_BASE + 17,
            ACTION_GET_SPECIFIC_CARD_DISARM = ACTION_BASE + 18,
            ACTION_LOOSE_EQUIPMENT = ACTION_BASE + 19,
            ACTION_CONTINUE_PLAYING = ACTION_BASE + 20,
            ACTION_GUESS_GOLOR = 21,
            ACTION_GUESS_SUCCESS = 22,
            ACTION_CHOOSED_CARD = 23,
            ACTION_GREED_TRANSFER_ACTION = 24,
            ACTION_RESPOND_shenmied = 25,
            ACTION_TURN_FINISH_DROP_CARD = 27,
            ACTION_START_TURN_FINISH_STAGE = 26,
            ACTION_USED_SKILL = 30,
            
            
            ACTION_TARGETTED = ACTION_BASE + 51,
            ACTION_REACTED = ACTION_BASE + 52,
            ACTION_PICK_CARD = 53,
            
            
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
            ac_require_somebody_using_magic = 8, // 魔法牌生效前
            ac_require_staking = 9,
            // ac_require_targetted_by_magic_card = 9, // 成为任意1张魔法牌的目标时
            // ac_require_damaged = 10, // 受到1次伤害时
            // ac_require_dying = 11, // 濒死状态
            // ac_require_dead = 12, // 死亡
            //
            // ac_require_attack_hitted = 13, // 使用攻击命中后
            // ac_require_damaged_x = 15, // 受到伤害大于1
            // kPlayerStateUsingHeroSkill = 9, // 英雄技能生效前
            ac_require_attack_hitted = 10, // 受到攻击的伤害
            ac_require_magic_hitted = 11, // 受到1次伤害
            kPlayerStateDealingDamage = 12, // 造成1次伤害
            kPlayerStateIsDying = 13, // 濒死状态
            ac_require_made_damage = 14, // 只有杀, 造成1次伤害
            kPlayerStateIsDead = 15, // 已死亡
            ac_require_arrow_drop_card = 16, // 成为任意1张魔法牌的目标时
            kPlayerStateTargetOfHeroSkill = 17, // 成为任意英雄技能的目标时
            ac_require_restored_hp = 18,
            ac_require_choosing = 19,
            ac_require_targetted_and_choosing = 20,
            ac_require_lose_equipment = 21,
            ac_require_greed_transfer_card = 22,
            ac_require_misleaded = 23,
            ac_require_sp_got = 24,
            ac_require_s_skill_used_sp_lost = 25,
            ac_require_shenmied = 26,
            ac_require_s_ViperRaid = 27
            
            
            ;
    
    
}
