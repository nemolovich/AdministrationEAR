/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractFacade;
import entity.TUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class TUserFacade extends AbstractFacade<TUser>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return this.em;
    }
    
    public TUserFacade()
    {
        super(TUser.class);
    }
    
    public TUser update(TUser user)
    {  
        return this.em.merge(user);
    }
  
    public void persist(Object object)
    {  
        this.em.persist(object);  
    }
}
