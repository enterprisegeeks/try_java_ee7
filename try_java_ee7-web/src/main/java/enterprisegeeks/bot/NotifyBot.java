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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
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
    
    private static final Trigger hourTrigger = new Trigger() {

        /**
         * 次回実行時の時刻を決める
         * @param le 前回実行時の情報(初回はnull)
         * @param date ユーティリティが定めた予定実行時刻
         * @return 次回の実行時刻
         */
        @Override
        public Date getNextRunTime(LastExecution le, Date date) {
            if (le == null) {
                // 初回実行時
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.HOUR, 1);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.MILLISECOND, 0);
                System.out.println("first execution at " + cal.getTime());
                return cal.getTime();
            }
            // 前回実行時の1時間後
            Date next =  new Date(le.getScheduledStart().getTime() + 3600 * 1000);
            System.out.println("next execution at " + next); 
            return next;
        }

        @Override
        public boolean skipRun(LastExecution le, Date date) {
            return false;
        }
    };
    
    
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
        
        scheduler.schedule(command, hourTrigger);
    }
    
}
