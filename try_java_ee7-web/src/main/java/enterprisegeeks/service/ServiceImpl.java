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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * 永続層周りの機能を提供
 */
@Dependent // IFと実装クラスを分ける場合、実装クラス側にCDIアノテーション付与。
public class ServiceImpl implements Serializable,Service{
    
    @Inject
    private EntityManager em;
    
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
        System.out.println("hit" + entity.getName());
        String token = UUID.randomUUID().toString();
        entity.setToken(token);
        return token;
    }
    
    
    
    /** チャットルーム一覧 */
    @Override
    public List<Room> allRooms() {
        return em.createNamedQuery("Room.all", Room.class).getResultList();
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
    public void addChat(Chat chat) {
        em.persist(chat);
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
