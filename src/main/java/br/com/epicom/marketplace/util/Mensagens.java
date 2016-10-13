package br.com.epicom.marketplace.util;

/**
 * Classe que representa constantes de mensagens
 * 
 * @author Paulo Cesar Abrantes
 *
 */
public abstract class Mensagens {

	/**
	 * Mensagens de Status HTTP
	 */
	public static final String HTTP_STATUS_200 = "Sucesso!";
	public static final String HTTP_STATUS_400 = "Requisicao invalida ou nao pode ser entregue.";
	public static final String HTTP_STATUS_401 = "Erro na autenticacao das credenciais utilizadas.";
	public static final String HTTP_STATUS_403 = "Permissao negada ao endpoint.";
	public static final String HTTP_STATUS_404 = "A URL solicitada ou recurso nao existe.";
	public static final String HTTP_STATUS_409 = "O recurso que esta sendo criado ja existe.";
	public static final String HTTP_STATUS_500 = "Erro interno.";
	
}
