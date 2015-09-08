package converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import models.IConvertible;
import models.Categoria;
@FacesConverter("categoriaConverter")
public class CategoriaSelectConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Categoria ret = null;
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
                                ret = (Categoria) val;
                                break;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            if (itens != null) {
                List<Categoria> values = (List<Categoria>) itens.getValue();
                if (values != null) {
                    for (Categoria val : values) {
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
        if (arg2 != null && arg2 instanceof Categoria) {
        	Categoria m = (Categoria) arg2;
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
	