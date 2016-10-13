package br.com.epicom.marketplace.test.sku;

import static org.junit.Assert.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
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


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastrarSkuTest extends ApplicationTest {

	private MockMvc mockMvc;
	
	private Validator validator;
	
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
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void testCadastrarComSucesso() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(obterJson("./json/sku.json"));
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(200);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testCadastrarJaExistente() throws Exception {

		String json = obterJson("./json/sku.json");
		sku = mapper.readValue(json, Sku.class);
		
		Mockito.when(skuRepository.findOne(Mockito.anyLong())).thenReturn(sku);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(409);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testCadastrarFormatoJsonInvalido() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(obterJson("./json/sku_json_invalido.json"));
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(400);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testImagensOrdemRepetida() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(obterJson("./json/sku_ordem_imagem_repetida.json"));
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(400);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
}
