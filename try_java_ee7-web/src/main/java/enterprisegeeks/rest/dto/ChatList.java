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
 */
public class ChatList {
    public long last;
    
    public List<Chat> chats = new ArrayList<>();
    
    public void addChat(String name, String gravatar, String content) {
        Chat c = new Chat();
        c.name = name;
        c.gravatar = gravatar;
        c.content = content;
        chats.add(c);
    }
    
    public static class Chat{
        public String name;
        public String gravatar;
        public String content;
        
    }
}
