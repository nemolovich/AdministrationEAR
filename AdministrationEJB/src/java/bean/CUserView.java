/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.CUserFacade;
import entity.CUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Stage
 */
@Named(value = "cUserView")
@SessionScoped
public class CUserView implements Serializable
{
    private static final long serialVersionUID = 1L;
    @EJB
    private CUserFacade cUserFacade;
    private CUser cUser;
    private final String webFolder="/restricted/admin/data/c_user/";
    
    public CUserView() {
    }
    
    public CUser getCUser()
    {
        return this.cUser;
    }

    public String cUserView(CUser cUser)
    {  
        this.cUser = cUser;  
        return this.webFolder+"view";
    }
    
    public List<CUser> getEntries()
    {
        return this.cUserFacade.findAll();
    }
}
