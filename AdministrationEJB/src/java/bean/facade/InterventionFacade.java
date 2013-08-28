/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade;

import bean.facade.abstracts.AbstractEmbdedDataList;
import entity.Intervention;
import entity.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
@Stateless
public class InterventionFacade extends AbstractEmbdedDataList<Task, Intervention>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InterventionFacade() throws NoSuchMethodException
    {
        super(Intervention.class,Task.class.getMethod("getInterventionList"),
                Task.class.getMethod("setInterventionList",new Class<?>[]{List.class}));
    }
    
}
