package com.electrotank.examples.chatlogger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dota.component.CardModel;
import com.wolf.dota.component.CharacterEnum;
import com.wolf.dota.component.DeskModel;
import com.wolf.dota.component.Player;
import com.wolf.dota.component.constants.Code;
import com.wolf.dota.component.constants.Commands;
import com.wolf.dota.component.constants.Params;


public class GamePlugin extends BasePlugin implements Code, Commands, Params {
    
    private int timerCallback = -1;
    private List<CharacterEnum> allCharactersForChoose = new ArrayList<CharacterEnum>();
    private List<String> players;
    private String currentPlayer;
    private Player[] realPlayers;
    private int[] playerChoseCharactors;
    private boolean gameStarted = false;
    private List<CardModel> cardStack;
    private List<Integer> dropStack;
    
    private String[] playerStates;
    private final String player_state_character_confirmed = "char_confirmed";
    private final String player_state_staked = "staked";
    private final String player_state_waiting_for_stake = "wait_stake";
    // 势力
    private Integer[] force;
    private final int force_a = 1;
    private final int force_b = 5;
    
    /******** game state start ********/
    private final int playerFlagInGameState = 1;
    private final int stateFlagInGameState = 0;
    private final int gameStage_none = -1;
    private final int playerIndex_none = -1;
    private int[] gameState = new int[2];
    {
        gameState[stateFlagInGameState] = gameStage_none;
        gameState[playerFlagInGameState] = playerIndex_none;
    }
    
    private int[] playerStakes;
    @SuppressWarnings("unused")
    private DeskModel desk;
    
    /******** action caches *********/
    private final int actionCacheNone = -1;
    private int actionCache = actionCacheNone;
    private int skillCache = actionCacheNone;
    private boolean isSkillCache = false;
    private int additionalEffect = actionCacheNone;
    private String userCacheNone = "";
    private String attackerCache = userCacheNone;
    private String targetCache = userCacheNone;
    private boolean strengthenCache = false;
    
    
    /******** game state end ********/
    
