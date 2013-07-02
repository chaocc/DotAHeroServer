package com.electrotank.examples.chatlogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.ChainAction;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserPublicMessageContext;
import com.electrotank.examples.chatlogger.components.CharacterEnum;
import com.electrotank.examples.chatlogger.components.PluginConstants;

//import org.slf4j.Logger;

public class ChatPlugin extends BasePlugin {
    
    private ChatLogger          chatLogger             = null;
    private List<CharacterEnum> allCharactersForChoose = new ArrayList<CharacterEnum>();
    private List<String>        players                = new ArrayList<String>();
    private List<String>        playerChoseCharactors  = new ArrayList<String>();
    
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
        
        String action = messageIn.getString(PluginConstants.ACTION);
        
        if (action.equals(PluginConstants.ACTION_START_GAME)) {
            getApi().getLogger().debug("got action start game");
            reorderUsers();
            chooseCharacters(user, messageIn);
            
        } else if (action.equals(PluginConstants.ACTION_CHOSE_CHARACTER)) {
            choseCharacter(messageIn);
        } else {
            //            EsObject obj = new EsObject();
            messageIn.setString("action", "nan");
            getApi().sendPluginMessageToUser(user, messageIn);
        }
        
    }
    
    private void choseCharacter(EsObject messageIn) {
        
    }
    
    private void reorderUsers() {
        String[] users = (String[]) getApi().getUsers().toArray();
        int random = new Random().nextInt(users.length);
        for (int i = 0; i < users.length; i++) {
            int count = i + random;
            count = count >= users.length ? count : (count - users.length);
            players.add(users[count]);
            getApi().getLogger().debug("adding the " + count + " user as the " + i + " player");
        }
        getApi().getLogger().debug("List<String> players is ready.");
    }
    
    private void chooseCharacters(String user, EsObject obj) {
        obj.setString(PluginConstants.ACTION, "bdc");
        getApi().sendPluginMessageToUser(user, obj);
        
        //        obj.setString(PluginConstants.ACTION, PluginConstants.CHOOSE_CHARACTER);
        //        String[] charsToChoose = new String[3];
        //        for (int i = 0; i < players.size(); i++) {
        //            String player = players.get(i);
        //            for (int choosingCount = 0; choosingCount < charsToChoose.length; choosingCount++) {
        //                int shouldAddCharacterCount = i * charsToChoose.length + choosingCount;
        //                charsToChoose[choosingCount] = allCharactersForChoose.get(shouldAddCharacterCount).getValue();
        //            }
        //            obj.setStringArray(PluginConstants.CHARACTORS_TO_CHOOSE, charsToChoose);
        //            getApi().sendPluginMessageToUser(player, obj);
        //            getApi().getLogger().debug("Characters " + charsToChoose + " are sending to player " + player);
        //        }
        
    }
    
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
}
