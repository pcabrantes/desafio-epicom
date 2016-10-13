package br.com.epicom.marketplace.util.request;

/**
 * Classe POJO que representa os parametros de uma notificacao
 * 
 * @author Paulo Cesar Abrantes
 *
 */
public class ParametrosNotificacao {

	private Long idProduto;
	private Long idSku;
	
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
}
