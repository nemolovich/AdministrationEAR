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
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
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
        if(!City.LOADED)
        {
            Properties prop=new Properties();
            try
            {
                prop.load(new InputStreamReader(new FileInputStream("resources/cities.txt"),"UTF-8"));
                for(Object key:prop.keySet())
                {
                    City.LIST.put((String)key, prop.getProperty((String)key));
                }
            }
            catch (IOException ex)
            {
                ApplicationLogger.writeError("Impossible de charger le fichier des "
                        + "code postaux", ex);
            }
            City.LOADED=true;
        }
    }
    
    public static String getPostalCode(String city)
    {
        for(String postalCode:City.LIST.keySet())
        {
            if(City.LIST.get(postalCode).equalsIgnoreCase(city))
            {
                return postalCode;
            }
        }
        return "";
    }
    
    public static String getCity(String postalCode)
    {
        for(String code:City.LIST.keySet())
        {
            if(code.equalsIgnoreCase(postalCode))
            {
                return City.LIST.get(code);
            }
        }
        return "";
    }
    
    public static List<String> getPostalCodes()
    {
        List<String> postalCodes=new ArrayList<String>();
        for(String key:City.LIST.keySet())
        {
            postalCodes.add(key);
        }
        return postalCodes;
    }
    
    public static List<String> getCityNames()
    {
        List<String> cityNames=new ArrayList<String>();
        for(String key:City.LIST.keySet())
        {
            cityNames.add(City.LIST.get(key));
        }
        return cityNames;
    }
    
    public static void setCity(ValueChangeEvent event)
    {
        UIInput cityInput=(UIInput)event.getComponent().getAttributes().get("cityInput");
        String value=String.format("%d",(Integer) event.getNewValue());
        if(City.LIST.containsKey(value))
        {
            cityInput.setValue(City.LIST.get(value));
        }
    }
    
    public static void setPostalCode(ValueChangeEvent event)
    {
        UIInput postalCodeInput=(UIInput)event.getComponent().getAttributes().get("postalCodeInput");
        String value=(String) event.getNewValue();
        if(City.LIST.containsValue(value))
        {
            postalCodeInput.setValue(City.getPostalCode(value));
        }
    }
}
