package com.wolf.dotah.server.layer.translator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.electrotank.electroserver5.extensions.api.value.UserValue;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel.tablevar;
import com.wolf.dotah.server.cmpnt.player.player_const.playercon;
import com.wolf.dotah.server.cmpnt.table.table_const.tablecon;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.server.util.u;
import com.wolf.tool.client_const;

public class MessageDispatcher {
    
    private TableTranslator tableTranslator;
    private PlayerTranslator playerTranslator;
    private DecisionTranslator decisionTranslator;
    private GamePlugin plugin;
    
    public void sendMessageToSingleUser(String user, EsObject msg) {
        this.debug(tag, "sendMessageToSingleUser:  user: " + user + ",  msg: " + msg.toString());
        plugin.getApi().sendPluginMessageToUser(user, msg);
    }
    
    public void sendMessageToAll(EsObject msg) {
        this.debug(tag, "sendMessageToAll: " + msg.toString());
        plugin.getApi().sendPluginMessageToRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId(), msg);
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
        this.debug(tag, "sendMessageToAllWithoutSpecificUser: exceptionUser: " + exceptionUser + ",  msg: " + msg.toString());
        Collection<UserValue> users = plugin.getApi().getUsersInRoom(plugin.getApi().getZoneId(), plugin.getApi().getRoomId());
        for (UserValue userv : users) {
            if (!userv.getUserName().equals(exceptionUser)) {
                plugin.getApi().sendPluginMessageToUser(userv.getUserName(), msg);
            }
        }
    }
    
    public void broadcastMessage(Data data) {
        sendMessageToAll(data);
    }
    
    private int schedule_waiting_for_everybody_id = -1;
    private int tickCounter = -1;
    
    //TODO 想想能不能抽出来waiter之类的 组件
    public MessageDispatcher waitingForEverybody() {
        tickCounter = tablevar.wait_time;
        waitingType = c.game_state.waiting_type.everybody;
        schedule_waiting_for_everybody_id = plugin.getApi().scheduleExecution(1000, -1, new WaitingForEverybody());
        return this;
    }
    
    class WaitingForEverybody implements ScheduledCallback {
        
        @Override
        public void scheduledCallback() {
            boolean allConfirmed = checkWaitingState();
            boolean autoDesided = tick();
            if (allConfirmed || autoDesided) {
                goon();
            }
            //TODO 2种条件cancel count down, 
            //一种是所有人都选完, 
            //另一种是时间到
        }
        
        private void goon() {
            waitingType = c.game_state.waiting_type.none;
            if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
                
                tableTranslator.getTable().broadcastHeroInited();
                tableTranslator.dspatchHandcards();
            } else if (waitReason.equals(c.server_action.choosing)) {
                if (MessageDispatcher.this.getTableTranslator().getTable().getState().getState() == tablecon.state.not_started.cutting) {
                    //TODO 给每个人手里都减少一张牌
                    //TODO 发给client id list
                    Map<String, Integer> cutCards = tableTranslator.getTable().getCutCards();
                    List<Player> pl = tableTranslator.getPlayerList().getPlayerList();
                    List<Integer> cards = new ArrayList<Integer>();
                    MessageDispatcher.this.debug(tag, "goon, ");
                    for (int i = 0; i < cutCards.size(); i++) {
                        Player p = pl.get(i);
                        int card = cutCards.get(p.getUserName());
                        cards.add(card);
                        List<Integer> handcards = p.getProperty().getHandCards().getCards();
                        MessageDispatcher.this.debug(tag, "removing card " + card + ", from player " + p.getUserName() + "'s hand " + handcards);
                        handcards.remove(handcards.indexOf(card));
                    }
                    Data data = new Data();
                    data.setAction(c.ac.cutted);
                    data.setIntegerArray(c.param_key.id_list, u.intArrayMapping(cards.toArray(new Integer[cards.size()])));
                    data.setInteger(client_const.param_key.kParamHandCardCount, pl.get(0).getProperty().getHandCards().getCards().size());
                    MessageDispatcher.this.sendMessageToAll(data);
                    waitReason = c.server_action.none;
                    //TODO 先拼点, 
                    String biggestPlayer = "";
                    int biggest = 0;
                    for (int i = 0; i < cards.size(); i++) {
                        if (cards.get(i) > biggest) {
                            biggest = cards.get(i);
                            biggestPlayer = pl.get(i).getUserName();
                        }
                    }
//                    Data obj=new Data();
//                    obj.setAction(serverAction);//kActionPlayingCard 出牌阶段
//                    obj.
                    //TODO 告诉玩家可以开始玩牌了, 
                }
            }
            //            waitReason = c.server_action.none;
            plugin.getApi().cancelScheduledExecution(schedule_waiting_for_everybody_id);
        }
        
        private boolean checkWaitingState() {
            int waiting = 0;
            int confirmed = 0;
            MessageDispatcher.this.debug(tag, "waitReason, " + waitReason);
            if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
                for (Player player : tableTranslator.getTable().getPlayers().getPlayerList()) {
                    String state = player.getState().getStateDesp();
                    if (state.equals(playercon.state.desp.choosing.choosing_hero)) {
                        waiting += 1;
                    } else if (state.equals(playercon.state.desp.confirmed.hero)) {
                        MessageDispatcher.this.debug(tag, "player: " + player.getUserName() + " confirmed");
                        confirmed += 1;
                    }
                }
                //                if (waiting < 1) {
                //                    waitingType = c.game_state.waiting_type.none;
                //                }
                MessageDispatcher.this.debug(tag, "confirmed: " + confirmed + " / " + tableTranslator.getTable().getPlayers().getCount());
                if (confirmed >= tableTranslator.getTable().getPlayers().getCount()) {
                    return true;
                } else {
                    return false;
                }
            } else if (waitReason.equals(c.server_action.choosing)) {
                MessageDispatcher.this.debug(tag, "cutted : " + tableTranslator.getTable().getCutCards().size() + " / " + tableTranslator.getTable().getPlayers().getCount());
                if (tableTranslator.getTable().getCutCards().size() == tableTranslator.getTable().getPlayers().getCount()) {
                    return true;
                } else {
                    return false;
                }
            }
            plugin.dlog(tag, "check waiting state fail");
            return false;
        }
        
        public boolean tick() {
            
            if (waitingType == c.game_state.waiting_type.none) {
                MessageDispatcher.this.debug(tag, "not waiting");
                plugin.getApi().cancelScheduledExecution(schedule_waiting_for_everybody_id);
            } else if (tickCounter < 1) {
                boolean autoDesided = false;
                if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
                    autoDesideHero();
                    autoDesided = true;
                    //                    tableTranslator.getTable().broadcastHeroInited();
                } else if (waitReason.equals(playercon.state.desp.choosing.choosing)) {
                    if (tableTranslator.getTable().getState().getState() == tablecon.state.not_started.cutting) {
                        autoDesideCutting();
                        autoDesided = true;
                    }
                }
                plugin.getApi().cancelScheduledExecution(schedule_waiting_for_everybody_id);
                return autoDesided;
            } else {
                //                sendCountDownSecondsLeftMessage();
                MessageDispatcher.this.debug(tag, "tick counter = " + tickCounter);
                tickCounter--;
            }
            return false;
        }
        
        private void autoDesideCutting() {
            MessageDispatcher.this.debug(tag, "autoDesideCutting");
            for (Player p : tableTranslator.getTable().getPlayers().getPlayerList()) {
                if (!tableTranslator.getTable().getCutCards().keySet().contains(p.getUserName())) {
                    p.performSimplestChoice();
                }
            }
        }
        
        /**
         * 
         */
        private void autoDesideHero() {
            tickCounter = -1;
            for (Player player : tableTranslator.getTable().getPlayers().getPlayerList()) {
                player.performSimplestChoice();
            }
            //            if (waitReason.equals(playercon.state.desp.choosing.choosing_hero)) {
            //                tableTranslator.dspatchHandcards();
            //            }
        }
        //        
        //        private void sendCountDownSecondsLeftMessage() {
        //            
        //            Data message = new Data();
        //            message.setAction(c.server_action.count_down);
        //            message.setInteger(c.param_key.left, tickCounter);
        //            MessageDispatcher.this.broadcastMessage(message);
        //            //            sendAndLog("GoFishGame.sendCountDownSecondsLeftMessage", message);
        //            tickCounter--;
        //        }
        
    }
    
    private String waitReason;
    private int waitingType;
    
    public void becauseOf(String serverAction) {
        waitReason = serverAction;
        
    }
    
    public void handleMessage(String user, EsObject msg) {
        
        int client_message = msg.getInteger(c.action, -1);
        if (client_const.ACTION_START_GAME == client_message) {
            this.debug(tag, "plugin: " + plugin);
            tableTranslator.translateGameStartFromClient(plugin, msg);
        } else if (client_const.ACTION_code_CHOSE_hero == client_message) {
            decisionTranslator.translateChose(tableTranslator.getPlayerList().getPlayerByUserName(user), msg);
        } else if (client_const.kActionChooseHeroId == client_message) {
            playerTranslator.translateUpdate(tableTranslator.getPlayerList().getPlayerByUserName(user), msg);
        } else if (client_const.kActionChooseCard == client_message) {
            playerTranslator.translateChose(user, msg);
        }
    }
    
    final String tag = "===>> MessageDispatcher ==>>  ";
    
    public MessageDispatcher(GamePlugin gamePlugin) {
        
        this.plugin = gamePlugin;
        tableTranslator = new TableTranslator(this);
        playerTranslator = new PlayerTranslator(this);
        decisionTranslator = new DecisionTranslator(this);
        tableTranslator.setDecisionTranslator(decisionTranslator);
        playerTranslator.setDecisionTranslator(decisionTranslator);
    }
    
    public void debug(String tag, String msg) {
        plugin.dlog(tag, msg);
    }
    
    public void destroyTable() {
        tableTranslator.destroyTable();
        
    }
    
    public GamePlugin getPlugin() {
        return plugin;
    }
    
    public void setPlugin(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    public TableTranslator getTableTranslator() {
        return tableTranslator;
    }
    
    public PlayerTranslator getPlayerTranslator() {
        return playerTranslator;
    }
    
    public DecisionTranslator getDecisionTranslator() {
        return decisionTranslator;
    }
    
}
