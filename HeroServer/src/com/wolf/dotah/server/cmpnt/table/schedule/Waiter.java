package com.wolf.dotah.server.cmpnt.table.schedule;

import com.wolf.dotah.server.MessageCenter;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;

public class Waiter {
    final String tag = "Waiter ===>> ";
    private String waitReason;
    private int waitingType;
    private MessageCenter messenger;
    
    public Waiter(MessageCenter input) {
    
        this.messenger = input;
    }
    
    
    //TODO 想想能不能抽出来waiter之类的 组件
    public Waiter waitingForEverybody() {
    
        l.logger().d(tag, "start waiting");
        waitingType = c.game_state.waiting_type.everybody;
        return this;
    }
    
    
    public void becauseOf(String reason) {
    
        int waitTime = c.default_wait_time + 1;
        becauseOf(reason, waitTime);
        
    }
    
    public void becauseOf(String reason, int waitTime) {
    
        
        l.logger().d(tag, "because of " + reason);
        waitReason = reason;
        if (waitReason.equals(c.playercon.state.desp.choosing.choosing_hero)) {
            this.execution_id = messenger.scheduleExecution(1000, waitTime, new ChooseHero(messenger.getTable(), this, waitingType));
        } else if (waitReason.equals(c.action_string.choosing)) {
            CutCard cc = new CutCard(messenger.getTable(), this, waitingType);
            this.execution_id = messenger.scheduleExecution(1000, waitTime, cc);
        } else if (waitReason.equals(c.reason.animating)) {
            CutCard cc = new CutCard(messenger.getTable(), this, waitingType);
            this.execution_id = messenger.scheduleExecution(1000, waitTime, cc);
        }
        
    }
    
    public void cancelScheduledExecution(int callback_id) {
    
        l.logger().d(tag, "cancelScheduledExecution " + callback_id);
        messenger.cancelScheduledExecution(callback_id);
    }
    
    //    public int choosing_hero = -1, cutting = -1;
    public int execution_id = -1;
    
    
}
