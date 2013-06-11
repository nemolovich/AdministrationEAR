/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.WorkstationFacade;
import entity.Workstation;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import validator.struct.UniqueFieldValidator;

/**
 *
 * @author Brian GOHIER
 */
@FacesValidator("workstationUniqueFieldValidator")
public class WorkstationUniqueFieldValidator extends UniqueFieldValidator<Workstation, WorkstationFacade>implements Validator
{
    @EJB
    private WorkstationFacade workstationFacade;
    
    public WorkstationUniqueFieldValidator()
    {
        super(Workstation.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.workstationFacade;
        super.validate(context, component, value);
    }
}