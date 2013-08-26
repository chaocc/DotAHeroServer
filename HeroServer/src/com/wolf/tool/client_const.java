package com.wolf.tool;

public interface client_const {
    
    public static final String kActionStartGame = "startGame"; // 开始游戏
    public static final String kActionUseCard = "useCard"; // 使用卡牌
    //    public static final String kActionUseHeroSkill = "useHeroSkill"; // 使用英雄技能
    //    public static final String kActionCancel = "cancel"; // 取消
    //        public static final String kActionDiscard = "discard"; // 确定弃牌
    
    //    public static final String kActionHeroList = "heroList"; // 所有玩家选中的英雄
    //    
    //    public static final String kActionChooseHeroId = "chooseHeroId"; // 选择英雄牌
    //    public static final String kActionChooseCardId = "chooseCardId"; // 选择卡牌ID
    //    public static final String kActionChooseColor = "chooseColor"; // 选择卡牌颜色
    //    public static final String kActionChooseSuits = "chooseSuits"; // 选择卡牌花色
    //    public static final String kActionChooseYesNo = "chooseYesNo"; // 选择Yes/No
    //    
    //    public static final String kActionArrangeCardId = "arrangeCardId";
    public static final int
            ACTION_BASE = 0,
            ACTION_USER_READY = ACTION_BASE + 1,
            ACTION_START_GAME = ACTION_BASE + 2,
            action_code_choosing_hero = ACTION_BASE + 3,
            ACTION_code_CHOSE_hero = ACTION_BASE + 4,
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
            ACTION_SPECIFY_INDEX = 29,
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
            ac_require_arrow_drop_card = 16,
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
            ac_require_s_ViperRaid = 27,
            ac_require_p_specify_target_index = 28,
            ac_require_p_drop_card_by_index = 29,
            ac_require_p_netherSwap_user_picking = 30,
            ac_require_p_swap_cards = 31
            
            ;
    
    interface action {
        int count_down = -100;
        int choosing_hero = 2;
        //        int 
    }
    
    public int
            kActionUseHandCard = 100, // 使用卡牌
            kActionUseHeroSkill = 101, // 使用英雄技能
            kActionCancel = 102, // 取消
            kActionDiscard = 103, // 确定弃牌
            
            kActionChooseHeroId = 200, // 选择英雄
            kActionChooseCard = 201, // 选择卡牌Id/Idx
            kActionChooseColor = 202, // 选择卡牌颜色
            kActionChooseSuits = 203, // 选择卡牌花色
            kActionArrangeCardId = 204; // 重新排列卡牌(如能量转移)
            
    // 1000 开始是server的 action
    public int
            kActionUpdateDeckHero = 1000, // 更新桌面: 待选英雄
            kActionUpdateDeckUsedCard = 1001, // 更新桌面: 用掉/弃掉的牌
            kActionUpdateDeckHandCard = 1002, // 更新桌面: 目标手牌/装备
            kActionUpdateDeckPlayingCard = 1003, // 更新桌面: 牌堆顶的牌
            action_update_table_confirmed_heros = 1004, // 更新桌面: 玩家列表
            
            kActionInitPlayerHero = 2000, // 初始化玩家: 选中的英雄
            kActionInitPlayerCard = 2001, // 初始化玩家: 发初始手牌
            kActionUpdatePlayerHero = 2002, // 更新玩家: 英雄的血量/怒气等信息
            kActionUpdatePlayerHand = 2003, // 更新玩家: 手牌
            kActionUpdatePlayerHandExtracted = 2004, // 更新玩家: 手牌被抽取
            kActionUpdatePlayerEquipment = 2005, // 更新玩家: 装备区的牌
            kActionUpdatePlayerEquipmentExtracted = 2006, // 更新玩家: 装备去的牌被抽取
            
            kActionPlayingCard = 3000, // 出牌阶段
            kActionChooseCardToUse = 3001, // 选择卡牌: 使用
            kActionChooseCardToCompare = 3002, // 选择卡牌: 拼点
            kActionChooseCardToExtract = 3003, // 选择目标卡牌: 抽取
            kActionChooseCardToGive = 3004, // 选择卡牌: 交给其他玩家
            kActionChooseCardToDiscard = 3005, // 选择卡牌: 弃置
            kActionChoosingColor = 3006, // 选择颜色阶段
            kActionChoosingSuits = 3007; // 选择花色阶段
            
