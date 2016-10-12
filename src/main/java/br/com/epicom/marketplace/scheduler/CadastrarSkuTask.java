package br.com.epicom.marketplace.scheduler;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Component
public class CadastrarSkuTask {
	
	private static final String URI = "https://sandboxmhubapi.epicom.com.br/v1/marketplace/produtos/{idProduto}/skus/{id}";

	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	@Autowired
	private SkuService skuService;
	
	@Value("${marketplace.api.chave}")
	private String chaveAcesso;
	
	@Value("${marketplace.api.token}")
	private String token;
	
	@Scheduled(fixedRate = 30000)
	public void cadastrarSku() throws Exception {
		
		List<Notificacao> listaNotif = (List<Notificacao>) notificacaoRepository.findByProcessada(false);
		Map<String, String> params = null;
		
		if (listaNotif != null && !listaNotif.isEmpty()) {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			
			String plainAuth = chaveAcesso + ":" + token;
			String base64Auth = new String(Base64.getEncoder().encode(plainAuth.getBytes()));
			
			headers.add("Authorization", "Basic " + base64Auth);
			
			for (Notificacao notif : listaNotif) {
				params = new HashMap<>();
				params.put("idProduto", String.valueOf(notif.getIdProduto()));
				params.put("id", String.valueOf(notif.getIdSku()));
				
				try {
					
					HttpEntity<String> request = new HttpEntity<String>(headers);
			        ResponseEntity<Sku> response = restTemplate.exchange(URI, HttpMethod.GET, request, Sku.class, params);
					Sku sku = response.getBody();
					
					if (sku != null) {
						skuService.cadastrar(sku);
						notif.setProcessada(true);
						notificacaoRepository.save(notif);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
	}
}
