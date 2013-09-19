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
    
    public ChooseHero(TableModel input, int waitingType) {
    
        this.table = input;
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
        table.initHandcards();
    }
    
    private boolean checkWaitingState() {
    
        int confirmed = 0;
        for (Player player : table.players.getPlayerList()) {
            String action = player.stateAction;
            if (action.equals(c.playercon.state.confirmed.hero)) {
                confirmed += 1;
            }
        }
        if (confirmed >= table.players.getCount()) {
            table.cancelScheduledExecution();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean tick() {
    
        if (waitingType == c.game_state.waiting_type.none) {
            table.cancelScheduledExecution();
        } else if (tickCounter < 1) {
            boolean autoDesided = false;
            autoDesideHero();
            autoDesided = true;
            table.cancelScheduledExecution();
            waitingType = c.game_state.waiting_type.none;
            return autoDesided;
        } else {
            tickCounter--;
        }
        return false;
    }
    
    private void autoDesideHero() {
    
        tickCounter = -1;
        for (Player player : table.players.getPlayerList()) {
            player.performSimplestChoice();
        }
    }
}
