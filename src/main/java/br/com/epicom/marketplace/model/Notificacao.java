package br.com.epicom.marketplace.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.epicom.marketplace.util.request.NotificacaoJson;

/**
 * Classe que representa uma notificação
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@Entity
public class Notificacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	private Date dataEnvio;
	private Long idProduto;
	private Long idSku;
	private boolean processada;
	
	public Notificacao() {}
	
	public Notificacao(NotificacaoJson jsonObject) {
		setTipo(jsonObject.getTipo());
		setDataEnvio(jsonObject.getDataEnvio());
		setIdProduto(jsonObject.getParametros().getIdProduto());
		setIdSku(jsonObject.getParametros().getIdSku());
		setProcessada(false);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public Long getIdSku() {
		return idSku;
	}
	public void setIdSku(Long idSku) {
		this.idSku = idSku;
	}
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public boolean isProcessada() {
		return processada;
	}
	public void setProcessada(boolean processada) {
		this.processada = processada;
	}
}
