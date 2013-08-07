/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.TUser;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named
@SessionScoped
public class User extends TUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    public User()
    {
    }
    
    public User(TUser tUser)
    {
        super(tUser);
    }

    public User(Integer id)
    {
        super(id);
    }

    public User(Integer id, String mail, String name, String firstname,
            String password, String rights)
    {
        super(id, mail, name, firstname, password, rights);
    }
    
    @Override
    public String getFullString()
    {
        return "entity.User{"+super.getFullString()+"}";
    }
}