/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.TUserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Brian GOHIER
 */
@FacesValidator("passwordValidator")
@ManagedBean
@RequestScoped
public class PasswordValidator implements Validator
{

    @EJB
    private TUserFacade tUserFacade;
    private Integer update_id = null;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        String password = (String) value;

        UIInput confirmComponent = (UIInput) component.getAttributes().get("confirm");
        String confirm = (String)confirmComponent.getSubmittedValue();
        this.update_id = (Integer)component.getAttributes().get("update_id");
        
        if(this.update_id==null)
        {
            System.err.println("Dans le validateur '"+this.getClass().getName()+
                    "'. Vous devez spécifier un attribut 'update_id' pour ce validateur. "
                    + "Exemple: <f:attribute name=\"update_id\" value=\"1\" /> pour la mise à jour de l'utilisateur ayant "
                    + "pour identifiant 1 (-1 pour la création).");
            return;
        }
        
        FacesMessage message1=new FacesMessage("Mot de passe incorrect","Le mot de passe doit contenir entre 8 et 32 caractères");
        message1.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesMessage message2=new FacesMessage("Mot de passe non confirmé","Veuillez confirmer le mot de passe");
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);

        if (this.update_id==-1 && (password == null || password.isEmpty() ||
                password.length()<8 || password.length()>32))
        {
            
            throw new ValidatorException(message1);
        }
        
        if(this.update_id==-1 && (confirm == null || confirm.isEmpty()))
        {
            confirmComponent.setValid(false);
            
            throw new ValidatorException(message2);
        }
        
        if(this.update_id!=-1 && confirm.isEmpty() && password.isEmpty())
        {
            Logger.getLogger(PasswordValidator.class.getName()).log(Level.WARNING, "Le mot de passe n'a pas été modifié");
        }
        else
        {
            if(password == null || password.isEmpty() ||
                password.length()<2 || password.length()>32)
            {
                throw new ValidatorException(message1);
            }
            if(confirm == null || confirm.isEmpty())
            {
                confirmComponent.setValid(false);
                throw new ValidatorException(message2);
            }
        }
        if (!password.equals(confirm))
        {
            confirmComponent.setValid(false);
            FacesMessage message=new FacesMessage("Confirmation du mot de pass échouée","Les mots de passes ne correspondent pas");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}