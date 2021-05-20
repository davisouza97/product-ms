package br.com.compassouol.productms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Produto não encontrado")
public class ProdutoNaoEncontradoException extends GenericException {
}
