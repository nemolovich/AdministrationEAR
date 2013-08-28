/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.view;

import bean.log.ApplicationLogger;
import bean.Utils;
import bean.facade.TaskFacade;
import bean.view.periodSelection.EntityPeriodView;
import entity.CUser;
import entity.Client;
import entity.Intervention;
import entity.Task;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
public class TaskView extends EntityPeriodView<Task, TaskFacade>
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
    
    public String entityCreateFromClient(Client client)
    {
        this.entityCreate();
        this.entityPopup.setIdClient(client);
        return "create";
    }
    
    public String entityCreateFromUser(CUser user)
    {
        this.entityCreateFromClient(user.getIdClient());
        this.entityPopup.setIdUser(user);
        return "create";
    }
    
    @Override
    public String cancelCreate()
    {
        Task entity=this.getEntityPopup();
        if(entity!=null)
        {
            entity.setIdClient(null);
            entity.setIdDevice(null);
            entity.setIdUser(null);
        }
        return super.cancelCreate();
    }
    
    public String getAccomplishedTime(Task entity)
    {
        String accomplishedTime=Utils.getTimeFormat(0);
        if(entity.getInterventionList()!=null&&!entity.getInterventionList().isEmpty())
        {
            double total=.0f;
            for(Intervention intervention:entity.getInterventionList())
            {
                Double duration=intervention.getDuration();
                if(duration>=0)
                {
                    total+=duration;
                }
            }
            accomplishedTime=Utils.getTimeFormat(total);
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
        List<Task> list=super.findAll();
        if(list!=null&&this.getStartDate()!=null
                &&this.getEndDate()!=null)
        {
            if(!super.verifDate(this.getStartDate(), this.getEndDate()))
            {
                return new ArrayList<Task>();
            }
            for(Task entity:super.findAll())
            {
                Date date=entity.getStartDate();
                if(date==null)
                {
                    continue;
                }
                boolean before=date.before(this.getStartDate());
                boolean after=date.after(this.getEndDate());
                boolean equals=this.getStartDate().toString().equals(
                                date.toString());
                if((before&&!equals)||after)
                {
                    list.remove(entity);
                }
            }
        }
        return list;
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