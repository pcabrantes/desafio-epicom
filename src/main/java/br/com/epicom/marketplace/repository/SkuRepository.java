package br.com.epicom.marketplace.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.epicom.marketplace.model.Sku;

public interface SkuRepository extends CrudRepository<Sku, Long> {

}
