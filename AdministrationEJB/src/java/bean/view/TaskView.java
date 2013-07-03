/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.facade.TaskFacade;
import bean.view.struct.EntityView;
import entity.Task;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "taskView")
@SessionScoped
public class TaskView extends EntityView<Task, TaskFacade>
{
    public static final String[] INTERVENTION_TYPES={"LOGICIELLE","MATERIELLE"};
    
    private static final long serialVersionUID = 1L;
    @EJB
    private TaskFacade taskFacade;
    
    public TaskView()
    {
        super(Task.class,"task");
    }
    
    public List<String> getInterventionTypes()
    {
        return Arrays.asList(INTERVENTION_TYPES);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.taskFacade);
    }

    @Override
    public List<Task> getEntries()
    {
        return super.findAll();
    }

    @Override
    public Task getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Task entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cette tâche ("+entity.toString()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Task entity)
    {
        super.setInstance(entity);
    }
}