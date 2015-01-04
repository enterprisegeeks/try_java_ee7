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
import enterprisegeeks.service.ChatNotifier;
import enterprisegeeks.service.Service;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 *
 */
@Named
@ViewScoped //viewScopeは管理Beanのみにしたほうが良い気がする。
public class ChatRoomAction implements Serializable{
    
    @Inject
    private Auth auth;
    
    @Inject
    private ChatRoom chatRoom;
    
    @Inject
    private Service service;
    
    @Inject
    private ChatNotifier notify;
    
    /**
     * VIEW初期化時に実行し、チャットルームの一覧をviewScopeのオブジェクトに設定する。
     */
    public void prepareRoom(){
        chatRoom.setRooms(service.allRooms());
    }
    
    /** 部屋を選択し、現在までの会話を取得する
     * 引数はViewから取得可能。
     */
    public void selectRoom(Room room) {
        auth.setSelected(room);
        List<Chat> added = service.findChatByRoom(auth.getSelected(), null);
        chatRoom.setChats(added);
        
        chatRoom.setContent("");
        if (!added.isEmpty()) {
            chatRoom.setLastPost(added.get(added.size() -1).getPosted());
        }
    }
    /** 発言を行う。 */
    @Transactional
    public void chat(){
        
        Chat chat = new Chat();
        chat.setContent(chatRoom.getContent().trim());
        chat.setRoom(auth.getSelected());
        chat.setSpeaker(auth.getAccount());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        chat.setPosted(now);
        
        service.addChat(chat);
        
        chatRoom.setContent("");
        
        notify.notifyNewChat();
        
    }
    
    // ** webscoket通知から起動され、チャット一覧を更新する。 */
    public void refreshChat(){
        // 以前より更新されたチャットを取得
        List<Chat> added = service.findChatByRoom(auth.getSelected(), chatRoom.getLastPost());
        
        if (!added.isEmpty()) {
            chatRoom.getChats().addAll(added);
            chatRoom.setLastPost(added.get(added.size() - 1).getPosted());   
        }
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }
}
