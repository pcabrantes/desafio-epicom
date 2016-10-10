package br.com.epicom.marketplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.epicom.marketplace.model.AtributoGrupo;
import br.com.epicom.marketplace.model.Grupo;
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
	
	@Transactional
	public MessageResponse cadastrar(@Valid Sku sku) throws Exception{
		
		executarValidacoesCadastro(sku);
		prepararInsersao(sku);
		skuRepository.save(sku);
		
		
		return new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
	}

	private void prepararInsersao(Sku sku) {
		sku.getDimensoes().setSku(sku);
		
		if (sku.getImagens() != null && !sku.getImagens().isEmpty()) {
			for (Imagem img : sku.getImagens()) {
				img.setSku(sku);
			}
		}
		
		if (sku.getGrupos() != null && !sku.getGrupos().isEmpty()) {
			for (Grupo grp : sku.getGrupos()) {
				grp.setSku(sku);
				
				if (grp.getAtributos() != null && !grp.getAtributos().isEmpty()) {
					for (AtributoGrupo att : grp.getAtributos()) {
						att.setGrupo(grp);
					}
				}
			}
		}
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
	
	public List<Sku> listar(Long id) throws Exception {
		List<Sku> lista = new ArrayList<>();
		
		if (id == null) {
			lista = (List<Sku>) skuRepository.findAll();
		} else {
			Sku sku = skuRepository.findOne(id);
			
			if (sku != null) {
				lista.add(sku);
			}
		}
		
		if (lista == null || lista.isEmpty()) {
			throw new RecursoNaoExistenteException();
		}
		
		return lista;
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
