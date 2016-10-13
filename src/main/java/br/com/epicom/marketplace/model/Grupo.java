package br.com.epicom.marketplace.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe que representa um Grupo
 * 
 * @author Paulo Cesar Abrantes
 *
 */
@Entity
public class Grupo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@NotNull
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="idSku")
	@JsonIgnore
	private Sku sku;
	
	@OneToMany(mappedBy="grupo", targetEntity=AtributoGrupo.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AtributoGrupo> atributos;
	
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
	public List<AtributoGrupo> getAtributos() {
		return atributos;
	}
	public void setAtributos(List<AtributoGrupo> atributos) {
		this.atributos = atributos;
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
}
