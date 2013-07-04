/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.facade.WorkstationFacade;
import converter.struct.EntityConverter;
import entity.Workstation;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Brian GOHIER
 */
@FacesConverter("workstationConverter")
public class WorkstationConverter extends EntityConverter<Workstation, WorkstationFacade>
{
    @EJB
    private WorkstationFacade workstationFacade;

    public WorkstationConverter() {
        super();
    }
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        super.entityFacade=this.workstationFacade;
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        super.entityFacade=this.workstationFacade;
        return super.getAsString(context, component, value);
    }
}
