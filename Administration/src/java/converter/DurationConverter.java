/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Brian GOHIER
 */
@ManagedBean
@RequestScoped
@FacesConverter("durationConverter")
public class DurationConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
    String value)
    {
        String duration=value;
        return duration.substring(0, 2)+" h "+duration.substring(3, 5)+" mins";
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
    Object value)
    {
        String duration=((String)value).substring(0, 2)+":"+((String)value).substring(5, 7);
        return duration;
    }
    
}
