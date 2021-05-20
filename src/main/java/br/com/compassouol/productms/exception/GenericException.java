package br.com.compassouol.productms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Erro interno no servidor.")
public class GenericException extends RuntimeException{
}
