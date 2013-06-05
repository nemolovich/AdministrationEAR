/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flowListener;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "clientCreateListener")
@SessionScoped
public class ClientCreateListener
{
//    @EJB
//    private ClientView clientView;
    private int stepNumber;

    public ClientCreateListener()
    {
        this.stepNumber = 1;
        System.err.println("called");
    }
    
    public int getStepNumber()
    {
        return this.stepNumber;
    }
    
    @Override
    public String toString()
    {
        return ClientCreateListener.class.getName()+"[step="+this.stepNumber+"]";
    }
    
    public String onFlowProcess(FlowEvent event)
    {
//        System.out.println("alors?: "+(this.clientView!=null&&this.clientView.getEntity()!=null));
        System.err.print("New Step: "+event.getNewStep());
        System.err.print("Old Step: "+event.getOldStep());
        System.err.print("Current ID: "+event.getComponent().getClientId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Flow infos", this.toString()));
        return event.getNewStep(); 
    }
}
