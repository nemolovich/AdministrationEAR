/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractFacade;
import entity.Workstation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class WorkstationFacade extends AbstractFacade<Workstation> {
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkstationFacade() {
        super(Workstation.class);
    }
    
}
