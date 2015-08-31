package models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="itens")
public class Item implements IConvertible, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 772394896069220352L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer quantidade;
	
	private Produto produto;

	private Double total;
	
	public Item() {
		this.quantidade = 1;
		this.total = 0d;
		this.produto = new Produto();
	}

	public Item(Integer qntd, Produto produto) {
		this.quantidade = qntd;
		this.total = 0d;
		this.produto = produto;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getTotal() {
		if (this.produto != null && this.quantidade != null)
			this.total = this.quantidade * this.produto.getValor();
		else 
			this.total = 0d;
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id  = id;
	}
}
