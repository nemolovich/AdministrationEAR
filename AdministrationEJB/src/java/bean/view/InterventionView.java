/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.view;

import bean.facade.InterventionFacade;
import bean.view.struct.EmbdedDataListView;
import entity.Intervention;
import entity.Task;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "interventionView")
@SessionScoped
public class InterventionView extends EmbdedDataListView<Task, Intervention, InterventionFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private InterventionFacade inteventionFacade;
    private Date startDate=Calendar.getInstance().getTime();;
    private Date endDate=this.addDate(this.startDate, Calendar.DAY_OF_YEAR, 30);
    
    public InterventionView() throws NoSuchMethodException
    {
        super(Intervention.class,"intervention",
                Intervention.class.getMethod("setIdTask",
                                        new Class<?>[]{Task.class}));
    }
    
    public String entityCreate(TaskView view, boolean createParent)
    {
        if(createParent)
        {
            view.entityCreate();
        }
        else
        {
            view.entityView(view.getEntity());
        }
        view.setCreating(createParent);
        return super.entityCreate(view.getEntityPopup());
    }
    
    public String entityView(TaskView view, Intervention entity)
    {
        view.entityView(entity.getIdTask());
        return super.entityView(entity);
    }
    
    public String cancelCreate(TaskView view)
    {
        view.cancelCreate();
        return super.cancelCreate();
    }
    
    public String create(TaskView view)
    {
        if(view.isCreating())
        {
            view.create();
        }
        return super.create(view.getEntityPopup());
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Date addDate(Date startDate, int field, int amount)
    {
        Calendar cal=Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(field, amount);
        return cal.getTime();
    }
    
    @Override
    public void setEntity(Intervention entity)
    {
        super.setInstance(entity);
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.inteventionFacade);
    }

    @Override
    public List<Intervention> getEntries()
    {
        List<Intervention> list=super.findAll();
        if(list!=null&&this.startDate!=null
                &&this.endDate!=null)
        {
            if(this.startDate.after(this.endDate))
            {
                FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Dates incorrectes", "La date de début doit être antérieure"
                        + " à celle de fin");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.err.println("-- Erreur dates");
                return list;
            }
            System.err.println("From: "+this.startDate+" to: "+this.endDate);
            for(Intervention intervention:super.findAll())
            {
                if(intervention.getIdTask().getStartDate().after(this.endDate)||
                        intervention.getIdTask().getStartDate().before(this.startDate))
                {
                    list.remove(intervention);
                }
            }
        }
        return list;
    }

    @Override
    public Intervention getEntity()
    {
        return super.getInstance();
    }

    @Override
    public String getDeleteMessage(Intervention entity)
    {
        String details=(entity==null||entity.getIdTask()==null?"":" ("+entity.getIdTask().getDescription()
                    + " id="+entity.getId()+")");
        return "Vous êtes sur le point de supprimer définitivement"
                    + " cette intervention"+details+". Cette action est irreversible,"
                    + " êtes-vous certain(e) de vouloir continuer?";
    }

    @Override
    public String deleteMessages(List<Intervention> entities)
    {
        if(entities!=null)
        {
            String out="Vous êtes sur le point de supprimer définitivement tous"
                + " les utilisateurs sélectionnés (";
            boolean first=true;
            for(Intervention intervention:entities)
            {
                if(first)
                {
                    first=false;
                }
                else
                {
                    out+=", ";
                }
                out+=intervention.getIdTask().getDescription()+" id="+intervention.getId();
            }
            out+="). Cette action est irreversible, "
                + "êtes-vous certain(e) de vouloir continuer?";
            return out;
        }
        return "Erreur!";
    }
}
