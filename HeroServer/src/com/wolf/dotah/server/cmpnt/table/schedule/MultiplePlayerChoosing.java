package com.wolf.dotah.server.cmpnt.table.schedule;

import java.util.HashMap;
import java.util.Map;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;


/*
 * 只要有1个人出了驱散, 则cancel schedule
 * 如果有人选了cancel, 则继续等待其他人cancel
 */
public class MultiplePlayerChoosing implements ScheduledCallback {
    final String tag = "==> MultiplePlayerChoosing ==> ";
    TableModel table;
    int tickCounter = c.default_wait_time;
    String reason;
    Map<Player, Boolean> canceledPlayer;
    
    public MultiplePlayerChoosing(TableModel inputTable, Player[] inputPlayerArray, String inputReason) {
    
        this.table = inputTable;
        canceledPlayer = new HashMap<Player, Boolean>();
        for (Player p : inputPlayerArray) {
            canceledPlayer.put(p, false);
        }
        this.reason = inputReason;
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
    
    private boolean stillWaiting() {
    
        l.logger().d(tag, "stillWaiting for " + this.reason);
        
        return canceledPlayer.values().contains(false);
    }
    
    public void tick() {
    
        if (tickCounter < 1) {
            for (Player player : canceledPlayer.keySet()) {
                player.autoDecise();
            }
            table.cancelScheduledExecution();
        }
        tickCounter -= 1;
    }
    
    public void playerCancel(Player player) {
    
        canceledPlayer.put(player, true);
    }
}
