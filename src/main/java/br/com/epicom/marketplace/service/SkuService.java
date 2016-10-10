package br.com.epicom.marketplace.service;

import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.epicom.marketplace.model.Imagem;
import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.repository.SkuRepository;
import br.com.epicom.marketplace.util.Mensagens;
import br.com.epicom.marketplace.util.exception.BadRequestException;
import br.com.epicom.marketplace.util.exception.RecursoJaExistenteException;
import br.com.epicom.marketplace.util.exception.RecursoNaoExistenteException;
import br.com.epicom.marketplace.util.response.MessageResponse;

@Service
@Scope("singleton")
public class SkuService {

	@Autowired
	private SkuRepository skuRepository;
	
	public MessageResponse cadastrar(@Valid Sku sku) throws Exception{
		
		executarValidacoesCadastro(sku);
		skuRepository.save(sku);
		
		sku = skuRepository.findOne(sku.getId());
		
		return new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
	}
	
	public MessageResponse remover(Long id) throws Exception {
		
		Sku sku = skuRepository.findOne(id);
		
		if (sku == null || !sku.isAtivo()) {
			throw new RecursoNaoExistenteException();
		}
		
		sku.setAtivo(false);
		skuRepository.save(sku);
 		
		return new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
	}
	
	private void executarValidacoesCadastro(Sku sku) throws Exception {

		if (skuRepository.exists(sku.getId())) {
			throw new RecursoJaExistenteException();
		}
		
		validarImagens(sku);
 	}
		
	private void validarImagens(Sku sku) throws BadRequestException {
		
		if (sku.getImagens() != null && !sku.getImagens().isEmpty()) {
			Set<Integer> ordens = new TreeSet<>();
			
			for (Imagem img : sku.getImagens()) {
				if (ordens.remove(img.getOrdem())) {
					throw new BadRequestException();
				}
				ordens.add(img.getOrdem());
			}
		}
	}
	
	public SkuRepository getSkuRepository() {
		return skuRepository;
	}

	public void setSkuRepository(SkuRepository skuRepository) {
		this.skuRepository = skuRepository;
	}
}
