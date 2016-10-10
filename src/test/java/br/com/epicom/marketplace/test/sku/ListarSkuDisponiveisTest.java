package br.com.epicom.marketplace.test.sku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.epicom.marketplace.controller.SkuRestController;
import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.repository.SkuRepository;
import br.com.epicom.marketplace.service.SkuService;
import br.com.epicom.marketplace.test.ApplicationTest;

public class ListarSkuDisponiveisTest extends ApplicationTest {

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
	public void testListarTodosComSucesso() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/marketplace/sku/");
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(200);

		Sku sku1 = mapper.readValue(obterJson("./json/sku.json"), Sku.class);
		Sku sku2 = mapper.readValue(obterJson("./json/sku2.json"), Sku.class);
		
		List<Sku> lista = new ArrayList<>();
		lista.add(sku1);
		lista.add(sku2);
		
		Mockito.when(skuRepository.findAll()).thenReturn(lista);
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
		
		List<Sku> list = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Sku>>(){});
		
		assertEquals(list.size(), 2);
		
		for (Sku s : list) {
			assertEquals(s.isAtivo(), true);
			assertEquals(s.isDisponivel(), true);
			assertTrue(s.getPreco() >= 10L);
			assertTrue(s.getPreco() <= 40L);
		}
	}
	
	@Test
	public void testListarSemResultado() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/marketplace/sku/");
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(404);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
		
	}
}
