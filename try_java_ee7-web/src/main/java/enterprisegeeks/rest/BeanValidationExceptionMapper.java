/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest;


import enterprisegeeks.rest.dto.Message;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.JsonStructure;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
/**
 * Bean Validationの例外に対するマッピング。
 * 
 * エラーメッセージをレスポンスに設定する。
 */
@Provider // 特定の例外に対して、処理を行う。Providerアノテーションで自動判別される。
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException exception) {
        
        // メソッドの戻り値が List<SomeObject> の場合は、List形式のデータを返しても動作する。
        // が、Responseを返す場合は、entity()にlistを渡すと、ジェネリクスが解消できないので、
        // 正常に動かない。
        
        // 方法1. List<SomeObject>型のフィールドを持つクラスを別途作り、そのクラスで返す。
        
        // 方法2. 配列で返す。
        //Message[] list = 
        //    exception.getConstraintViolations().stream()
        //        .map(v -> Message.of(last(v.getPropertyPath().toString()), v.getMessage()))
        //        .toArray(Message[]::new);
        
        // 方法3 genericEntity
        List<Message> list = 
            exception.getConstraintViolations().stream()
                .map(v -> Message.of(last(v.getPropertyPath().toString()), v.getMessage()))
                .collect(Collectors.toList());
        // ジェネリック型を指定した無名クラスを作る。
        // Genericsの仕様として型パラメータを指定して継承したクラスは、
        // 型パラメータを取得できるため、このようなテクニックを使っていると推測。
        GenericEntity<List<Message>> typedList = new GenericEntity<List<Message>>(list){};
        
        return Response.status(400).type(MediaType.APPLICATION_JSON)
                .entity(typedList)
                .build();
    }
    
    private String last(String str) {
        String[] arr = str.split("[.]");
        return arr[arr.length-1];
    }
}

