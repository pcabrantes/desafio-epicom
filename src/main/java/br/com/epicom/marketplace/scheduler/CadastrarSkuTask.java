package br.com.epicom.marketplace.scheduler;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.epicom.marketplace.model.Notificacao;
import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.repository.NotificacaoRepository;
import br.com.epicom.marketplace.service.SkuService;
import br.com.epicom.marketplace.util.exception.RecursoJaExistenteException;

/**
 * Classe utilizada para representar um agendador de tarefas
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@Component
public class CadastrarSkuTask {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String URI = "https://sandboxmhubapi.epicom.com.br/v1/marketplace/produtos/{idProduto}/skus/{id}";

	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Autowired
	private SkuService skuService;
	
	@Value("${marketplace.api.chave}")
	private String chaveAcesso;
	
	@Value("${marketplace.api.token}")
	private String token;
	
	/**
	 * Metodo utilizado para verificar a cada 30 segundos se existem novas notificacoes de cadastro de SKU e, caso
	 * existem, buscam na API marketplace e os cadastram no BD local
	 * 
	 * @throws Exception
	 */
	@Scheduled(fixedRate = 30000)
	public void cadastrarSku() throws Exception {
		
		List<Notificacao> listaNotif = (List<Notificacao>) notificacaoRepository.findByProcessada(false);
		Map<String, String> params = null;
		
		if (listaNotif != null && !listaNotif.isEmpty()) {

			logger.info(listaNotif.size() + " SKU's a cadastrar");
			
			int registrosAtualizados = 0;
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			
			String plainAuth = chaveAcesso + ":" + token;
			String base64Auth = new String(Base64.getEncoder().encode(plainAuth.getBytes()));
			
			headers.add("Authorization", "Basic " + base64Auth);
			
			HttpEntity<String> request = new HttpEntity<String>(headers);
			ResponseEntity<Sku> response = null;
			Sku sku = null;
			
			for (Notificacao notif : listaNotif) {
				params = new HashMap<>();
				params.put("idProduto", String.valueOf(notif.getIdProduto()));
				params.put("id", String.valueOf(notif.getIdSku()));
				
				try {
					logger.info("Acessando o servico GET /marketplace/produtos/{idProduto}/skus/{id}");
					logger.info("Parametros: idProduto=" + notif.getIdProduto() + ", id=" + notif.getIdSku());
					
			        response = restTemplate.exchange(URI, HttpMethod.GET, request, Sku.class, params);
					sku = response.getBody();
					
					if (sku != null) {
						skuService.cadastrar(sku);
						notif.setProcessada(true);
						notificacaoRepository.save(notif);
					}
					
					registrosAtualizados++;
				} catch (RecursoJaExistenteException e) {
					logger.error(e.getMessage(), e);
					logger.info("O recurso ja existe. A notificacao sera excluida.");
					notificacaoRepository.delete(notif);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}				
			}
			
			logger.info(registrosAtualizados + " registros cadastrados.");
		}
	}
}
