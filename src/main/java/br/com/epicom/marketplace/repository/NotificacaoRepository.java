package br.com.epicom.marketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Notificacao;

/**
 * Classe utilizada para realizar operações sobre Notificações no banco de dados local
 * 
 * @author pc
 *
 */
public interface NotificacaoRepository extends CrudRepository<Notificacao, Long> {

	/**
	 * Método utilziado para retornar todas as notificações de acordo com seu status
	 * 
	 * @param processada
	 * @return
	 */
	@Query("select n from Notificacao n where n.processada =?1")
	Iterable<Notificacao> findByProcessada(boolean processada);
}
