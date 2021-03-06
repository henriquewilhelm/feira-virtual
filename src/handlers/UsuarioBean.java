package handlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import handlers.SessionBean;
import models.Bairro;
import models.Cidade;
import models.Mail;
import models.UserTipo;
import models.Usuario;
import service.EmailService;
import util.JPA;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2003852110667669753L;

	private Usuario usuario;
	private Usuario meuUsuario;
	private Usuario novoUsuario;
	private Cidade cidade;
	private Bairro bairro;
	
	
	public UsuarioBean() {	
		try{ 
			String email = (String) SessionBean.getUserEmail();
			meuUsuario = getUsuarioPorEmail(email);
			cidade = meuUsuario.getCidade();
			bairro = meuUsuario.getBairro();	
		}catch (Exception e){
//	        if (meuUsuario==null){
//	        	meuUsuario = new Usuario();
//	        }
	        System.out.println("Usuario não logado, cadastrando");
		}
        novoUsuario = new Usuario();
        usuario = new Usuario();	
	}
	
	public String addUsuario() {
		System.out.println("Novo Cadastro");
		String string = "";
		
		novoUsuario.setBairro(getBairro());
		novoUsuario.setCidade(getCidade());
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(novoUsuario);
		em.getTransaction().commit();
		
		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			string = "/gerenciador/usuario/listar";
		else 
			string = "/loja/usuario";

		EmailService ThreadEmail = new EmailService(novoUsuario, "Novo Cadastro");
		new Thread(ThreadEmail).start();
		
		setNovoUsuario(new Usuario());

		return string;
	}
	
	public String updateMeuUsuario() {
		System.out.println("Update Usuario");
		String string = "";
		
		meuUsuario.setBairro(getBairro());
		meuUsuario.setCidade(getCidade());
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(meuUsuario);
		em.getTransaction().commit();
		
		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			string = "/gerenciador/usuario/listar";
		else
			string = "/loja/usuario";
		
		EmailService ThreadEmail = new EmailService(meuUsuario, "Update Usuario");
		new Thread(ThreadEmail).start();
		
		return string;
	}
	
	public String updateUsuario() {
		System.out.println("Update Usuario");
		String string = "";
		
		usuario.setBairro(getBairro());
		usuario.setCidade(getCidade());
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
		
		setUsuario(new Usuario());

		return "/gerenciador/usuario/listar";
	}
	
	public String clearUsuarios() {
		System.out.println("Clear All");
		setUsuario(new Usuario());

		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			return "/gerenciador/usuario/registrar";
		else
			return "/loja/usuario/editar";
	}
	
	public void  delete(ActionEvent event){
		Usuario selected = (Usuario) event.getComponent().getAttributes().get("selected");
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.remove(selected);
		em.getTransaction().commit();
	}
	
	public List<Usuario> getUsuarios() {

		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select p from Usuario p",
				Usuario.class);
		
		return query.getResultList();
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		this.bairro = usuario.getBairro();
		this.cidade = usuario.getCidade();
	}
	
	public Usuario getMeuUsuario() {
		if (meuUsuario==null){
			String email = (String) SessionBean.getUserEmail();
			meuUsuario = getUsuarioPorEmail(email);
			cidade = meuUsuario.getCidade();
			bairro = meuUsuario.getBairro();
		}
		return meuUsuario;
	}
	public void setMeuUsuario(Usuario usuario) {
		this.meuUsuario = usuario;
	}
	
	public Usuario getNovoUsuario() {
		return novoUsuario;
	}
	
	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
		getBairros();
	}
	
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Usuario getUsuarioPorEmail(String email) {
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
				Usuario.class);
		query.setParameter("email", email);
		Usuario usuario = query.getSingleResult();
		return usuario;
	}
	
	public List<Cidade> getCidades() {

		EntityManager em = JPA.getEM();
		TypedQuery<Cidade> query = em.createQuery("Select c from Cidade c",
				Cidade.class);
		
		return query.getResultList();
	}
	
	public List<Bairro> getBairros() {
		try{ 				
			EntityManager em = JPA.getEM();
			TypedQuery<Bairro> query = em.createQuery("Select b from Bairro b where b.cidade.id = :id",
					Bairro.class);
			query.setParameter("id", cidade.getId());
			return query.getResultList();
		
		}catch (Exception e){
			return new ArrayList<Bairro>();
		}
	}

	public String list() {
		return "/gerenciador/usuario/listar";
	}
}
