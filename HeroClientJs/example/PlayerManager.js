var players = [];
var scoresTop;
var namesLeft = 20;
var scoresLeft = 190;
var mistakesLeft = 260;
var totalScoreLeft = 340;
var scoresTopPadding = 275;
var rowHeight = 25;
var namesLabels;
var scoresLabels;
var mistakesLabels;
var totalScoreLabels;
var scoreHeaders;
var headerLeft = [20, 180, 240, 320];

function clearPlayers() {
  players = [];
  var i;
  for (i = 0; i < 4; i++) {
    removeCell("ph", i);
    removeCell("p", i);
    removeCell("ps", i);
    removeCell("pm", i);
    removeCell("pt", i);
  }
}

function removeCell(prefix, column) {
  var id;
  var thingelem;
  id = prefix + String(column);
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



function updatePlayerScore(pInfo) {
  var name = pInfo.getString(NAME);
	//log("updatePlayerScore for " + name);
  var score = pInfo.getInteger(SCORE);
  var mistakes = pInfo.getInteger(MISTAKES);
  var totalscore = pInfo.getInteger(TOTAL_SCORE);
  
  var thisPinfo = null;
  var ptr = -1;
  for (var i=0; i < players.length; i++) {
    thisPinfo = players[i];
    if (thisPinfo.name == name) {
      ptr = i;
      break;
    }
  }
  
  if (ptr < 0) {
    initLatePlayer(name, score, mistakes, totalscore);
    return;
  }
  
  // update the data structure
  //log("new player score is: " + score);
  thisPinfo.score = score;
  thisPinfo.mistakes = mistakes;
  thisPinfo.totalscore = totalscore;
  
  // update the view
  var scoreElem = document.getElementById("ps"+String(i));
  scoreElem.textContent = String(score);
  var mistakesElem = document.getElementById("pm"+String(i));
  mistakesElem.textContent = String(mistakes);
  var totalElem = document.getElementById("pt"+String(i));
  totalElem.textContent = String(totalscore);
}

function initLatePlayer(name, score, mistakes, totalscore) {
  var i = players.length;
  var pInfo = new PlayerInfo(name, score, totalscore, mistakes, i);
  players[i] = pInfo;
  addOnePlayerLabel(scoresTop, name, i);
  addOneScoresLabel(scoresTop, score, i);
  addOneMistakesLabel(scoresTop, mistakes, i);
  addOneTotalScoreLabel(scoresTop, totalscore, i);
}

function initScoreBoard(users) {
  var i;
  clearPlayers();
  var canvas = document.getElementById('canvas');
  var canvasTop = findTop(canvas);
  scoresTop = canvasTop + scoresTopPadding;
  addScoreHeaders(scoresTop);
  
  for (i = 0; i < users.length; i++) {
    var player = users[i];
    var name = player.userName;
    var pInfo = new PlayerInfo(name, 0, 0, 0, i);
    players[i] = pInfo;
    addOnePlayerLabel(scoresTop, name, i);
    addOneScoresLabel(scoresTop, 0, i);
    addOneMistakesLabel(scoresTop, 0, i);
    addOneTotalScoreLabel(scoresTop, 0, i);
  }
  
}

function addOneScoreHeader(scoresTop, name, i) {
  var uniqueid = "ph"+String(i);
  var d = document.createElement('scoreHeaders');
  d.innerHTML = (
  "<div class='scoreheader' id='"+uniqueid+"'>"+name+"</div>");
  document.body.appendChild(d);
  var thingelem = document.getElementById(uniqueid);
  var x = headerLeft[i];
  var y = scoresTop - rowHeight;
  thingelem.style.top = String(y)+"px";
  thingelem.style.left = String(x)+"px";
}

function addScoreHeaders(scoresTop) {
  addOneScoreHeader(scoresTop, "Name", 0);
  addOneScoreHeader(scoresTop, "Score", 1);
  addOneScoreHeader(scoresTop, "Mistakes", 2);
  addOneScoreHeader(scoresTop, "MultiGame", 3);
}

// make the player name for the score board
function addOnePlayerLabel(scoresTop, name, i) {
  var uniqueid = "p"+String(i);
  var d = document.createElement('namesLabels');
  d.innerHTML = (
  "<div class='scores' id='"+uniqueid+"'>"+name+"</div>");
  document.body.appendChild(d);
  var thingelem = document.getElementById(uniqueid);
  var x = namesLeft;
  var y = scoresTop + i*rowHeight;
  thingelem.style.top = String(y)+"px";
  thingelem.style.left = String(x)+"px";
}

// make the player round score for the score board
function addOneScoresLabel(scoresTop, pts, i) {
  var uniqueid = "ps"+String(i);
  var d = document.createElement('scoresLabels');
  d.innerHTML = (
  "<div class='scores' id='"+uniqueid+"'>"+pts+"</div>");
  document.body.appendChild(d);
  var thingelem = document.getElementById(uniqueid);
  var x = scoresLeft;
  var y = scoresTop + i*rowHeight;
  thingelem.style.top = String(y)+"px";
  thingelem.style.left = String(x)+"px";
}

// make the player mistakes for the score board
function addOneMistakesLabel(scoresTop, pts, i) {
  var uniqueid = "pm"+String(i);
  var d = document.createElement('mistakesLabels');
  d.innerHTML = (
  "<div class='scores' id='"+uniqueid+"'>"+pts+"</div>");
  document.body.appendChild(d);
  var thingelem = document.getElementById(uniqueid);
  var x = mistakesLeft;
  var y = scoresTop + i*rowHeight;
  thingelem.style.top = String(y)+"px";
  thingelem.style.left = String(x)+"px";
}

// make the player multigame score for the score board
function addOneTotalScoreLabel(scoresTop, pts, i) {
  var uniqueid = "pt"+String(i);
  var d = document.createElement('totalScoreLabels');
  d.innerHTML = (
  "<div class='scores' id='"+uniqueid+"'>"+pts+"</div>");
  document.body.appendChild(d);
  var thingelem = document.getElementById(uniqueid);
  var x = totalScoreLeft;
  var y = scoresTop + i*rowHeight;
  thingelem.style.top = String(y)+"px";
  thingelem.style.left = String(x)+"px";
}

function PlayerInfo(name, score, totalScore, mistakes, index) {
  this.name = name;
  this.score = score;
  this.totalScore = totalScore;
  this.mistakes = mistakes;
  this.index = index;
}

