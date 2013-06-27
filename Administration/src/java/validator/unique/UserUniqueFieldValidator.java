/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.unique;

import bean.facade.TUserFacade;
import entity.TUser;
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
@FacesValidator("userUniqueFieldValidator")
public class UserUniqueFieldValidator extends UniqueFieldValidator<TUser, TUserFacade>implements Validator
{
    @EJB
    private TUserFacade tUserFacade;
    
    public UserUniqueFieldValidator()
    {
        super(TUser.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.tUserFacade;
        super.validate(context, component, value);
    }
}