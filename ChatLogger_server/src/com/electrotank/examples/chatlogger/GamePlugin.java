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
import com.wolf.dota.component.constants.Commands;
import com.wolf.dota.component.constants.Params;
import com.wolf.dota.component.constants.Code;


public class GamePlugin extends BasePlugin implements Code, Commands, Params {
    
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
    private int additionalEffect = actionCacheNone;
    private String userCacheNone = "";
    private String attackerCache = userCacheNone;
    private String targetCache = userCacheNone;
    private boolean strengthenCache = false;
    
    
    /******** game state end ********/
    
    @Override
    public void init(EsObjectRO parameters) {
    
        initCharactorsRandomly();
        getApi().getLogger().debug("ChatPlugin initialized");
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
            m_choosed_card(user, messageIn);
        }
        
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
        sendGamePluginMessageToUser(user, turner);
        
    }
    
    
    private void usedCard(String player, EsObject messageIn) {
    
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
            case CardModel.function_id_m_ElunesArrow: {//done
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
        }
        
    }
    
    
    private void m_EnergyTransport(String player, EsObject messageIn) {
    
        dropCard(messageIn);
        actionCache = messageIn.getIntegerArray(USED_CARDS)[0];
        strengthenCache = true;
        int[] cards = new int[realPlayers.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = cardStack.get(0).getCardId();
            cardStack.remove(0);
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
        
        
        EsObject toTarget = new EsObject();
        //        toTarget.setIntegerArray(TARGET_CARD, realPlayers[players.indexOf(source)].getHandCardsArray());
        toTarget.setInteger(TARGET_CARD_COUNT, realPlayers[players.indexOf(source)].getHandCards().size());
        //        toSource.setInteger(code_client_action_required, ac_requre_choosing);
        toTarget.setInteger(code_client_action_required, ac_require_targetted_and_choosing);
        toTarget.setIntegerArray(USED_CARDS, messageIn.getIntegerArray(USED_CARDS));
        toTarget.setString(action, action_pick_for_targetted_by_gree);
        sendGamePluginMessageToUser(target, toTarget);
        
        
        attackerCache = user;
        targetCache = target;
        
    }
    
    
    private synchronized void m_Greed_picked(String user, EsObject messageIn) {
    
        if (user.equals(currentPlayer)) {
            EsObject toDropper = new EsObject();
            String target = targetCache;
            targetCache = userCacheNone;
            int type = messageIn.getInteger(TYPE);
            switch (type) {
                case type_quipment: {
                    toDropper.setInteger(code_client_action_required, ac_require_lose_equipment);
                    toDropper.setInteger(code_action, ACTION_LOOSE_EQUIPMENT);
                    break;
                }
                case type_hand: {
                    toDropper.setInteger(code_action, ACTION_DROP_CARDS);
                    break;
                }
            }
            toDropper.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
            
            sendGamePluginMessageToUser(target, toDropper);
            
            EsObject toPicker = new EsObject();
            toPicker.setInteger(code_action, ACTION_GET_SPECIFIC_CARD);
            toPicker.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
            
            if (attackerCache.equals(userCacheNone) && targetCache.equals(userCacheNone)) {
                toPicker.setInteger(code_client_action_required, ac_require_play);
            }
            sendGamePluginMessageToUser(currentPlayer, toPicker);
        } else {
            attackerCache = userCacheNone;
            EsObject toDropper = new EsObject();
            toDropper.setInteger(code_action, ACTION_DROP_CARDS);
            toDropper.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
            if (attackerCache.equals(userCacheNone) && targetCache.equals(userCacheNone)) {
                toDropper.setInteger(code_client_action_required, ac_require_play);
            }
            sendGamePluginMessageToUser(currentPlayer, toDropper);
            
            EsObject toPicker = new EsObject();
            toPicker.setInteger(code_action, ACTION_GET_SPECIFIC_CARD);
            toPicker.setIntegerArray(TARGET_CARD, messageIn.getIntegerArray(TARGET_CARD));
            
            
            sendGamePluginMessageToUser(user, toPicker);
        }
        
        
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
        obj.setInteger(SP_CHANGED, 1);
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
            getObject.setInteger(SP_CHANGED, 1);
            getObject.setInteger(code_client_action_required, ac_require_play);
            getObject.setInteger(code_action, ACTION_GET_SPECIFIC_CARD);
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
        spLost.setInteger(code_client_action_required, ac_require_play);
        spLost.setInteger(SP_CHANGED, -1);
        int card = cardStack.get(0).getCardId();
        realPlayers[players.indexOf(spLoster)].addHandCard(card);
        cardStack.remove(0);
        spLost.setInteger(TARGET_CARD, card);
        sendGamePluginMessageToUser(spLoster, spLost);
        
        
        // what target player happen
        EsObject spGain = new EsObject();
        spGain.setInteger(code_action, ACTION_SP_UP);
        spGain.setInteger(SP_CHANGED, 1);
        sendGamePluginMessageToUser(spGainer, spGain);
        
    }
    
    
    private void s_ViperRaid(String user, EsObject messageIn) {
    
        // TODO ?
        
    }
    
    
    private void s_GodsStrength(String user, EsObject obj) {
    
        additionalEffect = obj.getInteger(code_action);
        int[] cards = obj.getIntegerArray(USED_CARDS);
        if (cards != null) {
            for (int card : cards) {
                dropStack.add(card);
            }
        }
        
        dispatchHandCards(user, 1);
        
    }
    
    
    private void s_LagunaBlade(String user, EsObject obj) {
    
        obj.setInteger(code_action, ACTION_SP_LOST);
        obj.setInteger(SP_CHANGED, 3);
        //        spLost(user, 3, obj);
        attack(user, obj);
        
    }
    
    
    private void heal(String user, EsObject obj) {
    
        obj.setInteger(code_action, ACTION_HP_RESTORE);
        obj.setInteger(HP_CHANGED, 1);
        obj.setInteger(code_client_action_required, ac_require_restored_hp);
        sendGamePluginMessageToUser(obj.getStringArray(TARGET_PLAYERS)[0], obj);
        
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
        int additionalHit = additionalEffect == CardModel.function_id_s_GodsStrength ? 1 : 0;
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
                hurt.setInteger(SP_CHANGED, hurtAmount);
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
                hurt.setInteger(SP_CHANGED, hurtAmount + 1);
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
                hurt.setInteger(SP_CHANGED, hurtAmount);
                hurt.setInteger(USED_CARDS, actionCache);
                sendGamePluginMessageToUser(user, hurt);
                EsObject angry = new EsObject();
                angry.setInteger(code_action, ACTION_SP_UP);
                angry.setInteger(code_client_action_required, ac_require_made_damage);
                angry.setInteger(SP_CHANGED, 1);
                sendGamePluginMessageToUser(attackerCache, angry);
                break;
            }
            case CardModel.function_id_s_LagunaBlade: {
                EsObject hurt = new EsObject();
                int[] evations = obj.getIntegerArray(USED_CARDS, new int[] {});
                dropCard(hurt);
                obj.setInteger(code_action, ACTION_SP_UP);
                obj.setInteger(SP_CHANGED, 3 - evations.length);
                damage(user, -(3 - evations.length), hurt);
                break;
            }
            case CardModel.function_id_m_ElunesArrow: {
                EsObject hurt = new EsObject();
                int hurtAmount = 1;
                hurt.setInteger(code_action, ACTION_CANCEL);
                hurt.setInteger(code_client_action_required, ac_require_magic_hitted);
                hurt.setInteger(HP_CHANGED, -hurtAmount);
                hurt.setInteger(SP_CHANGED, hurtAmount);
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
    
    
    private void damage(String user, int howMuch, EsObject obj) {
    
        obj.setInteger(code_action, ACTION_CANCEL);
        obj.setInteger(HP_CHANGED, howMuch);
        sendGamePluginMessageToUser(user, obj);
    }
    
    
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
    
        int[] cards = messageIn.getIntegerArray(USED_CARDS);
        for (int card : cards) {
            dropStack.add(card);
        }
        actionCache = actionCacheNone;
        EsObject obj = new EsObject();
        obj.setInteger(code_client_action_required, ac_require_play);
        sendGamePluginMessageToUser(currentPlayer, obj);
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
        String startPlayer = players.get(0);
        for (int i = 1; i < players.size(); i++) {
            int stake = CardModel.valueOf("_" + playerStakes[i]).getPokerValue();
            if (stake > biggestNumber) {
                biggestNumber = stake;
                startPlayer = players.get(i);
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
    
    
    private void gameTurn(String player) {
    
        currentPlayer = player;
        updateRequiredAction(player, ac_require_determing);
        updateRequiredAction(player, ac_require_draw);
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
    
    
    final int
            testCard_1 = 20,
            testCard_2 = 22;
    
    
    private void dispatchHandCards(String player, int howmany, int action) {
    
        EsObject obj = new EsObject();
        int[] cards = new int[howmany + 2];
        for (int i = 0; i < howmany + 2; i++) {
            if (i < howmany) {
                cards[i] = cardStack.get(i).getCardId();
                d.debug(logprefix + "cardStack size : " + cardStack.size());
                cardStack.remove(0);
            }
            else {
                
                if (actionCache == actionCacheNone || CardModel.getFunctionById(actionCache) != CardModel.function_id_m_Chakra) {
                    cards[i] = testCard_1;
                    cards[i + 1] = testCard_2;
                }
                break;
            }
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
    
    private void sendGamePluginMessageToUser(String user, EsObject obj) {
    
        d.debug(logprefix + "sending plugin message to user " + user + " with obj: \r\n" + obj);
        if (cardStack != null) {
            obj.setInteger(STACK_CARD_COUNT, cardStack.size());
        }
        getApi().sendPluginMessageToUser(user, obj);
    }
    
    
    private static final String logprefix = "======== game =>> ";
    
    
    private class D {
        
        public void debug(String message) {
        
            getApi().getLogger().debug(message);
        }
    }
    
    
    private D d = new D();
    
}
