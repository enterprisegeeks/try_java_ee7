/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import enterprisegeeks.entity.Account;
import enterprisegeeks.entity.Chat;
import enterprisegeeks.entity.Room;
import java.sql.Timestamp;
import java.util.List;

/**
 * 永続層周りの機能を提供
 */
public interface Service{
    
    
    public boolean registerAccount(Account accout);
    
    /** ログイン用トークン発行 */
    public String publishToken(Account account);
    
    /** チャットルーム一覧 */
    public List<Room> allRooms();
    
    /** チャットルームのチャット一覧を取得 */
    public List<Chat> findChatByRoom(Room room, Timestamp from);
    
    public void addChat(Chat chat);
    
    public Account getAccountByToken(String token);
    
    public void notifyNewChat();
    
}
