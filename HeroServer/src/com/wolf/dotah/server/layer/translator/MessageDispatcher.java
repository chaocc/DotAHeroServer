package com.wolf.dotah.server.layer.translator;

import com.electrotank.electroserver5.extensions.api.ScheduledCallback;
import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.util.c;
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
    }
    
    public void sendMessageToAllWithoutSpecificUser(EsObject msg, String exceptionUser) {
        this.debug(tag, "sendMessageToAllWithoutSpecificUser: exceptionUser: " + exceptionUser + ",  msg: " + msg.toString());
    }
    
    public void boradcastMessage(Data data) {
        sendMessageToAll(data);
    }
    
    private int schedule_waiting_for_everybody_id = -1;
    private int tickCounter = -1;
    
    //TODO 想想能不能抽出来waiter之类的 组件
    public MessageDispatcher waitingForEverybody() {
        tickCounter = 10;
        waitingType = c.game_state.waiting_type.everybody;
        schedule_waiting_for_everybody_id = plugin.getApi().scheduleExecution(1000, -1, new WaitingForEverybody());
        return this;
    }
    
    class WaitingForEverybody implements ScheduledCallback {
        
        @Override
        public void scheduledCallback() {
            checkWaitingState();
            tick();
            
            //TODO 2种条件cancel count down, 
            //一种是所有人都选完, 
            //另一种是时间到
        }
        
        private void checkWaitingState() {
            // TODO Auto-generated method stub
            
        }
        
        public void tick() {
            
            //            if (gameState != GameState.Waiting) { return; }
            if (waitingType == c.game_state.waiting_type.none) {
                plugin.getApi().cancelScheduledExecution(schedule_waiting_for_everybody_id);
            } else if (tickCounter < 1) {
                //TODO 自动发给每个人牌, 然后cancel tick
                autoDeside();
                plugin.getApi().cancelScheduledExecution(schedule_waiting_for_everybody_id);
            } else {
                sendCountDownSecondsLeftMessage();
            }
        }
        
        private void autoDeside() {
            tickCounter = -1;
            
        }
        
        private void sendCountDownSecondsLeftMessage() {
            
            EsObject message = new EsObject();
            message.setInteger(c.action, client_const.action.count_down);
            message.setInteger(c.param_key.left, tickCounter);
            //            sendAndLog("GoFishGame.sendCountDownSecondsLeftMessage", message);
            tickCounter--;
        }
        
    }
    
    private String waitingReason;
    private int waitingType;
    
    public void becauseOf(String serverAction) {
        waitingReason = serverAction;
        
    }
    
    public void handleMessage(String user, EsObject msg) {
        
        int client_message = msg.getInteger(c.action, -1);
        if (client_const.ACTION_START_GAME == client_message) {
            this.debug(tag, "plugin: " + plugin);
            tableTranslator.translateGameStartFromClient(plugin, msg);
        } else if (client_const.ACTION_code_CHOSE_hero == client_message) {
            decisionTranslator.translateChose(tableTranslator.getPlayerList().getPlayerByUserName(user), msg);
        }
        //TODO 这个chose hero id的action, 就该交给decision translator?
        //        else if (c.client_constants.kActionChooseHeroId.equals(client_message)) {
        //            Player p = desk.getPlayerByUserName(user);
        //            p.setHeroId(message.getIntegerArray(c.choosing.id_list)[0]);
        //            //            desk.
        //        }
    }
    
    final String tag = "===>> MessageDispatcher ==>>  ";
    
    public MessageDispatcher(GamePlugin gamePlugin) {
        
        this.plugin = gamePlugin;
        tableTranslator = new TableTranslator(this);
        playerTranslator = PlayerTranslator.getTranslator();
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
    
}
