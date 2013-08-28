/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbeddedDataList;
import entity.Client;
import entity.Device;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class DeviceFacade extends AbstractEmbeddedDataList<Client,Device>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeviceFacade() throws NoSuchMethodException
    {
        super(Device.class,Client.class.getMethod("getDeviceList"),
                Client.class.getMethod("setDeviceList",new Class<?>[]{List.class}));
    }
}
