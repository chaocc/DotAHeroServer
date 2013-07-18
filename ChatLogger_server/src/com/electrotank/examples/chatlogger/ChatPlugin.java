package com.electrotank.examples.chatlogger;


import com.electrotank.electroserver5.extensions.BasePlugin;
import com.electrotank.electroserver5.extensions.ChainAction;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.EsObjectRO;
import com.electrotank.electroserver5.extensions.api.value.UserPublicMessageContext;
import com.wolf.dota.component.constants.Code;
import com.wolf.dota.component.constants.Commands;


//import org.slf4j.Logger;

public class ChatPlugin extends BasePlugin implements Commands {
	
	private static final String logprefix = "======== room =>> ";
	
	private ChatLogger chatLogger = null;
	
	
	@Override
	public void init(EsObjectRO parameters) {
	
		getApi().getLogger().debug("ChatPlugin initialized");
	}
	
	
	@Override
	public void request(String user, EsObjectRO message) {
	
		EsObject messageIn = new EsObject();
		messageIn.addAll(message);
		d.debug(logprefix + user + " requests: " + messageIn.toString());
		
		int action = messageIn.getInteger(code_action);
		if (action == Code.ACTION_USER_READY) {
			messageIn.setInteger(code_action, Code.ACTION_USER_READY);
			//            sendRoomPluginMessageToRoom(messageIn);
			getApi().sendPluginMessageToUser(user, messageIn);
		}
		
	}
	
	
	//    private void sendRoomPluginMessageToRoom(EsObject obj) {
	//        getApi().sendPluginMessageToRoom(getApi().getZoneId(), getApi().getRoomId(), obj);
	//        
	//    }
	
	//    private void sendRoomPluginMessageToUser(String user, EsObject obj) {
	//        getApi().sendPluginMessageToUser(user, obj);
	//    }
	
	/**************** logic in game loop start ***************************/
	
	/**************** logic before game start end ***************************/
	@Override
	public void destroy() {
	
		// TODO: if you want to log that the room was destroyed, do it here
		d.debug("ChatPlugin destroyed");
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
	
	
	private class D {
		
		public void debug(String message) {
		
			getApi().getLogger().debug(message);
			;
		}
	}
	
	
	private D d = new D();
	
}
