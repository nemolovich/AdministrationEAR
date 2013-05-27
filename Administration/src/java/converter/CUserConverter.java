/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.facade.CUserFacade;
import converter.struct.EntityConverter;
import entity.CUser;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Windows 7
 */
@FacesConverter("cUserConverter")
public class CUserConverter extends EntityConverter<CUser, CUserFacade>
{
    @EJB
    private CUserFacade cUserFacade;

    public CUserConverter() {
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        super.entityFacade=this.cUserFacade;
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        super.entityFacade=this.cUserFacade;
        return super.getAsString(context, component, value);
    }
}
