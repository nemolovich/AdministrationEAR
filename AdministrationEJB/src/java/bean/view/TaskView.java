/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.ApplicationLogger;
import bean.facade.TaskFacade;
import bean.view.struct.EntityView;
import entity.Intervention;
import entity.Task;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
    private Task entityPopup;
    
    public TaskView()
    {
        super(Task.class,"task");
    }
    
    public List<String> getInterventionTypes()
    {
        return Arrays.asList(INTERVENTION_TYPES);
    }

    @Override
    public String entityView(Task entity)
    {
        super.setCreating(false);
        super.setEditing(false);
        this.entityPopup = entity;
        return "view";
    }
    
    @Override
    public String entityCreate()
    {
        super.setCreating(true);
        super.setEditing(false);
        String message="Création d'une entité de la classe \""+Task.class.getName()+"\"";
        ApplicationLogger.writeInfo(message);
        try
        {
            this.entityPopup = Task.class.newInstance();
        }
        catch (InstantiationException ex)
        {
            ApplicationLogger.writeError("Impossible d'instancier un objet de la"
                    + " classe \""+Task.class.getName()+"\"", ex);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.writeError("Droits refusés pour l'instanciation"
                    + " d'un objet de la classe \""+Task.class.getName()+"\"", ex);
        }
        return "create";
    }
    
    public String getAccomplishedTime(Task entity)
    {
        String accomplishedTime="00 h 00 mins";
        if(entity.getInterventionList()!=null&&!entity.getInterventionList().isEmpty())
        {
            int hours=0;
            int mins=0;
            for(Intervention intervention:entity.getInterventionList())
            {
                String duration=intervention.getDuration();
                if(duration.length()==12&&duration.contains(" h ")&&
                        duration.contains(" mins"))
                {
                    hours+=Integer.valueOf(duration.substring(0, 2));
                    mins+=Integer.valueOf(duration.substring(5, 7));
                    if(mins>=60)
                    {
                        mins-=60;
                        hours+=1;
                    }
                }
            }
            accomplishedTime=String.format("%02d h %02d mins", hours, mins);
        }
        return accomplishedTime;
    }
    
    @Override
    public String entityUpdate(Task entity)
    {
        super.setCreating(false);
        super.setEditing(true);
        this.entityPopup = entity;
        return "update";
    }
    
    @Override
    public String create()
    {
        super.setCreating(false);
        super.setEditing(false);
        this.setFacade();
        this.taskFacade.create(this.entityPopup);
        return "list";
    }
    
    @Override
    public String update()
    {
        super.setCreating(false);
        super.setEditing(false);
        this.setFacade();
        this.taskFacade.edit(this.entityPopup);
        return "list";
    }
    
    public Task getEntityPopup()
    {
        return this.entityPopup;
    }
    
    public void setEntityPopup(Task entity)
    {
        this.entityPopup = entity;
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
        String details=entity!=null?" ("+entity.toString()
                + " id="+entity.getId()+")":"";
        return "Vous êtes sur le point de supprimer définitivement"
                + " cette tâche"+details+". Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public void setEntity(Task entity)
    {
        super.setInstance(entity);
    }
    
    public void ajaxSelect(AjaxBehaviorEvent e)
    {
    }
}