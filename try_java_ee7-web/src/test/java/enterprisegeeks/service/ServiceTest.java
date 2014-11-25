/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.service;

import enterprisegeeks.entity.Account;
import java.io.File;
import java.util.Properties;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import util.PrivateUtil;

/**
 * Serviceのテスト
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
        
        target.registerAccount(new Account());
      
    }
    
}
