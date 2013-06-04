/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractFacade;
import entity.CUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class CUserFacade extends AbstractFacade<CUser> {
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CUserFacade() {
        super(CUser.class);
    }
    
}
