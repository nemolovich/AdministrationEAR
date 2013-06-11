/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.facade.abstracts;

import bean.Utils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Brian GOHIER
 */
public abstract class AbstractEmbdedDataList<C,O> extends AbstractFacade<O>
{
    @PersistenceContext(unitName = "AdministrationEJBPU")
    private EntityManager em;
    private Method getDataListMethod;
    private Method setDataListMethod;
    
    public AbstractEmbdedDataList(Class<O> objectClass,
            Method getDataListMethod, Method setDataListMethod)
    {
        super(objectClass);
        this.getDataListMethod = getDataListMethod;
        this.setDataListMethod = setDataListMethod;
    }
    
    @SuppressWarnings("unchecked")
    public void updateDataList(C entity, O add)
    {
        List<O> list=(List<O>) Utils.callMethod(this.getDataListMethod,
                "méthode de récupération des données",entity);
        list.add(add);
        System.err.println(Arrays.toString(new Object[]{list}));
        Utils.callMethod(this.setDataListMethod,
                "méthode de paramétrage des données",entity,list);
        this.em.persist(add);
        this.em.merge(entity);
    }
}