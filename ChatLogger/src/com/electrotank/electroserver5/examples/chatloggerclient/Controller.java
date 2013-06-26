package com.electrotank.electroserver5.examples.chatloggerclient;

import com.electrotank.electroserver5.client.ElectroServer;
import com.electrotank.electroserver5.client.connection.Connection;
import com.electrotank.electroserver5.client.extensions.api.value.EsObject;
import com.electrotank.electroserver5.client.api.EsConnectionResponse;
import com.electrotank.electroserver5.client.api.EsCreateRoomRequest;
import com.electrotank.electroserver5.client.api.EsJoinRoomEvent;
import com.electrotank.electroserver5.client.api.EsPluginMessageEvent;
import com.electrotank.electroserver5.client.api.EsPluginRequest;
import com.electrotank.electroserver5.client.api.EsUserUpdateEvent;
import com.electrotank.electroserver5.client.api.EsLoginRequest;
import com.electrotank.electroserver5.client.api.EsLoginResponse;
import com.electrotank.electroserver5.client.api.EsMessageType;
import com.electrotank.electroserver5.client.api.EsPluginListEntry;
import com.electrotank.electroserver5.client.api.EsPublicMessageEvent;
import com.electrotank.electroserver5.client.api.EsPublicMessageRequest;
import com.electrotank.electroserver5.client.api.EsUserListEntry;
import com.electrotank.electroserver5.client.user.User;
import com.electrotank.electroserver5.client.zone.Room;
import com.electrotank.electroserver5.client.zone.Zone;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Controls all communications with the ES5.
 * 
 */
public class Controller {
    
    private View                view;
    private ElectroServer       es      = null;
    private Room                room    = null;
    private static final String xmlPath = "settings.xml";
    private String              pendingAction;
    
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
        //listen for certain events to allow the application to flow, and to support chatting and user list updates
        es.getEngine().addEventListener(EsMessageType.ConnectionResponse, this, "onConnectionResponse", EsConnectionResponse.class);
        es.getEngine().addEventListener(EsMessageType.LoginResponse, this, "onLoginResponse", EsLoginResponse.class);
        es.getEngine().addEventListener(EsMessageType.JoinRoomEvent, this, "onJoinRoomEvent", EsJoinRoomEvent.class);
        es.getEngine().addEventListener(EsMessageType.UserUpdateEvent, this, "onRoomUserUpdateEvent", EsUserUpdateEvent.class);
        es.getEngine().addEventListener(EsMessageType.PublicMessageEvent, this, "onPublicMessageEvent", EsPublicMessageEvent.class);
        es.getEngine().addEventListener(EsMessageType.PluginMessageEvent, this, "onPluginMessageEvent", EsPluginMessageEvent.class);
        
