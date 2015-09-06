package handlers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Produto;
import models.Pedido;
import models.Relacao;
import models.Relatorio;
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
			 getProdutosTipos().add(produtos.get(i).getTipo().getNome());
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
		List<Pedido> pedidos = getPedidos();
		List<Produto> produtos = getProdutosCadastrados();
		
		for (int j=0; j<produtos.size(); j++){
			ArrayList<Relacao> pedidoXproduto = new ArrayList<Relacao>();
			Relacao relacao = new Relacao();
			for (int i=0; i<pedidos.size(); i++){
				for (int k=0; k<pedidos.get(i).getListItens().size(); k++){
						if (produtos.get(j).getId() == pedidos.get(i).getListItens().get(k).getProduto().getId() ){
							relacao.setNome(pedidos.get(i).getNome());
							relacao.setQuantidade(pedidos.get(i).getListItens().get(k).getQuantidade().toString());
							relacao.setValor(pedidos.get(i).getListItens().get(k).getTotal().toString());
						}
				}
				pedidoXproduto.add(relacao);	
				relacao = new Relacao();
			}
			getPedidoXproduto().add(pedidoXproduto);	
		}
		
	}
	
	private List<Produto> getProdutosCadastrados() {
		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select c from Produto c",
				Produto.class);
		
		return query.getResultList();
	}
	
	public List<Pedido> getPedidos() {
		EntityManager em = JPA.getEM();
		TypedQuery<Pedido> query = em.createQuery("Select v from Pedido v",
				Pedido.class);
		
		return query.getResultList();
	}
}