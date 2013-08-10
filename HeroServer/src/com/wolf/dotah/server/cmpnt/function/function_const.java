package com.wolf.dotah.server.cmpnt.function;


public interface function_const {
    
    final int function_code_base = 100000;
    
    
    public interface functioncon {
        
        final int b_normal_attack = function_code_base + 1;
        final int b_flame_attack = function_code_base + 2;
        final int b_chaos_attack = function_code_base + 3;
        final int b_heal = function_code_base + 4;
        final int b_evasion = function_code_base + 5;
        
        final int s_GodsStrength = function_code_base + 6;
        final int s_viper_raid = function_code_base + 7;//蝮蛇突袭
        final int s_LagunaBlade = function_code_base + 8;
        
        final int m_Fanaticism = function_code_base + 9;//狂热
        final int m_Mislead = function_code_base + 10;//误导
        final int m_Chakra = function_code_base + 11;
        final int m_Dispel = function_code_base + 12;//驱散
        final int m_Disarm = function_code_base + 13;//缴械
        final int m_ElunesArrow = function_code_base + 14;//月神之剑
        final int m_EnergyTransport = function_code_base + 15;//能量转移
        final int m_Greed = function_code_base + 16;
        
        final int m_enhanced_Greed = -m_Greed;
        final int m_enhanced_ElunesArrow = -m_ElunesArrow;
        final int m_enhanced_EnergyTransport = -m_EnergyTransport;
        final int m_enhanced_Disarm = -m_Disarm;//缴械    强化
        
        
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
}
