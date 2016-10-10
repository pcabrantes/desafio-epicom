package br.com.epicom.marketplace.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ApplicationTest {

	private StringBuilder json = null;
	private BufferedReader br;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	public String obterJson(String path) throws IOException {

		try {
			br = new BufferedReader(new FileReader(getClass().getResource(path).getFile()));
	        String linha;
	        json = new StringBuilder();
	        
	        while ((linha = br.readLine()) != null) {
	        	json.append(linha).append("\n");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}

		return json.toString();
	}	
}

