/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    private String key;
    private String message;

    public Message(){}
    
    public static Message of(String key, String message) {
        Message m = new Message();
        m.key = key;
        m.message = message;
        return m;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
