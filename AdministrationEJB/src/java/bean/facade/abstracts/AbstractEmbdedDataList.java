/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import bean.Utils;
import java.lang.reflect.Method;
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
    public void addToDataList(C entity, O instance)
    {
        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
                "méthode de récupération des données",entity);
        list.add(instance);
        Utils.callMethod(this.setDataListMethod,
                "méthode de paramétrage des données",entity,list);
        this.em.persist(instance);
        this.em.merge(entity);
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Ajout de la donnée réussi",
                "La donnée a bien été ajoutée dans la liste");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void updateToDataList(C entity, O instance)
    {
//        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
//                "méthode de récupération des données",entity);
//        list.add(instance);
//        Utils.callMethod(this.setDataListMethod,
//                "méthode de paramétrage des données",entity,list);
        this.em.merge(instance);
        this.em.merge(entity);
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Modification de la donnée réussi",
                "La donnée a bien été modifiée dans la liste");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @SuppressWarnings("unchecked")
    public void removeToDataList(C entity, O... instances)
    {
        if(instances==null || instances.length==0)
        {
            return;
        }
        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
                "méthode de récupération des données",entity);
        for(O instance:instances)
        {
            list.remove(instance);
        }
        Utils.callMethod(this.setDataListMethod,
                "méthode de paramétrage des données",entity,list);
        boolean unique = instances.length==1;
        for(O instance:instances)
        {
            this.em.remove(this.em.merge(instance));
        }
        String s=unique?"":"s";
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Suppression "+(unique?"de la":"des")+" donnée"+s+" réussie",
                (unique?"La":"Les")+" donnée"+s+" "+(unique?"a":"ont")+
                " bien été supprimée"+s+" de la liste");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.em.merge(entity);
    }
}