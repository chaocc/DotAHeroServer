package com.wolf.dotah.server.cmpnt.table.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.cmpnt.card.Card;
import com.wolf.dotah.server.layer.dao.CardParser;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.l;
import com.wolf.dotah.server.util.u;

public class CutCard implements ScheduledCallback {
    final String tag = "===>> CutCard schedule callback ";
    TableModel table;
    //    Waiter waiter;
    int waitingType;
    int tickCounter = c.default_wait_time;
    
    public CutCard(TableModel inputTable, int waitingType) {
    
        this.table = inputTable;
        this.waitingType = waitingType;
    }
    
    @Override
    public void scheduledCallback() {
    
        l.logger().d(tag, "scheduledCallback, cutting card " + tickCounter);
        boolean allConfirmed = checkWaitingState();
        boolean autoDesided = tick();
        if (allConfirmed || autoDesided) {
            goon(autoDesided);
        }
        //TODO 2种条件cancel count down, 
        //一种是所有人都选完, 
        //另一种是时间到
    }
    
    private void goon(boolean autoDesided) {
    
        waitingType = c.game_state.waiting_type.none;
        //TODO 给每个人手里都减少一张牌
        //TODO 发给client id list
        Map<String, Integer> cutCards = table.showingCards();
        List<Player> pl = table.getPlayers().getPlayerList();
        List<Integer> cards = new ArrayList<Integer>();
        for (int i = 0; i < cutCards.size(); i++) {
            Player p = pl.get(i);
            l.logger().d(tag, "cutCards, " + cutCards.toString());
            int card = cutCards.get(p.getUserName());
            cards.add(card);
            p.getHandCards().remove(card, autoDesided);
        }
        
        //TODO 先拼点, 
        String biggestPlayer = "";
        int biggestFaceNumber = 0;
        int biggestCardId = -1;
        for (int i = 0; i < cards.size(); i++) {
            Card c = CardParser.getParser().getCardById(cards.get(i));
            if (c.getFaceNumber() > biggestFaceNumber) {
                biggestFaceNumber = c.getFaceNumber();
                biggestCardId = c.getId();
                biggestPlayer = pl.get(i).getUserName();
                l.logger().d(tag, "changing biggest to " + biggestPlayer + " with card " + c.getName());
            }
        }
        
        
        Data data = new Data();
        data.setAction(c.action.cutted);
        data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cards.toArray(new Integer[cards.size()])));
        data.setInteger(c.param_key.biggist_card_id, biggestCardId);
        data.setInteger(c.param_key.hand_card_count, pl.get(0).getHandCards().getCards().size());
        //  TODO  scheduler里不该有这么实际的逻辑, 要通过调用table等的行为实现. 所以这里不需要依赖messenger
        table.getMessenger().sendMessageToAll(data);
        
        
        l.logger().d(tag, "starting turn to player " + biggestPlayer);
        table.startTurn(biggestPlayer, 2);
    }
    
    private boolean checkWaitingState() {
    
        if (table.showingCards().size() == table.getPlayers().getCount()) {
            
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
            if (table.getState().getState() == c.game_state.not_started.cutting) {
                autoDesideCutting();
                autoDesided = true;
            }
            table.cancelScheduledExecution();
            waitingType = c.game_state.waiting_type.none;
            return autoDesided;
        } else {
            tickCounter--;
        }
        return false;
    }
    
    private void autoDesideCutting() {
    
        for (Player p : table.getPlayers().getPlayerList()) {
            if (!table.showingCards().keySet().contains(p.getUserName())) {
                p.performSimplestChoice();
            }
        }
    }
}
