/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.action;

import enterprisegeeks.entity.Account;
import enterprisegeeks.model.Auth;
import enterprisegeeks.service.Service;
import enterprisegeeks.util.MessageUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import util.PrivateUtil;

/**
 *　AuthActionのテスト
 *  mockおよびリフレクションによる方法
 */
public class AuthActionTest {
    
    @Mock(name="message")
    private MessageUtil message;
    
    @Mock(name="service")
    private Service service;
    
    @InjectMocks
    private AuthAction target = new AuthAction();
    
    @Before
    public void setUp() {
        
        // @InjectMocksの付いているオブジェクトに、@Mockのmockを注入する。
        MockitoAnnotations.initMocks(this);
        
        ArgumentMatcher<Account> successAccount = new ArgumentMatcher<Account>() {
            public boolean matches(Object a) {
                if(a == null) return false;
               return ((Account)a).getName().equals("test");
            }
        };
        ArgumentMatcher<Account> errorAccount = new ArgumentMatcher<Account>() {
            public boolean matches(Object a) {
                if(a == null) return false;
               return ((Account)a).getName().equals("error");
            }
        };
        
        when(service.registerAccount(argThat(successAccount))).thenReturn(true);
        when(service.registerAccount(argThat(errorAccount))).thenReturn(false);
    }
 
    @Test
    public void testEnterOnSuccess() {
        // リフレクション経由で、Action内部のフィールドを初期化
        Auth auth = new Auth();
        Account acc = new Account();
        acc.setName("test");
        acc.setEmail("test@test");
        auth.setAccount(acc);
        PrivateUtil.setField(target, "auth", auth);
        
        String act = target.enter();
        
        assertThat(act, is("chatroom.xhtml?faces-redirect=true"));
        assertThat(auth.isLoggedIn(), is(true));
    }
    
    @Test
    public void testEnterOnError() {
        // リフレクション経由で、Action内部のフィールドを初期化
        Auth auth = new Auth();
        Account acc = new Account();
        acc.setName("error");
        acc.setEmail("test@test");
        auth.setAccount(acc);
        PrivateUtil.setField(target, "auth", auth);

        String act = target.enter();
        
        assertThat(act, nullValue());
        assertThat(auth.isLoggedIn(), is(false));
    }

    
}
