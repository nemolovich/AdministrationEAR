/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.view.TaskView;
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
@FacesConverter("stateConverter")
public class StateConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
    String value)
    {
        System.err.println("str: "+value+", "+value.getClass().getName()+" ("+
                value.equalsIgnoreCase("true")+")");
        return Boolean.valueOf(value).toString();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
    Object value)
    {
        System.err.println("obj: "+value+", "+value.getClass().getName());
        return ((String)value).equals(TaskView.TASK_STATE[1])?"true":"false";
    }
    
}
