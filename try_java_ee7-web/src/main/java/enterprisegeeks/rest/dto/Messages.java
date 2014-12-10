/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kentaro.maeda
 */
public class Messages {
    private List<Message> messages = new ArrayList<>();

    public void addMessage(String key, String msg) {
        Message m = new Message();
        m.setKey(key);
        m.setMessage(msg);
        messages.add(m);
    }
    
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    public static class Message  {
        private String key;
        private static String message;

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
}
