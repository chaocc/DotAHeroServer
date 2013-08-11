package com.wolf.dotah.server.cmpnt.table;

import com.wolf.dotah.server.cmpnt.Player;


/**
 * 
 * not_start// 比如 choosing hero
 * playing_somebody_deciding
 * playing_somebody_free_play
 * 
 * game over
 * 
 * 宿命
 * @author Solomon
 *
 */
public class TableState implements table_const {
    
    int state;
    Player firstPlayer;
    Player currentPlayer;
}
