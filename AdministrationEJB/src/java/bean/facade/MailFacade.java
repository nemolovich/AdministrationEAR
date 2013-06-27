/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbdedDataList;
import entity.Client;
import entity.Mail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class MailFacade extends AbstractEmbdedDataList<Client,Mail>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailFacade() throws NoSuchMethodException
    {
        super(Mail.class,Client.class.getMethod("getMailList"),
                Client.class.getMethod("setMailList",new Class<?>[]{List.class}));
    }
    
}
