package apps.proman.api.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import apps.proman.api.controller.ext.ErrorResponse;
import apps.proman.service.common.exception.ApplicationException;
import apps.proman.service.common.exception.AuthenticationFailedException;
import apps.proman.service.common.exception.AuthorizationFailedException;
import apps.proman.service.common.exception.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationFailedException.class)
    public final ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity(errorResponse(ex), HttpStatus.NOT_FOUND);
    }

    private ErrorResponse errorResponse(final ApplicationException appExc) {
        return new ErrorResponse(appExc.getErrorCode().getCode(), appExc.getMessage());
    }

}