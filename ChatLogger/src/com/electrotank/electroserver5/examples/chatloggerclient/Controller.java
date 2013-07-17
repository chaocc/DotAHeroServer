package com.electrotank.electroserver5.examples.chatloggerclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.electrotank.electroserver5.client.ElectroServer;
import com.electrotank.electroserver5.client.api.EsConnectionResponse;
import com.electrotank.electroserver5.client.api.EsCreateRoomRequest;
import com.electrotank.electroserver5.client.api.EsJoinRoomEvent;
import com.electrotank.electroserver5.client.api.EsLoginRequest;
import com.electrotank.electroserver5.client.api.EsLoginResponse;
import com.electrotank.electroserver5.client.api.EsMessageType;
import com.electrotank.electroserver5.client.api.EsPluginListEntry;
import com.electrotank.electroserver5.client.api.EsPluginMessageEvent;
import com.electrotank.electroserver5.client.api.EsPluginRequest;
import com.electrotank.electroserver5.client.api.EsPublicMessageEvent;
import com.electrotank.electroserver5.client.api.EsPublicMessageRequest;
import com.electrotank.electroserver5.client.api.EsUserListEntry;
import com.electrotank.electroserver5.client.api.EsUserUpdateEvent;
import com.electrotank.electroserver5.client.connection.Connection;
import com.electrotank.electroserver5.client.extensions.api.value.EsObject;
import com.electrotank.electroserver5.client.user.User;
import com.electrotank.electroserver5.client.zone.Room;
import com.electrotank.electroserver5.client.zone.Zone;
import com.electrotank.electroserver5.examples.chatloggerclient.component.Player;

/**
 * Controls all communications with the ES5.
 * 
 */
public class Controller implements ClientConstants {
	
	private View view;
	private ElectroServer es = null;
	private Room room = null;
	private static final String xmlPath = "settings.xml";
	private final int no_action = -1;
	private int currentAction = no_action;
	private int requireAction = no_action;
	private int[] currentData;
	private Player player;
	private int cardStackCount = -1;
	
	public void setView(View view) {
		this.view = view;
	}
	
	/*******************************************************
	 * 
	 * METHODS FOR COMMUNICATING WITH ELECTRO-SERVER
	 * 
	 *******************************************************/
	public void initialize() {
		log("Initializing");
		es = new ElectroServer();
		// listen for certain events to allow the application to flow, and to
		// support chatting and user list updates
		es.getEngine().addEventListener(EsMessageType.ConnectionResponse, this,
				"onConnectionResponse", EsConnectionResponse.class);
		es.getEngine().addEventListener(EsMessageType.LoginResponse, this, "onLoginResponse",
				EsLoginResponse.class);
		es.getEngine().addEventListener(EsMessageType.JoinRoomEvent, this, "onJoinRoomEvent",
				EsJoinRoomEvent.class);
		es.getEngine().addEventListener(EsMessageType.UserUpdateEvent, this,
				"onRoomUserUpdateEvent", EsUserUpdateEvent.class);
		es.getEngine().addEventListener(EsMessageType.PublicMessageEvent, this,
				"onPublicMessageEvent", EsPublicMessageEvent.class);
		es.getEngine().addEventListener(EsMessageType.PluginMessageEvent, this,
				"onPluginMessageEvent", EsPluginMessageEvent.class);
		try {
			log("loadAndConnect");
			// load the connection settings, and connect
			es.loadAndConnect(xmlPath);
		} catch (Exception ex) {
			log("loadAndConnect exception: " + ex.getMessage());
		}
	}
	
	public void onPluginMessageEvent(EsPluginMessageEvent e) {
		log("onPluginMessageEvent");
		EsObject obj = getObj(e);
		
		// TODO implement waiting mechanism
		
		if (requireAction == ac_require_draw) {
			obj.setInteger(ac, ACTION_DRAW_CARDS);
			sendGamePluginRequest(obj);
		}
		
		if (currentAction == ClientConstants.ACTION_CHOOSE_CHARACTER) {
			gotCharactersToChoose(obj);
		} else if (currentAction == ClientConstants.ACTION_ALL_HEROS) {
			showChat("all heros: "
					+ Arrays.toString(obj.getIntegerArray(ClientConstants.ALL_HEROS)));
		} else if (currentAction == ClientConstants.ACTION_DISPATCH_FORCE) {
			showChat("my force: " + obj.getInteger(ClientConstants.FORCE));
			showChat("remaining cards: "
					+ obj.getInteger(ClientConstants.STACK_CARD_COUNT, 1));
		} else if (currentAction == ClientConstants.ACTION_DISPATCH_HANDCARD
				|| currentAction == ACTION_SEND_CARDS) {
			gotInitHandCards(obj);
		} else if (currentAction == 0) {
			showChat("0 worked");
		} else if (currentAction == ClientConstants.ACTION_STAKE) {
			showAllStakeCards(obj);
		}
		
		// TODO acton == game over
	}
	
