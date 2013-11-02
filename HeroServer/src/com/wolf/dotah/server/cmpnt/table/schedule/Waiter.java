package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.MessageCenter;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;

public class Waiter {
    final String tag = "Waiter ==> ";
    private String waitReason;
    private int waitingType;
    private ScheduledCallback scheduleCallback;
    private MessageCenter messenger;
    
    public Waiter(MessageCenter input) {
    
        this.messenger = input;
    }
    
    
    public Waiter waitingForEverybody() {
    
        l.logger().d(tag, "start waiting");
        waitingType = c.game_state.waiting_type.everybody;
        return this;
    }
    
    public Waiter waitForSingleChoosing(Player player, int sec) {
    
        l.logger().d(tag, "start waiting for ==> " + player.userName);
        waitingType = c.game_state.waiting_type.single;
        this.execution_id = messenger.scheduleExecution(1000, sec * 1000, new SinglePlayerChoosing(messenger.getTable(), player));
        l.logger().d(tag + " waitForSingleChoosing", "waiting with id=" + this.execution_id);
        return this;
    }
    
    public void becauseOf(String reason) {
    
        if (waitingType == c.game_state.waiting_type.single) { return; }
        // TODO 要查这个必须得+1的问题, 与fish server对比看看该怎么写.
        int waitTime = c.default_wait_time + 1;
        becauseOf(reason, waitTime);
        
    }
    
    public void becauseOf(String reason, int waitTime) {
    
        if (waitingType == c.game_state.waiting_type.single) { return; }
        
        l.logger().d(tag, "because of " + reason);
        waitReason = reason;
        if (waitReason.equals(c.playercon.state.choosing.choosing_hero)) {
            this.execution_id = messenger.scheduleExecution(1000, waitTime, new ChooseHero(messenger.getTable(), waitingType));
        } else if (waitReason.equals(c.action.choosing)) {
            CutCard cc = new CutCard(messenger.getTable(), waitingType);
            this.execution_id = messenger.scheduleExecution(1000, waitTime, cc);
        } else if (waitReason.equals(c.reason.animating)) {
            StartTurn st = new StartTurn(messenger.getTable(), waitTime);
            this.execution_id = messenger.scheduleExecution(1000, waitTime + 1, st);
        } else if (waitReason.equals(c.action.free_play)) {
            FreePlay fp = new FreePlay(messenger.getTable(), waitTime);
            this.execution_id = messenger.scheduleExecution(1000, waitTime + 1, fp);
        }
        l.logger().d(tag + " becauseOf", "waiting with id=" + this.execution_id);
        
    }
    
    public void cancelScheduledExecution() {
    
        l.logger().d(tag, "cancelScheduledExecution " + execution_id);
        messenger.cancelScheduledExecution(execution_id);
        scheduleCallback = null;
    }
    
    //    public int choosing_hero = -1, cutting = -1;
    public int execution_id = -1;
    
    public void waitingForThesePlayers(String reason, Player[] players, int sec) {
    
        this.waitReason = reason;
        if (reason.equals(c.reason.choosing_dispell)) {
            //1000, sec * 1000,
            MultiplePlayerChoosing mpc = new MultiplePlayerChoosing(messenger.getTable(), players, reason);
            this.execution_id = messenger.scheduleExecution(1000, sec * 1000, mpc);
            scheduleCallback = mpc;
        }
        
    }
    
    public ScheduledCallback getSchedulingCallback() {
    
        return this.scheduleCallback;
    }
    
    
}
