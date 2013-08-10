package com.wolf.dotah.server.tool;


public interface client_const {
    public static final String kActionStartGame = "startGame"; // 开始游戏
    public static final String kActionUseCard = "useCard"; // 使用卡牌
    public static final String kActionUseHeroSkill = "useHeroSkill"; // 使用英雄技能
    public static final String kActionCancel = "cancel"; // 取消
    //        public static final String kActionDiscard = "discard"; // 确定弃牌
    
    public static final String kActionHeroList = "heroList"; // 所有玩家选中的英雄
    
    public static final String kActionChooseHeroId = "chooseHeroId"; // 选择英雄牌
    public static final String kActionChooseCardId = "chooseCardId"; // 选择卡牌ID
    public static final String kActionChooseColor = "chooseColor"; // 选择卡牌颜色
    public static final String kActionChooseSuits = "chooseSuits"; // 选择卡牌花色
    public static final String kActionChooseYesNo = "chooseYesNo"; // 选择Yes/No
    
    public static final String kActionArrangeCardId = "arrangeCardId";
}
