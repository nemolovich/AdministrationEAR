/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.struct;

import bean.Utils;
import bean.facade.abstracts.AbstractEmbdedDataList;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EmbdedDataListView<C,O,F extends AbstractEmbdedDataList<C,O>> extends EntityView<O,F>
{
    private Method setReferenceMethod;

    public EmbdedDataListView()
    {
    }    

    public EmbdedDataListView(Class<O> entityClass,String webFolder,
            Method setReferenceMethod)
    {
        super(entityClass, webFolder);
        this.setReferenceMethod = setReferenceMethod;
    }
    
    public String entityCreate(C entity)
    {
        super.entityCreate();
        Utils.callMethod(this.setReferenceMethod,
                "méthode de récupération des données",this.getInstance(),
                entity);
        return "create";
    }
    
    @SuppressWarnings("unchecked")
    public String entityDelete(C entity, O instance)
    {
        this.setFacade();
        super.getEntityFacade().removeToDataList(entity,instance);
        return "list";
    }
    
    public String create(C entity)
    {
        this.setFacade();
        super.getEntityFacade().addToDataList(entity,this.getInstance());
        return "list";
    }
    
    public String update(C entity)
    {
        this.setFacade();
        super.getEntityFacade().updateToDataList(entity,this.getInstance());
        return "list";
    }
    
    public String remove(C entity, O... instances)
    {
        if(instances==null)
        {
            FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Sélection invalide",
                    "Vous devez sélectionner au moins un élément "
                    + "pour effectuer cette tâche");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        this.setFacade();
        super.getEntityFacade().removeToDataList(entity,instances);
        return "list";
    }
    
    // <editor-fold defaultstate="collapsed" desc="Les methodes abstraites">
    /**
     * Insérer le code:
     * <code>
     * super.entityFacade=this.[VotreEJBFacade];
     * </code>
     */
    @Override
    public abstract void setFacade();
    /**
     * Renvoi le message d'avertissement avant la suppression
     * de toutes ces entités sélectionnées
     * @return {@link String}, Le message d'avertissement
     */
    public abstract String deleteMessages(List<O> entities);
    
    // </editor-fold>
}
