package br.com.epicom.marketplace.util.request;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificacaoJson {

	private String tipo;
	private Date dataEnvio;
	private ParametrosNotificacao parametros;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public ParametrosNotificacao getParametros() {
		return parametros;
	}
	public void setParametros(ParametrosNotificacao parametros) {
		this.parametros = parametros;
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
