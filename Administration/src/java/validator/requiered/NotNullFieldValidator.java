/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.requiered;

import bean.ApplicationLogger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brian GOHIER
 */
@FacesValidator("notNullFieldValidator")
@ManagedBean
@RequestScoped
public class NotNullFieldValidator implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component,
        Object value) throws ValidatorException
    {
        String requieredTitle=(String) component.getAttributes().get("requieredTitle");
        String requieredMessage=(String) component.getAttributes().get("requieredMessage");
        
        if(requieredTitle==null || requieredTitle.isEmpty()
            || requieredMessage==null || requieredMessage.isEmpty() )
        {
            ApplicationLogger.writeWarning("Dans le validateur \""+this.getClass().getName()+
                    "\". Vous devez spécifier un attribut \"requieredTitle\" pour "
                    + "ce validateur ainsi qu'un attribut \"requieredMessage\". "
                    + "Exemple: <f:attribute name=\"requieredTitle\" value=\"Champs invalide\" />"
                    + "pour titre \"Champs invalide\" et "
                    + "<f:attribute name=\"requieredMessage\" value=\"Le champs est vide\" /> "
                    + "message \"Le champs est vide\".");
            return;
        }
        
        if(value==null || (value.getClass()==String.class && ((String)value).isEmpty()))
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    requieredTitle,
                    requieredMessage);
            throw new ValidatorException(message);
        }
    }
    
}
