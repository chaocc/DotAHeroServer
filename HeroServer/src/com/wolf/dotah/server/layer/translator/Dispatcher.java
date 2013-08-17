package com.wolf.dotah.server.layer.translator;

import com.electrotank.electroserver5.extensions.api.value.EsObject;
import com.wolf.dotah.server.GamePlugin;
import com.wolf.tool.c;
import com.wolf.tool.client_const;

public class Dispatcher {
    /*
     * TODO 问题, 如何来判断该给哪个translator呢, 如何把拿来的信息翻译成有用的信息呢
     * 需要有一个完美的依照理论
     * 
     * 解答,
     * 1, 对于client message来说比较好办, 会调用哪个model的行为就给哪个translator
     * 2, 对于model要给客户端的, 走哪个translator, 要写到更多逻辑才能清晰
     * 
     */
    
    
    private TableTranslator tableTranslator;
    private PlayerTranslator playerTranslator;
    private DecisionTranslator decisionTranslator;
    
    public void handleMessage(String user, EsObject msg) {
        String client_message = msg.getString(c.action, "");
        if (client_const.kActionStartGame.equals(client_message)) {
            tableTranslator.translateGameStartFromClient(client_const.kActionStartGame, msg);
        }
        //TODO 这个chose hero id的action, 就该交给decision translator?
        //        else if (c.client_constants.kActionChooseHeroId.equals(client_message)) {
        //            Player p = desk.getPlayerByUserName(user);
        //            p.setHeroId(message.getIntegerArray(c.choosing.id_list)[0]);
        //            //            desk.
        //        }
    }
    
    private static Dispatcher dispatcher;
    
    public static Dispatcher getDispatcher(GamePlugin gamePlugin) {
        if (dispatcher == null) {
            dispatcher = new Dispatcher(gamePlugin);
        }
        return dispatcher;
    }
    
    private Dispatcher(GamePlugin gamePlugin) {
        tableTranslator = TableTranslator.getTranslator(gamePlugin);
        playerTranslator = PlayerTranslator.getTranslator(gamePlugin);
        decisionTranslator = DecisionTranslator.getTranslator(gamePlugin);
        playerTranslator.setDecisionTranslator(decisionTranslator);
    }
}
