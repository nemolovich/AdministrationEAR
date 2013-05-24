/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.TUserFacade;
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
public class TUserView implements Serializable
{
    private static final long serialVersionUID = 1L;
    @EJB
    private TUserFacade tUserFacade;
    private TUser user;

    /**
     * Constructor
     */
    public TUserView()
    {
    }
    
    public TUser getUser()
    {  
        return this.user;  
    }

    public String userView(TUser user)
    {  
        this.user = user;  
        return "view";
    }

    public String userUpdate(TUser user)
    {  
        this.user = user;  
        return "update"; 
    }
    
    public String userDelete(TUser user)
    {
        this.tUserFacade.remove(user);
        return "list";
    }
    
    public String getDeleteMessage(TUser user)
    {
        return "Vous êtes sur le point de supprimer définitivement"
                + " cet utilisateur ("+user.getFirstname()+" "+user.getName()
                + " id="+user.getId()+"). Cette action est irreversible,"
                + " êtes-vous certain(e) de vouloir continuer?";
    }

    public String userCreate()
    {  
        this.user = new TUser();
        return "create"; 
    }
    
    public String create()
    {
        this.tUserFacade.persist(this.user);
        return "list";
    }
    
    public String delete()
    {
        this.tUserFacade.remove(this.user);
        return "list";
    }
    
    public String update(UserLogin currentUser)
    {
        this.user = this.tUserFacade.update(this.user);
        // Si on est en mode utilisateur
        if(currentUser!=null)
        {
            currentUser.setUser(this.user);
            return "view";
        }
        // Si on est en mode administrateur
        return "list";
    }
  
    public String list()
    {
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
    
    public String getUnknown_Rights()
    {
        return TUser.UNKNOWN_RIGHTS;
    }
    
    public List<TUser> getEntries()
    {
        return this.tUserFacade.findAll();
    }
}
