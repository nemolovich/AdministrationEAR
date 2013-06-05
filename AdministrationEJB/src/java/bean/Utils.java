/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "utils")
@ApplicationScoped
public class Utils
{
    public static String getHiddenString(String text)
    {
        String hidden="";
        for(int i=0;i<text.length();i++)
        {
            hidden+="•";
        }
        return hidden;
    }
}
