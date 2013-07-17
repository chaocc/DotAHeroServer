package com.electrotank.electroserver5.examples.chatloggerclient;

public enum CardModel {
	_0(0, "普通攻击", CardModel.function_id_normal_attack, "黑桃A", 1, 1 + CardModel.suit_spade),
	_1(1, "普通攻击", CardModel.function_id_normal_attack, "黑桃2", 2, 2 + CardModel.suit_spade),
	_2(2, "普通攻击", CardModel.function_id_normal_attack, "黑桃3", 3, 3 + CardModel.suit_spade),
	_3(3, "普通攻击", CardModel.function_id_normal_attack, "黑桃4", 4, 4 + CardModel.suit_spade),
	_4(4, "普通攻击", CardModel.function_id_normal_attack, "黑桃5", 5, 5 + CardModel.suit_spade),
	_5(5, "普通攻击", CardModel.function_id_normal_attack, "黑桃6", 6, 6 + CardModel.suit_spade),
	_6(6, "普通攻击", CardModel.function_id_normal_attack, "黑桃7", 7, 7 + CardModel.suit_spade),
	_7(7, "普通攻击", CardModel.function_id_normal_attack, "黑桃8", 8, 8 + CardModel.suit_spade),
	_8(8, "普通攻击", CardModel.function_id_normal_attack, "黑桃9", 9, 9 + CardModel.suit_spade),
	_9(9, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃10", 10, 10 + CardModel.suit_spade),
	_10(10, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃A", 1, 1 + CardModel.suit_spade),
	_11(11, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃2", 2, 2 + CardModel.suit_spade),
	_12(12, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃3", 3, 3 + CardModel.suit_spade),
	_13(13, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃4", 4, 4 + CardModel.suit_spade),
	_14(14, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃5", 5, 5 + CardModel.suit_spade),
	_15(15, "混乱攻击", CardModel.function_id_chaos_attack, "黑桃6", 6, 6 + CardModel.suit_spade),
	_16(16, "神灭斩", CardModel.function_id_s_LagunaBlade, "黑桃7", 7, 7 + CardModel.suit_spade),
	_17(17, "神灭斩", CardModel.function_id_s_LagunaBlade, "黑桃8", 8, 8 + CardModel.suit_spade),
	_18(18, "蝮蛇突袭", CardModel.function_id_s_viper_raid, "黑桃9", 9, 9 + CardModel.suit_spade),
	_19(19, "蝮蛇突袭", CardModel.function_id_s_viper_raid, "黑桃10", 10, 10 + CardModel.suit_spade),
	_20(20, "误导", CardModel.kPlayingCardMislead, "梅花A", 1, 1 + CardModel.suit_club),
	_21(21, "误导", CardModel.kPlayingCardMislead, "梅花2", 2, 2 + CardModel.suit_club),
	_22(22, "贪婪", CardModel.kPlayingCardGreed, "梅花3", 3, 3 + CardModel.suit_club),
	_23(23, "贪婪", CardModel.kPlayingCardGreed, "梅花4", 4, 4 + CardModel.suit_club),
	_24(24, "缴械", CardModel.kPlayingCardDisarm, "梅花5", 5, 5 + CardModel.suit_club),
	_25(25, "缴械", CardModel.kPlayingCardDisarm, "梅花6", 6, 6 + CardModel.suit_club),
	_26(26, "缴械", CardModel.kPlayingCardDisarm, "梅花7", 7, 7 + CardModel.suit_club),
	_27(27, "缴械", CardModel.kPlayingCardDisarm, "梅花8", 8, 8 + CardModel.suit_club),
	_28(28, "缴械", CardModel.kPlayingCardDisarm, "梅花9", 9, 9 + CardModel.suit_club),
	_29(29, "查克拉", CardModel.kPlayingCardChakra, "梅花10", 10, 10 + CardModel.suit_club),
	_30(30, "查克拉", CardModel.kPlayingCardChakra, "梅花A", 1, 1 + CardModel.suit_club),
	_31(31, "能量转移", CardModel.kPlayingCardEnergyTransport, "梅花2", 2, 2 + CardModel.suit_club),
	_32(32, "能量转移", CardModel.kPlayingCardEnergyTransport, "梅花3", 3, 3 + CardModel.suit_club),
	_33(33, "月神之箭", CardModel.kPlayingCardElunesArrow, "梅花4", 4, 4 + CardModel.suit_club),
	_34(34, "月神之箭", CardModel.kPlayingCardElunesArrow, "梅花5", 5, 5 + CardModel.suit_club),
	_35(35, "狂热", CardModel.kPlayingCardFanaticism, "梅花6", 6, 6 + CardModel.suit_club),
	_36(36, "狂热", CardModel.kPlayingCardFanaticism, "梅花7", 7, 7 + CardModel.suit_club),
	_37(37, "速度之靴", CardModel.function_id_flame_attack, "梅花8", 8, 8 + CardModel.suit_club),
	_38(38, "菲丽丝之戒", CardModel.function_id_flame_attack, "梅花9", 9, 9 + CardModel.suit_club),
	_39(39, "闪避护符", CardModel.function_id_flame_attack, "梅花10", 10, 10 + CardModel.suit_club),
	_40(40, "火焰攻击", CardModel.function_id_flame_attack, "红桃A", 1, 1 + CardModel.suit_heart),
	_41(41, "火焰攻击", CardModel.function_id_flame_attack, "红桃2", 2, 2 + CardModel.suit_heart),
	_42(42, "火焰攻击", CardModel.function_id_flame_attack, "红桃3", 3, 3 + CardModel.suit_heart),
	_43(43, "火焰攻击", CardModel.function_id_flame_attack, "红桃4", 4, 4 + CardModel.suit_heart),
	_44(44, "火焰攻击", CardModel.function_id_flame_attack, "红桃5", 5, 5 + CardModel.suit_heart),
	_45(45, "火焰攻击", CardModel.function_id_flame_attack, "红桃6", 6, 6 + CardModel.suit_heart),
	_46(46, "治疗药膏", CardModel.function_id_heal, "红桃7", 7, 7 + CardModel.suit_heart),
	_47(47, "治疗药膏", CardModel.function_id_heal, "红桃8", 8, 8 + CardModel.suit_heart),
	_48(48, "治疗药膏", CardModel.function_id_heal, "红桃9", 9, 9 + CardModel.suit_heart),
	_49(49, "治疗药膏", CardModel.function_id_heal, "红桃10", 10, 10 + CardModel.suit_heart),
	_50(50, "驱散", CardModel.kPlayingCardDispel, "红桃A", 1, 1 + CardModel.suit_heart),
	_51(51, "驱散", CardModel.kPlayingCardDispel, "红桃2", 2, 2 + CardModel.suit_heart),
	_52(52, "驱散", CardModel.kPlayingCardDispel, "红桃3", 3, 3 + CardModel.suit_heart),
	_53(53, "驱散", CardModel.kPlayingCardDispel, "红桃4", 4, 4 + CardModel.suit_heart),
	_54(54, "驱散", CardModel.kPlayingCardDispel, "红桃5", 5, 5 + CardModel.suit_heart),
	_55(55, "治疗药膏", CardModel.function_id_heal, "红桃6", 6, 6 + CardModel.suit_heart),
	_56(56, "治疗药膏", CardModel.function_id_heal, "红桃7", 7, 7 + CardModel.suit_heart),
	_57(57, "流浪法师斗篷", CardModel.kPlayingCardPlaneswalkersCloak, "红桃8", 8, 8 + CardModel.suit_heart),
	_58(58, "神之力量", CardModel.kPlayingCardGodsStrength, "红桃9", 9, 9 + CardModel.suit_heart),
	_59(59, "神之力量", CardModel.kPlayingCardGodsStrength, "红桃10", 10, 10 + CardModel.suit_heart),
	_60(60, "闪避", CardModel.function_id_evasion, "方块A", 1, 1 + CardModel.suit_diamond),
	_61(61, "闪避", CardModel.function_id_evasion, "方块2", 2, 2 + CardModel.suit_diamond),
	_62(62, "闪避", CardModel.function_id_evasion, "方块3", 3, 3 + CardModel.suit_diamond),
	_63(63, "闪避", CardModel.function_id_evasion, "方块4", 4, 4 + CardModel.suit_diamond),
	_64(64, "闪避", CardModel.function_id_evasion, "方块5", 5, 5 + CardModel.suit_diamond),
	_65(65, "闪避", CardModel.function_id_evasion, "方块6", 6, 6 + CardModel.suit_diamond),
	_66(66, "闪避", CardModel.function_id_evasion, "方块7", 7, 7 + CardModel.suit_diamond),
	_67(67, "闪避", CardModel.function_id_evasion, "方块8", 8, 8 + CardModel.suit_diamond),
	_68(68, "闪避", CardModel.function_id_evasion, "方块9", 9, 9 + CardModel.suit_diamond),
	_69(69, "闪避", CardModel.function_id_evasion, "方块10", 10, 10 + CardModel.suit_diamond),
	_70(70, "散失之刃", CardModel.kPlayingCardDiffusalBlade, "方块A", 1, 1 + CardModel.suit_diamond),
	_71(71, "洛萨之锋", CardModel.kPlayingCardLotharsEdge, "方块2", 2, 2 + CardModel.suit_diamond),
	_72(72, "冰魄之眼", CardModel.kPlayingCardEyeOfSkadi, "方块3", 3, 3 + CardModel.suit_diamond),
	_73(73, "黯灭之刃", CardModel.kPlayingCardStygianDesolator, "方块4", 4, 4 + CardModel.suit_diamond),
	_74(74, "散夜对剑", CardModel.kPlayingCardSangeAndYasha, "方块5", 5, 5 + CardModel.suit_diamond),
	_75(75, "攻击之爪", CardModel.kPlayingCardBladesOfAttack, "方块6", 6, 6 + CardModel.suit_diamond),
	_76(76, "恶魔刀锋", CardModel.kPlayingCardDemonEdge, "方块7", 7, 7 + CardModel.suit_diamond),
	_77(77, "圣者遗物", CardModel.kPlayingCardSacredRelic, "方块8", 8, 8 + CardModel.suit_diamond),
	_78(78, "刃甲", CardModel.kPlayingCardBladeMail, "方块9", 9, 9 + CardModel.suit_diamond),
	_79(79, "闪避", CardModel.function_id_evasion, "方块10", 10, 10 + CardModel.suit_diamond)
	
	;
	// == basic ==========================================
	// == equipment ==========================================
	// == magic cards ==========================================
	public static final int suit_club = 100; // 草花
	public static final int suit_spade = 1000; // 黑桃
	public static final int suit_diamond = 10000;
	public static final int suit_heart = 100000;
	
	public static final int
			function_id_base = -100,
			function_id_normal_attack = function_id_base - 20,
			function_id_flame_attack = function_id_base - 21,
			function_id_chaos_attack = function_id_base - 22,
			function_id_heal = function_id_base - 23,
			function_id_evasion = function_id_base - 24,
			kPlayingCardGodsStrength = function_id_base - 25,
			function_id_s_viper_raid = function_id_base - 26,
			kPlayingCardTimeLock = function_id_base - 27,
			kPlayingCardSunder = function_id_base - 28,
			function_id_s_LagunaBlade = function_id_base - 29,
			kPlayingCardFanaticism = function_id_base - 30,
			kPlayingCardMislead = function_id_base - 31,
			kPlayingCardChakra = function_id_base - 32,
			kPlayingCardWildAxe = function_id_base - 33,
			kPlayingCardDispel = function_id_base - 34,
			kPlayingCardDisarm = function_id_base - 35,
			kPlayingCardElunesArrow = function_id_base - 36,
			kPlayingCardEnergyTransport = function_id_base - 37,
			kPlayingCardGreed = function_id_base - 38,
			kPlayingCardSirenSong = function_id_base - 39,
			kPlayingCardEyeOfSkadi = function_id_base - 40,
			kPlayingCardBladesOfAttack = function_id_base - 41,
			kPlayingCardSacredRelic = function_id_base - 42,
			kPlayingCardDemonEdge = function_id_base - 43,
			kPlayingCardDiffusalBlade = function_id_base - 44,
			kPlayingCardLotharsEdge = function_id_base - 45,
			kPlayingCardStygianDesolator = function_id_base - 45,
			kPlayingCardSangeAndYasha = function_id_base - 47,
			kPlayingCardPlunderAxe = function_id_base - 48,
			kPlayingCardMysticStaff = function_id_base - 49,
			kPlayingCardEaglehorn = function_id_base - 50,
			kPlayingCardQuellingBlade = function_id_base - 51,
			kPlayingCardPhyllisRing = function_id_base - 52,
			kPlayingCardBladeMail = function_id_base - 53,
			kPlayingCardBootsOfSpeed = function_id_base - 54,
			kPlayingCardPlaneswalkersCloak = function_id_base - 55,
			kPlayingCardTalismanOfEvasion = function_id_base - 56
			
			;
	
	private int cardId;
	private String name;
	private int functionId;
	
	private String cardFace;
	private int pokerValue;
	private int suit; // 花色
	
	private CardModel(int cardId, String name, int functionId, String face, int pokerValue,
			int suit) {
		this.cardId = cardId;
		this.name = name;
		this.functionId = functionId;
		this.cardFace = face;
		this.pokerValue = pokerValue;
		this.suit = suit;
	}
	
	public int getCardId() {
		return cardId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getFunctionId() {
		return functionId;
	}
	
	public String getCardFace() {
		return cardFace;
	}
	
	public int getPokerValue() {
		return pokerValue;
	}
	
	public String getColor() {
		return suit >= CardModel.suit_heart ? "red" : "black";
	}
	
	public int getSuit() {
		return suit;
	}
	
	public static int getFunctionById(int id) {
		return CardModel.valueOf("_" + id).getFunctionId();
		
	}
	
	public int[] getObjectByFieldValue(String fieldValue) {
		int[] result = {};
		if (fieldValue.equals("普通攻击")) {
			result = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		} else if (fieldValue.equals("闪避")) {
			result = new int[] { 60, 61, 62, 63, 64, 65, 66, 67, 68, 69 };
		}
		return result;
	}
	
	private CardModel() {
		
	}
}
