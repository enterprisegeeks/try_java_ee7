/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.resource;

import enterprisegeeks.entity.Account;
import enterprisegeeks.rest.WithAuth;
import enterprisegeeks.service.Service;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    
    @GET
    @WithAuth
    public Viewable init(Account authedAccount) {
        System.out.println("cr-res");
        return new Viewable("/WEB-INF/jsp/chatroom.jsp", authedAccount);
    }
}
