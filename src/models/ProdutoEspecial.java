package models;

import java.io.Serializable;
import java.util.List;

public class ProdutoEspecial implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5245953398020700491L;
	private Produto produto;
	private Double quantidade;
	private List<Double> quantidades;
	
	public ProdutoEspecial(Produto produto, Double quantidade,
			List<Double> quantidades) {
		super();
		this.produto = produto;
		this.quantidade = quantidade;
		this.quantidades = quantidades;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getQuantidade() {
//		System.out.println("get"+quantidade);
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
//		System.out.println("set"+quantidade);
		this.quantidade = quantidade;
	}

	public List<Double> getQuantidades() {
		return quantidades;
	}

	public void setQuantidades(List<Double> quantidades) {
		this.quantidades = quantidades;
	}
}
