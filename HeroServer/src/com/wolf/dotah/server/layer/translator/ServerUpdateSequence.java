package com.wolf.dotah.server.layer.translator;

import java.util.ArrayList;
import java.util.List;
import com.wolf.dotah.server.cmpnt.Data;
import com.wolf.dotah.server.cmpnt.Player;
import com.wolf.dotah.server.cmpnt.TableModel;
import com.wolf.dotah.server.util.c;

/**
 * 在消息dispatch 来之后, 立刻设一个时间起始点, 这个时候开始维护server过程中都走了做了什么.
 * 所以其实从一开始client message dispatch来的时候, 就已经知道是要choosing还是free play等了
 * @param tag 这个tag目前没什么用, 只是保存了PlayerState. 当有复杂的sequence时候也许可以用来判断什么
 */
public class ServerUpdateSequence {
    private String finalTarget;// server action in general, also called action category
    private List<Step> updateSequence = new ArrayList<Step>();
    private Player subjectPlayer;
    private TableModel subjectTable;
    private boolean singleStepSequence = true;
    
    //TODO    private TableModel tableAsSubject;
    
    public ServerUpdateSequence(String serverAction, Player subject) {
        this.finalTarget = serverAction;
        this.subjectPlayer = subject;
    }
    
    public void add(String stepKey, Data data) {
        updateSequence.add(new Step(stepKey, data));
    }
    
    /**
    * submit 就是要将server处理完的信息发送给客户端了!
    * 
    * 所以要交给哪个translator也比较明确了, choosing交给decision translator, 
    * 并且这个是一开始就已经确定的!
    */
    public void submitServerUpdate() {
        if (subjectPlayer != null && subjectPlayer.isAi()) {
            submitAi();
        } else if (finalTarget.equals(c.server_action.choosing)) {
            //            .translate(this);
        } else if (finalTarget.equals(c.server_action.update_player_info)) {
            System.out.println("submit as update player info, not implemented");
        } else if (finalTarget.equals(c.server_action.free_play)) {
            System.out.println("submit as update player to free play, not implemented");
        }
        
    }
    
    public void submitServerUpdateByTable(TableModel table) {
        if (subjectPlayer != null && subjectPlayer.isAi()) {
            submitAi();
        } else if (finalTarget.equals(c.server_action.choosing)) {
            table.getTranslator().getDecisionTranslator().translate(this);
        }
    }
    
    private void submitAi() {
        subjectPlayer.performAiAction(finalTarget, c.param_key.hero_candidates);
        
    }
    
    public boolean isSingleStepSequence() {
        return singleStepSequence;
    }
    
    public void setSingleStepSequence(boolean singleStepSequence) {
        this.singleStepSequence = singleStepSequence;
    }
    
    public Player getSubjectPlayer() {
        return subjectPlayer;
    }
    
    public void setSubjectPlayer(Player subjectPlayer) {
        this.subjectPlayer = subjectPlayer;
    }
    
    public String getFinalTarget() {
        return finalTarget;
    }
    
    public void setFinalTarget(String finalTarget) {
        this.finalTarget = finalTarget;
    }
    
    public Step get(int i) {
        return updateSequence.get(i);
    }
    
    //    private String getStepType(String stepKey) {
    //        //TODO 这里还有很多东西要写, 要判断, 要处理
    //        if (stepKey.startsWith(c.server_action.choosing)) { return playercon.sequence.step.type.state; }
    //        return playercon.sequence.step.type.property;
    //    }
    
    public List<Step> getUpdateSequence() {
        return updateSequence;
    }
    
    public void setUpdateSequence(List<Step> updateSequence) {
        this.updateSequence = updateSequence;
    }
    
    @Override
    public String toString() {
        return "ServerUpdateSequence [updateSequence=" + updateSequence + "]";
    }
    
    /**
     * 每一个step 其实是一个server action, 比如hp变啦什么的, sp变啦, 等等
     * 里边有action 和 data
     */
    class Step {
        private String stepDesp;//其实就是server action
        private Data data;
        
        public Step(String stepDesp, Data data) {
            super();
            this.stepDesp = stepDesp;
            this.data = data;
        }
        
        public String getStepDesp() {
            return stepDesp;
        }
        
        public Data getData() {
            return data;
        }
        
    }
    
}
