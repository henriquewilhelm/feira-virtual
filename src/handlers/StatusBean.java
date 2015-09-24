package handlers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import util.JPA;
import util.Util;
import models.Bairro;
import models.Status;
import models.StatusPedido;
import models.Usuario;

@ManagedBean
@SessionScoped
public class StatusBean {
	
	private Status status;
	
	public StatusBean() {	
		status = new Status();
	}
	
	public String addStatus() {
		System.out.println("Add Status "+status.getNome());

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(status);
		em.getTransaction().commit();
		
		setStatus(new Status());
		return "/gerenciador/pedido/status/listar";
	}
	
	public void  delete(ActionEvent event){
		Status selected = (Status) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.remove(selected);
		em.getTransaction().commit();
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String clearStatus() {
		System.out.println("Clear Status");
		setStatus(new Status());
		return "/gerenciador/pedido/status/registrar";
	}
	
	public List<Status> getListStatus() {
		try{ 				
			EntityManager em = JPA.getEM();
			TypedQuery<Status> query = em.createQuery("Select s from Status s",
					Status.class);
			
			return query.getResultList();
		
		}catch (Exception e){
			return new ArrayList<Status>();
		}
	}	
	public List<StatusPedido> getListStatusPedido() {
			 return Util.getListStatusPedido();
	}
	
	public List<String> getDiasDaSemana() {

		List<String> diasDaSemaa = new ArrayList<String>();
		diasDaSemaa.add("Segunda-Feira");
		diasDaSemaa.add("Terça-Feira");
		diasDaSemaa.add("Quarta-Feira");
		diasDaSemaa.add("Quinta-Feira");
		diasDaSemaa.add("Sexta-Feira");
		diasDaSemaa.add("Sabado-Feira");
		diasDaSemaa.add("Domingo-Feira");
		
		return diasDaSemaa;
	}
	
	
}