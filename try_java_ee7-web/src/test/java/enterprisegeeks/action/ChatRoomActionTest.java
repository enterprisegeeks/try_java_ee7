/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import enterprisegeeks.entity.Account;
import enterprisegeeks.entity.Room;
import enterprisegeeks.model.Auth;
import enterprisegeeks.model.ChatRoom;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.InSessionScope;
import org.junit.After;
import org.junit.runner.RunWith;

/**
 * ChatRoomActionのテスト
 * CDIRunnerを使用し、依存するbeanを全て解消する。
 */
@RunWith(CdiRunner.class)
public class ChatRoomActionTest {
    /** テスト用のEntityManegaerのファクトリメソッド。
     * @Inject em に自動でインジェクションされる。
     */
    @Produces
    @Singleton //テストクラスやbean内で複数インジェクションされるため、シングルトンとする。
    public static EntityManager createEntityManager() {
        return Persistence.createEntityManagerFactory("stand_alone").createEntityManager();
    }
    
    // CDIRunner上では、テスト対象クラスそのものがインジェクション可能。
    @Inject
    private ChatRoomAction target;
    
    // Actionが内部でインジェクションしているbeanをインジェクションし、データ設定を行う。
    @Inject
    private Auth auth;
    
    @Inject
    private ChatRoom chatroom;
    
    @Inject
    private EntityManager em;
    
    @Before
    @InRequestScope // テストメソッドと同じスコープを指定する必要がある。
    public void setUp() {
        em.getTransaction().begin();
        Account acc = new Account();
        acc.setName("test");
        acc.setEmail("test@test");
        em.persist(acc);
        Room room = new Room();
        room.setName("room");
        em.persist(room);
        em.flush();
        em.detach(acc);
        em.detach(room);
        
        auth.setAccount(acc);
        auth.setLoggedIn(true);
        auth.setSelected(room);
        System.out.println(room.getId());
    }
    @After
    public void tearDown(){
        em.getTransaction().rollback();
    }
    
    @Test
    @InRequestScope // スコープをあわせる
    public void testChat() throws Exception{
        //setUp();
        chatroom.setContent("chat");
        
        target.chat();
        
        assertThat(chatroom.getChats().size(), is(1));
        assertThat(chatroom.getChats().get(0).getContent(), is("chat"));
        assertThat(chatroom.getLastPost(), notNullValue());
      
    }
    
}
