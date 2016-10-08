package br.com.epicom.marketplace.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class SkuService {

	public Object cadastrar() throws Exception{
		
		return new String("OK");
	}
}
