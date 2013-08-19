/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.DeviceFacade;
import entity.Device;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import validator.unique.struct.UniqueFieldValidator;

/**
 *
 * @author Brian GOHIER
 */
@FacesValidator("deviceUniqueFieldValidator")
public class DeviceUniqueFieldValidator extends UniqueFieldValidator<Device, DeviceFacade>implements Validator
{
    @EJB
    private DeviceFacade deviceFacade;
    
    public DeviceUniqueFieldValidator()
    {
        super(Device.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.deviceFacade;
        super.validate(context, component, value);
    }
}