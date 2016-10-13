package br.com.epicom.marketplace.controller;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.service.SkuService;
import br.com.epicom.marketplace.util.Mensagens;
import br.com.epicom.marketplace.util.exception.BadRequestException;
import br.com.epicom.marketplace.util.exception.RecursoJaExistenteException;
import br.com.epicom.marketplace.util.exception.RecursoNaoExistenteException;
import br.com.epicom.marketplace.util.response.MessageResponse;

/**
 * Classe responsável por gerenciar requisições REST de SKUs
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@RestController
public class SkuRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SkuService skuService;
	
	/**
	 * Método utilizado para cadastrar um novo SKU
	 * 
	 * @param sku O SKU em no formato json
	 * @return Um json informando que a operação foi executada com sucesso
	 * 
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value="/marketplace/sku/cadastrar")
	public MessageResponse cadastrarSKU(@RequestBody Sku sku) throws Exception {
		logger.info("Serviço iniciado: POST /marketplace/sku/cadastrar");
		logger.info("RequestBody: " + sku);
		return skuService.cadastrar(sku);
	}
	
	/**
	 * Método utilizado para remover um SKU cadastrado.
	 * 
	 * @param id O id do SKU
	 * @return Um json informando que a operação foi executado com sucesso
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.DELETE, value="/marketplace/sku/remover/{id}")
	public MessageResponse removerSKU(@PathVariable(value = "id") Long id) throws Exception {
		logger.info("Serviço iniciado: DELETE /marketplace/sku/remover/{id}");
		logger.info("Parametro: " + id);
		return skuService.remover(id);
	}
	
	
	/**
	 * Método utilizado para consultar um SKU
	 *  
	 * @param id O id do SKU
	 * @return O SKU no formato json
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/marketplace/sku/{id}")
	public Object listarSKU(@PathVariable(value = "id") Long id) throws Exception {
		logger.info("Serviço iniciado: GET /marketplace/sku/{id}");
		logger.info("Parametro: " + id);
		return skuService.obter(id);
	}
	
	
	/**
	 * Método utilizado para listar os SKUs ativos
	 * 
	 * @return Uma lista de SKU no formato json
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/marketplace/sku/")
	public Object listarSKU() throws Exception {
		logger.info("Serviço iniciado: GET /marketplace/sku/");
		return skuService.listar();
	}
	
	
	/**
	 * Método utilizado para retornar todos os SKUs ativos, disponíveis e com preço entre 10.00 e 40.00
	 * 
	 * @return Uma lista de SKU no formato json
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value="/marketplace/sku/disponiveis")
	public Object listarSKUDisponiveis() throws Exception {
		logger.info("Serviço iniciado: GET /marketplace/sku/disponiveis");
		return skuService.listarDisponiveis();
	}
	
	
	/**
	 * Método utilizado para atualizar um SKU
	 * 
	 * @param sku no formato json
	 * @return Um json informando que a operação foi executado com sucesso
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.PUT, value="/marketplace/sku/atualizar")
	public Object atualizarSKU(@RequestBody Sku sku) throws Exception {
		logger.info("Serviço iniciado: PUT /marketplace/sku/atualizar");
		logger.info("RequestBody: " + sku);
		return skuService.atualizar(sku);
	}
	
	/**
	 * Método utilizado para tratar exceções quando a aplicação não conseguir converter o json informado em Objeto
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
	public MessageResponse handlerConstraintViolation(ConstraintViolationException ex) {
		logger.error(ex.getMessage(), ex);
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
	
	/**
	 * Método utilizado para tratar exceções quando o json informado não estiver no formato correto
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
	public MessageResponse handlerBadRequest(BadRequestException ex) {
		logger.error(ex.getMessage(), ex);
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
	
	/**
	 * Método utilizado para tratar exceções quando o json informado não estiver no formato correto
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
	
	/**
	 * Método utilizado para tratar exceções ao tentar inserir um SKU já existente
	 * 
	 * @param ex
	 * @return Um json no seguinte formato: 
	 * {
	 * 		"code" : 409
	 * 		"message" : "O recurso que está sendo criado já existe."
	 * } 
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public MessageResponse handlerRecursoJaExistente(RecursoJaExistenteException ex) {
		logger.error(ex.getMessage(), ex);
		return new MessageResponse(ex.getStatus(), ex.getMessage());
	}
	
	/**
	 * Método utilizado para tratar exceções ao solcitar ou tentar alterar um SKU não cadastrado
	 * 
	 * @param ex
	 * @return Um json no seguinte formato: 
	 * {
	 * 		"code" : 404
	 * 		"message" : "A URL solicitada ou o recurso não existe."
	 * } 
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public MessageResponse handlerRecursoJaExistente(RecursoNaoExistenteException ex) {
		logger.error(ex.getMessage(), ex);
		return new MessageResponse(ex.getStatus(), ex.getMessage());
	}
	
}
