/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;


import enterprisegeeks.rest.dto.Messages;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * Bean Validationの例外に対するマッピング。
 * 
 * エラーメッセージをレスポンスに設定する。
 */
@Provider // 特定の例外に対して、処理を行う。Providerアノテーションで自動判別される。
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        
        Messages m = new Messages();
        exception.getConstraintViolations().stream()
                .forEach(v -> m.addMessage(last(v.getPropertyPath().toString()), v.getMessage()));
            
        return Response.status(400).entity(m).type(MediaType.APPLICATION_JSON).build();
    }
    
    private String last(String str) {
        String[] arr = str.split("[.]");
        return arr[arr.length-1];
    }
}

