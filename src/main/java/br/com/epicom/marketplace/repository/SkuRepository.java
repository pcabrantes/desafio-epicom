package br.com.epicom.marketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Sku;

public interface SkuRepository extends CrudRepository<Sku, Long> {

	@Query("select s from Sku s where s.ativo = true and s.disponivel = true and s.preco >= 10.0 and s.preco <= 40.0")
	Iterable<Sku> findAllDisponiveisPreco();
	
	@Query("select s from Sku s where s.ativo = true")
	Iterable<Sku> findAllAtivos();
	
	@Query("select s from Sku s where s.id = ?1 and s.ativo = true")
	Sku findOneAtivo(Long id);
}
