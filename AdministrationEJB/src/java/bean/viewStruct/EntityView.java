/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.facade.abstracts.AbstractFacade;
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
    protected F entityFacade;
    
    public EntityView()
    {
    }
    
    public EntityView(Class<C> entityClass,String webFolder)
    {
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
        this.entityClass=entityClass;
    }
    
    public C getInstance()
    {
        return this.entity;
    }
    
    @SuppressWarnings("unchecked")
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
        return "create";
    }

    public String entityView(C entity)
    {
        this.entity = entity;
        return this.webFolder+"view";
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
     * Permet de récupérer une liste des ces objets sous forme de noms
     */
    public abstract List<String> getList();
    
    // </editor-fold>
}