    @Override
    public void init(EsObjectRO parameters) {
        
        initCharactorsRandomly();
        getApi().getLogger().debug("GamePlugin initialized");
        //        startTicker();
        d.debug(logprefix + " v 0.05");
    }
    
    
    private void initCharactorsRandomly() {
        
        allCharactersForChoose = Arrays.asList(CharacterEnum.values());
        Collections.shuffle(allCharactersForChoose);
        d.debug("List<CharacterEnum> charactors is ready");
    }
    
    
    @Override
    public void request(String user, EsObjectRO message) {
        
        EsObject messageIn = new EsObject();
        messageIn.addAll(message);
        getApi().getLogger().debug(user + " requests: " + messageIn.toString());
        
        int action = messageIn.getInteger(code_action);
        
        if (action == ACTION_START_GAME && !gameStarted) {
            getApi().getLogger().debug("got action start game");
            players = new ArrayList<String>();
            reorderUsers();
            
            /* init information depending on player size and order */
            initSizes();
            chooseCharacters(messageIn);
            gameStarted = true;
            
        } else if (action == ACTION_CHOSE_CHARACTER) {
            choseCharacter(user, messageIn);
        } else if (action == ACTION_DRAW_CARDS) {
            dispatchHandCards(user);
            EsObject obj = new EsObject();
            obj.setInteger(code_client_action_required, ac_require_play);
            sendGamePluginMessageToUser(user, obj);
        } else if (action == ACTION_STAKE) {
            gotStakeCard(user, messageIn);
        } else if (action == ACTION_USED_CARD) {
            usedCard(user, messageIn);
        } else if (action == ACTION_CANCEL) {
            hitted(user, messageIn);
        } else if (action == ACTION_CONTINUE_PLAYING) {
            continuePlay(user, messageIn);
        } else if (action == ACTION_DROP_CARDS) {
            user_action_drop_cards(user, messageIn);
        } else if (action == ACTION_GUESS_GOLOR) {
            m_Chakra_guess(user, messageIn);
        } else if (action == ACTION_CHOOSED_CARD) {
            if (isSkillCache) {
                p_netherSwapChoosedCard(user, messageIn);
            } else {
                m_choosed_card(user, messageIn);
            }
        } else if (action == ACTION_RESPOND_shenmied) {
            hitted(user, messageIn);
        } else if (action == ACTION_USED_SKILL) {
            usedSkill(user, messageIn);
        } else if (action == ACTION_TURN_FINISH_DROP_CARD) {
            finishTurnDropCard(user, messageIn);
        } else if (action == ACTION_START_TURN_FINISH_STAGE) {
            startFinishTurnDropCardStage(user, messageIn);
        } else if (action == ACTION_SPECIFY_INDEX) {
            specifyIndex(user, messageIn);
        }
    }
    
    
    private void specifyIndex(String user, EsObject messageIn) {//lifeBreak
    
        int[] cardIndexes = messageIn.getIntegerArray(INDEX, new int[] {});
        //        int[] cardIds = new int[cardIndexes.length];
        //        Player p = realPlayers[players.indexOf(targetCache)];
        //        
        //        for (int i = 0; i < cardIndexes.length; i++) {
        //            cardIds[i] = p.getHandCards().get(cardIndexes[i]);
        //        }
        
        EsObject toTarget = new EsObject();
        toTarget.setInteger(code_client_action_required, ac_require_p_drop_card_by_index);
        toTarget.setIntegerArray(INDEX, cardIndexes);
        this.sendGamePluginMessageToUser(targetCache, toTarget);
        
        
        isSkillCache = false;
        skillCache = actionCacheNone;
        targetCache = userCacheNone;
        
    }
    
    
    private void startFinishTurnDropCardStage(String user, EsObject messageIn) {
        
        EsObject eso = new EsObject();
        eso.setInteger(code_client_action_required, ac_require_turn_end_drop);
        this.sendGamePluginMessageToUser(user, eso);
        
    }
    
    
    private void finishTurnDropCard(String user, EsObject meIn) {
        
        int[] handCards = meIn.getIntegerArray(HAND_CARDS);
        int[] dropCards = meIn.getIntegerArray(USED_CARDS);
        for (int i = 0; i < dropCards.length; i++) {
            dropStack.add(dropCards[i]);
        }
        realPlayers[players.indexOf(currentPlayer)].setHandCardArray(handCards);
        gameTurn(players.indexOf(currentPlayer) + 1);
    }
    
    
    private void m_choosed_card(String user, EsObject messageIn) {
        
        int functionId = CardModel.getFunctionById(actionCache);
        switch (functionId) {
            case CardModel.function_id_m_Greed: {
                m_Greed_picked(user, messageIn);
                break;
            }
            case CardModel.function_id_m_EnergyTransport: {
                m_EnergyTransport_result(user, messageIn);
                break;
            }
        }
    }
    
    
    private void m_Chakra_guess(String user, EsObject messageIn) {
        
        int color = messageIn.getInteger(TARGET_COLOR);
        CardModel preparedCard = cardStack.get(0);
        d.debug(logprefix + "cardStack size : " + cardStack.size());
        cardStack.remove(0);
        checkNoMoreCard();
        EsObject sending = new EsObject();
        if (preparedCard.getColorCode() == color) {
            
            sending.setInteger(code_action, ACTION_GUESS_SUCCESS);
            sending.setString(action, action_continue_chakra);
            sending.setInteger(code_client_action_required, ac_require_somebody_using_magic);
            sending.setIntegerArray(DISPATCH_CARDS, new int[] { preparedCard.getCardId() });
        } else {
            sending.setInteger(code_client_action_required, ac_require_play);
            sending.setIntegerArray(MISGUESSED_CARD, new int[] { preparedCard.getCardId() });
            actionCache = actionCacheNone;
        }
        sendGamePluginMessageToUser(user, sending);
    }
    
    
    private void continuePlay(String user, EsObject messageIn) {
        
        EsObject turner = new EsObject();
        turner.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(currentPlayer, turner);
        
    }
    
    
    private void usedSkill(String player, EsObject meIn) {
        
        int[] cards = meIn.getIntegerArray(USED_CARDS, new int[] {});
        for (int i = 0; i < cards.length; i++) {
            dropStack.add(cards[i]);
        }
        
        int skill = meIn.getInteger(USED_SKILL);
        switch (skill) {
        /** Slayer   秀逗魔导士 */
            case Player.function_id_p_lagunaBlade: {
                s_LagunaBlade(player, meIn, true);
                break;// 神灭斩                       2张红色手牌当神灭斩
            }
            /** VengefulSpirit   复仇之魂 */
            case Player.function_id_p_netherSwap: {
                p_netherSwap(player, meIn);
                break;//移形换位    
            }
            /** Bristleback   刚被兽 */
            case Player.function_id_p_warpath: {
                
                break;//战意,  每受到一次伤害, 可进行一次判定, 若为红色, 则可指定任何一个人弃置1闪或对其造成1伤害
            }
            /** SacredWarrior    神灵武士 */
            case Player.function_id_p_lifeBreak: {
                p_lifeBreak(player, meIn);
                break;//牺牲       对自己造成1点伤害, 弃置指定角色2张手牌
            }
            /** KeeperOfTheLight    光之守卫 */
            case Player.function_id_p_illuminate: {
                
                break;//冲击波              弃置3张不同花色的牌,  对指定非自己的1~2名角色造成1点伤害
            }
            case Player.function_id_p_chakraMagic: {
                
                break;//查克拉            可以将1张手牌当1张查克拉使用
            }
            case Player.function_id_p_grace: {
                
                break;//恩惠               这个角色的查克拉可以对任一名角色使用
            }
            /** Antimage    敌法师 */
            case Player.function_id_p_manaBreak: {
                
                break;//法力损毁              攻击成功后弃置对方一张手牌
            }
            case Player.function_id_p_blink: {
                
                break;//闪烁               黑色手牌当闪
            }
            case Player.function_id_p_manaVoid: {
                
                break;//法力虚空         3怒气,  造成X = 手牌上限-手牌数         点伤害
            }
        }
    }
    
    
    /**
     * 移形换位,   抽对方一张牌, 给对方一张牌, 但不能是同一张牌
     * 1, 发来移形换位,  + target,
     * 2, 返回给swap user 手牌数,  
     * 3, swap user 发来抽的index, 给的id,  
     * 4, 发给swap target要弃掉的index和给他的牌
     * 5, 发给swap user continue play
     */
    private void p_netherSwap(String player, EsObject meIn) {
        
        isSkillCache = true;
        targetCache = meIn.getStringArray(TARGET_PLAYERS)[0];
        //        int swapTargetHandcardCount = realPlayers[players.indexOf(targetCache)].getHandCards().size();
        EsObject obj = new EsObject();
        obj.setInteger(code_client_action_required, ac_require_p_netherSwap_user_picking);
        this.sendGamePluginMessageToUser(player, obj);
        
    }
    
    
    private void p_netherSwapChoosedCard(String user, EsObject message) {
        
        EsObject toSwapTarget = new EsObject();
        toSwapTarget.setInteger(code_client_action_required, ac_require_p_swap_cards);
        toSwapTarget.setIntegerArray(INDEX, message.getIntegerArray(INDEX));
        toSwapTarget.setIntegerArray(DISPATCH_CARDS, message.getIntegerArray(USED_CARDS));
        
        this.sendGamePluginMessageToUser(targetCache, toSwapTarget);
        
        isSkillCache = false;
        targetCache = userCacheNone;
        
        // TODO Auto-generated method stub
        
    }
    
    
    /**
     * 拿到出了技能后
     * 1, 先发给技能使用者掉血和摸牌
     * 2, 收到ACTION_SPECIFY_INDEX, 指定弃掉对手的牌的index
     * 3, 发给对手要弃掉指定的index
     * 4, 对手再发来弃掉的牌的id们,
     * 5, 发给使用者继续play
     * 
     */
    private void p_lifeBreak(String player, EsObject meIn) {
        
        isSkillCache = true;
        skillCache = Player.function_id_p_lifeBreak;
        targetCache = meIn.getStringArray(TARGET_PLAYERS, new String[] { userCacheNone })[0];
        
        
        EsObject toLifeBreakUser = new EsObject();
        toLifeBreakUser.setInteger(code_client_action_required, ac_require_p_specify_target_index);
        toLifeBreakUser.setInteger(HP_CHANGED, -1);
        toLifeBreakUser.setInteger(TARGET_CARD_COUNT, 2);
        this.sendGamePluginMessageToUser(currentPlayer, toLifeBreakUser);
        
        //        EsObject toLifeBreakTarget = new EsObject();
        //        toLifeBreakTarget.setInteger(code_client_action_required, ac_require_p_drop_card_by_index);
        //        toLifeBreakTarget.setIntegerArray(name, value);
        
        
    }
    
    
    private void usedCard(String player, EsObject messageIn) {
        
        syncHp(player, messageIn);
        
        int[] cards = messageIn.getIntegerArray(USED_CARDS);
        if (cards == null || cards.length == 0) {
            EsObject obj = new EsObject();
            obj.setString(error, "no card used");
            return;
        }
        if (cards.length > 1) {
            // todo, ...
            return;
        }
        realPlayers[players.indexOf(player)].removeCards(cards);
        
        if (CardModel.isEquipment(CardModel.getFunctionById(cards[0]))) {
            continuePlay(currentPlayer, new EsObject());
        } else {
            
            int function = CardModel.getFunctionById(cards[0]);
            switch (function) {
                case CardModel.function_id_normal_attack:
                case CardModel.function_id_flame_attack:
                case CardModel.function_id_chaos_attack: {
                    attack(player, messageIn);
                    break;
                }
                case CardModel.function_id_evasion: {
                    evasion(player, messageIn);
                    break;
                }
                case CardModel.function_id_heal: {
                    heal(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_ElunesArrow: {// done
                    m_ElunesArrow(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Chakra: {// done
                    m_Chakra(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_EnergyTransport: {// m_2
                    m_EnergyTransport(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Fanaticism: {// done
                    m_Fanaticism(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Greed: {// did
                    m_Greed(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Mislead: {// did
                    m_Mislead(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Dispel: {// m_6
                    m_Dispel(player, messageIn);
                    break;
                }
                case CardModel.function_id_m_Disarm: {// m_7
                    m_Disarm(player, messageIn);
                    break;
                }
                case CardModel.function_id_s_GodsStrength: {
                    s_GodsStrength(player, messageIn);
                    break;
                }
                case CardModel.function_id_s_LagunaBlade: {
                    s_LagunaBlade(player, messageIn, false);
                    break;
                }
                case CardModel.function_id_s_viper_raid: {
                    s_ViperRaid(player, messageIn);
                    break;
                }
            }
        }
    }
    
    
    private void syncHp(String player, EsObject messageIn) {
        
        Player p = realPlayers[players.indexOf(player)];
        p.setHp(messageIn.getInteger(HP, 1));
        
    }
    
    
    private void m_EnergyTransport(String player, EsObject messageIn) {
        
        dropCard(messageIn);
        actionCache = messageIn.getIntegerArray(USED_CARDS)[0];
        strengthenCache = true;
        int[] cards = new int[realPlayers.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = cardStack.get(0).getCardId();
            cardStack.remove(0);
            checkNoMoreCard();
        }
        EsObject cardObject = new EsObject();
        cardObject.setInteger(code_action, ACTION_SEND_CARDS);
        cardObject.setInteger(code_client_action_required, ac_require_choosing);
        cardObject.setIntegerArray(DISPATCH_CARDS, cards);
        sendGamePluginMessageToUser(player, cardObject);
    }
    
    
    private void m_EnergyTransport_result(String user, EsObject mi) {
        
        int[] cards = mi.getIntegerArray(DISPATCH_CARDS);
        int startCount = players.indexOf(user);
        for (int i = 0; i < cards.length; i++) {
            int userPosition = (i + startCount);
            userPosition = userPosition < cards.length ? userPosition : (userPosition - cards.length);
            String cardDeliverTo = players.get(userPosition);
            EsObject obj = new EsObject();
            obj.setIntegerArray(DISPATCH_CARDS, cards);
            obj.setInteger(code_action, ACTION_SEND_CARDS);
            sendGamePluginMessageToUser(cardDeliverTo, obj);
        }
        EsObject continuePlayObj = new EsObject();
        continuePlayObj.setInteger(code_client_action_required, ac_require_play);
        continuePlayObj.setInteger(code_action, ACTION_CONTINUE_PLAYING);
        if (strengthenCache == true) {
            int card = cardStack.get(0).getCardId();
            cardStack.remove(0);
            checkNoMoreCard();
            continuePlayObj.setInteger(DISPATCH_CARDS, card);
        }
        strengthenCache = false;
        actionCache = actionCacheNone;
        sendGamePluginMessageToUser(currentPlayer, continuePlayObj);
        
    }
    
    
    /**
     * rule:
     * 1, 抽取并暗至目标的2张手牌
     *    或者抽取装备区里的1张牌
     * 2, 目標抽取我的一張手牌
     * 3, 我拿走步驟1抽出的牌
     * 
     * process:
     * greed user 先发来一个greed请求, 
     * server 再分别把2个玩家的卡牌发给对方让他们抽
     * clients 再各自把抽完的牌发给server
     * 
     * 
     * to Source:
     * 
     * 
     * 
     * @param player 出greed那个玩家
     */
    private void m_Greed(String user, EsObject messageIn) {
        
        dropCard(messageIn);
        actionCache = messageIn.getIntegerArray(USED_CARDS)[0];
        Player player = realPlayers[players.indexOf(user)];
        player.removeCards(messageIn.getIntegerArray(USED_CARDS, new int[] {}));
        String source = user;
        String target = messageIn.getStringArray(TARGET_PLAYERS)[0];
        
        EsObject toSource = new EsObject();
        //      toSource.setIntegerArray(TARGET_CARD, realPlayers[players.indexOf(target)].getHandCardsArray());
        toSource.setInteger(TARGET_CARD_COUNT, realPlayers[players.indexOf(target)].getHandCards().size());
        toSource.setIntegerArray(TARGET_EQUIPMENTS, realPlayers[players.indexOf(target)].getWeapons());
        toSource.setInteger(code_client_action_required, ac_require_choosing);
        toSource.setString(action, action_pick_for_using_gree);
        sendGamePluginMessageToUser(source, toSource);
        
        strengthenCache = messageIn.getBoolean(STRENGTHED, false);
        if (!strengthenCache) {
            
            EsObject toTarget = new EsObject();
            //        toTarget.setIntegerArray(TARGET_CARD, realPlayers[players.indexOf(source)].getHandCardsArray());
            toTarget.setInteger(TARGET_CARD_COUNT, realPlayers[players.indexOf(source)].getHandCards().size());
            //        toSource.setInteger(code_client_action_required, ac_requre_choosing);
            toTarget.setInteger(code_client_action_required, ac_require_targetted_and_choosing);
            toTarget.setIntegerArray(USED_CARDS, messageIn.getIntegerArray(USED_CARDS));
            toTarget.setString(action, action_pick_for_targetted_by_gree);
            sendGamePluginMessageToUser(target, toTarget);
        }
        attackerCache = user;
        targetCache = greedTarget = target;
    }
    
    
    boolean greedCaching = false;
    int greedUserChoseTypeCache = -1;
    int[] greedUserChoseIndexCache = { -1, -1 };
    int greedUserChoseEquipIdCache = -1;
    int greedTargetChoseIndexCache = -1;
    String greedTarget = userCacheNone;
    int[] greedUserHandcards = new int[] {};
    int[] greedTargetHandcards = new int[] {};
    
    
    private synchronized void m_Greed_picked(String user, EsObject messageIn) {
        
        if (user.equals(currentPlayer)) {
            m_Greed_user_picking(user, messageIn);
        } else {
            m_Greed_target_picking(user, messageIn);
        }
        d.debug("attackerCache: " + attackerCache);
        d.debug("targetCache: " + targetCache);
        d.debug("strengthenCache: " + strengthenCache);
        d.debug("greedCaching: " + greedCaching);
        if (strengthenCache) {
            objToUser();
            objToTarget();
            clearGreedCacheAndContinuePlay();
        } else if (attackerCache.equals(userCacheNone) && targetCache.equals(userCacheNone) && (greedCaching == false)) {
            objToUser();
            objToTarget();
            clearGreedCacheAndContinuePlay();
            
        }
        
        //        EsObject continuePlayObh = new EsObject();
        //        continuePlayObh.setInteger(code_client_action_required, ac_require_play);
        //        sendGamePluginMessageToUser(currentPlayer, continuePlayObh);
        
    }
    
    
    private void clearGreedCacheAndContinuePlay() {
        
        greedCaching = false;
        greedUserChoseTypeCache = -1;
        greedUserChoseIndexCache = new int[] { -1, -1 };
        greedUserChoseEquipIdCache = -1;
        greedTargetChoseIndexCache = -1;
        strengthenCache = false;
        attackerCache = userCacheNone;
        targetCache = userCacheNone;
        greedTarget = userCacheNone;
        
        EsObject continuePlay = new EsObject();
        continuePlay.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(currentPlayer, continuePlay);
        
    }
    
    
    private void objToTarget() {
        
        EsObject obj = new EsObject();
        obj.setInteger(code_action, ACTION_GREED_TRANSFER_ACTION);
        obj.setInteger(code_client_action_required, ac_require_greed_transfer_card);
        if (strengthenCache) {
            obj.setIntegerArray(DISPATCH_CARDS, new int[] { greedTargetChoseIndexCache });
        } else {
            obj.setIntegerArray(DISPATCH_CARDS, new int[] { greedUserHandcards[greedTargetChoseIndexCache] });
        }
        obj.setIntegerArray(GREED_LOSE_CARDS, cardIndexToCardId(greedUserChoseIndexCache, greedTargetHandcards));
        obj.setInteger(GREED_TYPE, greedUserChoseTypeCache);
        sendGamePluginMessageToUser(greedTarget, obj);
        
        
    }
    
    
    private int[] cardIndexToCardId(int[] indexes, int[] idList) {
        
        int[] result = new int[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            result[i] = idList[indexes[i]];
        }
        return result;
    }
    
    
    private void objToUser() {
        
        d.debug(logprefix + " function: objToUser");
        EsObject obj = new EsObject();
        obj.setInteger(code_action, ACTION_GREED_TRANSFER_ACTION);
        obj.setInteger(code_client_action_required, ac_require_greed_transfer_card);
        obj.setIntegerArray(DISPATCH_CARDS, cardIndexToCardId(greedUserChoseIndexCache, greedTargetHandcards));
        if (strengthenCache) {
            obj.setIntegerArray(GREED_LOSE_CARDS, new int[] { greedTargetChoseIndexCache });
        } else {
            obj.setIntegerArray(GREED_LOSE_CARDS, cardIndexToCardId(new int[] { greedTargetChoseIndexCache }, greedUserHandcards));
        }
        sendGamePluginMessageToUser(currentPlayer, obj);
        
    }
    
    
    private void m_Greed_target_picking(String user, EsObject messageIn) {
        
        greedCaching = !greedCaching;
        greedTargetChoseIndexCache = messageIn.getIntegerArray(INDEX)[0];
        d.debug("target, " + user + ", " + realPlayers[players.indexOf(user)].getHandCards().toString());
        d.debug("currentPlayer, " + currentPlayer + ", " + realPlayers[players.indexOf(currentPlayer)].getHandCards().toString());
        attackerCache = userCacheNone;
        greedTargetHandcards = messageIn.getIntegerArray(HAND_CARDS);
    }
    
    
    private void m_Greed_user_picking(String user, EsObject messageIn) {
        
        greedCaching = !greedCaching;
        int type = greedUserChoseTypeCache = messageIn.getInteger(GREED_TYPE);
        switch (type) {
            case type_quipment: {
                greedUserChoseEquipIdCache = messageIn.getIntegerArray(TARGET_CARD)[0];
                break;
            }
            case type_hand: {
                greedUserChoseIndexCache = messageIn.getIntegerArray(INDEX);
                break;
            }
        }
        if (strengthenCache) {
            greedTargetChoseIndexCache = messageIn.getIntegerArray(GREED_SEND_CARDS)[0];// this is not a index if strengthened
            greedTargetHandcards = realPlayers[players.indexOf(greedTarget)].getHandCardsArray();
        }
        
        greedUserHandcards = messageIn.getIntegerArray(HAND_CARDS);
        d.debug("targetCache, " + targetCache + ", " + realPlayers[players.indexOf(targetCache)].getHandCards().toString());
        d.debug("currentPlayer, " + currentPlayer + ", " + realPlayers[players.indexOf(currentPlayer)].getHandCards().toString());
        targetCache = userCacheNone;
    }
    
    
    private void m_Fanaticism(String player, EsObject messageIn) {
        
        int[] cards = messageIn.getIntegerArray(USED_CARDS, new int[] {});
        if (dropStack != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        } else {
            d.debug("drop stack is null");
            return;
        }
        EsObject obj = new EsObject();
        obj.setInteger(code_client_action_required, ac_require_magic_hitted);
        obj.setInteger(HP_CHANGED, -1);
        addSp(obj, 1);
        sendGamePluginMessageToUser(player, obj);
        
        EsObject continuePlay = new EsObject();
        continuePlay.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(player, continuePlay);
        
    }
    
    
    private void m_Chakra(String player, EsObject messageIn) {
        
        int[] cards = messageIn.getIntegerArray(USED_CARDS, new int[] {});
        if (dropStack != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        } else {
            d.debug("drop stack is null");
            return;
        }
        actionCache = cards[0];
        d.debug("caching : " + actionCache);
        dispatchHandCards(currentPlayer, 1);
    }
    
    
    private void m_ElunesArrow(String player, EsObject messageIn) {
        
        int[] cards = messageIn.getIntegerArray(USED_CARDS, new int[] {});
        if (dropStack != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        } else {
            return;
        }
        
        actionCache = cards[0];
        EsObject elunesArrow = new EsObject();
        elunesArrow.setInteger(code_client_action_required, ac_require_arrow_drop_card);
        if (messageIn.getBoolean(STRENGTHED, false)) {
            elunesArrow.setInteger(TARGET_SUITS, messageIn.getInteger(TARGET_SUITS));
        } else {
            elunesArrow.setInteger(TARGET_COLOR, messageIn.getInteger(TARGET_COLOR));
        }
        
        elunesArrow.setIntegerArray(USED_CARDS, messageIn.getIntegerArray(USED_CARDS, new int[] {}));
        elunesArrow.setString(PLAYER_NAME, player);
        String target = messageIn.getStringArray(TARGET_PLAYERS, new String[] {})[0];
        sendGamePluginMessageToUser(target, elunesArrow);
        
    }
    
    
    private void m_Disarm(String user, EsObject messageIn) {
        
        String target = messageIn.getStringArray(TARGET_PLAYERS)[0];
        dropCard(messageIn, TARGET_CARD);
        
        EsObject obj = new EsObject();
        obj.setInteger(code_action, ACTION_LOOSE_EQUIPMENT);
        obj.setInteger(code_client_action_required, ac_require_lose_equipment);
        obj.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
        sendGamePluginMessageToUser(target, obj);
        
        
        if (messageIn.getBoolean(STRENGTHED, false)) {
            EsObject getObject = new EsObject();
            addSp(getObject, 1);
            getObject.setInteger(code_client_action_required, ac_require_play);
            getObject.setInteger(code_action, ACTION_GET_SPECIFIC_CARD_DISARM);
            getObject.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
            sendGamePluginMessageToUser(user, getObject);
        }
    }
    
    
    private void m_Dispel(String user, EsObject obj) {
        
        actionCache = actionCacheNone;
        additionalEffect = actionCacheNone;
        int[] cards = obj.getIntegerArray(USED_CARDS);
        if (cards != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        }
    }
    
    
    private void m_Mislead(String user, EsObject obj) {
        
        dropCard(obj);
        String[] ps = obj.getStringArray(TARGET_PLAYERS);
        String spLoster = ps[0];
        String spGainer = ps[1];
        
        
        // what source player happen
        EsObject spLost = new EsObject();
        spLost.setInteger(code_action, ACTION_SP_LOST);
        spLost.setInteger(code_client_action_required, ac_require_misleaded);
        addSp(spLost, -1);
        int card = cardStack.get(0).getCardId();
        realPlayers[players.indexOf(spLoster)].addHandCard(card);
        cardStack.remove(0);
        checkNoMoreCard();
        spLost.setIntegerArray(DISPATCH_CARDS, new int[] { card });
        sendGamePluginMessageToUser(spLoster, spLost);
        
        
        // what target player happen
        EsObject spGain = new EsObject();
        spGain.setInteger(code_action, ACTION_SP_UP);
        spGain.setInteger(code_client_action_required, ac_require_sp_got);
        
        addSp(spGain, 1);
        sendGamePluginMessageToUser(spGainer, spGain);
        
        
    }
    
    
    private void addSp(EsObject obj, int i) {
        
        obj.setInteger(SP_CHANGED, i);
        
    }
    
    
    private void s_ViperRaid(String user, EsObject messageIn) {
        
        int usedCard = actionCache = messageIn.getIntegerArray(USED_CARDS)[0];
        dropStack.add(usedCard);
        //        realPlayers[players.indexOf(user)].removeCard(usedCard);
        String target = messageIn.getStringArray(TARGET_PLAYERS)[0];
        
        EsObject obj = new EsObject();
        obj.setInteger(code_client_action_required, ac_require_s_ViperRaid);
        this.sendGamePluginMessageToUser(target, obj);
        
        EsObject toUser = new EsObject();
        toUser.setInteger(code_action, ACTION_SP_LOST);
        toUser.setInteger(code_client_action_required, ac_require_s_skill_used_sp_lost);
        addSp(toUser, -2);
        this.sendGamePluginMessageToUser(user, toUser);
        
    }
    
    
    private void s_GodsStrength(String user, EsObject obj) {
        
        additionalEffect = obj.getInteger(code_action);
        int[] cards = obj.getIntegerArray(USED_CARDS);
        if (cards != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
            additionalEffect = cards[0];
        }
        
        EsObject o = new EsObject();
        int fetchCard = cardStack.get(0).getCardId();
        cardStack.remove(0);
        checkNoMoreCard();
        o.setInteger(code_client_action_required, ac_require_s_skill_used_sp_lost);
        o.setInteger(code_action, ACTION_SEND_CARDS);
        o.setIntegerArray(DISPATCH_CARDS, new int[] { fetchCard });
        addSp(o, -2);
        realPlayers[players.indexOf(user)].addHandCard(fetchCard);
        sendGamePluginMessageToUser(user, o);
        
        EsObject co = new EsObject();
        co.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(currentPlayer, co);
        
    }
    
    
    private void s_LagunaBlade(String user, EsObject obj, boolean skill) {
        
        if (skill) {
            skillCache = Player.function_id_p_lagunaBlade;
            isSkillCache = true;
        } else {
            actionCache = obj.getIntegerArray(USED_CARDS)[0];
        }
        EsObject toUser = new EsObject();
        toUser.setInteger(code_client_action_required, ac_require_s_skill_used_sp_lost);
        addSp(toUser, -3);
        sendGamePluginMessageToUser(user, toUser);
        //        toUser.setInteger(code_client_action_required, value);
        
        EsObject toTarget = new EsObject();
        toTarget.setInteger(code_client_action_required, ac_require_shenmied);
        sendGamePluginMessageToUser(obj.getStringArray(TARGET_PLAYERS)[0], toTarget);
        //        attack(user, obj);
    }
    
    
    private void heal(String user, EsObject obj) {
        
        obj.setInteger(code_action, ACTION_HP_RESTORE);
        obj.setInteger(HP_CHANGED, 1);
        obj.setInteger(code_client_action_required, ac_require_restored_hp);
        String[] targetPlayers = obj.getStringArray(TARGET_PLAYERS, new String[] { currentPlayer });
        if (targetPlayers.length > 0) {
            sendGamePluginMessageToUser(targetPlayers[0], obj);
        } else {
            sendGamePluginMessageToUser(currentPlayer, obj);
        }
        
        EsObject p = new EsObject();
        p.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(currentPlayer, p);
        
    }
    
    
    private void attack(String user, EsObject obj) {
        
        int[] cards = obj.getIntegerArray(USED_CARDS);
        if (cards != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        }
        actionCache = cards[0];
        attackerCache = user;
        
        EsObject requireAction = new EsObject();
        requireAction.setInteger(code_action, ACTION_TARGETTED);
        requireAction.setInteger(code_client_action_required, ac_require_attacked);
        requireAction.setIntegerArray(USED_CARDS, cards);
        requireAction.setString(PLAYER_NAME, user);
        for (String target : obj.getStringArray(TARGET_PLAYERS)) {
            sendGamePluginMessageToUser(target, requireAction);
        }
    }
    
    
    private void hitted(String user, EsObject obj) {
        
        int[] cards = obj.getIntegerArray(USED_CARDS, new int[] {});
        if (cards != null && cards.length > 0) {
            for (int card : cards) {
                dropStack.add(card);
            }
        }
        int additionalHit = CardModel.getFunctionById(additionalEffect) == CardModel.function_id_s_GodsStrength ? 1 : 0;
        Player p = realPlayers[players.indexOf(currentPlayer)];
        if (p.getHeroId() == hero_id_kSacredWarrior && p.getHp() <= 2) {
            additionalHit = additionalHit + 1;
        }
        
        if (isSkillCache) {
            switch (skillCache) {
                case Player.function_id_p_lagunaBlade: {
                    EsObject hurt = new EsObject();
                    hurt.setInteger(code_client_action_required, ac_require_magic_hitted);
                    int[] evations = obj.getIntegerArray(USED_CARDS, new int[] {});
                    int damageAmount = 3 - evations.length;
                    dropCard(obj);
                    addSp(hurt, damageAmount);
                    hurt.setInteger(HP_CHANGED, -damageAmount);
                    sendGamePluginMessageToUser(user, hurt);
                    
                    EsObject hurter = new EsObject();
                    hurter.setInteger(code_client_action_required, ac_require_play);
                    sendGamePluginMessageToUser(currentPlayer, hurter);
                    break;
                }
            }
            
            isSkillCache = false;
            skillCache = actionCacheNone;
        } else {
            if (actionCache < 0) {
                d.debug("no action cache");
                return;
            }
            
            int function = CardModel.getFunctionById(actionCache);
            
            switch (function) {
                case CardModel.function_id_normal_attack: {
                    EsObject hurt = new EsObject();
                    int hurtAmount = (1 + additionalHit);
                    hurt.setInteger(code_action, ACTION_CANCEL);
                    hurt.setInteger(code_client_action_required, ac_require_attack_hitted);
                    hurt.setInteger(HP_CHANGED, -hurtAmount);
                    addSp(hurt, hurtAmount);
                    hurt.setInteger(USED_CARDS, actionCache);
                    sendGamePluginMessageToUser(user, hurt);
                    EsObject attacker = new EsObject();
                    attacker.setInteger(code_client_action_required, ac_require_made_damage);
                    sendGamePluginMessageToUser(attackerCache, attacker);
                    break;
                }
                case CardModel.function_id_flame_attack: {
                    EsObject hurt = new EsObject();
                    int hurtAmount = (1 + additionalHit);
                    hurt.setInteger(code_action, ACTION_CANCEL);
                    hurt.setInteger(code_client_action_required, ac_require_attack_hitted);
                    hurt.setInteger(HP_CHANGED, -hurtAmount);
                    addSp(hurt, hurtAmount + 1);
                    hurt.setInteger(USED_CARDS, actionCache);
                    sendGamePluginMessageToUser(user, hurt);
                    EsObject attacker = new EsObject();
                    attacker.setInteger(code_client_action_required, ac_require_made_damage);
                    sendGamePluginMessageToUser(attackerCache, attacker);
                    break;
                }
                case CardModel.function_id_chaos_attack: {
                    EsObject hurt = new EsObject();
                    int hurtAmount = (1 + additionalHit);
                    hurt.setInteger(code_action, ACTION_CANCEL);
                    hurt.setInteger(code_client_action_required, ac_require_attack_hitted);
                    hurt.setInteger(HP_CHANGED, -hurtAmount);
                    addSp(hurt, hurtAmount);
                    hurt.setInteger(USED_CARDS, actionCache);
                    sendGamePluginMessageToUser(user, hurt);
                    EsObject angry = new EsObject();
                    angry.setInteger(code_action, ACTION_SP_UP);
                    angry.setInteger(code_client_action_required, ac_require_made_damage);
                    addSp(angry, 1);
                    sendGamePluginMessageToUser(attackerCache, angry);
                    break;
                }
                case CardModel.function_id_s_LagunaBlade: {
                    EsObject hurt = new EsObject();
                    hurt.setInteger(code_client_action_required, ac_require_magic_hitted);
                    int[] evations = obj.getIntegerArray(USED_CARDS, new int[] {});
                    int damageAmount = 3 - evations.length;
                    dropCard(obj);
                    addSp(hurt, damageAmount);
                    hurt.setInteger(HP_CHANGED, -damageAmount);
                    sendGamePluginMessageToUser(user, hurt);
                    
                    EsObject hurter = new EsObject();
                    hurter.setInteger(code_client_action_required, ac_require_play);
                    sendGamePluginMessageToUser(currentPlayer, hurter);
                    break;
                }
                case CardModel.function_id_m_ElunesArrow: {
                    EsObject hurt = new EsObject();
                    int hurtAmount = 1;
                    hurt.setInteger(code_action, ACTION_CANCEL);
                    hurt.setInteger(code_client_action_required, ac_require_magic_hitted);
                    hurt.setInteger(HP_CHANGED, -hurtAmount);
                    addSp(hurt, hurtAmount);
                    hurt.setInteger(USED_CARDS, actionCache);
                    sendGamePluginMessageToUser(user, hurt);
                    EsObject attacker = new EsObject();
                    attacker.setInteger(code_client_action_required, ac_require_play);
                    sendGamePluginMessageToUser(currentPlayer, attacker);
                    break;
                }
                
            }
            
            actionCache = actionCacheNone;
            attackerCache = "";
            targetCache = "";
        }
    }
    
    
    //    private void damage(String user, int howMuch, EsObject obj) {
    //    
    //        obj.setInteger(code_action, ACTION_CANCEL);
    //        obj.setInteger(HP_CHANGED, howMuch);
    //        sendGamePluginMessageToUser(user, obj);
    //    }
    
    
    private void evasion(String user, EsObject obj) {
        
        int[] cards = obj.getIntegerArray(USED_CARDS);
        if (cards != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        }
        obj.setInteger(code_action, ACTION_REACTED);
        obj.setStringArray(TARGET_PLAYERS, new String[] { user });
        obj.setInteger(code_client_action_required, ac_require_play);
        actionCache = additionalEffect = actionCacheNone;
        sendGamePluginMessageToUser(attackerCache, obj);
        attackerCache = "";
    }
    
    
    private void dropCard(EsObject obj, String key) {
        
        int[] cards = obj.getIntegerArray(key, new int[] {});
        
        for (int card : cards) {
            dropStack.add(card);
        }
        
    }
    
    
    private void dropCard(EsObject obj) {
        
        dropCard(obj, USED_CARDS);
    }
    
    
    private void user_action_drop_cards(String user, EsObject messageIn) {
        
        if (isSkillCache) {
            switch (skillCache) {
                case Player.function_id_p_lifeBreak: {
                    int[] cardIds = messageIn.getIntegerArray(USED_CARDS);
                    for (int cardId : cardIds) {
                        dropStack.add(cardId);
                    }
                    EsObject obj = new EsObject();
                    obj.setInteger(code_client_action_required, ac_require_play);
                    this.sendGamePluginMessageToUser(currentPlayer, obj);
                    break;
                }
            }
        } else {
            
            
            if (CardModel.getFunctionById(actionCache) == CardModel.function_id_s_viper_raid) {
                int[] droppedCard = messageIn.getIntegerArray(USED_CARDS);
                for (int i = 0; i < droppedCard.length; i++) {
                    dropStack.add(droppedCard[i]);
                }
                if (droppedCard.length < 2) {
                    EsObject toVipperred = new EsObject();
                    toVipperred.setInteger(code_client_action_required, ac_require_magic_hitted);
                    toVipperred.setInteger(HP_CHANGED, -1);
                    addSp(toVipperred, 1);
                    this.sendGamePluginMessageToUser(user, toVipperred);
                    
                    
                } else {
                    EsObject toCurrentPlayer = new EsObject();
                    toCurrentPlayer.setInteger(code_client_action_required, ac_require_play);
                    this.sendGamePluginMessageToUser(currentPlayer, toCurrentPlayer);
                }
                
                actionCache = actionCacheNone;
            } else {
                int[] cards = messageIn.getIntegerArray(USED_CARDS);
                for (int card : cards) {
                    dropStack.add(card);
                }
                actionCache = actionCacheNone;
                EsObject obj = new EsObject();
                obj.setInteger(code_client_action_required, ac_require_play);
                sendGamePluginMessageToUser(currentPlayer, obj);
            }
        }
    }
    
    
    // private void dropCard(String user, EsObject obj){
    // todo user also need drop his/her cards
    
    private void initSizes() {
        
        playerChoseCharactors = new int[players.size()];
        force = new Integer[players.size()];
        playerStates = new String[players.size()];
        playerStakes = new int[players.size()];
        realPlayers = new Player[players.size()];
        // deprecating above logics, change to use desk model
        desk = new DeskModel(players.size());
    }
    
    
    private void gotStakeCard(String user, EsObject messageIn) {
        
        playerStates[players.indexOf(user)] = player_state_staked;
        playerStakes[players.indexOf(user)] = messageIn
                .getIntegerArray(USED_CARDS)[0];
        dropStack.add(playerStakes[players.indexOf(user)]);
        for (String playerState : playerStates) {
            if (!playerState.equals(player_state_staked)) { return; }
        }
        
        int biggestNumber = CardModel.valueOf("_" + playerStakes[0])
                .getPokerValue();
        int startPlayer = 0;
        for (int i = 1; i < players.size(); i++) {
            int stake = CardModel.valueOf("_" + playerStakes[i]).getPokerValue();
            if (stake > biggestNumber) {
                biggestNumber = stake;
                startPlayer = i;
            }
        }
        
        EsObject obj = new EsObject();
        obj.setInteger(code_action, ACTION_STAKE);
        obj.setIntegerArray(ALL_STAKE_CARDS, playerStakes);
        for (String p : players) {
            sendGamePluginMessageToUser(p, obj);
        }
        
        gameTurn(startPlayer);
        
    }
    
    
    private void gameTurn(int playerIndex) {
        
        int pIndex = playerIndex;
        if (pIndex >= players.size()) {
            pIndex = 0;
        }
        d.debug(logprefix + " changing current player from " + currentPlayer);
        currentPlayer = players.get(pIndex);
        d.debug(logprefix + " changing current player to " + currentPlayer);
        updateRequiredAction(currentPlayer, ac_require_turn_start);
        updateRequiredAction(currentPlayer, ac_require_draw);
    }
    
    
    private void updateRequiredAction(String player, int actionRequired) {
        
        d.debug("sending state : " + actionRequired);
        EsObject obj = new EsObject();
        obj.setInteger(code_client_action_required, actionRequired);
        sendGamePluginMessageToUser(player, obj);
        d.debug("sent state with obj: " + obj + " to player " + player);
        
    }
    
    
    /**************** logic in game loop end ***************************/
    /**************** logic before game start start ***************************/
    
    private void initCardStack() {
        
        cardStack = new LinkedList<CardModel>(Arrays.asList(CardModel.values()));
        Collections.shuffle(cardStack);
        getApi().getLogger().debug("card stack is ready to use,");
        dropStack = new LinkedList<Integer>();
    }
    
    
    private void dispatchHandCards(String player) {
        
        dispatchHandCards(player, 2);
    }
    
    
    private void dispatchHandCards(String player, int howMany) {
        
        dispatchHandCards(player, howMany, ACTION_SEND_CARDS);
    }
    
    
    //    final int
    //            testCard_1 = 18,
    //            testCard_2 = 19;
    
    
    private void dispatchHandCards(String player, int howmany, int action) {
        
        EsObject obj = new EsObject();
        //        howmany=howmany+2;// only for test
        int[] cards = new int[howmany];
        for (int i = 0; i < howmany; i++) {
            if (i < howmany) {
                cards[i] = cardStack.get(i).getCardId();
                d.debug(logprefix + "cardStack size : " + cardStack.size());
                cardStack.remove(0);
                checkNoMoreCard();
            }
            //            else {
            //                
            //                if (actionCache == actionCacheNone || CardModel.getFunctionById(actionCache) != CardModel.function_id_m_Chakra) {
            //                    cards[i] = testCard_1;
            //                    cards[i + 1] = testCard_2;
            //                }
            //                break;
            //            }
        }
        if (actionCache != actionCacheNone && CardModel.getFunctionById(actionCache) == CardModel.function_id_m_Chakra) {
            obj.setInteger(code_client_action_required, ac_require_somebody_using_magic);
            cards = new int[] { cards[0] };
        }
        if (action == ACTION_DISPATCH_HANDCARD) {
            obj.setInteger(code_client_action_required, ac_require_staking);
            obj.setString(Commands.action, action_should_stake);
        }
        obj.setInteger(code_action, action);
        obj.setIntegerArray(DISPATCH_CARDS, cards);
        realPlayers[players.indexOf(player)].addHandCards(cards);
        
        sendGamePluginMessageToUser(player, obj);
    }
    
    
    private synchronized void choseCharacter(String user, EsObject messageIn) {
        
        getApi().getLogger().debug(logprefix + "set to user : " + user + " of index in players list : " + players.indexOf(user));
        playerChoseCharactors[players.indexOf(user)] = messageIn.getInteger(SELECTED_HERO_ID);
        
        playerStates[players.indexOf(user)] = player_state_character_confirmed;
        
        // after all players has chose their characters, then dispatch states
        
        for (int i = 0; i < players.size(); i++) {
            String playerState = playerStates[i];
            if (playerState == null ||
                !playerState.equals(player_state_character_confirmed)) { return; }
        }
        sendAllHeros();
        initRealPlayers();
        initCardStack();
        dispatchForce();
        for (String player : players) {
            dispatchHandCards(player, 5, ACTION_DISPATCH_HANDCARD);
        }
        
        // then wait for stake
        for (int i = 0; i < players.size(); i++) {
            playerStates[i] = player_state_waiting_for_stake;
        }
        
    }
    
    
    private void initRealPlayers() {
        
        for (int i = 0; i < playerChoseCharactors.length; i++) {
            realPlayers[i] = Player.getPlayerById(playerChoseCharactors[i],
                                                  players.get(i));
        }
    }
    
    
    @SuppressWarnings("unused")
    private int nextPlayer() {
        
        actionCache = actionCacheNone;
        additionalEffect = actionCacheNone;
        int nextPlayerIndex = gameState[playerFlagInGameState] + 1;
        if (nextPlayerIndex > players.size()) {
            nextPlayerIndex = 0;
        }
        gameState[playerFlagInGameState] = nextPlayerIndex;
        return nextPlayerIndex;
    }
    
    
    private void sendAllHeros() {
        
        EsObject obj = new EsObject();
        obj.setInteger(code_action, ACTION_ALL_HEROS);
        obj.setIntegerArray(ALL_HEROS, playerChoseCharactors);
        for (String player : players) {
            sendGamePluginMessageToUser(player, obj);
        }
        
        // sendGamePluginMessageToRoom(obj);
    }
    
    
    private void dispatchForce() {
        
        EsObject obj = new EsObject();
        List<Integer> forceList = Arrays.asList(new Integer[] { force_a,
                                                               force_b });
        
        switch (players.size()) {
            case 1: {
                forceList = Arrays.asList(new Integer[] { force_a });
                break;
            }
            case 2: {
                forceList = Arrays.asList(new Integer[] { force_a, force_b });
                break;
            }
            case 3: {
                forceList = Arrays
                        .asList(new Integer[] { force_a, force_b, force_a });
                break;
            }
            case 4: {
                forceList = Arrays
                        .asList(new Integer[] { force_a, force_b, force_a,
                                               force_b });
                break;
            }
            case 5: {
                forceList = Arrays.asList(new Integer[] { force_a, force_b,
                                                         force_a, force_b,
                                                         force_a });
                break;
            }
        }
        
        Collections.shuffle(forceList);
        force = forceList.toArray(new Integer[players.size()]);
        getApi().getLogger().debug(
                                   "dispatching force: " + Arrays.toString(force));
        
        obj.setInteger(code_action, ACTION_DISPATCH_FORCE);
        obj.setInteger(STACK_CARD_COUNT, cardStack.size());
        for (int i = 0; i < force.length; i++) {
            int f = force[i];
            obj.setInteger(FORCE, f);
            sendGamePluginMessageToUser(players.get(i), obj);
        }
    }
    
    
    private void reorderUsers() {
        
        for (UserValue user : getApi().getUsersInRoom(getApi().getZoneId(),
                                                      getApi().getRoomId())) {
            players.add(user.getUserName());
        }
    }
    
    
    private void chooseCharacters(EsObject obj) {
        
        getApi().getLogger().debug("players = " + players.size());
        for (int i = 0; i < players.size(); i++) {
            int[] charsToChoose = new int[3];
            String player = players.get(i);
            for (int choosingCount = 0; choosingCount < charsToChoose.length; choosingCount++) {
                int shouldAddCharacterCount = i * charsToChoose.length
                                              + choosingCount;
                getApi().getLogger()
                        .debug(
                               "charsToChoose = "
                                       + Arrays.toString(charsToChoose) + "\n"
                                       + "choosingCount = " + choosingCount
                                       + "\n"
                                       + "shouldAddCharacterCount = "
                                       + shouldAddCharacterCount);
                charsToChoose[choosingCount] = allCharactersForChoose.get(
                                                                          shouldAddCharacterCount).getId();
            }
            obj.setInteger(code_action, ACTION_CHOOSE_CHARACTER);
            obj.setIntegerArray(CHARACTORS_TO_CHOOSE, charsToChoose);
            sendGamePluginMessageToUser(player, obj);
            getApi().getLogger().debug(
                                       "Characters " + Arrays.toString(charsToChoose)
                                               + " are sending to player " + player);
        }
        
    }
    
    
    // private void sendGamePluginMessageToRoom(EsObject obj) {
    // if (cardStack != null) {
    // obj.setInteger(STACK_CARD_COUNT, cardStack.size());
    // }
    // getApi().sendPluginMessageToRoom(getApi().getZoneId(),
    // getApi().getRoomId(), obj);
    // }
    
    
    private void addCardsForSpirit(String user, EsObject obj) {
        
        Player p = realPlayers[players.indexOf(user)];
        if (p == null) { return; }
        int heroId = p.getHeroId();
        int spChanged = obj.getInteger(SP_CHANGED, 0);
        if (heroId == hero_id_kVengefulSpirit && spChanged > 0) {
            int cardCount = obj.getInteger(SP_CHANGED) * 2;
            int[] cardArray = new int[cardCount];
            for (int i = 0; i < cardArray.length; i++) {
                cardArray[i] = cardStack.get(0).getCardId();
                cardStack.remove(0);
                checkNoMoreCard();
                
            }
            obj.setIntegerArray(DISPATCH_CARDS, cardArray);
        }
        
    }
    
    
    // 钢背兽掉血判定
    private void checkHpForBristleback(String user, EsObject obj) {
        
        Player p = realPlayers[players.indexOf(user)];
        if (p == null) { return; }
        int heroId = p.getHeroId();
        int hpChanged = obj.getInteger(HP_CHANGED, 0);
        if (heroId == hero_id_kBristleback && hpChanged < -1) {
            obj.setInteger(HP_CHANGED, hpChanged + 1);
        }
        
        
    }
    
    
    private void sendGamePluginMessageToUser(String user, EsObject obj) {
        
        addCardsForSpirit(user, obj);
        checkHpForBristleback(user, obj);
        d.debug(logprefix + " current player: " + currentPlayer);
        d.debug(logprefix + "sending plugin message to user " + user + " with obj: \r\n" + obj);
        if (cardStack != null) {
            obj.setInteger(STACK_CARD_COUNT, cardStack.size());
        }
        getApi().sendPluginMessageToUser(user, obj);
    }
    
    
    private void checkNoMoreCard() {
        
        if (cardStack.size() < 1) {
            for (int iid : dropStack) {
                cardStack.add(CardModel.valueOf("_" + iid));
            }
            Collections.shuffle(cardStack);
            dropStack = new ArrayList<Integer>();
        }
        
    }
    
    
    //    private void startTicker() {
    //    
    //        timerCallback = getApi().scheduleExecution(1000,
    //                -1,
    //                new ScheduledCallback() {
    //                    
    //                    public void scheduledCallback() {
    //                    
    //                        tick();
    //                    }
    //                });
    //    }
    
    
    @Override
    public void destroy() {
        
        getApi().cancelScheduledExecution(timerCallback);
        super.destroy();
    }
    
    
    public void tick() {
        
        EsObject message = new EsObject();
        message.setString(action, action_tick);
        message.setInteger(code_action, -9999999);
        message.setString("message ", "  test plugin message to whole room");
        getApi().sendPluginMessageToRoom(getApi().getZoneId(), getApi().getRoomId(), message);
        //        message.setString("message ", "  test plugin sending public message");
        //        getApi().sendPublicMessageToRoomFromPlugin(currentPlayer,
        //                getApi().getZoneId(), getApi().getRoomId(), " message out of EsObject ", message, true, false);
        
    }
    
    
    private static final String logprefix = "======== game =>> ";
    
    
    private class D {
        
        public void debug(String message) {
            
            getApi().getLogger().debug(message);
        }
    }
    
    
    private D d = new D();
    
}
