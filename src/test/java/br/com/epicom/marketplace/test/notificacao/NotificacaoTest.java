package br.com.epicom.marketplace.test.notificacao;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.epicom.marketplace.controller.NotificacaoRestController;
import br.com.epicom.marketplace.model.Sku;
import br.com.epicom.marketplace.repository.NotificacaoRepository;
import br.com.epicom.marketplace.service.NotificacaoService;
import br.com.epicom.marketplace.test.ApplicationTest;
import br.com.epicom.marketplace.util.request.NotificacaoJson;

public class NotificacaoTest extends ApplicationTest {

	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Spy
	private NotificacaoService notificaacaoService; 
	
	@Spy
	private Sku sku;
	
	@Mock
	private NotificacaoRepository notificacaoRepository;
	
	@InjectMocks
	private NotificacaoRestController controller;
	
	private List<NotificacaoJson> listaJson;
	
	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        notificaacaoService.setNotificacaoRepository(notificacaoRepository);

        listaJson = mapper.readValue(obterJson("./json/notif.json"), new TypeReference<List<NotificacaoJson>>(){});
	}
	
	@Test
	public void testCadastrarComSucesso() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/notificacao")
				.contentType(APPLICATION_JSON_UTF8)
				.content(listaJson.get(0).toString());
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(200);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
	
	@Test
	public void testCadastrarJsonInvalido() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/marketplace/notificacao")
				.contentType(APPLICATION_JSON_UTF8)
				.content(obterJson("./json/notif_invalida.json"));
		
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().is(400);
		
		mockMvc.perform(requestBuilder)
				.andExpect(resultMatcher)
				.andReturn();
	}
}
