package handlers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPA;
import models.Categoria;
import models.Produto;

@ManagedBean
@SessionScoped
public class CategoriaBean {
	
	private Categoria categoria;
	
	public CategoriaBean() {	
		categoria = new Categoria();
	}
	
	public String addCategoria() {
		System.out.println("Add Categoria "+categoria.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		
		setCategoria(new Categoria());

		return "/gerenciador/produto/categoria/listar";
	}
	
	public String updateCategoria() {
		System.out.println("Add Categoria "+categoria.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(categoria);
		em.getTransaction().commit();
		
		setCategoria(new Categoria());

		return "/gerenciador/produto/categoria/listar";
	}
	
	public String clearCategorias() {
		System.out.println("Clear All");
		setCategoria(new Categoria());
		return "/gerenciador/produto/categoria/registrar";
	}
	
	public void  delete(ActionEvent event){
		Categoria selected = (Categoria) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			TypedQuery<Produto> queryProdutos = em.createQuery(
					"select p from Produto p left join fetch p.categoria c where c.id = :id",
					Produto.class);
			queryProdutos.setParameter("id", selected.getId());
			List<Produto> produtos = queryProdutos.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!produtos.isEmpty()){
				for (int i=0; i<produtos.size(); i++){
					System.out.println("Produtos: "+produtos.get(i).getNome());	
					System.out.println("Categoria: "+selected.getNome());
							if (produtos.get(i).getCategoria()!= null && produtos.get(i).getCategoria().equals(selected)){
								
						//		em.remove(produtos.get(i).getCategoria());
								produtos.get(i).setCategoria(null);
							}
							em.persist(produtos.get(i));
						//	em.remove(selected);
							System.out.println("Tinha categoria - Removeu: "+selected.getNome());
				}
			}
			else{
				em.remove(selected);
				System.out.println("Removeu: "+selected.getNome());	
			}
		em.getTransaction().commit();
	}

	public List<Categoria> getCategorias() {

		EntityManager em = JPA.getEM();
		TypedQuery<Categoria> query = em.createQuery("Select c from Categoria c",
				Categoria.class);
		
		return query.getResultList();
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public String list() {
		return "/gerenciador/produto/categoria/listar";
	}
}