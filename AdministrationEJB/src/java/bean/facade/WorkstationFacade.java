/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbdedDataList;
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
public class WorkstationFacade extends AbstractEmbdedDataList<Client,Workstation>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkstationFacade() throws NoSuchMethodException
    {
        super(Workstation.class,Client.class.getMethod("getWorkstationList"),
                Client.class.getMethod("setWorkstationList",new Class<?>[]{List.class}));
    }
}
