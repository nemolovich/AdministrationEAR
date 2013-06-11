/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.viewStruct;

import bean.Utils;
import bean.facade.abstracts.AbstractEmbdedDataList;
import java.lang.reflect.Method;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EmbdedDataListView<C,O,F extends AbstractEmbdedDataList<C,O>> extends EntityView<O,F>
{
    private Method setReferenceMethod;

    public EmbdedDataListView() {
    }    

    public EmbdedDataListView(Class<O> entityClass,String webFolder,
            Method setReferenceMethod)
    {
        super(entityClass, webFolder);
        this.setReferenceMethod = setReferenceMethod;
    }
    
    public String entityCreate(C entity)
    {
        super.entityCreate();
        Utils.callMethod(this.setReferenceMethod,
                "méthode de récupération des données",this.getInstance(),
                entity);
        return "create";
    }
    
    public String create(C entity)
    {
        this.setFacade();
        super.getEntityFacade().updateDataList(entity,this.getInstance());
        return "list";
    }
    
    @Override
    public abstract void setFacade();
}
