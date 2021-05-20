package br.com.compassouol.productms.handler;

import br.com.compassouol.productms.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GenericException.class})
    protected ResponseEntity<Object> handlergenericException(GenericException e){
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = getMessage(responseStatus);
        return ResponseEntity.status(status).body(new ErroMessage(status.value(), message));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handlerException(Exception e){
        return ResponseEntity.status(500).body(new ErroMessage(500, "Erro unterno no servidor."));
    }

    private String getMessage(ResponseStatus responseStatus) {
        return responseStatus != null && StringUtils.hasText(responseStatus.reason()) ? responseStatus.reason() : "Erro interno no servidor.";
    }
}
