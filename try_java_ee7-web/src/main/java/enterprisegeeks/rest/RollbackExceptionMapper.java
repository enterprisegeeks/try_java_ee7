package enterprisegeeks.rest;

import javax.transaction.RollbackException;
import javax.transaction.TransactionalException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;

/**
 * Transactionalアノテーション中で発生した例外はRollbackExceptionに包まれるため、
 * 実際の例外クラスの例外マッパーを取得する。
 */
@Provider
public class RollbackExceptionMapper implements ExceptionMapper<TransactionalException> {

    @Context
    private Providers p;

    @Override
    public Response toResponse(TransactionalException exception) {
        Throwable cause = exception.getCause();
        Class<Throwable> type = (Class<Throwable>) cause.getClass();
        System.out.println("rbexecpt:" + type.getName());
        return p.getExceptionMapper(type).toResponse(cause);
    }
}