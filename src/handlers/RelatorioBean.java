package handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Item;
import models.Produto;
import models.Pedido;
import models.Relacao;
import models.Relatorio;
import models.StatusPedido;
import models.Usuario;
import util.JPA;

@ManagedBean
@RequestScoped
public class RelatorioBean {
	
	private List<String> produtosDisponiveis = new ArrayList<String>();
	private List<String> produtosPrecos = new ArrayList<String>();
	private List<String> produtosTipos = new ArrayList<String>();
	private ArrayList<ArrayList<Relacao>> pedidoXproduto = new ArrayList<ArrayList<Relacao>>();

	private Relatorio relatorio;
	private List<Relatorio> relatorios;
	
	public RelatorioBean() {
		montaProdutosDisponiveis();
		montaProdutosPrecos();
		montaProdutosTipos();
		montaProdutosXPedidos();
		montaRelatorio();
		imprimir();
	} 
	
	public void imprimir() {
		for (int i=0; i<relatorios.size(); i++){
			System.out.print(relatorios.get(i).getProdutoDisponivel()+ " "+relatorios.get(i).getProdutoTipo()+ " "+ relatorios.get(i).getProdutoPreco());
			for (int j=0; j<relatorios.get(i).getPedidoXproduto().size(); j++){
				System.out.print(relatorios.get(i).getPedidoXproduto().get(j).getNome()+" ("+relatorios.get(i).getPedidoXproduto().get(j).getQuantidade()+" - "+relatorios.get(i).getPedidoXproduto().get(j).getValor()+")");
			
			}
			System.out.println("");
		}
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	
	public List<Relatorio> getRelatorios() {
		return relatorios;
	}
	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}
	
	private List<String> getProdutosDisponiveis() {
		return produtosDisponiveis;
	}
	private List<String> getProdutosPrecos() {
		return produtosPrecos;
	}
	private List<String> getProdutosTipos() {
		return produtosTipos;
	}
	private ArrayList<ArrayList<Relacao>> getPedidoXproduto() {
		return pedidoXproduto;
	}

	private void montaProdutosDisponiveis(){
		List<Produto> produtos = getProdutosCadastrados();
		for (int i=0; i<produtos.size(); i++){
			 getProdutosDisponiveis().add(produtos.get(i).getNome());
		}		
	}
	
	private void montaProdutosPrecos(){
		List<Produto> produtos = getProdutosCadastrados();
		for (int i=0; i<produtos.size(); i++){
			 getProdutosPrecos().add(produtos.get(i).getValor().toString());
		}
	}
	
	private void montaProdutosTipos(){
		List<Produto> produtos = getProdutosCadastrados();
		for (int i=0; i<produtos.size(); i++){
			if (produtos.get(i).getTipo() != null)
				getProdutosTipos().add(produtos.get(i).getTipo().getNome());
			else
				getProdutosTipos().add("");
		}
	}
	
	public void montaRelatorio(){
		relatorios = new ArrayList<Relatorio>();
		for (int k=0; k<produtosDisponiveis.size(); k++){
			
			relatorio = new Relatorio();
			relatorio.setProdutoDisponivel(produtosDisponiveis.get(k));
			relatorio.setProdutoTipo(produtosTipos.get(k));
			relatorio.setProdutoPreco(produtosPrecos.get(k));
			relatorio.setPedidoXproduto(getPedidoXproduto().get(k));
			relatorios.add(relatorio);
		}
	}
	
	private void montaProdutosXPedidos(){
		Double produtoValorTotal;
		Double produtoUnidadeTotal;
		List<Double> listValorTotal = new ArrayList<Double>();
		List<Pedido> pedidos = getPedidos();
		List<Produto> produtos = getProdutosCadastrados();
		
		System.out.println("Tam. Pedidos: "+pedidos.size());
		System.out.println("Tam. Produtos: "+produtos.size());
		
		
		for (int j=0; j<produtos.size(); j++){
			produtoValorTotal = 0d;
			produtoUnidadeTotal = 0d;
			ArrayList<Relacao> pedidoXproduto = new ArrayList<Relacao>();
			Relacao relacao = new Relacao();
			for (int i=0; i<pedidos.size(); i++){
				for (int k=0; k<pedidos.get(i).getListItens().size(); k++){
						if (produtos.get(j).getId() == pedidos.get(i).getListItens().get(k).getProduto().getId() ){
							relacao.setNome(pedidos.get(i).getNome());
							relacao.setQuantidade(pedidos.get(i).getListItens().get(k).getQuantidade().toString());
							relacao.setValor(pedidos.get(i).getListItens().get(k).getTotal().toString());
							produtoValorTotal = produtoValorTotal + pedidos.get(i).getListItens().get(k).getTotal();
							produtoUnidadeTotal = produtoUnidadeTotal + pedidos.get(i).getListItens().get(k).getQuantidade();
						}
				}
				if (pedidos.get(i).getListItens().size()==0){
					if (pedidos.get(i).getNome().equals("Total")){
						relacao.setNome(pedidos.get(i).getNome());
						relacao.setQuantidade(produtoUnidadeTotal.toString());
						relacao.setValor(produtoValorTotal.toString());
					}
				}
				pedidoXproduto.add(relacao);
				relacao = new Relacao();
			}
			
			if (produtos.get(j).getNome().equals("Total")){
				ArrayList<Relacao> pedidoXprodutoAux = new ArrayList<Relacao>();
				for (int l=0; l<pedidos.size()-1; l++){
					pedidoXprodutoAux.add(new Relacao("Total", pedidos.get(l).getTotal().toString(), ""));
				}
				getPedidoXproduto().add(pedidoXprodutoAux);
			}

			listValorTotal.add(produtoValorTotal);
			getPedidoXproduto().add(pedidoXproduto);
		}
	}
	
	private List<Produto> getProdutosCadastrados() {
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select c from Produto c",
				Produto.class);
		List<Produto> produtos = query.getResultList();
		Produto produto = new Produto("Total", null, null, 0d, null);
		produtos.add(produto);
		
		return produtos;
	}
	
	public List<Pedido> getPedidos() {
		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query = em.createQuery("Select v from Pedido v",
				Pedido.class);
		List<Pedido> pedidos = query.getResultList();
		Usuario usuario = new Usuario("", "", "Total", "", 0, "", null, null, 0l, null);
		Pedido pedido = new Pedido(null, usuario, "", new ArrayList<Item>(),0d,StatusPedido.EMANDAMENTO);
		pedidos.add(pedido);
		
		return pedidos;
	}
}