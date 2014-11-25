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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * チャットルーム
 */
@Entity
@NamedQueries(@NamedQuery(name = "Room.all", query = "select r from Room r order by r.id"))
public class Room implements Serializable {
    
    /** id(自動生成) */
    @Id
    @GeneratedValue
    private long id;
    
    /** 部屋名 */
    @Column(nullable = false, length = 40, unique = true)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
