/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.ClientFacade;
import bean.viewStruct.EntityView;
import entity.Client;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Stage
 */
@Named(value = "clientView")
@SessionScoped
public class ClientView extends EntityView<Client, ClientFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private ClientFacade clientFacade;
    
    public ClientView()
    {
        super("client");
        super.entityFacade=clientFacade;
    }
}
