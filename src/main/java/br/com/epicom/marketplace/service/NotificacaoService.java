package br.com.epicom.marketplace.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.epicom.marketplace.model.Notificacao;
import br.com.epicom.marketplace.repository.NotificacaoRepository;
import br.com.epicom.marketplace.util.Mensagens;
import br.com.epicom.marketplace.util.request.NotificacaoJson;
import br.com.epicom.marketplace.util.response.MessageResponse;

@Service
public class NotificacaoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	public MessageResponse receberNotificacao(NotificacaoJson jsonObject) {
		
		MessageResponse ret = new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
		
		Notificacao notificacao = new Notificacao(jsonObject);
		notificacaoRepository.save(notificacao);
		
		logger.info("Retorno: " + ret);
		
		return ret;
	}
	
	public NotificacaoRepository getNotificacaoRepository() {
		return notificacaoRepository;
	}

	public void setNotificacaoRepository(NotificacaoRepository notificacaoRepository) {
		this.notificacaoRepository = notificacaoRepository;
	}
}
