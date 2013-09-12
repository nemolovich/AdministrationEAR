/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.unique;

import bean.facade.CUserFacade;
import entity.CUser;
import entity.Client;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import validator.unique.struct.UniqueDependantFieldValidator;

/**
 *
 * @author Brian GOHIER
 */
@FacesValidator("cUserUniqueFieldValidator")
public class CUserUniqueFieldValidator extends
        UniqueDependantFieldValidator<Client, CUser, CUserFacade> implements Validator
{
    @EJB
    private CUserFacade cUserFacade;

    public CUserUniqueFieldValidator()
    {
        super(Client.class, CUser.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.cUserFacade;
        super.validate(context, component, value);
    }
}