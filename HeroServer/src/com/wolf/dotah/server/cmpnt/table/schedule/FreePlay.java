package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.TableModel.tablevar;

public class FreePlay implements ScheduledCallback {
    final String tag = "==> FreePlay counting down ==> ";
    TableModel table;
    int tickCounter = tablevar.wait_time;
    int waitingType;
    Waiter waiter;
    public FreePlay(TableModel inputTable, Waiter inputWaiter, int inputWaitingType){
        this.table = inputTable;
        this.waiter = inputWaiter;
        this.waitingType = inputWaitingType;
    }
    
    
    @Override
    public void scheduledCallback() {
    
        // TODO Auto-generated method stub
        
    }
    
}
