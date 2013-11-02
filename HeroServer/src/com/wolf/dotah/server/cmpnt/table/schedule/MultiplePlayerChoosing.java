package com.wolf.dotah.server.cmpnt.table.schedule;

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
    Player[] playerArray;
    String reason;
    
    public MultiplePlayerChoosing(TableModel inputTable, Player[] inputPlayerArray, String inputReason) {
    
        this.table = inputTable;
        this.playerArray = inputPlayerArray;
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
        
        return false;
    }
    
    public void tick() {
    
        if (tickCounter < 1) {
            for (Player player : playerArray) {
                player.autoDecise();
            }
            table.cancelScheduledExecution();
        }
        tickCounter -= 1;
    }
}
