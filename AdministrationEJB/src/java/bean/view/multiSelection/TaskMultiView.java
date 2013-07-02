/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view.multiSelection;

import bean.view.multiSelection.struct.EntityMultiView;
import entity.Task;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "taskMultiView")
@SessionScoped
public class TaskMultiView extends EntityMultiView<Task>
{
    private static final long serialVersionUID = 1L;
    
    public TaskMultiView()
    {
        super(Task.class);
    }
}
