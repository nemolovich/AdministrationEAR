/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.TUserFacade;
import entity.TUser;
import java.util.List;
import javax.ejb.EJB;
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
 * @author Windows 7
 */
@FacesValidator("userUniqueFieldValidator")
@ManagedBean
@RequestScoped
public class UserUniqueFieldValidator implements Validator
{
    @EJB
    private TUserFacade tUserFacade;
    private String fieldName = null;
    private Integer update_ID = null;

    public UserUniqueFieldValidator()
    {
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        String field = (String) value;
        
        this.fieldName=(String) component.getAttributes().get("fieldName");
        this.update_ID=(Integer) component.getAttributes().get("update_id");
        
        if(this.fieldName==null || this.fieldName.isEmpty()
            || this.update_ID==null )
        {
            System.err.println("Dans le validateur '"+this.getClass().getName()+
                    "'. Vous devez spécifier un attribut 'fieldName' pour ce validateur ainsi qu'un attribut 'update_id'. "
                    + "Exemple: <f:attribute name=\"fieldName\" value=\"Mail\" /> pour un champs 'Mail' et "
                    + "<f:attribute name=\"update_id\" value=\"1\" /> pour la mise à jour de l'utilisateur ayant "
                    + "pour identifiant 1 (-1 pour la création).");
            return;
        }

        if (field == null || field.isEmpty())
        {
            return;
        }
        
        List<TUser> users;
        users = this.tUserFacade.findAll();
        for(TUser user:users)
        {
            if(user.getMail().equals(field)&&user.getId()!=this.update_ID)
            {
                FacesMessage message=new FacesMessage("Le champs '"+this.fieldName+"' est déjà utilisé par une autre entrée");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
        if(this.fieldName.equalsIgnoreCase("Mail"))
        {
            EmailPatternValidator mailValidator = new EmailPatternValidator();
            mailValidator.validate(context, component, value);
        }
    }
}