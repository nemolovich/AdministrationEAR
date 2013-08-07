/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "city")
@ViewScoped
public class City
{
    private static Map<String, String> LIST=new HashMap<String, String>();
    private static boolean LOADED=false;
    
    public City()
    {
        City.load();
    }
    
    public static void load()
    {
        if(!LOADED)
        {
            Properties prop=new Properties();
            try
            {
                prop.load(new InputStreamReader(new FileInputStream("resources/cities.txt"),"UTF-8"));
                for(Object key:prop.keySet())
                {
                    LIST.put((String)key, prop.getProperty((String)key));
                }
            }
            catch (IOException ex)
            {
                ApplicationLogger.writeError("Impossible de charger le fichier des "
                        + "code postaux", ex);
            }
            LOADED=true;
        }
    }
    
    public static String getPostalCode(String city)
    {
        for(String postalCode:LIST.keySet())
        {
            if(LIST.get(postalCode).equalsIgnoreCase(city))
            {
                return postalCode;
            }
        }
        return "";
    }
    
    public static String getCity(String postalCode)
    {
        for(String code:LIST.keySet())
        {
            if(code.equalsIgnoreCase(postalCode))
            {
                return LIST.get(code);
            }
        }
        return "";
    }
    
    public static List<String> getPostalCodes()
    {
        List<String> postalCodes=new ArrayList<String>();
        for(String key:LIST.keySet())
        {
            postalCodes.add(key);
        }
        return postalCodes;
    }
    
    public static List<String> getCityNames()
    {
        List<String> cityNames=new ArrayList<String>();
        for(String key:LIST.keySet())
        {
            cityNames.add(LIST.get(key));
        }
        return cityNames;
    }
}
