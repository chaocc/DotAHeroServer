var server = new ElectroServer.Server("server1");

var availableConnection = new ElectroServer.AvailableConnection("localhost", 8989, ElectroServer.TransportType.BinaryHTTP);

server.addAvailableConnection(availableConnection);

var es = new ElectroServer();
es.initialize();

var username;
var room;
var checkConnection; 

es.engine.addServer(server);

es.engine.addEventListener(MessageType.ConnectionResponse, onConnectionResponse);
es.engine.addEventListener(MessageType.LoginResponse, onLoginResponse);
es.engine.addEventListener(MessageType.JoinRoomEvent, onJoinRoomEvent);
es.engine.addEventListener(MessageType.PluginMessageEvent, onPluginMessageEvent);




function onPluginMessageEvent(event) {
	resetServerTimeout();
  //log("PluginMessageEvent " );
  
  var esob = event.parameters;
  var action = esob.getString(ACTION);
  log("PluginMessageEvent << action: " + action);
//    switch(action){
//        case :{
//            break;
//        }
//    }
}



function connect() {
  setStatus("Connecting...");
  es.engine.connect();
}

function login() {
  var r = new LoginRequest();
  var text = "Sol_9989";
  r.userName = text;
  setStatus("Attempting to log in as " + r.userName);
  es.engine.send(r);
}

function onConnectionResponse(event) {
  setStatus("ConnectionResponse.successful: "+ event.successful);
  if (event.successful)
    login();
}

function onLoginResponse(event) {
    setStatus("LoginResponse.successful: "+ event.successful);
    
    username = event.userName;
    
    setStatus("username " + username);
    
    if (event.successful){
        joinRoom();
    }
  
}
function onJoinRoomEvent(event) {
    setStatus("joined a room " + event.zoneId + " " + event.roomId);
    room = es.managerHelper.zoneManager.zoneById(event.zoneId).roomById(event.roomId);
    var esob = new ElectroServer.EsObject();
    esob.setInteger(ACTION, START_GAME);
    sendToPlugin(esob);
}
var joinRoom = function() {
    setStatus("joining Room");
    zoneName = "test_zone";
    roomName = "text_room";
    var crr = new CreateRoomRequest();
    crr.zoneName = zoneName;
    crr.roomName = roomName;
    var plugins = new Array();
    var gamePluginEntry = new PluginListEntry();
    gamePluginEntry.extensionName = "HeroServer";
    gamePluginEntry.pluginHandle = "GamePlugin";
    gamePluginEntry.pluginName = "GamePlugin";
    plugins.push(gamePluginEntry);
    crr.plugins = plugins;
//    EsPluginListEntry
    
    
    
    es.engine.send(crr);

}

function sendToPlugin(esob) {
  var pr = new PluginRequest();
  pr.zoneId = room.zoneId;
  pr.roomId = room.id;
  pr.pluginName = PLUGIN_NAME;
  pr.parameters = esob;

  es.engine.send(pr);
}


function setStatus(msg) {
    document.getElementById("status").innerHTML = msg + "<br/>";
    log(msg);
}


function log(msg) {
  document.getElementById("log").innerHTML += msg + "<br/>";
}