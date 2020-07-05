package br.com.humbertofernandes.stoom.api.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * @author Humberto Tadeu de Paiva Gomes Fernandes
 */
@Order
@RestControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralExceptionHandler.class);
    private static final String ERROR_NOT_EXPECTED = "Error not expected";

    private final ApiExceptionHandler apiExceptionHandler;

    @Autowired
    public GeneralExceptionHandler(ApiExceptionHandler apiExceptionHandler) {
        this.apiExceptionHandler = apiExceptionHandler;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerInternalServerError(Exception exception, Locale locale) {
        LOG.error(ERROR_NOT_EXPECTED, exception);
        final String errorCode = "error-1";
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = ErrorResponse.of(status, apiExceptionHandler.toApiError(errorCode, locale));

        return ResponseEntity.status(status).body(errorResponse);
    }
}
