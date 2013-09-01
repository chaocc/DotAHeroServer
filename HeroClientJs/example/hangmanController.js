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
es.engine.addEventListener(MessageType.PublicMessageEvent, onPublicMessageEvent);
es.engine.addEventListener(MessageType.CreateOrJoinGameResponse, onCreateOrJoinGameResponse);
es.engine.addEventListener(MessageType.PluginMessageEvent, onPluginMessageEvent);

function connect() {
  setStatus("Connecting...");
  es.engine.connect();
}

function login() {
  var r = new LoginRequest();
  var text = document.LoginForm.username.value;
  r.userName = text;
  setStatus("Attempting to log in as " + r.userName);
  es.engine.send(r);
  document.getElementById('login').style.display='none';
}

function onPublicMessageEvent(event) {
	resetServerTimeout();
  log(event.userName + ": " + event.message);
}

function onServerTimeout() {
	setStatus("Server not responding");
	log("Server not responding - either wait or refresh the page");
}

function resetServerTimeout() {
	if (checkConnection) {
		window.clearTimeout(checkConnection);
	}
	checkConnection = window.setTimeout('onServerTimeout()', SERVER_TIMEOUT_MS);
}

function onPluginMessageEvent(event) {
	resetServerTimeout();
  //log("PluginMessageEvent " );
  
  var esob = event.parameters;
  var action = esob.getString(ACTION);
  //log("PluginMessageEvent << action: " + action);
  
  switch(action) {
    case COUNTDOWN_SECONDS:
      var secondsLeft = esob.getInteger(COUNTDOWN_LEFT);
    setStatus("Waiting to start: " + secondsLeft );
    break;
    case START_GAME:
      setStatus("Starting game" );
    handleStartGame(esob);
    break;
    case SUBMIT_LETTER:
      handleSubmitLetter(esob);
    break;
    case GAME_OVER:
      removeAllAlphabetLetters();
      break;
    case SCORE:
    	handleAnnounceScores(esob);
    	break;
    case ERROR:
      var msg = esob.getString(ERROR);
      setStatus("Error: " + msg);
      if (msg == "GameInPlay") {
      	handleGameInPlayError(esob);
      }
      break;
    case HEARTBEAT:
    	sendToPlugin(esob);
   		break;
    default:
      setStatus("Action not yet handled: " + action);
  }
}

function handleGameInPlayError(esob) {
	//log("handleGameInPlayError");
  var board = esob.getCharacterArray(BOARD);
  var availableLetters = esob.getCharacterArray(AVAILABLE_LETTERS);
  var numberOfLetters = board.length;
  
  init(numberOfLetters, availableLetters);
  setStatus("Game in Play");
  initScoreBoard(room.users);
	updateBoard(board);
  var arr = esob.getEsObjectArray(PLAYER_LIST);
  for (i=0; i< arr.length; i++) {
  	updatePlayerScore(arr[i]);
  }
}

function handleStartGame(esob) {
  var board = esob.getCharacterArray(BOARD);
  var availableLetters = esob.getCharacterArray(AVAILABLE_LETTERS);
  var numberOfLetters = board.length;
  
  init(numberOfLetters, availableLetters);
  setStatus("Game in Play");
  initScoreBoard(room.users);
}

// this is only needed when it's a different player's guess
function removeLetterButton(letter) {
	var id;
	var thingelem;
	for (i = 0; i < alphabet.length; i++) {
		id = "a" + String(i);
		thingelem = document.getElementById(id);
		if (letter == thingelem.textContent) {
			thingelem.style.display = "none";
		}
	}
}

function removeAllAlphabetLetters() {
	var id;
	var thingelem;
	for (i = 0; i < alphabet.length; i++) {
		id = "a" + String(i);
		thingelem = document.getElementById(id);
		if (thingelem) {
			thingelem.style.display = "none";
			thingelem.textContent = "";
			thingelem.removeEventListener('click',pickelement,false);
			var pn = thingelem.parentNode;
			if (pn) {
				pn.removeChild(thingelem); 
			}
		}
	}
}

function removeAllBlanks() {
	var id;
	var thingelem;
	for (i = 0; i < numberOfLetters; i++) {
		id = "s" + String(i);
		thingelem = document.getElementById(id);
		if (thingelem) {
			thingelem.style.display = "none";
			thingelem.textContent = "";
			var pn = thingelem.parentNode;
			if (pn) {
				pn.removeChild(thingelem); 
			}
		}
	}
}

function removeAllAlphabetListeners() {
	var id;
	var thingelem;
	for (i = 0; i < alphabet.length; i++) {
		id = "a" + String(i);
		thingelem = document.getElementById(id);
		if (thingelem) {
			thingelem.style.display = "none";
			thingelem.textContent = "";
			thingelem.removeEventListener('click',pickelement,false);
		}
	}
}

