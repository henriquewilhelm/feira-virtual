package handlers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import handlers.SessionBean;
import models.Mail;
import models.UserTipo;
import models.Usuario;
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
	private Mail mail;
	
	public UsuarioBean() {	
		try{ 
			String email = (String) SessionBean.getUserEmail();
			meuUsuario = getUsuarioPorEmail(email);
		}catch (Exception e){
	        if (meuUsuario==null)
	        	meuUsuario = new Usuario();
	        System.out.println("Usuario não logado, cadastrando");
		}
        novoUsuario = new Usuario();
        usuario = new Usuario();
        mail = new Mail();
	}
	
	public String addUsuario() {
		System.out.println("Add Usuario");
		String string = "";
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(novoUsuario);
		em.getTransaction().commit();
		
		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			string = "/gerenciador/usuario/listar";
		else {
			string = "/loja/usuario";
		}

		mail.setAssunto("Cadastro realizado");
		mail.setDestino(novoUsuario.getEmail());
		mail.setMsg("Seu cadastro foi realizado com sucesso! Seu login de acesso é "+novoUsuario.getEmail()+" e sua senha é "+novoUsuario.getPassword()+".");
		mail.setNomeDestino(novoUsuario.getNome());
		mail.sendMail();
		setUsuario(new Usuario());

		return string;
	}
	
	public String updateMeuUsuario() {
		System.out.println("Update Usuario");
		String string = "";
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(meuUsuario);
		em.getTransaction().commit();
		
		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			string = "/gerenciador/usuario/listar";
		else
			string = "/loja/usuario";

		mail.setAssunto("Alteração no cadastro realizada");
		mail.setDestino(meuUsuario.getEmail());
		mail.setMsg("Seu cadastro foi alterado com sucesso! Seu login de acesso é "+meuUsuario.getEmail()+" e sua senha é "+meuUsuario.getPassword()+"");
		mail.setNomeDestino(meuUsuario.getNome());
		mail.sendMail();
		
		mail = new Mail();
		return "/loja/usuario";
	}
	
	public String updateUsuario() {
		System.out.println("Update Usuario");
		String string = "";
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
		
		if (meuUsuario.getTipo().equals(UserTipo.ADMIN))
			string = "/gerenciador/usuario/listar";
		else
			string = "/loja/usuario";
		
		setUsuario(new Usuario());

		return string;
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
	}
	public Usuario getMeuUsuario() {
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
	
	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public Usuario getUsuarioPorEmail(String email) {
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
				Usuario.class);
		query.setParameter("email", email);
		Usuario usuario = query.getSingleResult();
		return usuario;
	}
	
	public String list() {
		return "/gerenciador/usuario/listar";
	}
}
