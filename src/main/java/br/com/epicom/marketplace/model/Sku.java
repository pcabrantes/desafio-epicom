package br.com.epicom.marketplace.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Sku {

	@Id
	@NotNull @Min(1) 
	private Long id;
	
	@NotNull
	private String nome;
	private String nomeReduzido;

	@NotNull
	private String codigo;
	private String modelo;
	private String ean;
	
	private String url;
	private boolean foraDeLinha;
	
	@NotNull @Min(0)
	private double preco;
	
	@Min(0)
	private double precoDe;
	private boolean disponivel;
	
	@Min(0)
	private int estoque;
	private boolean ativo;
	private String codigoCategoria;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sku")
	private Dimensoes dimensoes;
	
	@OneToMany(mappedBy="sku", targetEntity=Imagem.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Imagem> imagens;
	
	@OneToMany(mappedBy="sku", targetEntity=Grupo.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Grupo> grupos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeReduzido() {
		return nomeReduzido;
	}
	public void setNomeReduzido(String nomeReduzido) {
		this.nomeReduzido = nomeReduzido;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isForaDeLinha() {
		return foraDeLinha;
	}
	public void setForaDeLinha(boolean foraDeLinha) {
		this.foraDeLinha = foraDeLinha;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public double getPrecoDe() {
		return precoDe;
	}
	public void setPrecoDe(double precoDe) {
		this.precoDe = precoDe;
	}
	public boolean isDisponivel() {
		return disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	public Dimensoes getDimensoes() {
		return dimensoes;
	}
	public void setDimensoes(Dimensoes dimensoes) {
		this.dimensoes = dimensoes;
	}
	public List<Imagem> getImagens() {
		return imagens;
	}
	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		try {
			str = new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
		}
		
		return str;
	}
	
}
