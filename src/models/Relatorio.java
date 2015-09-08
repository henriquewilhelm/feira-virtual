package models;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {
	
		private String produtoDisponivel;
		private String produtoPreco;
		private String produtoTipo;
		private List<Relacao> pedidoXproduto;
	
		public Relatorio() {
			pedidoXproduto = new ArrayList<Relacao>();
		}
		
		public Relatorio(String produtosDisponivel, String produtosPreco,
				String produtosTipo, List<Relacao> pedidoXproduto) {
			super();
			this.produtoDisponivel = produtosDisponivel;
			this.produtoPreco = produtosPreco;
			this.produtoTipo = produtosTipo;
			this.pedidoXproduto = pedidoXproduto;
		}
		
		public String getProdutoDisponivel() {
			return produtoDisponivel;
		}

		public void setProdutoDisponivel(String produtosDisponivel) {
			this.produtoDisponivel = produtosDisponivel;
		}

		public String getProdutoPreco() {
			return produtoPreco;
		}

		public void setProdutoPreco(String produtosPreco) {
			this.produtoPreco = produtosPreco;
		}

		public String getProdutoTipo() {
			return produtoTipo;
		}

		public void setProdutoTipo(String produtosTipo) {
			this.produtoTipo = produtosTipo;
		}

		public List<Relacao> getPedidoXproduto() {
			return pedidoXproduto;
		}

		public void setPedidoXproduto(List<Relacao> pedidoXproduto) {
			this.pedidoXproduto = pedidoXproduto;
		}
		
		public Relacao pedidoXproduto(Integer index) {
			if ( this.pedidoXproduto.size() > index)
				return this.pedidoXproduto.get(index);
			return new Relacao();
		}
}
