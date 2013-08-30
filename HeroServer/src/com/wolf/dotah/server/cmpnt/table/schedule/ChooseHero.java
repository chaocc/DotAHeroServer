package com.wolf.dotah.server.cmpnt.table.schedule;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.MessageDispatcher;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel.tablevar;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
import com.wolf.dotah.server.util.c;

public class ChooseHero implements ScheduledCallback {
    
    final String tag = "==> choose hero";
    MessageDispatcher disp;
    int tickCounter = tablevar.wait_time;
    int waitingType;
    
    public ChooseHero(MessageDispatcher dispatcher, int waitingType) {
        this.disp = dispatcher;
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
        disp.getTableTranslator().getTable().broadcastHeroInited();
        disp.getTableTranslator().dspatchHandcards();
    }
    
    private boolean checkWaitingState() {
        //            int waiting = 0;
        int confirmed = 0;
        for (Player player : disp.getTableTranslator().getTable().getPlayers().getPlayerList()) {
            String action = player.getAction();
            ;
            if (action.equals(playercon.state.desp.confirmed.hero)) {
                confirmed += 1;
            }
        }
        if (confirmed >= disp.getTableTranslator().getTable().getPlayers().getCount()) {
            disp.cancelScheduledExecution(disp.choosing_hero);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean tick() {
        
        if (waitingType == c.game_state.waiting_type.none) {
            disp.cancelScheduledExecution(disp.choosing_hero);
        } else if (tickCounter < 1) {
            boolean autoDesided = false;
            autoDesideHero();
            autoDesided = true;
            disp.cancelScheduledExecution(disp.choosing_hero);
            waitingType = c.game_state.waiting_type.none;
            return autoDesided;
        } else {
            tickCounter--;
        }
        return false;
    }
    
    private void autoDesideHero() {
        tickCounter = -1;
        for (Player player : disp.getTableTranslator().getTable().getPlayers().getPlayerList()) {
            player.performSimplestChoice();
        }
    }
}
