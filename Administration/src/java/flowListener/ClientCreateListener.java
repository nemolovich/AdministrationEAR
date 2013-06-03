/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flowListener;

import bean.ClientView;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
    @Inject
    private ClientView clientView;
    private int stepNumber;
    private String internetPassword;
    private String internetLogin;
    private String operator;

    public ClientCreateListener()
    {
        this.stepNumber = 1;
        System.err.println("called");
    }
    
    public int getStepNumber()
    {
        return this.stepNumber;
    }

    public String getInternetPassword() {
        return internetPassword;
    }

    public void setInternetPassword(String internetPassword) {
        this.internetPassword = internetPassword;
    }

    public String getInternetLogin() {
        return internetLogin;
    }

    public void setInternetLogin(String internetLogin) {
        this.internetLogin = internetLogin;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String onFlowProcess(FlowEvent event)
    {
//        System.out.println("alors?: "+(this.clientView!=null&&this.clientView.getEntity()!=null));
        System.err.print("New Step: "+event.getNewStep());
        System.err.print("Old Step: "+event.getOldStep());
        System.err.print("Current ID: "+event.getComponent().getClientId());
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.addMessage(null, new FacesMessage("Update called", "updated by user"));
        return event.getNewStep(); 
    }  
}
