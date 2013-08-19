/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.facade.DeviceFacade;
import converter.struct.EntityConverter;
import entity.Device;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Brian GOHIER
 */
@FacesConverter("deviceConverter")
public class DeviceConverter extends EntityConverter<Device, DeviceFacade>
{
    @EJB
    private DeviceFacade deviceFacade;

    public DeviceConverter() {
        super();
    }
    
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        super.entityFacade=this.deviceFacade;
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        super.entityFacade=this.deviceFacade;
        return super.getAsString(context, component, value);
    }
}
