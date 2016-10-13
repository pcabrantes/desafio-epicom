package br.com.epicom.marketplace.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SkuRepository skuRepository;
	
	@Transactional
	public MessageResponse cadastrar(@Valid Sku sku) throws Exception{
		
		MessageResponse ret = new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
		
		executarValidacoesCadastro(sku);
		prepararInsersao(sku);
		skuRepository.save(sku);
		
		logger.info("Retorno: " + ret);
		
		return ret;
	}
	
	@Transactional
	public MessageResponse atualizar(@Valid Sku sku) throws Exception {
		
		MessageResponse ret = new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
		
		Sku skuAnterior = skuRepository.findOneAtivo(sku.getId());
		
		if (skuAnterior == null) {
			throw new RecursoNaoExistenteException();
		}
		
		skuRepository.delete(skuAnterior);
		validarImagens(sku);
		prepararInsersao(sku);
		skuRepository.save(sku);
		
		logger.info("Retorno: " + ret);
		
		return ret;
	}

	public MessageResponse remover(Long id) throws Exception {
		
		MessageResponse ret = new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
		
		Sku sku = skuRepository.findOne(id);
		
		if (sku == null || !sku.isAtivo()) {
			throw new RecursoNaoExistenteException();
		}
		
		sku.setAtivo(false);
		skuRepository.save(sku);
 		
		logger.info("Retorno: " + ret);
		
		return ret;
	}
	
	public List<Sku> listar() throws Exception {
		
		List<Sku> lista =  (List<Sku>) skuRepository.findAllAtivos();
			
		if (lista == null || lista.isEmpty()) {
			throw new RecursoNaoExistenteException();
		}

		logger.info(lista.size() + " Registros encontrados.");
		
		return lista;
	}
	
	public Sku obter(Long id) throws RecursoNaoExistenteException {
		Sku sku = skuRepository.findOneAtivo(id);

		if (sku == null) {
			throw new RecursoNaoExistenteException();
		}
		
		logger.info("Retorno: " + sku);
		
		return sku;
	}
	
	public List<Sku> listarDisponiveis() throws Exception {
		List<Sku> lista = (List<Sku>) skuRepository.findAllDisponiveisPreco();
		
		if (lista == null || lista.isEmpty()) {
			throw new RecursoNaoExistenteException();
		}
		
		logger.info(lista.size() + " Registros encontrados.");
		
		return lista;
	}
	
	private void executarValidacoesCadastro(Sku sku) throws Exception {

		Sku skuAnterior = skuRepository.findOne(sku.getId());
		
		if (skuAnterior != null) {
			if (skuAnterior.isAtivo()) {
				throw new RecursoJaExistenteException();
			} else {
				skuRepository.delete(sku.getId());
			}
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
	
	public SkuRepository getSkuRepository() {
		return skuRepository;
	}

	public void setSkuRepository(SkuRepository skuRepository) {
		this.skuRepository = skuRepository;
	}
}
