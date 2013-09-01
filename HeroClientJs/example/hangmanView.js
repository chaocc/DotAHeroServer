// This code is based on a single player game found in "The Essential Guide to HTML5" by Jeanine Meyer.

var ctx;
var thingelem;
var alphabet;
var alphabety;
var alphabetTopPadding = 200;
var alphabetx = 20;
var alphabetwidth = 25;
var numberOfLetters;
var canClick = false;
var secret;
var lettersguessed = 0;
var secretx = 160;
var secrety;
var secretyPadding = 50;
var secretwidth = 50;
var gallowscolor = "brown";
var facecolor = "tan";
var bodycolor = "tan";
var noosecolor = "#F60";
var bodycenterx = 70;
var canvasTop;
var steps = [
drawgallows,
drawhead,
drawbody,
drawrightarm,
drawleftarm,
drawrightleg,
drawleftleg,
drawnoose
];
var cur = 0;
function drawgallows() {
  ctx.lineWidth = 8;
  ctx.strokeStyle = gallowscolor;
  ctx.beginPath();
  ctx.moveTo(2, 180);
  ctx.lineTo(40,180);
  ctx.moveTo(20,180);
  ctx.lineTo(20,40);
  ctx.moveTo(2,40);
  ctx.lineTo(80,40);
  ctx.stroke();
  ctx.closePath();
  
}
function drawhead() {
  ctx.lineWidth = 3;
  ctx.strokeStyle = facecolor;
  ctx.save();  //before scaling of circle to be oval
  ctx.scale(.6,1);
  ctx.beginPath();
  ctx.arc (bodycenterx/.6,80,10,0,Math.PI*2,false);
  ctx.stroke();
  ctx.closePath();
  ctx.restore();
}

function drawbody() {
  ctx.strokeStyle = bodycolor;
  ctx.beginPath();
  ctx.moveTo(bodycenterx,90);
  ctx.lineTo(bodycenterx,125);
  ctx.stroke();
  ctx.closePath();
  
}
function drawrightarm() {
  ctx.beginPath();
  ctx.moveTo(bodycenterx,100);
  ctx.lineTo(bodycenterx+20,110);
  ctx.stroke();
  ctx.closePath();
  
}
function drawleftarm() {
  ctx.beginPath();
  ctx.moveTo(bodycenterx,100);
  ctx.lineTo(bodycenterx-20,110);
  ctx.stroke();
  ctx.closePath();
  
}
function drawrightleg() {
  ctx.beginPath();
  ctx.moveTo(bodycenterx,125);
  ctx.lineTo(bodycenterx+10,155);
  ctx.stroke();
  ctx.closePath();
  
  
}

function drawleftleg() {
  ctx.beginPath();
  ctx.moveTo(bodycenterx,125);
  ctx.lineTo(bodycenterx-10,155);
  ctx.stroke();
  ctx.closePath();
  
}
function drawnoose() {
  ctx.strokeStyle = noosecolor;
  ctx.beginPath();
  ctx.moveTo(bodycenterx-10,40);
  ctx.lineTo(bodycenterx-5,95);
  ctx.stroke();
  ctx.closePath();
  ctx.save();
  ctx.scale(1,.3);
  ctx.beginPath();
  ctx.arc(bodycenterx,95/.3,8,0,Math.PI*2,false);
  ctx.stroke();
  ctx.closePath();
  ctx.restore();
  drawneck();
  drawhead();
  
  
}
function drawneck() {
  ctx.strokeStyle=bodycolor;
  ctx.beginPath();
  ctx.moveTo(bodycenterx,90);
  ctx.lineTo(bodycenterx,95);
  ctx.stroke();
  ctx.closePath();
  
}
function init(numberOfLetters, availableLetters){
  ctx = document.getElementById('canvas').getContext('2d');
  clearCanvas();
  setupgame(numberOfLetters, availableLetters);
  ctx.font="bold 20pt Ariel";
}

function clearCanvas(){
  var canvas = document.getElementById('canvas');
  var context = canvas.getContext('2d');
  context.clearRect(0, 0, canvas.width, canvas.height);
}

function setupgame(numberOfLetters, availableLetters) {
  var i;
  var x;
  var y;
  var uniqueid;
  this.numberOfLetters = numberOfLetters;
  cur = 0;
  alphabet = "";
  canClick = false;
  
  for (ii= 0; ii < availableLetters.length; ii++) {
    alphabet += String.fromCharCode(availableLetters[ii]);
  }
  
  secret = "";
  var canvas = document.getElementById('canvas');
  canvasTop = findTop(canvas);
  alphabety = canvasTop + alphabetTopPadding;
  secrety = canvasTop + secretyPadding;
  
  var an = alphabet.length;
  for(i=0;i<an;i++) {
    
    uniqueid = "a"+String(i);
    d = document.createElement('alphabet');
    d.innerHTML = (
    "<div class='letters' id='"+uniqueid+"'>"+alphabet[i]+"</div>");
    document.body.appendChild(d);
    thingelem = document.getElementById(uniqueid);
    x = alphabetx + alphabetwidth*i;
    y = alphabety;
    thingelem.style.top = String(y)+"px";
    thingelem.style.left = String(x)+"px";
    thingelem.addEventListener('click',pickelement,false);
  }
  
  for (i = 0; i < numberOfLetters; i++) {
    uniqueid = "s"+String(i);
    d = document.createElement('secret');
    d.innerHTML = (
    "<div class='blanks' id='"+uniqueid+"'> __ </div>");
    document.body.appendChild(d);
    thingelem = document.getElementById(uniqueid);
    x = secretx + secretwidth*i;
    y = secrety;
    thingelem.style.top = String(y)+"px";
    thingelem.style.left = String(x)+"px";
    
  }
  steps[cur]();
  cur++;
  
  // enable clicking
  canClick = true;
  return false;
}

// tell server the letter chosen, remove the button, then wait
function pickelement(ev) {
  if (!canClick) {
    return;
  }
  canClick = false;
  var picked = this.textContent;
  var id = this.id;
  document.getElementById(id).style.display = "none";
  submitLetter(picked);
}

function findTop(obj) {
  var curtop = 0;
  if (obj.offsetParent) {
    do {
      curtop += obj.offsetTop;
    } while (obj = obj.offsetParent);
  }
  return curtop;
}
