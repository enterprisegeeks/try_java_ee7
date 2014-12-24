/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.util;

import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

/**
 *
 * @author kentaro.maeda
 */
@Dependent
public class CoucurrencyProducer {
    @Resource(lookup="concurrent/__defaultManagedExecutorService")
    @Produces
    private ExecutorService myExecutor;
}
