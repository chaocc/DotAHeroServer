package com.electrotank.examples.chatlogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.ChainAction;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserPublicMessageContext;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.electrotank.examples.chatlogger.components.CardEnum;
import com.electrotank.examples.chatlogger.components.CharacterEnum;
import com.electrotank.examples.chatlogger.components.PluginConstants;

//import org.slf4j.Logger;

public class ChatPlugin extends BasePlugin {
    
    private ChatLogger          chatLogger                       = null;
    private List<CharacterEnum> allCharactersForChoose           = new ArrayList<CharacterEnum>();
    private List<String>        players;
    private int[]               playerChoseCharactors;
    private static final String logprefix                        = "======== sol =>> ";
    private boolean             gameStarted                      = false;
    private List<CardEnum>      cardStack;
    
    private String[]            playerStates;
    private final String        player_state_character_confirmed = "char_confirmed";
    
    private Integer[]           force;                                                            //势力
    private final int           force_a                          = 1;
    private final int           force_b                          = 5;
    
    //    private PluginApi           api                    = getApi();
    
    @Override
    public void init(EsObjectRO parameters) {
        getApi().getLogger().debug("ChatPlugin initialized");
        initCharactorsRandomly();
    }
    
    private void initCharactorsRandomly() {
        allCharactersForChoose = Arrays.asList(CharacterEnum.values());
        Collections.shuffle(allCharactersForChoose);
        getApi().getLogger().debug("List<CharacterEnum> charactors is ready");
    }
    
    @Override
    public void request(String user, EsObjectRO message) {
        EsObject messageIn = new EsObject();
        messageIn.addAll(message);
        getApi().getLogger().debug(user + " requests: " + messageIn.toString());
        
        int action = messageIn.getInteger(PluginConstants.ACTION);
        
        //TODO 在发给出每人3张英雄牌之前, 要先把player list发给客户端
        
        if (action == PluginConstants.ACTION_START_GAME && !gameStarted) {
            getApi().getLogger().debug("got action start game");
            players = new ArrayList<String>();
            reorderUsers();
            
            /*  init information depending on player size and order   */
            playerChoseCharactors = new int[players.size()];
            force = new Integer[players.size()];
            playerStates = new String[players.size()];
            
            chooseCharacters(messageIn);
            gameStarted = true;
            
        } else if (action == PluginConstants.ACTION_CHOSE_CHARACTER) {
            choseCharacter(user, messageIn);
            
        } else if (action == PluginConstants.ACTION_DRAW_CARDS) {
            dispatchHandCards(user);
        }
        
        //        else if (action == PluginConstants.ACTION_CONFIRM_INIT_HANDCARDS) {
        //            confirmGotHandcards(user);
        //        }
        
    }
    
    /**************** logic in game loop start ***************************/
    
    //    private synchronized void confirmGotHandcards(String user) {
    //        playerStates[players.indexOf(user)] = player_state_get_init_handcards_confirmed;
    //        for (int i = 0; i < players.size(); i++) {
    //            String state = playerStates[i];
    //            if (!state.equals(player_state_get_init_handcards_confirmed)) { return; }
    //            
    //            if (i == players.size() - 1) {
    //                gameTurn(players.get(0));
    //            }
    //        }
    //    }
    
