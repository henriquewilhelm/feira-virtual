package handlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import models.Bairro;
import models.Cidade;
import models.Mail;
import models.UserTipo;
import models.Usuario;
import service.EmailService;
import util.JPA;


@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2003852110667669753L;
	private String email;
	private String password;
	private Usuario usuario;
	private Cidade cidade;
	private Bairro bairro;
	private Mail mail;
	
	public LoginBean() {
        	usuario = new Usuario();
	}
	
	public String clearLogin() {
		System.out.println("Clear All");
		setUsuario(new Usuario());

		if (usuario.getTipo().equals(UserTipo.ADMIN))
			return "/gerenciador/usuario/registrar";
		else
			return "/loja/usuario/editar";
	}
	
	public String lembrarSenha() {
		System.out.println("Esqueci Senha");
		if (getEmail()!=null || getEmail().equals("")){
			EntityManager em = JPA.getEM();
			TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
					Usuario.class);
			query.setParameter("email", getEmail());
			System.out.println(getEmail() + " "+ getPassword());
	        usuario = query.getSingleResult();
	        
		    if (usuario!=null){
		    	EmailService ThreadEmail = new EmailService(usuario, "Esqueci Senha");
				new Thread(ThreadEmail).start();
		    }
		    FacesContext.getCurrentInstance().addMessage(
	                null,
	                new FacesMessage(FacesMessage.SEVERITY_WARN,
	                        "Senha enviada para "+usuario.getEmail(),
	                        "Um e-mail com o lembrete de Senha foi enviado para o endereço informado."));
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "E-mail e/ou Senha Incorretos",
                        "Por favor entre com E-mail e Senha validos"));
		}
		return "/login";
	}

	 //validate login
	public String login() {
		try{
			EntityManager em = JPA.getEM();
			TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email and u.password = :password",
					Usuario.class);
			query.setParameter("email", getEmail());
			query.setParameter("password", getPassword());
			System.out.println(getEmail() + " "+ getPassword());
	        usuario = query.getSingleResult();

	            HttpSession session = SessionBean.getSession();
	            session.setAttribute("usuario", usuario);
	            if (usuario.getTipo().equals(UserTipo.ADMIN))
	            		return "admin";
	            else
	            		return "usuario";
		} catch ( Exception e){
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "E-mail e/ou Senha Incorretos",
                            "Por favor entre com E-mail e Senha validos"));
            return "login";
		}
    }
 
	public String addUsuario() {
		System.out.println("Novo Cadastro");

		usuario.setBairro(getBairro());
		usuario.setCidade(getCidade());
		
		
		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

		HttpSession session = SessionBean.getSession();
        session.setAttribute("usuario", usuario);
        
    	EmailService ThreadEmail = new EmailService(usuario, "Novo Cadastro");
		new Thread(ThreadEmail).start();
		
		return "usuario";
	}
	
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.setAttribute("usuario", null);
        session.invalidate();
        return "/login";
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
}
