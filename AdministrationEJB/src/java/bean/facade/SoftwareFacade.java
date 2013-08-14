/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbdedDataList;
import entity.Client;
import entity.Software;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class SoftwareFacade extends AbstractEmbdedDataList<Client, Software>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SoftwareFacade() throws NoSuchMethodException
    {
        super(Software.class,Client.class.getMethod("getSoftwareList"),
                Client.class.getMethod("setSoftwareList",new Class<?>[]{List.class}));
    }
    
}
