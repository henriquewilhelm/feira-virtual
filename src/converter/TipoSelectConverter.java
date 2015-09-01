package converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import models.IConvertible;
import models.Tipo;
@FacesConverter("tipoConverter")
public class TipoSelectConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Tipo ret = null;
        UIComponent src = arg1;
        if (src != null) {
            List<UIComponent> childs = src.getChildren();
            UISelectItems itens = null;
            if (childs != null) {
                for (UIComponent ui : childs) {
                    if (ui instanceof UISelectItems) {
                        itens = (UISelectItems) ui;
                        break;
                    } else if (ui instanceof UISelectItem) {
                        UISelectItem item = (UISelectItem) ui;
                        try {
                            IConvertible val = (IConvertible) item.getItemValue();
                            if (arg2.equals("" + val.getId())) {
                                ret = (Tipo) val;
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            if (itens != null) {
                List<Tipo> values = (List<Tipo>) itens.getValue();
                if (values != null) {
                    for (Tipo val : values) {
                        if (arg2.equals("" + val.getId())) {
                            ret = val;
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String ret = "";
        if (arg2 != null && arg2 instanceof Tipo) {
        	Tipo m = (Tipo) arg2;
            if (m != null) {
                Integer id = m.getId();
                if (id != null) {
                    ret = id.toString();
                }
            }
        }
        return ret;
    }
}
	