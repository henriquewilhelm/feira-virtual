package handlers;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import models.Mail;
import models.UserTipo;
import models.Usuario;
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
	private Mail mail;
	
	public LoginBean() {
        	usuario = new Usuario();
        	mail = new Mail();
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
		return "/login";
	}

	 //validate login
	public String login() {
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email and u.password = :password",
				Usuario.class);
		query.setParameter("email", getEmail());
		query.setParameter("password", getPassword());
		System.out.println(getEmail() + " "+ getPassword());
        boolean valid = query.getResultList().size()!=0;
        if (valid) {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("email", email);
            usuario = query.getResultList().get(0);
            if (usuario.getTipo().equals(UserTipo.ADMIN))
            		return "admin";
            else
            		return "usuario";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "E-mail e/ou Senha Incorretos",
                            "Por favor entre com E-mail e Senha validos"));
            return "login";
        }
    }
 
	public String addUsuario() {
		System.out.println("Add Usuario");

		EntityManager em = JPA.getEM();
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

		HttpSession session = SessionBean.getSession();
        session.setAttribute("email", usuario.getEmail());
		
		mail.setAssunto("Cadastro realizado");
		mail.setDestino(usuario.getEmail());
		mail.setMsg("Seu cadastro foi realizado com sucesso! Seu login de acesso é "+usuario.getEmail()+" e sua senha é "+usuario.getPassword()+".");
		mail.setNomeDestino(usuario.getNome());
		mail.sendMail();
		setUsuario(new Usuario());

		return "usuario";
	}
	
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionBean.getSession();
        session.invalidate();
        return "/login";
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
}
