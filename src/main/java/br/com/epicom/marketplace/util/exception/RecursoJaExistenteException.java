package br.com.epicom.marketplace.util.exception;

import org.springframework.http.HttpStatus;

import br.com.epicom.marketplace.util.Mensagens;

/**
 * Excecao que indica a tentativa de inserir um registro que ja existe
 * 
 * @author Paulo Cesar Abrantes
 *
 */
public class RecursoJaExistenteException extends HttpException {

	private static final long serialVersionUID = 8695393751475428015L;

	public RecursoJaExistenteException() {
		super(Mensagens.HTTP_STATUS_409, HttpStatus.CONFLICT.value());
	}
}
