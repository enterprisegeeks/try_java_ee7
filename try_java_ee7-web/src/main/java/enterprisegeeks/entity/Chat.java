/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 発言エンティティ
 */
@Entity
public class Chat implements Serializable {
    
    /** id(自動生成) */
    @Id
    @GeneratedValue // ID自動生成 
    private long id;
    
    /** チャットを行ったチャットルーム */
    @ManyToOne // 多対1関連
    private Room room;
    
    /** 発言者 */
    @ManyToOne
    private Account speaker;
    
    /** 内容 */
    @Column(length = 200)
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
