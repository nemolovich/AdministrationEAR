/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbeddedDataList;
import entity.Client;
import entity.Services;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class ServicesFacade extends AbstractEmbeddedDataList<Client, Services>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServicesFacade() throws NoSuchMethodException
    {
        super(Services.class,Client.class.getMethod("getServicesList"),
                Client.class.getMethod("setServicesList",new Class<?>[]{List.class}));
    }
    
}
