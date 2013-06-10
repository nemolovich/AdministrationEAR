/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractFacade;
import entity.Client;
import entity.Workstation;
import java.util.List;
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
    
    @SuppressWarnings("unchecked")
    public void updateWorkstationList(Client entity, Workstation workstation)
    {
        List<Workstation> list=entity.getWorkstationList();
        entity.setOldWorkstationList(list);
        list.add(workstation);
        entity.setWorkstationList(list);
    }
    
}
