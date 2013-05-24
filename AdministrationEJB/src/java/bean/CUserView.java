/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.CUserFacade;
import bean.viewStruct.EntityView;
import entity.CUser;
import java.util.ArrayList;
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
public class CUserView extends EntityView<CUser, CUserFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private CUserFacade cUserFacade;
    
    public CUserView()
    {
        super(CUser.class,"c_user");
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.cUserFacade);
    }

    @Override
    public List<CUser> getEntries()
    {
        return super.findAll();
    }

    @Override
    public CUser getEntity()
    {
        return super.getInstance();
    }

    @Override
    public List<String> getList()
    {
        List<String> list=new ArrayList<String>();
        for(CUser user:this.getEntries())
        {
            list.add(user.getName());
        }
        return list;
    }
}
