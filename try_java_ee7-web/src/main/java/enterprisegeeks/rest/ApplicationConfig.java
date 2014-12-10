/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;

import java.util.Set;
import javax.transaction.RollbackException;
import javax.ws.rs.core.Application;

/**
 *
 */
@javax.ws.rs.ApplicationPath("rs")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(enterprisegeeks.rest.BeanValidationExceptionMapper.class);
        resources.add(enterprisegeeks.rest.RollbackExceptionMapper.class);
        resources.add(enterprisegeeks.rest.resource.Join.class);
    }
    
}
