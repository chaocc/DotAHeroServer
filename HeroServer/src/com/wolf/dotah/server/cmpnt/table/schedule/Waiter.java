package com.wolf.dotah.server.cmpnt.table.schedule;

import com.wolf.dotah.server.MessageCenter;
import com.wolf.dotah.server.cmpnt.TableModel.tablevar;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
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
    
    
    public void becauseOf(String serverAction) {
    
        l.logger().d(tag, "because of " + serverAction);
        waitReason = serverAction;
        if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
            this.choosing_hero = messenger.scheduleExecution(1000, tablevar.wait_time + 1, new ChooseHero(messenger.getTable(), this, waitingType));
        } else if (waitReason.equals(c.server_action.choosing)) {
            CutCard cc = new CutCard(messenger.getTable(), this, waitingType);
            this.cutting = messenger.scheduleExecution(1000, tablevar.wait_time + 1, cc);
        }
        
    }
    
    public void cancelScheduledExecution(int callback_id) {
    
        l.logger().d(tag, "cancelScheduledExecution " + callback_id);
        messenger.cancelScheduledExecution(callback_id);
    }
    
    public int choosing_hero = -1, cutting = -1;
}
