package com.wolf.dotah.server.cmpnt;


import java.util.ArrayList;
import java.util.List;

import com.wolf.dotah.server.DeskController;
import com.wolf.dotah.testframework.User;


public class DeskModel {
    public boolean game_started = false;
    public String game_state = "";
    
    List<Player> players;
    
    private DeskController controller;
    private Integer[] fakeHeroIds = { 21, 12, 2, 3, 28, 17 };
    
    public DeskModel(DeskController deskController) {
        // 21, 12, 2, 3, 28, 17
        
        // pick ( 3* player size ) heros for choosing
        // shuffle
        // each give 3
        
        //TODO 移到 Players里
        players = new ArrayList<Player>();
        
        this.controller = deskController;
        int userIndex = 0;
        for (String userName : deskController.getApi().getUsers()) {
            Player player = new Player(controller);
            player.setUserName(userName);
            List<Integer> heroIdsForChoose = new ArrayList<Integer>();
            for (int i = 0; i < 3; i++) {
                int heroIndex = i + 3 * userIndex;
                heroIdsForChoose.add(heroIndex);
            }
            player.setHerosForChoosing(heroIdsForChoose);
            players.add(player);
            userIndex++;
        }
        
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    public Player getPlayerByUser(User user) {
        
        
        return new Player(controller);
        
    }
    
    public Player getPlayerByUserName(String userName) {
        for (Player p : players) {
            if (p.getUserName().equals(userName)) { return p; }
        }
        return null;
    }
    
}
