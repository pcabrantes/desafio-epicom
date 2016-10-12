package br.com.epicom.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.epicom.marketplace.service.NotificacaoService;
import br.com.epicom.marketplace.util.Mensagens;
import br.com.epicom.marketplace.util.request.NotificacaoJson;
import br.com.epicom.marketplace.util.response.MessageResponse;

@RestController
public class NotificacaoRestController {

	@Autowired
	NotificacaoService notificacaoService;
	
	@RequestMapping(method = RequestMethod.POST, value="/marketplace/notificacao")
	public MessageResponse cadastrarSKU(@RequestBody NotificacaoJson jsonObject) throws Exception {
		return notificacaoService.receberNotificacao(jsonObject);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handlerFormatoJsonInvalido(HttpMessageNotReadableException ex) {
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
}
