/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.filteredSelection;

import bean.ApplicationLogger;
import bean.Utils;
import bean.view.struct.EntityView;
import entity.TUser;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntitySleepingSelection<C> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Class<C> entityClass;
    public static final SelectItem[] SLEEPING_STATES={
                    new SelectItem("","Les deux","Affiche les éléments actifs et innactifs"),
                    new SelectItem("false","Actif","Affiche les éléments actifs"),
                    new SelectItem("true","Inactif","Affiche les éléments innactifs")
                };
    private List<C> filteredEntities;
    private boolean displaySleepingEntities=false;
    private List<C> fullList;

    public EntitySleepingSelection()
    {
    }
    
    public EntitySleepingSelection(Class<C> entityClass)
    {
        this.entityClass = entityClass;
    }

    public List<C> getFullList()
    {
        return fullList;
    }

    public void setFullList(List<C> fullList)
    {
        this.fullList = fullList;
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
        List<C> res=new ArrayList<C>();
        for(C c:this.fullList)
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
}
