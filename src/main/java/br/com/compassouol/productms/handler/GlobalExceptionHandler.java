package br.com.compassouol.productms.handler;

import br.com.compassouol.productms.exception.GenericException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERRO_INTERNO_NO_SERVIDOR = "Erro interno no servidor.";

    @ExceptionHandler(value = {GenericException.class})
    protected ResponseEntity<Object> handlerGenericException(GenericException e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR;
        String message = getMessage(responseStatus);
        return ResponseEntity.status(status).body(new ErroMessage(status.value(), message));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        return ResponseEntity.status(500).body(new ErroMessage(400, validationList.toString() ));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handlerException(Exception e) {
        return ResponseEntity.status(500).body(new ErroMessage(500, ERRO_INTERNO_NO_SERVIDOR));
    }

    private String getMessage(ResponseStatus responseStatus) {
        return responseStatus != null && StringUtils.hasText(responseStatus.reason()) ? responseStatus.reason() : ERRO_INTERNO_NO_SERVIDOR;
    }
}
