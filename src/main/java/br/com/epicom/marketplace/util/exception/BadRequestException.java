package br.com.epicom.marketplace.util.exception;

import org.springframework.http.HttpStatus;

import br.com.epicom.marketplace.util.Mensagens;

public class BadRequestException extends HttpException {

	private static final long serialVersionUID = 3419526807697175988L;

	public BadRequestException() {
		super(Mensagens.HTTP_STATUS_400, HttpStatus.BAD_REQUEST.value());
	}
}
