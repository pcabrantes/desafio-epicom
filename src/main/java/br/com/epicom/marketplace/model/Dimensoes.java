package br.com.epicom.marketplace.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Dimensoes implements Serializable {

	private static final long serialVersionUID = 4927149502303135932L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@DecimalMin("0.0")
	private double altura;
	
	@DecimalMin("0.0")
	private double largura;
	
	@DecimalMin("0.0")
	private double comprimento;
	
	@Min(0)
	private int peso;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSku")
	@JsonIgnore
	private Sku sku;
	
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getLargura() {
		return largura;
	}
	public void setLargura(double largura) {
		this.largura = largura;
	}
	public double getComprimento() {
		return comprimento;
	}
	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
	public void clone(Dimensoes d) {
		setAltura(d.getAltura());
		setComprimento(d.getComprimento());
		setLargura(d.getLargura());
		setPeso(d.getPeso());
	}
}
