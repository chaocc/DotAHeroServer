package com.wolf.dotah.server.cmpnt;


import com.wolf.dotah.server.TablePlugin;
import com.wolf.dotah.server.cmpnt.table.CardDropStack;
import com.wolf.dotah.server.cmpnt.table.CardRemainStack;
import com.wolf.dotah.server.cmpnt.table.HeroCandidateModel;
import com.wolf.dotah.server.cmpnt.table.PlayerList;
import com.wolf.dotah.server.cmpnt.table.TableState;
import com.wolf.dotah.server.cmpnt.table.Ticker;


public class TableModel {
    
    private TablePlugin dispatcher;
    TableState state;  //TODO define states
    HeroCandidateModel heroCandidates;
    PlayerList players;
    CardRemainStack remainStack;
    CardDropStack dropStack;
    Ticker ticker;
    
    
    private Integer[] fakeHeroIds = { 21, 12, 2, 3, 28, 17 };
    
    
    public TableModel(TablePlugin deskController) {
        //TODO init card deck   ,       parsing and model behaviors
        //TODO init hero candidates,    parsing and model behaviors
        //TODO init player basic info, from plugin api
        //TODO design ticker
        //TODO init remain card list
        // 21, 12, 2, 3, 28, 17
        
        // pick ( 3* player size ) heros for choosing
        // shuffle
        // each give 3
        
        //TODO 移到 Players里
        //        players = new ArrayList<Player>();
        //        
        //        this.dispatcher = deskController;
        //        int userIndex = 0;
        //        for (String userName : deskController.getApi().getUsers()) {
        //            Player player = new Player(dispatcher);
        //            player.setUserName(userName);
        //            List<Integer> heroIdsForChoose = new ArrayList<Integer>();
        //            for (int i = 0; i < 3; i++) {
        //                int heroIndex = i + 3 * userIndex;
        //                heroIdsForChoose.add(heroIndex);
        //            }
        //            //            player.setHerosForChoosing(heroIdsForChoose);
        //            players.add(player);
        //            userIndex++;
        //        }
        
    }
    
    //TODO 等选完hero了, 把player list 里的每个player更新好
    
    
    
}
