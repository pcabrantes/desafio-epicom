package br.com.epicom.marketplace.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AtributoGrupo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	private String codigoAtributoCategoria;
	private String nome;
	private String codigoValorAtributoCategoria;
	private String valor;
	
	@ManyToOne
	@JoinColumn(name = "idGrupo")
	@JsonIgnore
	private Grupo grupo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigoAtributoCategoria() {
		return codigoAtributoCategoria;
	}
	public void setCodigoAtributoCategoria(String codigoAtributoCategria) {
		this.codigoAtributoCategoria = codigoAtributoCategria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigoValorAtributoCategoria() {
		return codigoValorAtributoCategoria;
	}
	public void setCodigoValorAtributoCategoria(String codigoValorAtributoCategoria) {
		this.codigoValorAtributoCategoria = codigoValorAtributoCategoria;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
