/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.resource;

import enterprisegeeks.entity.Chat;
import enterprisegeeks.entity.Room;
import enterprisegeeks.rest.anotation.WithAuth;
import enterprisegeeks.rest.dto.Authed;
import enterprisegeeks.rest.dto.ChatList;
import enterprisegeeks.rest.dto.ChatRooms;
import enterprisegeeks.rest.dto.NewChat;
import enterprisegeeks.service.Service;
import java.sql.Timestamp;
import java.util.List;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.mvc.Viewable;

/**
 * REST Web Service
 */
@Path("chatroom")
@RequestScoped
@Transactional
public class ChatroomResource {
    
    @Inject
    private Service service;
    
    @Inject 
    private Authed authed;
    
    @GET
    @WithAuth
    public Viewable init() {
        return new Viewable("/WEB-INF/jsp/chatroom.jsp", authed.getAccount());
    }
    
    @GET
    @Path("rooms")
    @WithAuth
    @Produces(MediaType.APPLICATION_JSON)
    public ChatRooms getRooms() {
        ChatRooms rooms = new ChatRooms();
        rooms.rooms = service.allRooms();
        return rooms;
    }
    
    /** 指定時刻以降のチャット一覧の取得。 */
    @GET
    @Path("chat")
    @WithAuth
    @Produces(MediaType.APPLICATION_JSON)
    public ChatList getChat(@QueryParam("roomId")long roomId, @QueryParam("from")long from) {
        Room r = new Room();
        r.setId(roomId);
        
        Timestamp tsFrom = new Timestamp(from);
        List<Chat> chatList = service.findChatByRoom(r, tsFrom);
        
        ChatList res = new ChatList();
        for (Chat c : chatList) {
            res.addChat(c.getSpeaker().getName(), c.getSpeaker().getGravatar(), c.getContent());
        }
        if (!chatList.isEmpty()) {
            res.last = chatList.get(chatList.size() - 1).getPosted().getTime();
        }
        
        return res;
    }
    
    /** 新規チャットの登録 */
    @PUT
    @Path("chat")
    @WithAuth
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addChat(@Valid NewChat newChat) {
        // 関連するエンティティを組み立てる。
        // ステートフルのJSFがベースなので、JAX-RSだと若干面倒。
        // JAX-RSにあわせたサービスの設計を行ったほうがよいかも。
        Chat chat = new Chat();
        chat.setContent(newChat.content);
        Room room = new Room();
        room.setId(newChat.roomId);
        chat.setRoom(room);
        chat.setPosted(new Timestamp(System.currentTimeMillis()));
        chat.setSpeaker(authed.getAccount());
        
        service.addChat(chat);
        
        service.notifyNewChat();
        return Response.noContent().build();
        
    }
    
}