	public void attemptSendMessage(String message) {
		if (room == null) {
			log("Unable to send message.  Room is null.");
			return;
		}
		if (message == null || message.isEmpty()) {
			return;
		}
		log("Attempting to send message: " + message);
		// create the request
		EsPublicMessageRequest spmr = new EsPublicMessageRequest();
		spmr.setRoomId(room.getId());
		spmr.setZoneId(room.getZoneId());
		spmr.setMessage(message);
		// send it
		es.getEngine().send(spmr);
		
		log("currentAction = " + currentAction);
		log("currentData == " + Arrays.toString(currentData));
		
		if (message.equals(ClientConstants.USER_INPUT_MESSAGE_START)) {
			EsObject esob = new EsObject();
			esob.setInteger(ClientConstants.ac, ClientConstants.ACTION_START_GAME);
			sendGamePluginRequest(esob);
		} else if (currentAction == ClientConstants.ACTION_CHOOSE_CHARACTER) {
			log(" " + Arrays.toString(currentData));
			for (int hero : currentData) {
				if (hero == Integer.parseInt(message)) {
					EsObject esob = new EsObject();
					esob.setInteger(ClientConstants.ac,
							ClientConstants.ACTION_CHOSE_CHARACTER);
					esob.setInteger(ClientConstants.SELECTED_HERO_ID,
							Integer.parseInt(message));
					sendGamePluginRequest(esob);
					initPlayer(hero);
					return;
				}
			}
			
			showChat("Wrong selection, please choose: " + Arrays.toString(currentData));
			
		} else if (message.equals("gg")) {
			EsObject esob = new EsObject();
			esob.setInteger(ClientConstants.ac, 0);
			sendRoomPluginRequest(esob);
		} else if (currentAction == ClientConstants.ACTION_DISPATCH_HANDCARD) {
			for (int card : currentData) {
				if (card == Integer.parseInt(message)) {
					EsObject esob = new EsObject();
					esob.setInteger(ClientConstants.ac, ClientConstants.ACTION_STAKE);
					esob.setIntegerArray(ClientConstants.USED_CARDS,
							new int[] { Integer.parseInt(message) });
					sendGamePluginRequest(esob);
					// player.removeCard(card);
					return;
				}
			}
			showChat("Wrong selection, please select card for stake from : "
					+ Arrays.toString(currentData));
		} else if (message.startsWith(("kill"))) {
			EsObject esob = new EsObject();
			esob.setInteger(ClientConstants.ac, ClientConstants.ACTION_USED_CARD);
			esob.setIntegerArray(ClientConstants.USED_CARDS,
					new int[] { 14 });
			esob.setStringArray(TARGET_PLAYERS, new String[] { message.substring(message.indexOf("kill") + 1) });
			sendGamePluginRequest(esob);
			// player.removeCard(card);
			return;
		}
		
	}
	
	private void showAllStakeCards(EsObject obj) {
		int[] stakes = obj.getIntegerArray(ALL_STAKE_CARDS);
		showChat(Arrays.toString(stakes));
		
	}
	
	private void gotInitHandCards(EsObject obj) {
		int[] cards = obj.getIntegerArray(ClientConstants.DISPATCH_CARDS);
		log(obj.toString());
		currentData = cards;
		// player.addHandCards(cards);
		showChat("got cards: " + Arrays.toString(currentData));
		
	}
	
	private EsObject getObj(EsPluginMessageEvent e) {
		EsObject obj = e.getParameters();
		log("currentObj: " + obj.toString());
		currentAction = no_action;
		requireAction = no_action;
		
		currentAction = obj.getInteger(ClientConstants.ac, -1);
		requireAction = obj.getInteger(ac_required, -1);
		log("" + currentAction);
		if (obj.getInteger(ClientConstants.STACK_CARD_COUNT, -1) != -1) {
			cardStackCount = obj.getInteger(ClientConstants.STACK_CARD_COUNT);
		}
		return obj;
	}
	
	// private void confirmCharacterSelected(EsObject obj) {
	// String character = obj.getString(ClientConstants.CHARACTER_CONFIRMATION);
	// showChat("character selected : " + character);
	// }
	
	private void initPlayer(int hero) {
		// read plahyer from id
		
	}
	
	private void sendRoomPluginRequest(EsObject obj) {
		log("send  Room   PluginRequest");
		EsPluginRequest pmr = new EsPluginRequest();
		pmr.setPluginName("ChatPlugin");
		pmr.setRoomId(room.getId());
		pmr.setZoneId(room.getZoneId());
		pmr.setParameters(obj);
		
		es.getEngine().send(pmr);
		log("sendPluginRequest");
	}
	
	private void sendGamePluginRequest(EsObject obj) {
		log("sendPluginRequest");
		EsPluginRequest pmr = new EsPluginRequest();
		pmr.setPluginName("GamePlugin");
		pmr.setRoomId(room.getId());
		pmr.setZoneId(room.getZoneId());
		pmr.setParameters(obj);
		
		es.getEngine().send(pmr);
		log("send  Game   PluginRequest");
	}
	
	private void gotCharactersToChoose(EsObject obj) {
		int[] charsToChoose = obj.getIntegerArray(ClientConstants.CHARACTORS_TO_CHOOSE);
		currentData = charsToChoose;
		showChat("please choose: " + Arrays.toString(charsToChoose));
	}
	
