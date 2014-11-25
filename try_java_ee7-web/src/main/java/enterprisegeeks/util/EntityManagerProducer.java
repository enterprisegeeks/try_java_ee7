/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * CDIの機能により、PersisetenceContextを@Injectで注入可能にする。
 */
@Named
@Dependent
public class EntityManagerProducer {
    @PersistenceContext(unitName = "default")
    @Produces
    private EntityManager em;
}

