package br.com.epicom.marketplace.util;

public abstract class Mensagens {

	/**
	 * Mensagens de Status HTTP
	 */
	public static final String HTTP_STATUS_200 = "Sucesso!";
	public static final String HTTP_STATUS_400 = "Requisição inválida ou não pôde ser entregue.";
	public static final String HTTP_STATUS_401 = "Erro na autenticação das credenciais utilizadas.";
	public static final String HTTP_STATUS_403 = "Permissão negada ao endpoint.";
	public static final String HTTP_STATUS_404 = "A URL solicitada ou recurso não existe.";
	public static final String HTTP_STATUS_409 = "O recurso que está sendo criado já existe.";
	public static final String HTTP_STATUS_500 = "Erro interno.";
	
}
