package handlers;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPA;
import models.Categoria;
import models.Produto;
import models.Pedido;
import models.Tipo;
import models.Usuario;

@ManagedBean
@SessionScoped
public class ProdutoBean {
	
	private Produto produto;
	private Tipo tipo;
	private Categoria categoria;
	
	public ProdutoBean() {	
		produto = new Produto();
	}
	
	public String addProduto() {

		if (getTipo()!=null){
			System.out.println("Add Produto");
			produto.setTipo(getTipo());
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance(); 

			facesContext.addMessage(null, new FacesMessage( 
            FacesMessage.SEVERITY_ERROR, "Ops, faltou escolher um tipo...", null));
			System.out.println("Favor selecione uma opcao");
			
			return null;
		}
		
		if (getCategoria()!=null){
			produto.setCategoria(getCategoria());
			System.out.println("Add Categoria");
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance(); 

			facesContext.addMessage(null, new FacesMessage( 
            FacesMessage.SEVERITY_ERROR, "Ops, faltou escolher uma categoria...", null));
			System.out.println("Favor selecione uma opcao");
			
			return null;
		}
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(produto);
		em.getTransaction().commit();
		
		setProduto(new Produto());
		return "/gerenciador/produto/listar";
	}
	
	public String updateProduto() {
		
		if (getTipo()!=null){
			produto.setTipo(getTipo());
			System.out.println("Add Tipo");
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance(); 

			facesContext.addMessage(null, new FacesMessage( 
            FacesMessage.SEVERITY_ERROR, "Ops, faltou escolher um tipo...", null));
			System.out.println("Favor selecione uma opcao");
			
			return null;
		}

		if (getCategoria()!=null){
			produto.setCategoria(getCategoria());
			System.out.println("Add Categoria");
		}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance(); 

			facesContext.addMessage(null, new FacesMessage( 
            FacesMessage.SEVERITY_ERROR, "Ops, faltou escolher uma categoria...", null));
			System.out.println("Favor selecione uma opcao");
			
			return null;
		}
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(produto);
		em.getTransaction().commit();
		
		setProduto(new Produto());
		return "/gerenciador/produto/listar";
	}
	
	public String clearProdutos() {
		System.out.println("Clear All");
		setProduto(new Produto());
		return "/gerenciador/produto/registrar";
	}
	
	public void  delete(ActionEvent event) {
		Produto selected = (Produto) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			TypedQuery<Pedido> queryPedidos = em.createQuery(
					"select p from Pedido p left join fetch p.listItens c where c.produto.id = :id",
					Pedido.class);
			queryPedidos.setParameter("id", selected.getId());
			List<Pedido> pedidos = queryPedidos.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!pedidos.isEmpty()){
				for (int i=0; i<pedidos.size(); i++){
					System.out.println("Pedidos: "+pedidos.get(i).getNome());	
					System.out.println("Produto: "+selected.getNome());
					if (pedidos.get(i).getListItens() != null){
						for (int j=0; j<pedidos.get(i).getListItens().size(); j++){
							if (pedidos.get(i).getListItens().get(j).getProduto()!= null && pedidos.get(i).getListItens().get(j).getProduto().equals(selected)){
								System.out.println("entrei");	
								em.remove(pedidos.get(i).getListItens().get(j).getProduto());
								pedidos.get(i).getListItens().get(j).setProduto(null);
								
								em.remove(pedidos.get(i).getListItens().get(j));
								pedidos.get(i).getListItens().remove(pedidos.get(i).getListItens().get(j));
							}

							em.persist(pedidos.get(i));
							em.remove(selected);
							System.out.println("Tinha Pedidos - Removeu: "+selected.getNome());
						}
					}
				}
			}
			else{
				em.remove(selected);
				System.out.println("Removeu: "+selected.getNome());	
			}
		em.getTransaction().commit();
	}

	public List<Produto> getProdutosPorCategoria() {

		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select p from Produto p left join fetch p.categoria c where c.id = :id",
				Produto.class);
		if (categoria!=null)
		query.setParameter("id", categoria.getId());
		
		return query.getResultList();
	}
	
	public List<Produto> getProdutos() {

		EntityManager em = JPA.getEM();
		TypedQuery<Produto> query = em.createQuery("Select p from Produto p",
				Produto.class);
		
		return query.getResultList();
	}
	
	public List<Tipo> getTipos() {

		EntityManager em = JPA.getEM();
		TypedQuery<Tipo> query = em.createQuery("Select c from Tipo c",
				Tipo.class);
		
		return query.getResultList();
	}
	
	public List<Categoria> getCategorias() {

		EntityManager em = JPA.getEM();
		TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c",
				Categoria.class);
		
		return query.getResultList();
	}
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String list() {
		return "/gerenciador/produto/listar";
	}
}