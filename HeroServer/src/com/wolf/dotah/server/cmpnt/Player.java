package com.wolf.dotah.server.cmpnt;

import com.wolf.dotah.server.cmpnt.player.Ai;
import com.wolf.dotah.server.cmpnt.player.PlayerAvailableTargetModel;
import com.wolf.dotah.server.cmpnt.player.PlayerProperty;
import com.wolf.dotah.server.cmpnt.player.PlayerState;
import com.wolf.dotah.server.cmpnt.player.player_const;
import com.wolf.dotah.server.layer.translator.ServerUpdateSequence;
import com.wolf.dotah.server.util.c;
import com.wolf.dotah.testframework.ClientRequest;

public class Player implements player_const {
    
    //TODO attackable
    //TODO disarmable
    
    private PlayerState state;//hero 在干嘛, 可以干嘛
    private PlayerProperty property;//player 属性的状态
    
    private PlayerAvailableTargetModel targets;
    
    public void act(ClientRequest request) {
        
        //TODO 这个还要吗? 不知道是否有用
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
    /**
     * 每一个public的update方法, 都要把update的过程加入到update steps里, 供translate时候用
     */
    public void updateState(String state, Data params, ServerUpdateSequence sequence) {
        
        //如果sequence是空的, 就可以抛出update sequence not start exception
        updateState(this.getState().setStateDesp(state), params, sequence);
    }
    
    /**
     * 每一个public的update方法, 都要把update的过程加入到update steps里, 供translate时候用
     */
    public void updateProperty(String propertyName, Data result, ServerUpdateSequence sequence) {
        
        // TODO 先要把update property 翻译成server action, 然后把server action放到step里, 而不是property name
        // sequence.add(some server action,  and data);
        // TODO Auto-generated method stub
    }
    
    private void updateState(PlayerState state, Data params, ServerUpdateSequence sequence) {
        
        this.state = state;
        state.updateDetail(params);
        sequence.add(state.getStateDesp(), this.getState().toData());
    }
    
    public Data toData() {
        
        Data result = new Data();
        //TODO 加state
        //TODO 加property
        //TODO 加PlayerAvailableTargetModel
        return result;
        //TODO 是否要加username 和ai?
    }
    
    public Player(String name) {
        
        this.userName = name;
        state = new PlayerState();
    }
    
    public PlayerProperty getProperty() {
        
        return property;
    }
    
    public void setProperty(PlayerProperty property) {
        
        this.property = property;
    }
    
    public PlayerState getState() {
        
        return state;
    }
    
    public void setState(PlayerState state) {
        
        this.state = state;
    }
    
    private String userName;
    private Ai ai;
    
    public boolean isAi() {
        if (ai == null) { return false; }
        return ai.isAi();
    }
    
    public void setAi(Ai ai) {
        
        this.ai = ai;
    }
    
    public Ai getAi() {
        
        return ai;
    }
    
    public String getUserName() {
        
        return userName;
    }
    
    public void setUserName(String userName) {
        
        this.userName = userName;
    }
    
    /**
     * 相当于客户端拿来了新消息, 在做判断
     * @param action
     */
    public void performAiAction(String action) {
        
        if (action.equals(c.server_action.free_play)) {
        } else {// choosing
            if (state.getStateDesp().equals(playercon.state.desp.choosing.choosing_hero)) {
                int[] pickResult = state.toData().getIntegerArray(playercon.state.param_key.general.id_list, new int[] {});
                int heroId = ai.chooseSingle(pickResult);
                this.initPropertyWithHeroId(heroId);
            }
        }
    }
    
    public void getResult(int[] pickResult) {
        
        if (this.getState().getStateDesp().equals(playercon.state.desp.choosing.choosing_hero)) {
            int heroId = pickResult[0];
            this.initPropertyWithHeroId(heroId);
        }
        
    }
    
    private void initPropertyWithHeroId(int heroId) {
        
        property = new PlayerProperty(heroId);
        //TODO 不是在这里, 而是在全都收到选择了英雄后broadcast chose property
    }
    
}
