package br.com.epicom.marketplace.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
public class Imagem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String menor;
	private String maior;
	private String zoom;
	
	@Min(0)
	private int ordem;
	
	@ManyToOne
	@JoinColumn(name="idImagem")
	private Sku sku;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenor() {
		return menor;
	}
	public void setMenor(String menor) {
		this.menor = menor;
	}
	public String getMaior() {
		return maior;
	}
	public void setMaior(String maior) {
		this.maior = maior;
	}
	public String getZoom() {
		return zoom;
	}
	public void setZoom(String zoom) {
		this.zoom = zoom;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
}
