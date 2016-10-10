package br.com.epicom.marketplace.test.sku;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.epicom.marketplace.controller.SkuRestController;
import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.repository.SkuRepository;
import br.com.epicom.marketplace.service.SkuService;
import br.com.epicom.marketplace.test.ApplicationTest;

public class RemoverSkuTest extends ApplicationTest {
	
	private MockMvc mockMvc;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Spy
	private SkuService skuService; 
	
	@Spy
	private Sku sku;
	
	@Mock
	private SkuRepository skuRepository;
	
	@InjectMocks
	private SkuRestController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        skuService.setSkuRepository(skuRepository);
	}
	
	@Test
	public void testRemoverComSucesso() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/marketplace/sku/remover/{id}", 1);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(200);

		Sku sku = mapper.readValue(obterJson("./json/sku.json"), Sku.class);
		
		Mockito.when(skuRepository.findOne(1L)).thenReturn(sku);
		Mockito.when(skuRepository.exists(1L)).thenReturn(true);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testRemoverJaExcluidoLogicamente() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/marketplace/sku/remover/{id}", 1);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(404);

		Sku sku = mapper.readValue(obterJson("./json/sku.json"), Sku.class);
		sku.setAtivo(false);
		
		Mockito.when(skuRepository.findOne(1L)).thenReturn(sku);
		Mockito.when(skuRepository.exists(1L)).thenReturn(true);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testRemoverNaoExistente() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/marketplace/sku/remover/{id}", 1);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(404);

		Sku sku = mapper.readValue(obterJson("./json/sku.json"), Sku.class);
		sku.setAtivo(false);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}

}
