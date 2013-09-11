package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;

public class Animating implements ScheduledCallback {
    final String tag = "===>> CutCard schedule callback ";
    TableModel table;
    Waiter waiter;
    int waitingType;
    int tickCounter = c.default_wait_time;
    
    public Animating(TableModel inputTable, Waiter inputWaiter, int waitingType) {
    
        this.table = inputTable;
        this.waiter = inputWaiter;
        this.waitingType = waitingType;
    }
    
    
    @Override
    public void scheduledCallback() {
    
        // TODO Auto-generated method stub
        
    }
    
}
