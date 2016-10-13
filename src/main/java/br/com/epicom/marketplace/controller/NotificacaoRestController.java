package br.com.epicom.marketplace.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Classe responsável por gerenciar requisições REST de notificações
 * 
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@RestController
public class NotificacaoRestController {

	@Autowired
	NotificacaoService notificacaoService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Serviço utilizado para rececber uma notificação de cadastro de SKU
	 * 
	 * @param jsonObject no seguinte Formato:
	 * 
	 * {
  	 *		"tipo": "criacao_sku",
  	 *		"dataEnvio": "<data>",
  	 *		"parametros": {
     *			"idProduto": <idProduto>,
     *			"idSku": <idSku>
  	 *		}
	 * }
	 * 
	 * @return Um json indicando que a operação foi concluída com sucesso
	 * 
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/marketplace/notificacao")
	public MessageResponse notificar(@RequestBody NotificacaoJson jsonObject) throws Exception {
		logger.info("Serviço iniciado: POST /marketplace/notificacao");
		logger.info("RequestBody: " + jsonObject);
		return notificacaoService.receberNotificacao(jsonObject);
	}
	
	
	/**
	 * Método utilizado para capturar uma exceção de formato inválido do json enviado para o método notificar.
	 * 
	 * @param ex
	 * @return Um json no seguinte formato: 
	 * {
	 * 		"code" : 400
	 * 		"message" : "Requisição inválida ou não pôde ser entregue."
	 * } 
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handlerFormatoJsonInvalido(HttpMessageNotReadableException ex) {
		logger.error(ex.getMessage(), ex);
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
}
