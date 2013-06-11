/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.Utils;
import bean.facade.abstracts.AbstractEmbdedDataList;
import java.lang.reflect.Method;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EmbdedDataListView<C,O,F extends AbstractEmbdedDataList<C,O>> extends EntityView<O,F>
{
    private Method setReferenceMethod;

    public EmbdedDataListView() {
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
    
    public String create(C entity)
    {
        this.setFacade();
        super.getEntityFacade().addToDataList(entity,this.getInstance());
        return "list";
    }
    
    public String remove(O[] instances, C entity)
    {
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
    public abstract String getDeleteMessages();
    
    // </editor-fold>
}
