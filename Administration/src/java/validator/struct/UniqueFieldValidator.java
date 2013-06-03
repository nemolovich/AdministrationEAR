/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import bean.facade.abstracts.AbstractFacade;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Windows 7
 */
@ManagedBean
@RequestScoped
public abstract class UniqueFieldValidator<C, F extends AbstractFacade<C>> implements Validator
{
    private Class<C> entityClass;
    protected F entityFacade;
    private String fieldName = null;
    private Integer update_ID = null;

    public UniqueFieldValidator()
    {
    }
    
    public UniqueFieldValidator(Class<C> entityClass)
    {
        this.entityClass = entityClass;
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
        
        List<C> entities;
        entities = this.entityFacade.findAll();
        Method m, getId = null;
        try
        {
            m=this.entityClass.getMethod("get"+fieldName);
            getId=this.entityClass.getMethod("getId");
        }
        catch (NoSuchMethodException ex)
        {
            Logger.getLogger(CUserUniqueFieldValidator.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        catch (SecurityException ex)
        {
            Logger.getLogger(CUserUniqueFieldValidator.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        for(C entity:entities)
        {
            try
            {
                boolean equals=false;
                if(m.getReturnType()==String.class)
                {
                    equals=((String)m.invoke(entity)).equalsIgnoreCase(field);
                }
                if((equals||m.invoke(entity).equals(field))&&((Integer)getId.invoke(entity))!=this.update_ID)
                {
                    FacesMessage message=new FacesMessage("Champs '"+fieldName+"' incorrect",
                            "La valeur '"+field+"' pour le champs '"+this.fieldName+
                            "' est déjà utilisé par une autre entrée");
                    message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(message);
                }
            }
            catch (IllegalAccessException ex)
            {
                Logger.getLogger(CUserUniqueFieldValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalArgumentException ex)
            {
                Logger.getLogger(CUserUniqueFieldValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex)
            {
                Logger.getLogger(CUserUniqueFieldValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(this.fieldName.equalsIgnoreCase("Mail"))
        {
            EmailPatternValidator mailValidator = new EmailPatternValidator();
            mailValidator.validate(context, component, value);
        }
    }
}