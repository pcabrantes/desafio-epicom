package br.com.epicom.marketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Sku;

/**
 * Classe utilizada para realiza operações sobre SKUs no banco de dados local
 * 
 * @author Paulo Cesar Abrantes
 *
 */
public interface SkuRepository extends CrudRepository<Sku, Long> {

	/**
	 * Método que retorna todos os SKUs ativos, disponíveis com preço entre 10.00 e 40.00 em ordem ascendente pelo preço
	 * 
	 * @return Uma lista de SKUs
	 */
	@Query("select s from Sku s where s.ativo = true and s.disponivel = true and s.preco >= 10.0 and s.preco <= 40.0 order by s.preco asc")
	Iterable<Sku> findAllDisponiveisPreco();
	
	
	/**
	 * Método utilizado para consultar todos os SKUs ativos
	 * 
	 * @return Uma lista de SKUs
	 */
	@Query("select s from Sku s where s.ativo = true")
	Iterable<Sku> findAllAtivos();
	
	
	/**
	 * Método utilizado para consultar um SKU ativo a partir de seu id
	 * 
	 * @param id
	 * @return Um SKU
	 */
	@Query("select s from Sku s where s.id = ?1 and s.ativo = true")
	Sku findOneAtivo(Long id);
}
