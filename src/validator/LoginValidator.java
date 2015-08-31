package validator;
import java.util.regex.*;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Usuario;
import util.JPA;
 
@FacesValidator("loginValidator") 
public class LoginValidator implements Validator
{
 
    private static final String EMAIL_REGEXP =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
 
    @Override
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException
    {
        String email = (String) val;
        Pattern mask = null;
        mask = Pattern.compile(EMAIL_REGEXP);
        Matcher matcher = mask.matcher(email);
        if (!matcher.matches()) {
        	FacesMessage message = new FacesMessage();
            message.setDetail("Por favor entre com um email valido...");
            message.setSummary("Email invalido!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        else if (findEmail(email)) {
        	FacesMessage message = new FacesMessage();
            message.setDetail("E-mail já cadastrado entre com um email valido...");
            message.setSummary("Email invalido!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    public boolean findEmail(String email) {
    	boolean status = false;
		EntityManager em = JPA.getEM();
		TypedQuery<Usuario> query = em.createQuery("Select u from Usuario u where u.email = :email",
				Usuario.class);
		query.setParameter("email", email);
		if  (query.getResultList().size()!=0)
			status = true;
		return status;
	}
}