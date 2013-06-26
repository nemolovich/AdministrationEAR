/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import bean.ApplicationLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private String getFullString(T entity)
    {
        try
        {
            Method getFullString=this.entityClass.getMethod("getFullString");
            String fullString=(String) getFullString.invoke(entity);
            String spacer="\t";
            for(int i=0;i<this.entityClass.getName().length()*2+5;i++)
            {
                spacer+=" ";
            }
            fullString=fullString.replaceAll(", ", 
                                    ",\n"+spacer)
                    .replaceAll("}", "\n"+spacer+"}");
            return fullString;
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.displayError("Impossible de trouver la méthode \""+
                    "getFullString\" pour la classe \""+this.entityClass.getName()+
                    "\"", ex);
        }
        catch (SecurityException ex)
        {
            ApplicationLogger.displayError("Accès refusé à la méthode \""+
                    "getFullString\" pour la classe \""+this.entityClass.getName()+
                    "\"", ex);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.displayError("Impossible d'accéder à la méthode \""+
                    "getFullString\" pour la classe \""+this.entityClass.getName()+
                    "\"", ex);
        }
        catch (IllegalArgumentException ex)
        {
            ApplicationLogger.displayError("Arguments invalides pour la méthode \""+
                    "getFullString\" pour la classe \""+this.entityClass.getName()+
                    "\"", ex);
        }
        catch (InvocationTargetException ex)
        {
            ApplicationLogger.displayError("Impossible d'appeler à la méthode \""+
                    "getFullString\" pour la classe \""+this.entityClass.getName()+
                    "\"", ex);
        }
        return null;
    }

    public void create(T entity)
    {
        getEntityManager().persist(entity);
        String details=this.getFullString(entity);
        details=details!=null?details:entity.toString();
        ApplicationLogger.writeWarning("Création de l'entité de la classe \""+
                this.entityClass.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+this.entityClass.getName()+"\": \""+
                details+"\"");
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Ajout de la donnée réussie",
                "L'enregistrement s'est correctement déroulé");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void edit(T entity)
    {
        getEntityManager().merge(entity);
        String details=this.getFullString(entity);
        details=details!=null?details:entity.toString();
        ApplicationLogger.addSmallSep();
        ApplicationLogger.writeWarning("Modification de l'entité de la classe \""+
                this.entityClass.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+this.entityClass.getName()+"\": \""+
                details+"\"");
        ApplicationLogger.addSmallSep();
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Modification de la donnée réussie",
                "La mise-à-jour s'est correctement déroulée");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void remove(T entity)
    {
        T temp=entity;
        String details=this.getFullString(temp);
        details=details!=null?details:temp.toString();
        getEntityManager().remove(getEntityManager().merge(entity));
        ApplicationLogger.writeWarning("Suppression de l'entité de la classe \""+
                this.entityClass.getName()+"\" réussie");
        ApplicationLogger.write("\tObjet: \""+this.entityClass.getName()+"\": \""+
                details+"\"\r");
        FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Supression de la donnée réussie",
                "La suppression s'est correctement déroulée");
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
