package handlers;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPA;
import models.Bairro;
import models.Cidade;
import models.Pedido;

@ManagedBean
@SessionScoped
public class CidadeBean {
	
	private Cidade cidade;
	
	public CidadeBean() {	
		cidade = new Cidade();
	}
	
	public String addCidade() {
		System.out.println("Add Cidade "+cidade.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(cidade);
		em.getTransaction().commit();
		
		setCidade(new Cidade());

		return "/gerenciador/cidade/listar";
	}
	
	public String clearCidades() {
		System.out.println("Clear All");
		setCidade(new Cidade());
		return "/gerenciador/cidade/registrar";
	}
	
	public void  delete(ActionEvent event){
		Cidade selected = (Cidade) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			TypedQuery<Pedido> queryPedidos = em.createQuery(
					"select p from Pedido p left join fetch p.cidade c where c.id = :id",
					Pedido.class);
			queryPedidos.setParameter("id", selected.getId());
			List<Pedido> pedidos = queryPedidos.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!pedidos.isEmpty()){
				for (int i=0; i<pedidos.size(); i++){
					System.out.println("Pedidos: "+pedidos.get(i).getNome());	
					System.out.println("Cidade: "+selected.getNome());
							if (pedidos.get(i).getCidade()!= null && pedidos.get(i).getCidade().equals(selected)){
								
						//		em.remove(pedidos.get(i).getCidade());
								pedidos.get(i).setCidade(null);
							}
							em.persist(pedidos.get(i));
						//	em.remove(selected);
							System.out.println("Tinha cidade - Removeu: "+selected.getNome());
				}
			}
			//remove da tabela bairros se houve
			TypedQuery<Bairro> queryBairros = em.createQuery(
					"select b from Bairro b left join fetch b.cidade c where c.id = :id",
					Bairro.class);
			queryBairros.setParameter("id", selected.getId());
			List<Bairro> bairros = queryBairros.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!bairros.isEmpty()){
				for (int i=0; i<bairros.size(); i++){
					System.out.println("Bairro: "+bairros.get(i).getNome());	
					System.out.println("Cidade: "+selected.getNome());
							if (bairros.get(i).getCidade()!= null && bairros.get(i).getCidade().equals(selected)){
								
								em.remove(bairros.get(i).getCidade());
								bairros.get(i).setCidade(null);
							}
							em.persist(bairros.get(i));
							em.remove(selected);
							System.out.println("Tinha cidade - Removeu: "+selected.getNome());
				}
			}
			else{
				em.remove(selected);
				System.out.println("Removeu: "+selected.getNome());	
			}
		em.getTransaction().commit();
	}

	public List<Cidade> getCidades() {

		EntityManager em = JPA.getEM();
		TypedQuery<Cidade> query = em.createQuery("Select c from Cidade c",
				Cidade.class);
		
		return query.getResultList();
	}
	
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String list() {
		return "/gerenciador/cidade/listar";
	}
}