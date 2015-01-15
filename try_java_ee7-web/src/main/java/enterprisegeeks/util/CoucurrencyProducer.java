/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.util;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;


/**
 * 
 * ConcurrencyUtilのリソースのProducer
 * 
 * ManagedExecutorServiceとManagedScheduledExecutorServiceの2つを
 * CDI対象とする場合、両者ともManagedExecutorServiceを実装しているため、
 * Qualifierによる指定が必要となる。
 */
@Dependent
public class CoucurrencyProducer {
   
    @Resource(lookup = "concurrent/__defaultManagedScheduledExecutorService")
    @ScheduledExecutor
    @Produces
    private ManagedScheduledExecutorService scheduler;
    
    @Resource(lookup = "concurrent/__defaultManagedExecutorService")
    @Executor
    @Produces
    private ManagedExecutorService executor;
}
