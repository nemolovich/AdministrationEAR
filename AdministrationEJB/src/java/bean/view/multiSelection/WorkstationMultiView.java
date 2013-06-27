/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection;

import bean.view.multiSelection.struct.EntityMultiView;
import entity.Workstation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "workstationMultiView")
@SessionScoped
public class WorkstationMultiView extends EntityMultiView<Workstation>
{
    private static final long serialVersionUID = 1L;
    
    public WorkstationMultiView()
    {
        super(Workstation.class);
    }
}
