package converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("stringToListConverter")
public class StringToStringList implements Converter {
	 @Override
	    public Object getAsObject(FacesContext arg0, UIComponent arg1, String key) {
		 	List<String> aux = new ArrayList<String>();
			
			for (String retval: key.split(","))
			    	  aux.add((retval.trim()));
	 		return aux;
	    }
	 
	    @Override
	    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
	    	@SuppressWarnings("unchecked")
			List<String> aux = (List<String>) arg2;
	    	String habilidades = "";
	    	if (aux != null) {
	    		for (int i=0; i<aux.size(); i++){
	    			if (!aux.get(i).equals("")){
	    					if (i==0)
	    						habilidades = aux.get(i);
	    					else if (i < aux.size())
	    						habilidades = habilidades + ", " + aux.get(i) + " ";
	    					else 
	    						habilidades = habilidades + ", " +aux.get(i);
	    			}
	    		}
	    	return habilidades;
	    	}
	    	return "";
	    }
}

