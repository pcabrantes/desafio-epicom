package br.com.epicom.marketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Notificacao;

public interface NotificacaoRepository extends CrudRepository<Notificacao, Long> {

	@Query("select n from Notificacao n where n.processada =?1")
	Iterable<Notificacao> findByProcessada(boolean processada);
}
