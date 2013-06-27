/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.unique;

import bean.facade.MailFacade;
import entity.Mail;
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
@FacesValidator("mailUniqueFieldValidator")
public class MailUniqueFieldValidator extends UniqueFieldValidator<Mail, MailFacade>implements Validator
{
    @EJB
    private MailFacade mailFacade;
    
    public MailUniqueFieldValidator()
    {
        super(Mail.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.mailFacade;
        super.validate(context, component, value);
    }
}