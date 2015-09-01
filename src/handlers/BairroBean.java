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
public class BairroBean {
	
	private Bairro bairro;
	
	private Cidade cidade;
	
	public BairroBean() {	
		bairro = new Bairro();
	}
	
	public String addBairro() {
		System.out.println("Add Bairro "+bairro.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(bairro);
		em.getTransaction().commit();
		
		setBairro(new Bairro());
		cidade = getBairro().getCidade();
		return "/gerenciador/bairro/listar";
	}
	
	/*public void addCidade() {
		System.out.println("Add Cidade "+cidade.getNome());

		this.bairro.setCidade(cidade);
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(bairro);
		em.getTransaction().commit();
	}*/
	
	public String clearBairros() {
		System.out.println("Clear All");
		setBairro(new Bairro());
		return "/gerenciador/bairro/registrar";
	}
	
	public void  delete(ActionEvent event){
		Bairro selected = (Bairro) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
			TypedQuery<Pedido> queryPedidos = em.createQuery(
					"select p from Pedido p left join fetch p.bairro b where b.id = :id",
					Pedido.class);
			queryPedidos.setParameter("id", selected.getId());
			List<Pedido> pedidos = queryPedidos.getResultList();
			// Se possui filho, ou seja, se a consulta encontrar Categoria "Filho" na tabela (categoria_pai)
			if (!pedidos.isEmpty()){
				for (int i=0; i<pedidos.size(); i++){
					System.out.println("Pedidos: "+pedidos.get(i).getNome());	
					System.out.println("Bairro: "+selected.getNome());
							if (pedidos.get(i).getBairro()!= null && pedidos.get(i).getBairro().equals(selected)){
								
								em.remove(pedidos.get(i).getBairro());
								pedidos.get(i).setBairro(null);
							}
							em.persist(pedidos.get(i));
						//	em.remove(selected);
							System.out.println("Tinha bairro - Removeu: "+selected.getNome());
				}
			}
		
			else{
				selected.setCidade(null);
				em.remove(selected);
				System.out.println("Removeu: "+selected.getNome());	
			}
		em.getTransaction().commit();
	}

	public List<Bairro> getBairros() {

		EntityManager em = JPA.getEM();
		TypedQuery<Bairro> query = em.createQuery("Select b from Bairro b",
				Bairro.class);
		
		return query.getResultList();
	}
	
	public List<Cidade> getCidades() {

		EntityManager em = JPA.getEM();
		TypedQuery<Cidade> query = em.createQuery("Select c from Cidade c",
				Cidade.class);
		
		return query.getResultList();
	}
	
	public Bairro getBairro() {
		return bairro;
	}
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
		this.bairro.setCidade(cidade);
	}

	public String list() {
		return "/gerenciador/bairro/listar";
	}
}