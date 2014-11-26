/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.util;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * ヴァリデーションエラー時に適用するセレクタを取得する。
 */
@RequestScoped
@Named
public class ErrorStyle implements Serializable {
    
    public String get(String componentId) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIInput input = (UIInput) context.getViewRoot().findComponent(componentId);
        return input.isValid() ? "" : "has-error";
    
    }
}
