/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.TUserFacade;
import bean.viewStruct.EntityView;
import entity.TUser;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Windows 7
 */
@Named(value = "tUserView")
@SessionScoped
public class TUserView extends EntityView<TUser, TUserFacade>
{
    private static final long serialVersionUID = 1L;
    @EJB
    private TUserFacade tUserFacade;

    public TUserView()
    {
        super(TUser.class,"");
        super.setWebFolder("/restricted/admin/user/");
    }
    
    @Override
    public String entityUpdate(TUser entity)
    {
        super.entityUpdate(entity);
        return "/restricted/user/user/update";
    }
    
    public String update(UserLogin currentUser)
    {
        this.setEntity(this.tUserFacade.update(this.getEntity()));
        // Si on est en mode utilisateur
        if(currentUser!=null)
        {
            currentUser.setUser(this.getEntity());
            return "view";
        }
        // Si on est en mode administrateur
        return "list";
    }
    
    public String getAdmin_Rights()
    {
        return TUser.ADMIN_RIGHTS;
    }
    
    public String getUser_Rights()
    {
        return TUser.USER_RIGHTS;
    }
    
    @Override
    public String getDeleteMessage(TUser entity)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cet utilisateur ("+entity.getFirstname()+" "+entity.getName()
                + " id="+entity.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }
    
    public String getUnknown_Rights()
    {
        return TUser.UNKNOWN_RIGHTS;
    }
    
    @Override
    public List<TUser> getEntries()
    {
        return super.findAll();
    }

    @Override
    public void setFacade()
    {
        super.setEntityFacade(this.tUserFacade);
    }

    @Override
    public TUser getEntity()
    {
        return super.getInstance();
    }
}
