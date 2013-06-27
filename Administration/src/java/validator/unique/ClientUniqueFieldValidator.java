/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.unique;

import bean.facade.ClientFacade;
import entity.Client;
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
@FacesValidator("clientUniqueFieldValidator")
public class ClientUniqueFieldValidator extends UniqueFieldValidator<Client, ClientFacade>implements Validator
{
    @EJB
    private ClientFacade clientFacade;
    
    public ClientUniqueFieldValidator()
    {
        super(Client.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.clientFacade;
        super.validate(context, component, value);
    }
}