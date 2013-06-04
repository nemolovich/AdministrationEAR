/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Brian GOHIER
 */
public abstract class AbstractFacade<T>
{
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity)
    {
        getEntityManager().persist(entity);
        FacesMessage message=new FacesMessage("Ajout de la donnée réussie",
                "L'enregistrement s'est correctement déroulé");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void edit(T entity)
    {
        getEntityManager().merge(entity);
        FacesMessage message=new FacesMessage("Modification de la donnée réussie",
                "La mise-à-jour s'est correctement déroulée");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void remove(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
        FacesMessage message=new FacesMessage("Supression de la donnée réussie",
                "La suppression s'est correctement déroulée");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public T find(Object id)
    {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        javax.persistence.criteria.CriteriaQuery cq;
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findRange(int[] range)
    {
        javax.persistence.criteria.CriteriaQuery cq;
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public int count()
    {
        javax.persistence.criteria.CriteriaQuery cq;
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
