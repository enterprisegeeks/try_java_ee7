/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.model;

import enterprisegeeks.entity.Chat;
import enterprisegeeks.entity.Room;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ChatRoom viewで使用するデータ構造
 */
@Dependent
public class ChatRoom implements Serializable{
    
    /** チャットルーム一覧 */
    private List<Room> rooms = new ArrayList<>();
    
    /** 現在選択されているチャットルームのチャット一覧 */
    private List<Chat> chats = new ArrayList<>();
    
    /** チャットを取得した最終時刻 */
    private Timestamp lastPost;
    
    @NotNull(message = "{required}")
    @Size(message = "{required}", min = 1)
    private String content;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getLastPost() {
        return lastPost;
    }

    public void setLastPost(Timestamp lastPost) {
        this.lastPost = lastPost;
    }
    
    
}
