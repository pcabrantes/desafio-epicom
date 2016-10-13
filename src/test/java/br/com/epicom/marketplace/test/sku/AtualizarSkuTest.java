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

public class AtualizarSkuTest extends ApplicationTest {

private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
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
	public void testAtualizarComSucesso() throws Exception {

		String json = obterJson("./json/sku_id_invalido.json");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/marketplace/sku/atualizar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json);
		
		Sku sku = mapper.readValue(json, Sku.class);
		Mockito.when(skuRepository.findOneAtivo(Mockito.any())).thenReturn(sku);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(200);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testAtualizarNaoExistente() throws Exception {
		String json = obterJson("./json/sku_id_invalido.json");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/marketplace/sku/atualizar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json);
		
		Sku sku = mapper.readValue(json, Sku.class);
		Mockito.when(skuRepository.findOne(sku.getId())).thenReturn(null);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(404);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
}
