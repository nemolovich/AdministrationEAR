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
//        System.out.println("Row: '"+rowKey+"'");
//        int minCompare=Integer.MAX_VALUE;
        C res=null;
        for(C c:(List<C>)this.getWrappedData())
        {
//            System.out.println("toString: '"+c.toString()+"'");
            if(c.toString().equalsIgnoreCase(rowKey))
            {
                return c;
            }
//            else if(c.toString().compareTo(rowKey)<minCompare)
//            {
//                minCompare=c.toString().compareTo(rowKey);
//                res=c;
//            }
        }
        return res;
    }
}
