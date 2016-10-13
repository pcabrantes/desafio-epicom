package br.com.epicom.marketplace.util.response;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe que representa um json de resposta a um requisição em um serviço REST
 * 
 * @author Paulo Cesar Abrantes
 *
 */
public class MessageResponse {

	private int code;
	private String message;
	
	public MessageResponse(int code, String message) {
		setCode(code);
		setMessage(message);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		try {
			str = new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {}
				
		return str;
	}
}
