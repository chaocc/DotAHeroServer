package com.wolf.dotah.server.cmpnt.cardandskill;

public interface card_const {

	final int card_code_base = 10000;
	String color = "color";
	String suits = "suits";

	public interface card_type {

		final char e = 'e';// none weapon equipment
		final char w = 'w';// weapon, also equipment
		final char d = 'd';// dual hand weapon, also equipment
		final char m = 'm';// magic, no distance limited
		final char b = 'b';// basic, attacks, heal, and evasion
		final char s = 's';// super skills

		final int bCode = card_code_base + 100;
		final int mCode = card_code_base + 200;
		final int sCode = card_code_base + 300;
		final int eCode = card_code_base + 400;
		final int wCode = card_code_base + eCode + 10;
		final int dCode = card_code_base + eCode + 20;

	}

	public interface colorcon {

		String red = "red";
		String black = "black";

		int red_code = card_code_base + 50;
		int black_code = card_code_base + 60,

		invalid_client = 0, red_client = 1, // 红色
				black_client = 2; // 黑色
		int[] color_array = { red_client, black_client };

	}

	public interface suitscon {

		String heart = "heart";
		String diamond = "diamond";
		String spade = "spade";
		String club = "club";

		int heart_code = card_code_base + 10;
		int diamond_code = card_code_base + 20;
		int spade_code = card_code_base + 30;
		int club_code = card_code_base + 40,

		kCardSuitsInvalid = 0, heart_client = 1, // 红桃
				diamond_client = 2, // 方块
				spade_client = 3, // 黑桃
				club_client = 4 // 梅花
				;
		int[] suits_array = { heart_client, diamond_client, spade_client,
				club_client };
	}

	public interface func_name {

		String normal_attack = "普通攻击";
		String flame_attack = "火焰攻击";
		String chaos_attack = "混乱攻击";
		String heal = "治疗药膏";
		String evasion = "闪避";

		String god_strength = "神之力量";
		String viper_raid = "蝮蛇突袭";
		String laguna_blade = "神灭斩";

		String Fanaticism = "狂热";
		String Mislead = "误导";
		String Chakra = "查克拉";
		String Dispel = "驱散";
		String Disarm = "Disarm";
		String ElunesArrow = "ElunesArrow";
		String EnergyTransport = "EnergyTransport";
		String Greed = "Greed";
	}

	final int function_code_base = 100000;

	public interface functioncon {

		final int b_normal_attack = function_code_base + 1;
		final int b_flame_attack = function_code_base + 2;
		final int b_chaos_attack = function_code_base + 3;
		final int b_heal = function_code_base + 4;
		final int b_evasion = function_code_base + 5;

		final int s_GodsStrength = function_code_base + 6;
		final int s_viper_raid = function_code_base + 7;// 蝮蛇突袭
		final int s_LagunaBlade = function_code_base + 8;

		final int m_Fanaticism = function_code_base + 9;// 狂热
		final int m_Mislead = function_code_base + 10;// 误导
		final int m_Chakra = function_code_base + 11;
		final int m_Dispel = function_code_base + 12;// 驱散
		final int m_Disarm = function_code_base + 13;// 缴械
		final int m_ElunesArrow = function_code_base + 14;// 月神之剑
		final int m_EnergyTransport = function_code_base + 15;// 能量转移
		final int m_Greed = function_code_base + 16;
		// final int enhanced = 100;
		// final int m_enhanced_Greed = m_Greed + enhanced;
		// final int m_enhanced_ElunesArrow = m_ElunesArrow + enhanced;
		// final int m_enhanced_EnergyTransport = m_EnergyTransport + enhanced;
		// final int m_enhanced_Disarm = m_Disarm + enhanced;//缴械 强化

		final int e_BootsOfSpeed = function_code_base + 17;// 速度之靴
		final int e_PhyllisRing = function_code_base + 18;// 菲丽丝之戒
		final int e_TalismanOfEvasion = function_code_base + 19;// 闪避护符
		final int e_PlaneswalkersCloak = function_code_base + 20;// 流浪法师斗篷
		final int e_BladeMail = function_code_base + 21;// 刃甲

		final int w_DiffusalBlade = function_code_base + 22;// 散失之刃
		final int w_LotharsEdge = function_code_base + 23;// 洛萨之锋
		final int w_EyeOfSkadi = function_code_base + 24;// 冰魄之眼
		final int w_StygianDesolator = function_code_base + 25;// 黯灭之刃
		final int w_SangeAndYasha = function_code_base + 26;// 散夜对剑
		final int w_BladesOfAttack = function_code_base + 27;// 攻击之爪
		final int w_DemonEdge = function_code_base + 28;// 恶魔刀锋
		final int d_SacredRelic = function_code_base + 29;// 圣者遗物
	}

	// kHeroAttributeStrength = 1, // 力量型
	// kHeroAttributeAgility, // 敏捷型
	// kHeroAttributeIntelligence // 智力型
	// kHeroSkillCategoryActive = 0, // 主动技能
	// kHeroSkillCategoryPassive, // 被动技能
	// kHeroSkillTypeGeneral = 0, // 普通技
	// kHeroSkillTypeRestricted, // 限制技
	// kHeroSkillTypeLimited // 限定技
	public interface hero_type {

		int strengthCode = 1;
		int agilityCode = 2;
		int intelligenceCode = 3;

		String strength = "力量";
		String agility = "敏捷";
		String intelligence = "智力";

	}

	public interface hero_skill {

		public interface categorycon {

			int activeCode = 0;
			int passiveCode = 1;
			String active = "主动技";
			String passive = "被动技";
		}

		public interface typecon {

			int generalCode = 0;
			int limitedCode = 1;
			String general = "普通技";
			String limited = "限制技";
		}
	}
}