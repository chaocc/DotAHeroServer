// actions
var ACTION 				  = "action";
var START_GAME = 2;


    // 1000 开始是server的 action
var kActionUpdateDeckHero = 1000, // 更新桌面: 待选英雄
    kActionUpdateDeckUsedCard = 1001, // 更新桌面: 用掉/弃掉的牌
    kActionUpdateDeckHandCard = 1002, // 更新桌面: 目标手牌/装备
    kActionUpdateDeckPlayingCard = 1003, // 更新桌面: 牌堆顶的牌
    action_update_table_confirmed_heros = 1004, // 更新桌面: 玩家列表
    action_cutted = 1005,// 切牌完成
    //client to server
    kActionInitPlayerHero = 2000, // 初始化玩家: 选中的英雄
    kActionInitPlayerCard = 2001, // 初始化玩家: 发初始手牌
    kActionUpdatePlayerHero = 2002, // 更新玩家: 英雄的血量/怒气等信息
    kActionUpdatePlayerHand = 2003, // 更新玩家: 手牌
    kActionUpdatePlayerHandExtracted = 2004, // 更新玩家: 手牌被抽取
    kActionUpdatePlayerEquipment = 2005, // 更新玩家: 装备区的牌
    kActionUpdatePlayerEquipmentExtracted = 2006, // 更新玩家: 装备去的牌被抽取
    //server to client
    kActionPlayingCard = 3000, // 出牌阶段
    kActionChooseCardToUse = 3001, // 选择卡牌: 使用
    kActionChooseCardToCompare = 3002, // 选择卡牌: 拼点
    kActionChooseCardToExtract = 3003, // 选择目标卡牌: 抽取
    kActionChooseCardToGive = 3004, // 选择卡牌: 交给其他玩家
    kActionChooseCardToDiscard = 3005, // 选择卡牌: 弃置
    kActionChoosingColor = 3006, // 选择颜色阶段
    kActionChoosingSuits = 3007; // 选择花色阶段

// parameters
var kParamUserList = "player_list",// 所有玩家列表
    kParamRemainingCardCount = "remaining_count",// 牌堆剩余牌数
    kParamSourcePlayerName = "player_name",// 回合开始/伤害来源/出牌的玩家
    kParamTargetPlayerList = "target_player_list",// 目标玩家列表
    id_list = "id_list",// 卡牌列表(英雄牌/摸的牌/获得的牌/使用的牌/弃置的牌)
    index_list = "index_list",// 选中的哪几张牌
    hand_card_count = "hand_card_count",// 玩家手牌数量
    kParamSelectableCardCount = "selectable_count",// 可选择的卡牌数量
    //                selectable_ids = "selectable_ids",
    kParamExtractedCardCount = "extracted_count",// 可抽取目标的卡牌数量
    kParamSelectedHeroId = "id",// 选中的英雄
    kParamSelectedSkillId = "selected_skill_id",// 选中的英雄技能
    kParamSelectedColor = "selected_color",// 选中的颜色
    kParamSelectedSuits = "selected_suits",// 选中的花色
    kParamIsStrengthened = "is_strengthened",// 是否被强化
    kParamHeroBloodPoint = "hp",// 血量值
    kParamHeroAngerPoint = "sp", // 怒气值
    available_id_list = "available_id_list";






// system values
PLUGIN_NAME="GamePlugin";