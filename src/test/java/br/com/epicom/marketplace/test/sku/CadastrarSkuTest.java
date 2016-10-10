package br.com.epicom.marketplace.test.sku;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
import br.com.epicom.marketplace.model.Imagem;
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

		Mockito.when(skuRepository.exists(1L)).thenReturn(true);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(obterJson("./json/sku.json"));
		
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
	public void testCadastrarIdInvalido() throws Exception {
		
		String json = obterJson("./json/sku_id_invalido.json");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(400);
		
		Sku sku = mapper.readValue(json, Sku.class);
		Set<ConstraintViolation<Sku>> violations = validator.validate(sku);
		
		if (!violations.isEmpty()) {
			Mockito.when(skuService.cadastrar(sku)).thenThrow(new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations)));
		}
		
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
	
	@Test
	public void testImagensOrdemNegativa() throws Exception {
		String json = obterJson("./json/sku_ordem_imagem_negativa.json");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/sku/cadastrar")
				.contentType(APPLICATION_JSON_UTF8)
				.content(json);
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(400);
		
		Sku sku = mapper.readValue(json, Sku.class);
		List<Imagem> imgs = sku.getImagens(); 
		
		Set<ConstraintViolation<Imagem>> violations = null;
		
		if (imgs != null && !imgs.isEmpty()) {
			for (Imagem img : imgs) {
				violations = validator.validate(img);
				if (!violations.isEmpty()) {
					Mockito.when(skuService.cadastrar(sku)).thenThrow(new ConstraintViolationException(
							new HashSet<ConstraintViolation<?>>(violations)));
					break;
				}
			}
		}
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
}
