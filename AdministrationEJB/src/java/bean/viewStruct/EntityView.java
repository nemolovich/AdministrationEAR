/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.facade.abstracts.AbstractFacade;
import entity.TUser;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stage
 */
public abstract class EntityView<C,F extends AbstractFacade<C>> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private C entity;
    private Class<C> entityClass;
    private  String webFolder=null;
    private F entityFacade;
    
    public EntityView()
    {
    }
    
    protected void setWebFolder(String webFolder)
    {
        this.webFolder=webFolder;
    }
    
    public EntityView(Class<C> entityClass,String webFolder)
    {
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
        this.entityClass=entityClass;
    }
    
    public String create()
    {
        this.entityFacade.create(this.entity);
        return this.webFolder+"list";
    }
    
    public String delete()
    {
        this.entityFacade.remove(this.entity);
        return this.webFolder+"list";
    }
    
    public String update()
    {
        this.entityFacade.edit(this.entity);
        return this.webFolder+"list";
    }

    public String entityView(C entity)
    {
        this.entity = entity;
        return this.webFolder+"view";
    }
    
    public String entityUpdate(C entity)
    {
        this.entity = entity;
        return this.webFolder+"update";
    }
    
    public String entityDelete(C entity)
    {
        this.entityFacade.remove(entity);
        return this.webFolder+"list";
    }
    
    public String entityCreate()
    {
        try
        {
            this.entity = this.entityClass.newInstance();
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(EntityView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(EntityView.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public List<C> findAll()
    {
        this.setFacade();
        return this.entityFacade.findAll();
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
