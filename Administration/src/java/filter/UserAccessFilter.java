/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.TUser;
import java.util.Arrays;

/**
 *
 * @author Stage
 */
public class UserAccessFilter extends AccessFilter
{
    public UserAccessFilter()
    {
        super(Arrays.asList(TUser.ADMIN_RIGHTS,TUser.USER_RIGHTS));
    }   
}