	public void onPublicMessageEvent(EsPublicMessageEvent e) {
		String from = e.getUserName();
		String msg = e.getMessage();
		
		showChat(from + ": " + msg + "\n");
		// EsObject esob = new EsObject();
		// // if (msg.equals(ClientConstants.USER_INPUT_MESSAGE_START)) {
		// // esob.setString(ClientConstants.ACTION,
		// ClientConstants.ACTION_START_GAME);
		// // sendPluginRequest(esob);
		// // } else
		// if (currentAction != null &&
		// currentAction.equals(ClientConstants.CHOOSE_CHARACTER) && currentData
		// != null && Arrays.asList(currentData).contains(msg)) {
		// esob.setString(ClientConstants.ACTION_CHOSE_CHARACTER, msg);
		// sendPluginRequest(esob);
		// }
		
	}
	
	/**
	 * Tries to create a room. If it already exists, you join that room.
	 */
	private void joinRoom() {
		log("Attempting to join room");
		// Create the request
		EsCreateRoomRequest crr = new EsCreateRoomRequest();
		crr.setRoomName("ChatRoom2");
		crr.setZoneName("TestZone");
		crr.setUsingLanguageFilter(true);
		
		crr.setReceivingVideoEvents(false);
		List<EsPluginListEntry> plugins = new ArrayList<EsPluginListEntry>();
		
		EsPluginListEntry ple = new EsPluginListEntry();
		ple.setExtensionName("ChatLogger");
		ple.setPluginHandle("ChatPlugin");
		ple.setPluginName("ChatPlugin");
		plugins.add(ple);
		
		ple = new EsPluginListEntry();
		ple.setExtensionName("ChatLogger");
		ple.setPluginHandle("GamePlugin");
		ple.setPluginName("GamePlugin");
		plugins.add(ple);
		
		crr.setPlugins(plugins);
		es.getEngine().send(crr);
	}
	
	public void onJoinRoomEvent(EsJoinRoomEvent e) {
		log("Joined a room!");
		int roomId = e.getRoomId();
		int zoneId = e.getZoneId();
		// store the room reference
		room = es.getManagerHelper().getZoneManager().zoneById(zoneId).roomById(roomId);
		if (room == null) {
			log("silly zone manager can't find the room");
			// see if it can find the zone
			Zone zone = es.getManagerHelper().getZoneManager().zoneById(zoneId);
			log(" zone == null? " + (zone == null));
			Collection<Room> rooms = zone.getRooms();
			log(" zone has this many rooms: " + rooms.size());
			log(" is our roomId in there? " + (zone.roomById(roomId) != null));
		}
		
		// get the user list
		List<String> users = new ArrayList<String>();
		for (EsUserListEntry user : e.getUsers()) {
			users.add(user.getUserName());
		}
		
		showUserList(users);
	}
	
	public void onRoomUserUpdateEvent(EsUserUpdateEvent e) {
		List<String> users = new ArrayList<String>();
		for (User user : room.getUsers()) {
			users.add(user.getUserName());
		}
		
		showUserList(users);
	}
	
	private void logSuccessfulConnection() {
		List<Connection> connections = es.getEngine().getActiveConnections();
		for (Connection conn : connections) {
			String host = conn.getHost();
			String serverId = conn.getServerId();
			int port = conn.getPort();
			String transport = conn.getTransportType().toString();
			log("Active connection: " + host + ", " + port + ", " + transport + ", "
					+ serverId);
		}
	}
	
	public void onConnectionResponse(EsConnectionResponse e) {
		if (e.isSuccessful()) {
			log("Connection accepted");
			
			logSuccessfulConnection();
			
			showUsernameDialog();
		}
	}
	
	public void attemptLogin(String name) {
		if (name == null || name.isEmpty()) {
			name = "user" + Math.round(10000 * Math.random());
		}
		
		log("Attempting to login as: " + name);
		// Build the LoginRequest and populate it
		EsLoginRequest lr = new EsLoginRequest();
		lr.setUserName(name);
		
		// sent the LoginRequest
		es.getEngine().send(lr);
	}
	
	public void onLoginResponse(EsLoginResponse e) {
		if (e.isSuccessful()) {
			log("Login accepted.");
			log("You are logged in as: " + e.getUserName());
			joinRoom();
		} else {
			log("Login failed: " + e.getError().toString());
		}
	}
	
	/*****************************************
	 * 
	 * METHODS FOR UPDATING THE GUI
	 * 
	 ****************************************/
	private void showChat(String message) {
		view.showChat("==== >> current state start " + getCurrentTimeStamp() + " << ====");
		view.showChat(cardStackCount + " cards remaining");
		if (player != null && player.getHandCards() != null) {
			view.showChat("my cards : " + Arrays.toString(player.getHandCards().toArray()));
		}
		view.showChat(message);
		view.showChat("======= >> current state end << =========");
	}
	
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	private void showUserList(List<String> users) {
		view.showUserList(users);
	}
	
	private void showUsernameDialog() {
		view.showUsernameDialog();
	}
	
	private void log(String logMessage) {
		view.log(logMessage);
	}
}
