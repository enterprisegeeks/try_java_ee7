/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import enterprisegeeks.entity.Room;
import enterprisegeeks.model.Auth;
import enterprisegeeks.model.ChatRoom;
import enterprisegeeks.service.Service;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */
@Named
@RequestScoped
public class ChatRoomAction {
    
    @Inject
    private Auth auth;
    
    @Inject
    private ChatRoom chatRoom;
    
    @Inject
    private Service service;
    
    public String prepareRoom(){
        chatRoom.setRooms(service.allRooms());
        return "chatroom";
    }
    
    /** 部屋を選択し、現在までの会話を取得する
     * 引数はViewから取得可能。
     */
    public void selectRoom(Room room) {
        auth.setSelected(room);
        
    }
    /** 発言を行い、発言内容を更新する。 */
    public void chat(){
        //TODO 登録
        chatRoom.setContent("");
        
        //TODO 再取得
    }
}
