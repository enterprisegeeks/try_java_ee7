/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.lang.reflect.Field;

/**
 *
 */
public class PrivateUtil {
    public static void setField(Object instance, String name, Object obj) {
        
        try {
            Field f = instance.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(instance, obj);
        } catch (NoSuchFieldException | IllegalArgumentException  | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
