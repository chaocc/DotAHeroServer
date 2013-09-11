package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;

public class Animating implements ScheduledCallback {
    final String tag = "==> Animating waiting 2 ";
    TableModel table;
    int waitingType;
    int tickCounter = c.default_wait_time;
    
    public Animating(TableModel inputTable, int waitingType) {
    
        this.table = inputTable;
        this.waitingType = waitingType;
    }
    
    
    @Override
    public void scheduledCallback() {
    
        // TODO Auto-generated method stub
        
    }
    
}