    //    typedef NS_ENUM(NSInteger, BGCardColor) {
    //        kCardColorInvalid = 0,
    //        kCardColorRed = 1,                  // 红色
    //        kCardColorBlack                     // 黑色
    //    };
    //    
    //    typedef NS_ENUM(NSInteger, BGCardSuits) {
    //        kCardSuitsInvalid = 0,
    //        kCardSuitsHearts = 1,               // 红桃
    //        kCardSuitsDiamonds,                 // 方块
    //        kCardSuitsSpades,                   // 黑桃
    //        kCardSuitsClubs                     // 梅花
    //    };
    //
    //    typedef NS_ENUM(NSInteger, BGCardType) {
    //        kCardTypeBasic = 0,                 // 基本牌
    //        kCardTypeMagic,                     // 魔法牌
    //        kCardTypeSuperSkill,                // S技能牌
    //        kCardTypeEquipment                  // 装备牌
    //    };
    //
    //    typedef NS_ENUM(NSInteger, BGEquipmentType) {
    //        kEquipmentTypeWeapon = 0,           // 武器
    //        kEquipmentTypeArmor                 // 防具
    //    };
    //    typedef NS_ENUM(NSInteger, BGHeroCardEnum) {
    //        kHeroCardDefault = -1,
    //        kHeroCardLordOfAvernus = 0,             // 死亡骑士
    //        kHeroCardSkeletonKing = 1,              // 骷髅王
    //        kHeroCardBristleback = 2,               // 刚背兽
    //        kHeroCardSacredWarrior = 3,             // 神灵武士
    //        kHeroCardOmniknight = 4,                // 全能骑士
    //        kHeroCardAxe = 5,                       // 斧王
    //        kHeroCardCentaurWarchief = 6,           // 半人马酋长
    //        kHeroCardDragonKnight = 7,              // 龙骑士
    //        kHeroCardGuardianKnight = 8,            // 守护骑士
    //        
    //        kHeroCardGorgon = 9,                    // 蛇发女妖
    //        kHeroCardLightningRevenant = 10,        // 闪电幽魂
    //        kHeroCardJuggernaut = 11,               // 剑圣
    //        kHeroCardVengefulSpirit = 12,           // 复仇之魂
    //        kHeroCardStrygwyr = 13,                 // 血魔
    //        kHeroCardTrollWarlord = 14,             // 巨魔战将
    //        kHeroCardDwarvenSniper = 15,            // 矮人火枪手
    //        kHeroCardNerubianAssassin = 16,         // 地穴刺客
    //        kHeroCardAntimage = 17,                 // 敌法师
    //        kHeroCardNerubianWeaver = 18,           // 地穴编织者
    //        kHeroCardUrsaWarrior = 19,              // 熊战士
    //        kHeroCardChenYunSheng = 20,             // 陈云生
    //        
    //        kHeroCardSlayer = 21,                   // 秀逗魔导师
    //        kHeroCardNecrolyte = 22,                // 死灵法师
    //        kHeroCardTwinHeadDragon = 23,           // 双头龙
    //        kHeroCardCrystalMaiden = 24,            // 水晶室女
    //        kHeroCardLich = 25,                     // 巫妖
    //        kHeroCardShadowPriest = 26,             // 暗影牧师
    //        kHeroCardOrgeMagi = 27,                 // 食人魔法师
    //        kHeroCardKeeperOfTheLight = 28,         // 光之守卫
    //        kHeroCardGoblinTechies = 29,            // 哥布林工程师
    //        kHeroCardStormSpirit = 30,              // 风暴之灵
    //        kHeroCardEnchantress = 31,              // 魅惑魔女
    //        kHeroCardElfLily = 32                   // 精灵莉莉
    //    };
    //
    //    typedef NS_ENUM(NSInteger, BGHeroAttribute) {
    //        kHeroAttributeStrength = 1,             // 力量型
    //        kHeroAttributeAgility,                  // 敏捷型
    //        kHeroAttributeIntelligence              // 智力型
    //    };
    //    typedef NS_ENUM(NSInteger, BGHeroSkillEnum) {
    //        kHeroSkillDefault = -1,
    //        kHeroSkillDeathCoil = 0,                // 死亡缠绕
    //        kHeroSkillFrostmourne = 1,              // 霜之哀伤
    //        
    //        kHeroSkillReincarnation = 2,            // 重生
    //        kHeroSkillVampiricAura = 3,             // 吸血
    //        
    //        kHeroSkillWarpath = 4,                  // 战意
    //        kHeroSkillBristleback = 5,              // 刚毛后背
    //        
    //        kHeroSkillLifeBreak = 6,                // 牺牲
    //        kHeroSkillBurningSpear = 7,             // 沸血之矛
    //        
    //        kHeroSkillPurification = 8,             // 洗礼
    //        kHeroSkillHolyLight = 9,                // 圣光
    //        
    //        kHeroSkillBattleHunger = 10,            // 战争饥渴
    //        kHeroSkillCounterHelix = 11,            // 反转螺旋
    //        
    //        kHeroSkillDoubleEdge = 12,              // 双刃剑
    //        
    //        kHeroSkillBreatheFire = 13,             // 火焰气息
    //        kHeroSkillDragonBlood = 14,             // 龙族血统
    //
    //        kHeroSkillGuardian = 15,                // 援护
    //        kHeroSkillFaith = 16,                   // 信仰
    //        kHeroSkillFatherlyLove = 17,            // 父爱
    //        
    //        kHeroSkillMysticSnake = 18,             // 秘术异蛇
    //        kHeroSkillManaShield = 19,              // 魔法护盾
    //        
    //        kHeroSkillPlasmaField = 20,             // 等离子场
    //        kHeroSkillUnstableCurrent = 21,         // 不定电流
    //        
    //        kHeroSkillOmnislash = 22,               // 无敌斩
    //        kHeroSkillBladeDance = 23,              // 剑舞
    //        
    //        kHeroSkillNetherSwap = 24,              // 移形换位
    //        kHeroSkillWaveOfTerror = 25,            // 恐怖波动
    //        
    //        kHeroSkillBloodrage = 26,               // 血之狂暴
    //        kHeroSkillStrygwyrsThirst = 27,         // 嗜血
    //        kHeroSkillBloodBath = 28,               // 屠戮
    //        
    //        kHeroSkillBattleTrance = 29,            // 战斗专注
    //        kHeroSkillFervor = 30,                  // 热血战魂
    //        
    //        kHeroSkillHeadshot = 31,                // 爆头
    //        kHeroSkillTakeAim = 32,                 // 瞄准
    //        kHeroSkillShrapnel = 33,                // 散弹
    //        
    //        kHeroSkillManaBurn = 34,                // 法力燃烧
    //        kHeroSkillVendetta = 35,                // 复仇
    //        kHeroSkillSpikedCarapace = 36,          // 穿刺护甲
    //        
    //        kHeroSkillManaBreak = 37,               // 法力损毁
    //        kHeroSkillBlink = 38,                   // 闪烁
    //        kHeroSkillManaVoid = 39,                // 法力虚空
    //        
    //        kHeroSkillTheSwarm = 40,                // 蝗虫群
    //        kHeroSkillTimeLapse = 41,               // 时光倒流
    //        
    //        kHeroSkillFurySwipes = 42,              // 怒意狂击
    //        kHeroSkillEnrage = 43,                  // 激怒
    //        
    //        kHeroSkillOrdeal = 44,                  // 神判
    //        kHeroSkillSpecialBody = 45,             // 特殊体质
    //        
    //        kHeroSkillFierySoul = 46,               // 炽魂
    //        kHeroSkillLagunaBlade = 47,             // 神灭斩
    //        kHeroSkillFanaticismHeart = 48,         // 狂热之心
    //        
    //        kHeroSkillHeartstopperAura = 49,        // 竭心光环
    //        kHeroSkillSadist = 50,                  // 施虐之心
    //        
    //        kHeroSkillIcePath = 51,                 // 冰封
    //        kHeroSkillLiquidFire = 52,              // 液态火
    //        
    //        kHeroSkillFrostbite = 53,               // 冰封禁制
    //        kHeroSkillBrillianceAura = 54,          // 辉煌光环
    //        
    //        kHeroSkillDarkRitual = 55,              // 邪恶祭祀
    //        kHeroSkillFrostArmor = 56,              // 霜冻护甲
    //        
    //        kHeroSkillShallowGrave = 57,            // 薄葬
    //        kHeroSkillShadowWave = 58,              // 暗影波
    //        
    //        kHeroSkillFireblast = 59,               // 火焰爆轰
    //        kHeroSkillMultiCast = 60,               // 多重施法
    //        
    //        kHeroSkillIlluminate = 61,              // 冲击波
    //        kHeroSkillChakraMagic = 62,             // 查克拉
    //        kHeroSkillGrace = 63,                   // 恩惠
    //        
    //        kHeroSkillRemoteMines = 64,             // 遥控炸弹
    //        kHeroSkillFocusedDetonate = 65,         // 引爆
    //        kHeroSkillSuicideSquad = 66,            // 自爆
    //        
    //        kHeroSkillOverload = 67,                // 超负荷
    //        kHeroSkillBallLightning = 68,           // 球状闪电
    //        
    //        kHeroSkillUntouchable = 69,             // 不可侵犯
    //        kHeroSkillEnchant = 70,                 // 魅惑
    //        kHeroSkillNaturesAttendants = 71,       // 自然之助
    //        
    //        kHeroSkillHealingSpell = 72,            // 治疗术
    //        kHeroSkillDispelWizard = 73,            // 驱散精灵
    //        kHeroSkillMagicControl = 74             // 魔法掌控
    //    };
    //
    //    typedef NS_ENUM(NSInteger, BGHeroSkillCategory) {
    //        kHeroSkillCategoryActive = 0,           // 主动技能
    //        kHeroSkillCategoryPassive,              // 被动技能
    //    };
    //
    //    typedef NS_ENUM(NSInteger, BGHeroSkillType) {
    //        kHeroSkillTypeGeneral = 0,              // 普通技
    //        kHeroSkillTypeRestricted,               // 限制技
    //        kHeroSkillTypeLimited                   // 限定技
    //    };
    
}
