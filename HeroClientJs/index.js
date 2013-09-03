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



var me;
var handcards;
var namesOfAllPlayers;
var allPlayers;
var cardCount;
var timeCountDown;





function onPluginMessageEvent(event) {
	resetServerTimeout();
  //log("PluginMessageEvent " );
  
  var esob = event.parameters;
  var action = esob.getString(ACTION);
  log("PluginMessageEvent << action: " + action);
    switch(action){
        case START_GAME:{
//          TEMP
            self.users = [obj stringArrayWithKey:kParamUserList];
//            self.users = _es.managerHelper.userManager.users;
            [[BGRoomLayer sharedRoomLayer] showGameLayer];
            NSLog(@"All login users: %@", self.users);
            
            _gameLayer = [BGGameLayer sharedGameLayer];
            _playingDeck = _gameLayer.playingDeck;
            break;
            
        case kActionUpdateDeckHero:
            [_playingDeck updatePlayingDeckWithHeroIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdateDeckSelectedHeros:
            [_gameLayer renderOtherPlayersHeroWithHeroIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
        
        case kActionUpdateDeckCuttedCard:
            _playingDeck.isNeedClearDeck = YES;
        case kActionUpdateDeckUsedCard:
        case kActionUpdateDeckAssigning:
            [_playingDeck updatePlayingDeckWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            _player.handCardCount = [obj intWithKey:kParamHandCardCount];
            break;
            
        case kActionUpdateDeckHandCard:
            [_playingDeck updatePlayingDeckWithCardCount:[obj intWithKey:kParamHandCardCount]
                                            equipmentIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionInitPlayerHero:
            [_player renderHeroWithHeroId:[obj intWithKey:kParamSelectedHeroId]];
            break;
            
        case kActionInitPlayerCard:
            [_player renderHandCardWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdatePlayerHero:
            [_player updateHeroWithBloodPoint:[obj intWithKey:kParamHeroBloodPoint]
                                   angerPoint:[obj intWithKey:kParamHeroAngerPoint]];
            break;
        
        case kActionUpdatePlayerHand:
            [_player updateHandCardWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionUpdatePlayerEquipment:
            [_player updateEquipmentWithCardIds:[obj intArrayWithKey:kParamCardIdList]];
            break;
            
        case kActionChooseCardToCut:
            [_player addPlayingMenu];
            [_player addProgressBar];
            [_gameLayer addProgressBarForOtherPlayers];
            break;
            
        case kActionPlayingCard:
            _playingDeck.isNeedClearDeck = YES; // 每张卡牌结算完后需要清除桌面
        case kActionChooseCardToUse:
        case kActionChooseCardToDiscard:
        case kActionChoosingColor:
        case kActionChoosingSuits:
            [_player addProgressBar];
            if (_player.playerName == _gameLayer.selfPlayer.playerName) {
                [_player addPlayingMenu];
                [_player enableHandCardWithCardIds:[obj intArrayWithKey:kParamAvailableIdList]
                               selectableCardCount:[obj intWithKey:kParamSelectableCardCount]];
            }
            break;
            
        case kActionChooseCardToExtract:
            _player.canExtractCardCount = [obj intWithKey:kParamExtractedCardCount];
            break;
    }
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