package handlers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import models.Usuario;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable{
 
    /**
	 * 
	 */	
	private static final long serialVersionUID = 3086926813352741049L;

	public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
 
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
 
    public static String getUserEmail() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
         Usuario usuario = (Usuario) session.getAttribute("usuario");
         
         return usuario.getEmail();
    }
    
    public static Usuario getUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

         return (Usuario) session.getAttribute("usuario");
    }
 
    public static String getUserId() {
        HttpSession session = getSession();
        if (session != null)
            return (String) session.getAttribute("id");
        else
            return null;
    }
}