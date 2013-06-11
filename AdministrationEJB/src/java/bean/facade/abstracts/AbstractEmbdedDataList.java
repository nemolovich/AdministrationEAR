/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import bean.Utils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class AbstractEmbdedDataList<C,O> extends AbstractFacade<O>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;
    private Method getDataListMethod;
    private Method setDataListMethod;
    
    public AbstractEmbdedDataList(Class<O> objectClass,
            Method getDataListMethod, Method setDataListMethod)
    {
        super(objectClass);
        this.getDataListMethod = getDataListMethod;
        this.setDataListMethod = setDataListMethod;
    }
    
    @SuppressWarnings("unchecked")
    public void addToDataList(C entity, O add)
    {
        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
                "méthode de récupération des données",entity);
        list.add(add);
        Utils.callMethod(this.setDataListMethod,
                "méthode de paramétrage des données",entity,list);
        this.em.persist(add);
        this.em.merge(entity);
        FacesMessage message=new FacesMessage("Ajout de la donnée réussi",
                "La donnée a bien été ajoutée dans la liste");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @SuppressWarnings("unchecked")
    public void removeToDataList(C entity, O[] instances)
    {
        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
                "méthode de récupération des données",entity);
        for(O instance:instances)
        {
            list.remove(instance);
        }
        Utils.callMethod(this.setDataListMethod,
                "méthode de paramétrage des données",entity,list);
        for(O instance:instances)
        {
            this.em.remove(this.em.merge(instance));
        }
        this.em.merge(entity);
        FacesMessage message=new FacesMessage("Suppression de la donnée réussie",
                "La donnée a bien été supprimée de la liste");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}