package br.com.epicom.marketplace.util.exception;

import org.springframework.http.HttpStatus;

import br.com.epicom.marketplace.util.Mensagens;

public class RecursoNaoExistenteException extends HttpException {

	private static final long serialVersionUID = 5751408897526464645L;

	public RecursoNaoExistenteException() {
		super(Mensagens.HTTP_STATUS_404, HttpStatus.NOT_FOUND.value());
	}
}