function handleAnnounceScores(esob) {
	var word = esob.getString(WORD);
	//log("word: " + word);
	//displaySolution(word);
	var winner = esob.getString(ROUND_WINNER);
	//log("winner: " + winner);

  ctx.fillStyle=gallowscolor;
  out = "Word was: " +  word;
  ctx.fillText(out,200, 100);
  ctx.fillText("Winner: " + winner,200, 140);
  
  var arr = esob.getEsObjectArray(PLAYER_LIST);
  for (i=0; i< arr.length; i++) {
  	// process each player
  	updatePlayerScore(arr[i]);
  }
  
  // setup for next round
  removeAllBlanks();
  var ele = document.getElementById('alphabet');
  if (ele) {
  	ele.removeNode(true); 
	}
	
  var ele2 = document.getElementById('secret');
  if (ele2) {
  	ele2.removeNode(true); 
 	}
}

function displaySolution(word) {
  for (i=0; i < word.length; i++) {
      id = "s"+String(i);
      document.getElementById(id).textContent = word[i];
  }
}

function updateBoard(board) {
  for (i=0;i<board.length;i++) {
    if (board[i] != '_') {
      id = "s"+String(i);
      document.getElementById(id).textContent = String.fromCharCode(board[i]);
    }
  }
}

function handleMistake() {
    steps[cur]();
    cur++;
    if (cur >= steps.length) {
    	removeAllAlphabetListeners();
    }
}

function handleSubmitLetter(esob) {
	var letter = esob.getString(SUBMIT_LETTER);
	removeLetterButton(letter);
	var pInfo = esob.getEsObject(NAME);
	var name = pInfo.getString(NAME);
	var score = esob.getInteger(SCORE);
	if (name == username) {
		canClick = true;
	}
	if (score > 0) {
		// good letter!
		setStatus("good letter: " + letter);
	} else {
		// bad guess
		if (name == username) {
			setStatus("bad letter: " + letter);
			handleMistake();
		}
	}
  var board = esob.getCharacterArray(BOARD);
	updateBoard(board);
	updatePlayerScore(pInfo);
}

function quickJoin() {
  var qjr = new QuickJoinGameRequest();
  qjr.gameType = PLUGIN_NAME;
  qjr.zoneName = PLUGIN_NAME;
  qjr.createOnly = false;
  
  var criteria = new SearchCriteria();
  criteria.gameType = PLUGIN_NAME;
  criteria.gameId = -1;
  
  qjr.criteria = criteria;
  
  es.engine.send(qjr);
}

function onJoinRoomEvent(event) {
  setStatus("joined a room " + event.zoneId + " " + event.roomId);
  room = es.managerHelper.zoneManager.zoneById(event.zoneId).roomById(event.roomId);
}

function onCreateOrJoinGameResponse(event) {
  setStatus("CreateOrJoinGameResponse.  success =  " + event.successful );
  if (event.successful) {
    room = es.managerHelper.zoneManager.zoneById(event.zoneId).roomById(event.roomId);
    document.getElementById('chat').style.display='block';
    document.SendForm.chat.focus();
  }
}

function sendPublicMessage() {
  var pmr = new PublicMessageRequest();
  pmr.zoneId = room.zoneId;
  pmr.roomId = room.id;
  var text = document.SendForm.chat.value;
  pmr.message = text;
  
  es.engine.send(pmr);
  //log(">> " + username + ": " + pmr.message);
  document.SendForm.chat.value = "";
  document.SendForm.chat.focus();
  showAll();
}

function sendToPlugin(esob) {
  var pr = new PluginRequest();
  pr.zoneId = room.zoneId;
  pr.roomId = room.id;
  pr.pluginName = PLUGIN_NAME;
  pr.parameters = esob;

  es.engine.send(pr);
}

function submitLetter(letter) {
  var esob = new ElectroServer.EsObject();
  esob.setString(ACTION, SUBMIT_LETTER);
  esob.setString(SUBMIT_LETTER, letter);
  sendToPlugin(esob);
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
  
  if (event.successful)
    quickJoin();
  
}

function setStatus(msg) {
  document.getElementById("status").innerHTML = msg + "<br/>";
}


function log(msg) {
  document.getElementById("log").innerHTML += msg + "<br/>";
}

function disableEnterKey(e)
{
  var key;
  
  if(window.event)
    key = window.event.keyCode;     //IE
  else
    key = e.which;     //firefox
  
  if(key == 13)
    return false;
  else
    return true;
}

