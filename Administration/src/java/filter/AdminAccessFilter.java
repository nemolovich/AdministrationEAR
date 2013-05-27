/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import filter.struct.AccessFilter;
import entity.TUser;
import java.util.Arrays;

/**
 *
 * @author Stage
 */
public class AdminAccessFilter extends AccessFilter
{
    public AdminAccessFilter()
    {
        super(Arrays.asList(TUser.ADMIN_RIGHTS));
    }   
}
