package br.com.epicom.marketplace.service;

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

	@Autowired
	private NotificacaoRepository notificacaoRepository;
	
	public MessageResponse receberNotificacao(NotificacaoJson jsonObject) {
		
		Notificacao notificacao = new Notificacao(jsonObject);
		notificacaoRepository.save(notificacao);
		
		return new MessageResponse(HttpStatus.OK.value(), Mensagens.HTTP_STATUS_200);
	}
	
	public NotificacaoRepository getNotificacaoRepository() {
		return notificacaoRepository;
	}

	public void setNotificacaoRepository(NotificacaoRepository notificacaoRepository) {
		this.notificacaoRepository = notificacaoRepository;
	}
}
