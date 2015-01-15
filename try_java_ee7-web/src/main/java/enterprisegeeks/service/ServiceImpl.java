/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import enterprisegeeks.entity.Account;
import enterprisegeeks.entity.Chat;
import enterprisegeeks.entity.Room;
import enterprisegeeks.util.Gravater;
import enterprisegeeks.websocket.ChatNotifyEndPoint;
import enterprisegeeks.websocket.WsClient;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.WebApplicationException;

/**
 * 永続層周りの機能を提供
 */
@RequestScoped // IFと実装クラスを分ける場合、実装クラス側にCDIアノテーション付与。
public class ServiceImpl implements Serializable,Service{
    
    @Inject
    private EntityManager em;

    @Override
    public Account findAccountByName(String name) {
        Account acc = em.find(Account.class, name);
        em.detach(acc);
        return acc;
    }
    
    @Override
    public boolean registerAccount(Account accout) {
        List<Account> exists = em.createNamedQuery("Account.nameOrEmali", Account.class)
                .setParameter("name", accout.getName())
                .setParameter("email", accout.getEmail()).getResultList();
            
        if (exists.isEmpty()) {
            // 既存アカウントが無い場合登録
            accout.setGravaterHash(Gravater.md5Hex(accout.getEmail()));
            em.persist(accout);
            return true;
        }else {
            // 取得できた場合、名前とメールが一致しない場合はエラーとする。
            return exists.stream().anyMatch(a ->
                    a.getEmail().equals(accout.getEmail()) && a.getName().equals(accout.getName()));
        }
    }

    @Override
    public String publishToken(Account account) {
        Account entity = em.find(Account.class, account.getName());
        String token = UUID.randomUUID().toString();
        entity.setToken(token);
        return token;
    }
    
    
    
    /** チャットルーム一覧 */
    @Override
    public List<Room> allRooms() {
        List<Room> roomList = em.createNamedQuery("Room.all", Room.class).getResultList();
        em.clear();
        return roomList;
    }
    
    /** チャットルームのチャット一覧を取得 */
    @Override
    public List<Chat> findChatByRoom(Room room, Timestamp from) {
        
        // 練習がてらcriteriaAPI を試す。
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Chat> cq = cb.createQuery(Chat.class);
        Root<Chat> chat = cq.from(Chat.class);
        if (from != null) {
            
            cq.where(cb.and(cb.equal(chat.get("room"), room), cb.greaterThan(chat.get("posted"), from)));
        } else {
            cq.where(cb.equal(chat.get("room"), room));
        }
        cq.orderBy(cb.asc(chat.get("posted")));
        
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void addChat(Chat chat) {
        
        em.persist(chat);
        em.flush();
        
    }

    
    @Override
    public Account getAccountByToken(String token) {
        Account ac = em.createNamedQuery("Account.byToken", Account.class)
                   .setParameter("token", token)
                   .getSingleResult();
        em.detach(ac);
        return ac;
    }
    
    
}
