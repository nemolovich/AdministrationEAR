/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.unique;

import bean.facade.FactureFacade;
import entity.Facture;
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
@FacesValidator("factureUniqueFieldValidator")
public class FactureUniqueFieldValidator extends UniqueFieldValidator<Facture, FactureFacade>implements Validator
{
    @EJB
    private FactureFacade factureFacade;
    
    public FactureUniqueFieldValidator()
    {
        super(Facture.class);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException
    {
        super.entityFacade = this.factureFacade;
        super.validate(context, component, value);
    }
}