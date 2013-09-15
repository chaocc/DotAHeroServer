package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;

public class FreePlay implements ScheduledCallback {
    final String tag = "==> FreePlay counting down ==> ";
    TableModel table;
    Player player;
    int tickCounter = c.default_wait_time;
    
    public FreePlay(TableModel inputTable, Player inputPlayer) {
    
        this.table = inputTable;
        this.player = inputPlayer;
    }
    
    
    @Override
    public void scheduledCallback() {
    
        boolean stillWaiting = stillWaiting();
        if (stillWaiting) {
            tick();
        } else {
            table.cancelScheduledExecution();
        }
    }
    
    
    private void tick() {
    
        if (tickCounter < 1) {
            player.cancel();
            table.cancelScheduledExecution();
        }
        tickCounter -= 1;
        
    }
    
    
    private boolean stillWaiting() {
    
        return player.stateAction.equals(c.action.free_play);
    }
    
}
