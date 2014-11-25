/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import enterprisegeeks.entity.Account;
import enterprisegeeks.entity.Room;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * 永続層周りの機能を提供
 */
@Dependent
public class Service {
    
    @Inject
    private EntityManager em;
    
    public boolean registerAccount(Account accout) {
        
        try {
            Account exists = em.createNamedQuery("Account.nameOrEmali", Account.class)
                .setParameter("name", accout.getName())
                .setParameter("email", accout.getEmail()).getSingleResult();
            
            // 取得できた場合、名前とメールが一致しない場合はエラーとする。
            if (!exists.getName().equals(accout.getName()) || !exists.getEmail().equals(accout.getEmail())) {
                return false;
            }
            
        } catch(NoResultException e) {
            // 既存アカウントが無い場合登録
            em.persist(accout);
        }
        return true;
        
    }
    
    public List<Room> allRooms() {
        return em.createNamedQuery("Room.all", Room.class).getResultList();
    }
    
    
    
}
