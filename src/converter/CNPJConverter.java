package converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cnpjConverter")
public class CNPJConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		String c = arg2.replaceAll("\\D", "");
		if (c != "") {
			Long l = Long.parseLong(c);
			return l;
		}
		
		return arg2;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String t = String.valueOf((Long) arg2);
		return t.substring(0, 2) + "." + t.substring(2, 5) + "." + t.substring(5, 8) + "/" + t.substring(8,12) + "-" + t.substring(12);
	}

}
