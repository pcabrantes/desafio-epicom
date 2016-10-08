package br.com.epicom.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.epicom.marketplace.service.SkuService;

@RestController
public class SkuRestController {

	@Autowired
	private SkuService skuService;
	
	@RequestMapping(method = RequestMethod.POST, value="/marketplace/sku/cadastrar")
	public Object cadastrarSKU(@RequestBody Object sku) throws Exception {
		return skuService.cadastrar();
	}
}