    private void gameTurn(String player) {
        EsObject obj = new EsObject();
        obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_START_TURN);
        obj.setString(PluginConstants.PLAYER_NAME, player);
        getApi().sendPluginMessageToRoom(getApi().getZoneId(), getApi().getRoomId(), obj);
    }
    
    private void hitOne(String user, EsObject messageIn) {
        
        //TODO drop stack add this card
        
        //TODO someone hitted 
        //        String target = messageIn.getString(PluginConstants.HIT_TARGET);
        //        messageIn.setString(PluginConstants.ACTION, PluginConstants.ACTION_ATTACHED);
        //        
    }
    
    /**************** logic in game loop end ***************************/
    
    /**************** logic before game start start ***************************/
    //    private synchronized void confirmedForce(String player) {
    //        playerStates[players.indexOf(player)] = player_state_force_confirmed;
    //        for (int i = 0; i < players.size(); i++) {
    //            String playerState = playerStates[i];
    //            if (playerState != null && !playerState.equals(player_state_force_confirmed)) { return; }
    //            if (i == players.size() - 1) {
    //                initCardStack();
    //            }
    //        }
    //    }
    
    private void initCardStack() {
        cardStack = new LinkedList<CardEnum>( Arrays.asList(CardEnum.values()));
        Collections.shuffle(cardStack);
        getApi().getLogger().debug("card stack is ready to use,");
        
        //        EsObject obj = new EsObject();
        //        //        obj.getInteger(PluginConstants.ACTION, PluginConstants.ACTION_INIT_CARD_STACK);
        //        //        obj.setStringArray(PluginConstants.CARD_STACK, cardStack.toArray(new String[cardStack.size()]));
        //        obj.setInteger(PluginConstants.STACK_CARD_COUNT, cardStack.size());
        //        getApi().sendPluginMessageToRoom(getApi().getZoneId(), getApi().getRoomId(), obj);
    }
    
    private void dispatchHandCards(String player) {
        dispatchHandCards(player, 2);
    }
    
    private void dispatchHandCards(String player, int howmany) {
        EsObject obj = new EsObject();
        int[] cards = new int[howmany];
        for (int i = 0; i < howmany; i++) {
            cards[i] = cardStack.get(i).getCardId();
            cardStack.remove(i);
            
        }
        obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_DISPATCH_HANDCARD);
        obj.setIntegerArray(PluginConstants.INIT_HAND_CARDS, cards);
        
        getApi().sendPluginMessageToUser(player, obj);
    }
    
    private synchronized void choseCharacter(String user, EsObject messageIn) {
        getApi().getLogger().debug(logprefix + "set to user : " + user + " of index in players list : " + players.indexOf(user));
        playerChoseCharactors[players.indexOf(user)] = messageIn.getInteger(PluginConstants.SELECTED_HERO_ID);
        
        playerStates[players.indexOf(user)] = player_state_character_confirmed;
        
        //after all players has chose their characters, then dispatch states
        
        for (int i = 0; i < players.size(); i++) {
            String playerState = playerStates[i];
            if (playerState == null || !playerState.equals(player_state_character_confirmed)) { return; }
        }
        sendAllHeros();
        initCardStack();
        dispatchForce();
        for (String player : players) {
            dispatchHandCards(player, 4);
        }
        gameTurn(players.get(0));
        
    }
    
    private void sendAllHeros() {
        EsObject obj = new EsObject();
        obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_ALL_HEROS);
        obj.setIntegerArray(PluginConstants.ALL_HEROS, playerChoseCharactors);
        sendPluginMessageToRoom(obj);
    }
    
    private void dispatchForce() {
        EsObject obj = new EsObject();
        List<Integer> forceList = Arrays.asList(new Integer[] { force_a, force_b });
        Collections.shuffle(forceList);
        force = forceList.toArray(new Integer[players.size()]);
        getApi().getLogger().debug("dispatching order: " + Arrays.toString(force));
        
        obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_DISPATCH_FORCE);
        obj.setInteger(PluginConstants.STACK_CARD_COUNT, cardStack.size());
        for (int i = 0; i < force.length; i++) {
            int f = force[i];
            obj.setInteger(PluginConstants.FORCE, f);
            getApi().sendPluginMessageToUser(players.get(i), obj);
        }
    }
    
    private void reorderUsers() {
        Object[] users = getApi().getUsersInRoom(getApi().getZoneId(), getApi().getRoomId()).toArray();
        int random = new Random().nextInt(users.length);
        for (int i = 0; i < users.length; i++) {
            int count = i + random;
            count = count < users.length ? count : (count - users.length);
            getApi().getLogger().debug("adding the " + count + " user as the " + i + " player");
            players.add(((UserValue) users[count]).getUserName());
            
        }
        getApi().getLogger().debug("(List<String>) players order is ready.\n" + players);
        
        EsObject obj = new EsObject();
        obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_PLAYER_LIST_ORDERED);
        obj.setStringArray(PluginConstants.SORTED_PLAYER_NAMES, players.toArray(new String[players.size()]));
        
        sendPluginMessageToRoom(obj);
    }
    
    private void chooseCharacters(EsObject obj) {
        
        getApi().getLogger().debug("players = " + players.size());
        for (int i = 0; i < players.size(); i++) {
            int[] charsToChoose = new int[3];
            String player = players.get(i);
            for (int choosingCount = 0; choosingCount < charsToChoose.length; choosingCount++) {
                int shouldAddCharacterCount = i * charsToChoose.length + choosingCount;
                getApi().getLogger().debug(
                        "charsToChoose = " + Arrays.toString(charsToChoose) + "\n" + "choosingCount = " + choosingCount + "\n" + "shouldAddCharacterCount = " + shouldAddCharacterCount);
                charsToChoose[choosingCount] = allCharactersForChoose.get(shouldAddCharacterCount).getId();
            }
            obj.setInteger(PluginConstants.ACTION, PluginConstants.ACTION_CHOOSE_CHARACTER);
            obj.setIntegerArray(PluginConstants.CHARACTORS_TO_CHOOSE, charsToChoose);
            getApi().sendPluginMessageToUser(player, obj);
            getApi().getLogger().debug("Characters " + Arrays.toString(charsToChoose) + " are sending to player " + player);
        }
        
    }
    
    /**************** logic before game start end ***************************/
    @Override
    public void destroy() {
        // TODO: if you want to log that the room was destroyed, do it here
        getApi().getLogger().debug("ChatPlugin destroyed");
    }
    
    @Override
    public ChainAction userSendPublicMessage(UserPublicMessageContext message) {
        String chatLine = message.getMessage();
        getApi().getLogger().debug("chat message recd: {}", chatLine);
        String user = message.getUserName();
        getChatLogger().addChatEvent(user, chatLine, getApi().getRoomId(), getApi().getZoneId());
        return ChainAction.OkAndContinue;
    }
    
    private ChatLogger getChatLogger() {
        if (chatLogger == null) {
            chatLogger = (ChatLogger) getApi().acquireManagedObject("ChatLogger", null);
        }
        return chatLogger;
    }
    
    private void sendPluginMessageToRoom(EsObject obj) {
        getApi().sendPluginMessageToRoom(getApi().getZoneId(), getApi().getRoomId(), obj);
    }
    //TODO 所有的发送EsObj都携带牌堆中的牌数
}
