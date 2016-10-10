package br.com.epicom.marketplace.controller;

import javax.validation.ConstraintViolationException;

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

@RestController
public class SkuRestController {

	@Autowired
	private SkuService skuService;
	
	@RequestMapping(method = RequestMethod.POST, value="/marketplace/sku/cadastrar")
	public MessageResponse cadastrarSKU(@RequestBody Sku sku) throws Exception {
		return skuService.cadastrar(sku);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/marketplace/sku/remover/{id}")
	public MessageResponse removerSKU(@PathVariable(value = "id") Long id) throws Exception {
		return skuService.remover(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/marketplace/sku/{id}")
	public Object listarSKU(@PathVariable(value = "id") Long id) throws Exception {
		return skuService.listar(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/marketplace/sku/")
	public Object listarSKU() throws Exception {
		return skuService.listar(null);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handlerConstraintViolation(ConstraintViolationException ex) {
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handlerBadRequest(BadRequestException ex) {
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MessageResponse handlerFormatoJsonInvalido(HttpMessageNotReadableException ex) {
		return new MessageResponse(HttpStatus.BAD_REQUEST.value(), Mensagens.HTTP_STATUS_400);
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.CONFLICT)
	public MessageResponse handlerRecursoJaExistente(RecursoJaExistenteException ex) {
		return new MessageResponse(ex.getStatus(), ex.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public MessageResponse handlerRecursoJaExistente(RecursoNaoExistenteException ex) {
		return new MessageResponse(ex.getStatus(), ex.getMessage());
	}
	
}
