package handlers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPA;
import models.Tipo;
import models.Produto;

@ManagedBean
@SessionScoped
public class TipoBean {
	
	private Tipo tipo;
	
	public TipoBean() {	
		tipo = new Tipo();
	}
	
	public String addTipo() {
		System.out.println("Add Tipo "+tipo.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(tipo);
		em.getTransaction().commit();
		
		setTipo(new Tipo());

		return "/gerenciador/produto/tipo/listar";
	}
	
	public String updateTipo() {
		System.out.println("Add Tipo "+tipo.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(tipo);
		em.getTransaction().commit();
		
		setTipo(new Tipo());

		return "/gerenciador/produto/tipo/listar";
	}
	
	public String clearTipos() {
		System.out.println("Clear All");
		setTipo(new Tipo());
		return "/gerenciador/produto/tipo/registrar";
	}
	
	public void  delete(ActionEvent event){
		Tipo selected = (Tipo) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			TypedQuery<Produto> queryProdutos = em.createQuery(
					"select p from Produto p left join fetch p.tipo c where c.id = :id",
					Produto.class);
			queryProdutos.setParameter("id", selected.getId());
			List<Produto> produtos = queryProdutos.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!produtos.isEmpty()){
				for (int i=0; i<produtos.size(); i++){
					System.out.println("Produtos: "+produtos.get(i).getNome());	
					System.out.println("Tipo: "+selected.getNome());
							if (produtos.get(i).getTipo()!= null && produtos.get(i).getTipo().equals(selected)){
								
						//		em.remove(produtos.get(i).getTipo());
								produtos.get(i).setTipo(null);
							}
							em.persist(produtos.get(i));
						//	em.remove(selected);
							System.out.println("Tinha tipo - Removeu: "+selected.getNome());
				}
			}
			else{
				em.remove(selected);
				System.out.println("Removeu: "+selected.getNome());	
			}
		em.getTransaction().commit();
	}

	public List<Tipo> getTipos() {

		EntityManager em = JPA.getEM();
		TypedQuery<Tipo> query = em.createQuery("Select c from Tipo c",
				Tipo.class);
		
		return query.getResultList();
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public String list() {
		return "/gerenciador/produto/tipo/listar";
	}
}