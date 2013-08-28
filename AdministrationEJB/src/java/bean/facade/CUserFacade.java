/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbeddedDataList;
import entity.CUser;
import entity.Client;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class CUserFacade extends AbstractEmbeddedDataList<Client, CUser>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CUserFacade() throws NoSuchMethodException
    {
        super(CUser.class,Client.class.getMethod("getCUserList"),
                Client.class.getMethod("setCUserList",new Class<?>[]{List.class}));
    }
    
}
