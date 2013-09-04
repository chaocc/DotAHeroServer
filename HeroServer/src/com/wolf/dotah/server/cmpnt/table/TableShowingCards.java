package com.wolf.dotah.server.cmpnt.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableShowingCards {
    List<Integer> cards;
    List<String> choosingOrUsingPlayer;// 如果是每人选一个, 或者每人出一个的, 就会对应用户名到这个域里.
    
    String faceToWhom;// 让谁看到牌面
    int count;
    
    public void startUsing(int count) {
    
        this.count = count;
        cards = new ArrayList<Integer>();
        choosingOrUsingPlayer = new ArrayList<String>();
    }
    
    
    public Map<String, Integer> getAllPlayerChoosingOrUsingCardMap() {
    
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (int i = 0; i < cards.size(); i++) {
            result.put(choosingOrUsingPlayer.get(i), cards.get(i));
        }
        return result;
    }
    
    public void addResultToShow(String user, Integer card) {
    
        if (user != null) {
            choosingOrUsingPlayer.add(user);
        }
        cards.add(card);
    }
    
}
