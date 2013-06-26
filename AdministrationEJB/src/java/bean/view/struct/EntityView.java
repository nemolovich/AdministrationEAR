/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.struct;

import bean.ApplicationLogger;
import bean.Utils;
import bean.facade.abstracts.AbstractFacade;
import entity.TUser;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntityView<C,F extends AbstractFacade<C>> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private C entity;
    private Class<C> entityClass;
    private String webFolder=null;
    private F entityFacade;
    private boolean creating=false;
    private boolean editing=false;
    public static final SelectItem[] SLEEPING_STATES={
                    new SelectItem("","Les deux","Affiche les éléments actifs et innactifs"),
                    new SelectItem("false","Actif","Affiche les éléments actifs"),
                    new SelectItem("true","Inactif","Affiche les éléments innactifs")
                };
    private List<C> filteredEntities;
    private boolean displaySleepingEntities=false;
    
    public EntityView()
    {
    }
    
    public EntityView(Class<C> entityClass,String webFolder)
    {
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
        this.entityClass=entityClass;
    }
    
    public void switchDisplaySleepingEntities()
    {
        this.getFilteredEntities();
    }

    public boolean isDisplaySleepingEntities()
    {
        return displaySleepingEntities;
    }

    public void setDisplaySleepingEntities(boolean displaySleepingEntities)
    {
        this.displaySleepingEntities = displaySleepingEntities;
    }
    
    private Boolean isSleepingCall(C entity)
    {
        Method m;
        try
        {
            m=this.entityClass.getMethod("getSleeping");
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"getSleeping\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"", ex);
            return null;
        }
        catch (SecurityException ex)
        {
            ApplicationLogger.writeError("La méthode \"getSleeping\" n'est pas"+
                    " accessible pour la classe \""+TUser.class.getName()+"\"", ex);
            return null;
        }
        catch (NullPointerException ex)
        {
            ApplicationLogger.writeError("La méthode \"getSleeping\" renvoi \"null\""
                    + " pour la classe '"+TUser.class.getName()+"'", ex);
            return null;
        }
        return (Boolean)Utils.callMethod(m, "récupération de l'état de veille", entity);
    }
    
    private List<C> getSleepEntities(boolean sleeping)
    {
        this.setFacade();
        List<C> res=new ArrayList<C>();
        for(C c:this.findAll())
        {
            if(this.isSleepingCall(c)==sleeping)
            {
                res.add(c);
            }
        }
        return res;
    }
  
    public SelectItem[] getSleepingOptions()
    {
        return EntityView.SLEEPING_STATES;
    }
    
    public List<C> getFilteredEntities()
    {
        List<C> temp=this.filteredEntities!=null?
                new ArrayList<C>(this.filteredEntities):
                new ArrayList<C>();
        if(!this.displaySleepingEntities&&this.filteredEntities!=null)
        {
            try
            {
                for(C c:temp)
                {
                    if(c!=null&&this.isSleepingCall(c))
                    {
                        this.filteredEntities.remove(c);
                    }
                }
                temp.clear();
            }
            catch (ConcurrentModificationException ex)
            {
                ApplicationLogger.writeError("Accès concurrent à la méthode"
                        + " \"getFilteredEntities\" pour la liste des données de"
                        + " la classe \""+this.entityClass.getName()+"\"", ex);
            }
        }
        return this.filteredEntities;
    }

    public void setFilteredEntities(List<C> filteredEntities)
    {
        this.filteredEntities = filteredEntities;
    }

    public boolean isEditing()
    {
        return editing;
    }

    public void setEditing(boolean editing)
    {
        this.editing = editing;
    }
    
    public boolean isCreating()
    {
        return this.creating;
    }

    public void setCreating(boolean creating)
    {
        this.creating = creating;
    }
    
    protected void setWebFolder(String webFolder)
    {
        this.webFolder=webFolder;
    }
    
    public String create()
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.create(this.entity);
        return this.webFolder+"list";
    }
    
    public String delete()
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.remove(this.entity);
        this.entity=null;
        return this.webFolder+"list";
    }
    
    public String update()
    {
        this.creating=false;
        this.editing=false;
        this.setFacade();
        this.entityFacade.edit(this.entity);
        return this.webFolder+"list";
    }

    public String entityView(C entity)
    {
        this.creating = false;
        this.editing = false;
        this.entity = entity;
        return this.webFolder+"view";
    }
    
    public C checkSingle(C[] entities)
    {
        if(entities!=null&&entities.length!=1)
        {
            FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Sélection invalide",
                    "Vous devez sélectionner un et un seul élément "
                    + "pour effectuer cette tâche");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        return entities[0];
    }
    
    public String entityUpdate(C entity)
    {
        this.creating = false;
        this.editing = true;
        this.entity = entity;
        return this.webFolder+"update";
    }
    
    public String entityParameter(C entity)
    {
        this.creating = false;
        this.editing = true;
        this.entity = entity;
        return this.webFolder+"parameters";
    }
    
    public String entityDelete(C entity)
    {
        this.creating = false;
        this.editing = false;
        this.setFacade();
        this.entityFacade.remove(entity);
        return this.webFolder+"list";
    }
    
    public String entitySleep(C entity)
    {
        try
        {
            Method m=entity.getClass().getMethod("setSleeping", Boolean.class);
            Utils.callMethod(m, "mise en veille de la donnée", entity, true);
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"setSleeping\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"", ex);
        }
        this.entityFacade.edit(entity);
        return this.webFolder+"list";
    }
    
    public String entityWake(C entity)
    {
        try
        {
            Method m=entity.getClass().getMethod("setSleeping", Boolean.class);
            Utils.callMethod(m, "réactivation de la donnée", entity, false);
        }
        catch (NoSuchMethodException ex)
        {
            ApplicationLogger.writeError("La méthode \"setSleeping\" n'a pas"+
                    " été trouvée pour la classe \""+this.entityClass.getName()+"\"", ex);
        }
        this.entityFacade.edit(entity);
        return this.webFolder+"list";
    }
    
    public String entityCreate()
    {
        this.creating = true;
        this.editing = false;
        String message="Création d'une entité de la classe \""+this.entityClass.getName()+"\"";
        ApplicationLogger.writeInfo(message);
        try
        {
            this.entity = this.entityClass.newInstance();
        }
        catch (InstantiationException ex)
        {
            ApplicationLogger.writeError("Impossible d'instancier un objet de la"
                    + " classe \""+this.entityClass.getName()+"\"", ex);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.writeError("Droits refusés pour l'instanciation"
                    + " d'un objet de la classe \""+this.entityClass.getName()+"\"", ex);
        }
        return this.webFolder+"create";
    }
    
    public String setInstance(C entity)
    {
        this.entity = entity;
        return null;
    }
    
    public C getInstance()
    {
        return this.entity;
    }

    public void setEntityFacade(F entityFacade)
    {
        this.entityFacade=entityFacade;
    }

    protected F getEntityFacade()
    {
        return this.entityFacade;
    }
    
    public List<C> findAll()
    {
        this.setFacade();
        List<C> result=this.entityFacade.findAll();
        if(result==null)
        {
            result=new ArrayList<C>();
        }
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Les methodes abstraites">
    /**
     * Insérer le code:
     * <code>
     * super.entityFacade=this.[VotreEJBFacade];
     * </code>
     */
    public abstract void setFacade();
    /**
     * Insérer le code:
     * <code>
     * return super.findAll();
     * </code>
     */
    public abstract List<C> getEntries();
    /**
     * Insérer le code:
     * <code>
     * return super.getInstance();
     * </code>
     */
    public abstract C getEntity();
    /**
     * Insérer le code:
     * <code>
     * super.setInstance(C entity);
     * </code>
     * @param entity 
     */
    public abstract void setEntity(C entity);
    /**
     * Renvoi le message d'avertissement avant la suppression
     * de cette entité
     * @param entity {@link C} - L'entité à supprimer
     * @return {@link String}, Le message d'avertissement
     */
    public abstract String getDeleteMessage(C entity);
    
    // </editor-fold>
}
