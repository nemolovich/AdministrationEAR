/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.Utils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Brian GOHIER
 */
@ManagedBean
@RequestScoped
public class YesNoConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        return (Boolean)(value.equalsIgnoreCase(Utils.YES_BUTTON)?true:
                (value.equalsIgnoreCase(Utils.NO_BUTTON)?false:null));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        return ((String)value).equalsIgnoreCase(Utils.YES_BUTTON)?Utils.YES_BUTTON:
                (((String)value).equalsIgnoreCase(Utils.NO_BUTTON)?Utils.NO_BUTTON:null);
    }
    
}
