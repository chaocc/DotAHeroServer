package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;

public class ChooseHero implements ScheduledCallback {
    
    final String tag = "==> choose hero";
    TableModel table;
    int tickCounter = c.default_wait_time;
    int waitingType;
    Waiter waiter;
    
    public ChooseHero(TableModel input, Waiter inputWaiter, int waitingType) {
    
        this.table = input;
        this.waiter = inputWaiter;
        this.waitingType = waitingType;
    }
    
    @Override
    public void scheduledCallback() {
    
        boolean allConfirmed = checkWaitingState();
        boolean autoDesided = tick();
        if (allConfirmed || autoDesided) {
            goon();
        }
    }
    
    private void goon() {
    
        waitingType = c.game_state.waiting_type.none;
        table.broadcastHeroInited();
        table.dispatchHandcards();
    }
    
    private boolean checkWaitingState() {
    
        int confirmed = 0;
        for (Player player : table.getPlayers().getPlayerList()) {
            String action = player.getAction();
            if (action.equals(c.playercon.state.desp.confirmed.hero)) {
                confirmed += 1;
            }
        }
        if (confirmed >= table.getPlayers().getCount()) {
            waiter.cancelScheduledExecution(waiter.execution_id);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean tick() {
    
        if (waitingType == c.game_state.waiting_type.none) {
            waiter.cancelScheduledExecution(waiter.execution_id);
        } else if (tickCounter < 1) {
            boolean autoDesided = false;
            autoDesideHero();
            autoDesided = true;
            waiter.cancelScheduledExecution(waiter.execution_id);
            waitingType = c.game_state.waiting_type.none;
            return autoDesided;
        } else {
            tickCounter--;
        }
        return false;
    }
    
    private void autoDesideHero() {
    
        tickCounter = -1;
        for (Player player : table.getPlayers().getPlayerList()) {
            player.performSimplestChoice();
        }
    }
}
