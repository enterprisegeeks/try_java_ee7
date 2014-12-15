/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 *
 */
@javax.ws.rs.ApplicationPath("rs")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        this.packages("enterprisegeeks.rest","enterprisegeeks.rest.resource");
        this.register(JspMvcFeature.class);
    }
}
