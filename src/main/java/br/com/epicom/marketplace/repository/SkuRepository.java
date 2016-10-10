package br.com.epicom.marketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Sku;

public interface SkuRepository extends CrudRepository<Sku, Long> {

	@Query("select s from Sku s, Dimensoes d where s.id = d.sku.id")
	Iterable<Sku> findAllWithRelationships();
}
