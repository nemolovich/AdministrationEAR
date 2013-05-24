/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.facade.abstracts.AbstractFacade;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.EJB;

/**
 *
 * @author Stage
 */
public abstract class EntityView<C,F extends AbstractFacade<C>> implements Serializable
{
    private static final long serialVersionUID = 1L;
    private C entity;
    private  String webFolder=null;
    protected F entityFacade;
    
    public EntityView()
    {
    }
    
    public EntityView(String webFolder)
    {
        this.webFolder="/restricted/admin/data/"+webFolder+"/";
    }
    
    public C getEntity()
    {
        return this.entity;
    }

    public String entityView(C entity)
    {
        this.entity = entity;
        return this.webFolder+"view";
    }
    
    public List<C> getEntries()
    {
        System.err.println("Facade: "+(this.entityFacade!=null));
        return this.entityFacade.findAll();
    }
}
