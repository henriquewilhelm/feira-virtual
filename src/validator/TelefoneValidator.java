package validator;
import java.util.regex.*;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.*;
 
@FacesValidator("telefoneValidator") 
public class TelefoneValidator implements Validator
{
 //"\\d{2}-\\d{4}-\\d{4}$";
    private static final String TELEFONE_REGEXP =
    "\\(?[0-9]{2}\\)?[0-9]{4}-?[0-9]{4}";
   
    @Override
    public void validate(FacesContext context, UIComponent c, Object val) throws ValidatorException
    {
        String telefone = String.valueOf( val );
        Pattern mask = null;
        mask = Pattern.compile(TELEFONE_REGEXP);
        Matcher matcher = mask.matcher(telefone);
        FacesMessage message = new FacesMessage();
        if (!matcher.matches()) {
            message.setDetail("Por favor entre com um telefone valido. Ex: (48) XXXX-XXXX...");
            message.setSummary("Telefone invalido!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}