
/**
 * 
 * 有很多客户端需要做的事情目前都在服务器端做了.
 * 为了保证服务器的纯净, 需将本该客户端做的事情抽出来.
 * 因此增加vclient 顶层包.
 * 
 * vclient是GamePlugin与MessageCenter之间的一层.
 * 其实MessageCenter是模拟本来GamePlugin该做的分发消息的功能.
 * 
 * @author Solomon
 *
 */
package com.wolf.dotah.vclient;