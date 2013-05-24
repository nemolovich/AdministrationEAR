/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.UserLogin;
import bean.facade.TUserFacade;
import entity.TUser;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
 * @author Windows 7
 */
@FacesValidator("userLoginValidator")
@ManagedBean
@RequestScoped
public class UserLoginValidator implements Validator
{
    @EJB
    private TUserFacade tUserFacade;
    @EJB
    private UserLogin userLogin;

    public UserLoginValidator()
    {
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        String mail = (String) value;
        UIInput passwordComponent = (UIInput) component.getAttributes().get("password");
        String password = (String)passwordComponent.getSubmittedValue();

        if (mail == null || mail.isEmpty())
        {
            FacesMessage message=new FacesMessage("Veuillez entrer votre adresse mail");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        if (password == null || password.isEmpty())
        {
            FacesMessage message=new FacesMessage("Veuillez entrer votre mot de passe");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
        
        List<TUser> users;
        users = this.tUserFacade.findAll();
        for(TUser user:users)
        {
            if(user.getMail().equals(mail))
            {
                MessageDigest md;
                try
                {
                    md = MessageDigest.getInstance("MD5");
                }
                catch (NoSuchAlgorithmException ex)
                {
                    Logger.getLogger(UserLoginValidator.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                md.update(password.getBytes(/*"UTF-8"*/));
                byte[] md5 = md.digest();
                String passwordEncrypted=new String(md5/*,"UTF-8"*/);
                
                if(!user.getPassword().equals(passwordEncrypted))
                {
                    passwordComponent.setValid(false);
                    FacesMessage message=new FacesMessage("Votre mot de passe est incorrect");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
                
                this.userLogin.setUser(user);
                return;
            }
        }
        FacesMessage message=new FacesMessage("Votre adresse mail n'a pas été reconnue");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}