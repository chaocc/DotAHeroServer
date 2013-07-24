package com.wolf.dotah.server.component;


import com.wolf.dotah.server.DeskController;
import com.wolf.dotah.testframework.ClientRequest;


public class PlayerModel {
    
    DeskController controller;
    
    
    public PlayerModel(DeskController deskController) {
    
        this.controller = deskController;
    }
    
    
    public void act(ClientRequest request) {
    
        //3, player处理action, 得到结果: 改变状态或要求客户端回应
        //4, 把改变后的状态或者需要新信息的请求发给客户端
    }
    
}
