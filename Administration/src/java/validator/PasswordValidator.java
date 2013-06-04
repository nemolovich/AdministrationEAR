/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Integer update_id = null;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        String password = (String) value;

        UIInput confirmComponent = (UIInput) component.getAttributes().get("confirm");
        String confirm = (String)confirmComponent.getSubmittedValue();
        this.update_id = (Integer)component.getAttributes().get("update_id");
        Boolean passwordMinLength = (Boolean)component.getAttributes().get("min_length");
        if(passwordMinLength==null)
        {
            String warning="La taille minimum du mot de passe n'est pas requise dans "+
                    "le validateur '"+PasswordValidator.class.getName()+"'";
            Logger.getLogger(PasswordValidator.class.getName()).log(Level.WARNING, 
                    warning);
            passwordMinLength=false;
        }
        
        if(confirm==null)
        {
            System.err.println("Dans le validateur '"+this.getClass().getName()+
                    "'. Vous devez spécifier un champs avec l'id 'confirm'. "
                    + "Exemple: <h:inputSecret id=\"confirm\" binding=\"#{confirm}\"/> "
                    + "si vous ajouter e attribut pour ce validateur: "
                    + "<f:attribute name=\"confirm\" value=\"#{confirm}\" />");
            return;
        }
        
        if(this.update_id==null)
        {
            System.err.println("Dans le validateur '"+this.getClass().getName()+
                    "'. Vous devez spécifier un attribut 'update_id' pour ce validateur. "
                    + "Exemple: <f:attribute name=\"update_id\" value=\"1\" /> "
                    + "pour la mise à jour de l'utilisateur ayant "
                    + "pour identifiant 1 (-1 pour la création).");
            return;
        }
        
        FacesMessage message1=new FacesMessage("Mot de passe incorrect",
                "Le mot de passe doit contenir entre 8 et 32 caractères");
        message1.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesMessage message2=new FacesMessage("Mot de passe non confirmé",
                "Veuillez confirmer le mot de passe");
        message2.setSeverity(FacesMessage.SEVERITY_ERROR);
        
//        System.err.println("Status: ID="+this.update_id+" - password='"+password+"' - confirmation='"+
//                confirm+"' minLength="+passwordMinLength);

        if (this.update_id==-1 && (password == null || (passwordMinLength &&
                (password.isEmpty() || password.length()<8)) ||
                password.length()>32))
        {
            throw new ValidatorException(message1);
        }
        
        if(this.update_id==-1 && (confirm == null || confirm.isEmpty()) &&(passwordMinLength))
        {
            confirmComponent.setValid(false);
            throw new ValidatorException(message2);
        }
        
        if(this.update_id!=-1 && (confirm == null || confirm.isEmpty()) && password.isEmpty())
        {
            Logger.getLogger(PasswordValidator.class.getName()).log(Level.WARNING, 
                    "Le mot de passe n'a pas été modifié");
        }
        else
        {
            if(password == null || (passwordMinLength &&
                (password.isEmpty() || password.length()<8)) ||
                password.length()>32)
            {
                throw new ValidatorException(message1);
            }
            if((confirm == null || confirm.isEmpty()) && passwordMinLength)
            {
                confirmComponent.setValid(false);
                throw new ValidatorException(message2);
            }
        }
        if (!password.equals(confirm)&&!password.isEmpty())
        {
            confirmComponent.setValid(false);
            FacesMessage message=new FacesMessage("Confirmation du mot de passe échouée",
                    "Les mots de passes ne correspondent pas");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}