        try {
            log("loadAndConnect");
            //load the connection settings, and connect
            es.loadAndConnect(xmlPath);
        } catch (Exception ex) {
            log("loadAndConnect exception: " + ex.getMessage());
        }
    }
    
    private void sendInitMeRequest() {
        log("sendInitMeRequest");
        EsObject obj = new EsObject();
        obj.setString("action", "kill");
        sendPluginRequest(obj);
    }
    
    private void sendPluginRequest(EsObject obj) {
        log("sendPluginRequest");
        EsPluginRequest pmr = new EsPluginRequest();
        pmr.setPluginName("ChatPlugin");
        pmr.setRoomId(room.getId());
        pmr.setZoneId(room.getZoneId());
        pmr.setParameters(obj);
        
        es.getEngine().send(pmr);
        log("sendPluginRequest");
    }
    
    public void onPluginMessageEvent(EsPluginMessageEvent e) {
        log("onPluginMessageEvent");
        EsObject obj = e.getParameters();
        String action = obj.getString(ClientConstants.ACTION);
        log(action);
        if(action.equals("print_test")){
            showChat("printing test");
        }
        /*
         * if (!e.getPluginName().equals(PLUGIN_NAME) &&
         * !e.getPluginName().equals(LOBBY_PLUGIN_NAME)) { return; } EsObject
         * obj = e.getParameters(); // log("onPluginMessageEvent: " +
         * obj.toString()); String action =
         * obj.getString(PluginConstants.ACTION, "");
         * 
         * if (action.isEmpty()) { log("onPluginMessageEvent: Action empty! ");
         * return; } else if (action.equals(PluginConstants.START_GAME)) {
         * handleStartGame(obj); } else if
         * (action.equals(PluginConstants.MOVE_RESPONSE)) {
         * handleMoveResponse(obj); } else if
         * (action.equals(PluginConstants.MOVE_EVENT)) { handleMoveEvent(obj); }
         * else if (action.equals(PluginConstants.SCORE)) {
         * handleScoreEvent(obj); } else if
         * (action.equals(PluginConstants.GAME_OVER)) { handleGameOver(obj); }
         * else if (action.equals(PluginConstants.INIT_ME)) {
         * handleWatcherInit(obj); } else if
         * (action.equals(PluginConstants.SCORES_SET)) { handleTopScores(obj); }
         * else if (action.equals(PluginConstants.LEADERBOARDS)) {
         * handleLeaderBoards(obj); } else { log("unhandled action: " + action);
         * }
         */
    }
    
    public void onPublicMessageEvent(EsPublicMessageEvent e) {
        String from = e.getUserName();
        String msg = e.getMessage();
        
        showChat(from + ": " + msg + "\n");
        
//        if (pendingAction == ClientConstants.PENDING_ACTION_NEED_START && msg.equals(ClientConstants.USER_INPUT_MESSAGE_START)) {
        if(msg.equals("start")){
            EsObject esob = new EsObject();
            esob.setString(ClientConstants.ACTION, ClientConstants.ACTION_START_GAME);
            sendPluginRequest(esob);
        }
        
    }
    
    /**
     * Tries to create a room. If it already exists, you join that room.
     */
    private void joinRoom() {
        log("Attempting to join room");
        // Create the request
        EsCreateRoomRequest crr = new EsCreateRoomRequest();
        crr.setRoomName("ChatLoggerRoom");
        crr.setZoneName("TestZone");
        crr.setUsingLanguageFilter(true);
        
        // Java clients don't handle video 
        crr.setReceivingVideoEvents(false);
        
        /**
         * Create plugin associated with this room. Then as chat messages are
         * sent they are intercepted by the server and logged before being
         * distributed to the room.
         */
        EsPluginListEntry ple = new EsPluginListEntry();
        ple.setExtensionName("ChatLogger");
        ple.setPluginHandle("ChatPlugin");
        ple.setPluginName("ChatPlugin");
        
        List<EsPluginListEntry> plugins = new ArrayList<EsPluginListEntry>();
        plugins.add(ple);
        
        crr.setPlugins(plugins);
        
        es.getEngine().send(crr);
        
    }
    
    public void onJoinRoomEvent(EsJoinRoomEvent e) {
        log("Joined a room!");
        int roomId = e.getRoomId();
        int zoneId = e.getZoneId();
        //store the room reference
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
    
    public void attemptSendMessage(String message) {
        if (room == null) {
            log("Unable to send message.  Room is null.");
            return;
        }
        if (message == null || message.isEmpty()) { return; }
        log("Attempting to send message: " + message);
        // create the request
        EsPublicMessageRequest spmr = new EsPublicMessageRequest();
        spmr.setRoomId(room.getId());
        spmr.setZoneId(room.getZoneId());
        spmr.setMessage(message);
        // send it
        es.getEngine().send(spmr);
    }
    
    private void logSuccessfulConnection() {
        List<Connection> connections = es.getEngine().getActiveConnections();
        for (Connection conn : connections) {
            String host = conn.getHost();
            String serverId = conn.getServerId();
            int port = conn.getPort();
            String transport = conn.getTransportType().toString();
            log("Active connection: " + host + ", " + port + ", "
                    + transport + ", " + serverId);
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
        view.showChat(message);
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
