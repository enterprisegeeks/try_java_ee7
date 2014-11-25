/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import enterprisegeeks.entity.Chat;
import enterprisegeeks.entity.Room;
import enterprisegeeks.model.Auth;
import enterprisegeeks.model.ChatRoom;
import enterprisegeeks.service.Service;
import java.sql.Timestamp;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

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
        List<Chat> added = service.findChatByRoom(auth.getSelected(), null);
        chatRoom.setChats(added);
        if (!added.isEmpty()) {
            
            chatRoom.setLastPost(added.get(added.size() -1).getPosted());
        }
    }
    /** 発言を行い、発言内容を更新する。 */
    @Transactional
    public void chat(){
        
        Chat chat = new Chat();
        chat.setContent(chatRoom.getContent());
        chat.setRoom(auth.getSelected());
        chat.setSpeaker(auth.getAccount());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        chat.setPosted(now);
        
        service.addChat(chat);
        
        chatRoom.setContent("");
        
        // 以前より更新されたチャットを取得
        List<Chat> added = service.findChatByRoom(auth.getSelected(), chatRoom.getLastPost());
        
        chatRoom.getChats().addAll(added);
        chatRoom.setLastPost(added.get(added.size() - 1).getPosted());
    }
}
