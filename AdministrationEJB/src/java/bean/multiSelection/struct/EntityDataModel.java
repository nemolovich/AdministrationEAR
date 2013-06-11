/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.multiSelection.struct;

import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Brian GOHIER
 */
public abstract class EntityDataModel<C> extends ListDataModel<C>
    implements SelectableDataModel<C>
{
    
    public EntityDataModel()
    {
    }

    public EntityDataModel(List<C> list)
    {
        super(list);
    }
    
    @Override
    public Object getRowKey(C c)
    {
        return c.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public C getRowData(String rowKey)
    {
        for(C c:(List<C>)this.getWrappedData())
        {
            if(c.toString().equalsIgnoreCase(rowKey))
            {
                return c;
            }
        }
        return null;
    }
}
