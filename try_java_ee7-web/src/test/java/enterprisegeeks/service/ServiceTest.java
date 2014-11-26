/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import enterprisegeeks.entity.Room;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import util.PrivateUtil;

/**
 * Serviceのテスト
 * EntityManagerへの依存性を自力で解消する方法
 */
public class ServiceTest {
    
    @BeforeClass
    public static void setUpClass() {

    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private Service target;
    private EntityManager em;
    private EntityTransaction tx;
    
    @Before
    public void setUp() throws Exception{
        em = Persistence.createEntityManagerFactory("stand_alone").createEntityManager();
        target = new Service();
        PrivateUtil.setField(target, "em", em);
        tx = em.getTransaction();
        tx.begin();
    }
    
    @After
    public void tearDown() {
        tx.rollback();
        em.close();
    }
    
    @Test
    public void testRegisterAccount() throws Exception{     
        // prepare
        Room room = new Room();
        room.setId(1);
        room.setName("test");
        Room room2 = new Room();
        room2.setId(2);
        room2.setName("test2");
        em.persist(room);
        em.persist(room2);
        
        em.flush();
        
        // TEST
        List<Room> act = target.allRooms();
        
        //assert
        assertThat(act.size(), is(2));
        assertThat(act.get(1).getName(), is("test2"));
        
        
      
    }
    
}
