package converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("statusConverter")
public class StatusSelectConverter implements Converter{  
		  
        public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) throws ConverterException {  
            Integer resultado = null;  
            List<String> diasDaSemaa = new ArrayList<String>();

	    		diasDaSemaa.add("Domingo-Feira");	
	            diasDaSemaa.add("Segunda-Feira");
	    		diasDaSemaa.add("Terça-Feira");
	    		diasDaSemaa.add("Quarta-Feira");
	    		diasDaSemaa.add("Quinta-Feira");
	    		diasDaSemaa.add("Sexta-Feira");
	    		diasDaSemaa.add("Sabado-Feira");
	    		
            try {  
            	for (int i=0; i<diasDaSemaa.size(); i++){
            		if (valor.equals(diasDaSemaa.get(i)))
                        resultado = i+1;
            	}
            	 
            } catch (NumberFormatException nfe) {  
                throw new ConverterException(valor + " não é um número válido!", nfe);  
            }  
            return resultado;  
        }     
  
  
        public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) throws ConverterException {  
              
            String resultado ="";  
             if (obj != null) {  
                 resultado = obj.toString();  
                        }  
            return resultado;  
            }   
  } 

	