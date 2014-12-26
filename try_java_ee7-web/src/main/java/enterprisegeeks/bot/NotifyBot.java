/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.bot;

import enterprisegeeks.rest.dto.NewChat;
import enterprisegeeks.service.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;

/*
 * チャット用Bot EJB番
 */
@Singleton
@Startup
public class NotifyBot {
    
    @Inject
    private ManagedScheduledExecutorService scheduler;
    
    @Inject
    private Service service;
    
    
    @PostConstruct
    public void chatEveryHour() {
        
        
        String token = service.findAccountByName("manager").getToken();
        List<Long> roomIds = service.allRooms().stream().map(r -> r.getId())
                .collect(Collectors.toList());
        
        Runnable command = () -> {
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            roomIds.forEach(roomId -> {
                NewChat nc = new NewChat();
                nc.roomId = roomId;
                nc.content = "今は" + now + "です。";
                Client c = ClientBuilder.newClient().register(NewChat.class);
                c.target("http://localhost:8080/try_java_ee7-web/rs/chatroom/chat")
                        .request()
                        .header("authorization", token)
                        .put(Entity.entity(nc, MediaType.APPLICATION_JSON_TYPE.withCharset("UTF-8")));
            });
        };
        
        scheduler.scheduleAtFixedRate(command,
            2L, 30L, TimeUnit.SECONDS);
    }
    
}
