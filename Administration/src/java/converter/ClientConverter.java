/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import converter.struct.EntityConverter;
import bean.facade.ClientFacade;
import entity.Client;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Brian GOHIER
 */
@FacesConverter("clientConverter")
public class ClientConverter extends EntityConverter<Client, ClientFacade>
{
    @EJB
    private ClientFacade clientFacade;

    public ClientConverter() {
        super();
    }
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        super.entityFacade=this.clientFacade;
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        super.entityFacade=this.clientFacade;
        return super.getAsString(context, component, value);
    }
}
