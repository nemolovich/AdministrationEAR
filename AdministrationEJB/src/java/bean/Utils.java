/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
    /**
     * Nom de l'application
     */
    public static final String APPLICATION_NAME="Administration";
    /**
     * Le nombre maximum de ligne dans une liste de données
     */
    private int maxDataRows = 10;
    
    /**
     * Renvoi le nombre max de lignes dans une liste de données
     * @return {@link Integer int} Nombre de lignes
     */
    public int getMaxDataRows()
    {
        return this.maxDataRows;
    }
    
    /**
     * Remplace chaque caractère d'un texte par une étoile
     * @param text {@link String} - Le texte à convertir
     * @return {@link String} Le texte contenant les étoiles
     */
    public static String getHiddenString(String text)
    {
        return text.replaceAll(".", "•");
    }
    
    /**
     * Raccourci un texte vers la taille donnée
     * @param text {@link String} - Le texte à raccourcir
     * @param maxSize {@link Integer int} - La taille max
     * @return {@link String} Le texte plus court
     */
    public static String getShortString(String text,int maxSize)
    {
        return (text!=null&&!text.isEmpty()&&text.length()>maxSize)?text.substring(0, maxSize)+"...":text;
    }
    
    /**
     * Remplace les retours à la ligne d'un texte java par des retours à la
     * ligne en HTML
     * @param text {@link String} - Le texte à convertir
     * @return {@link String} Le texte HTML
     */
    public static String getBreakLinesString(String text)
    {
        return text.replaceAll("\n", "<br/>");
    }
    
    /**
     * Appelle un méthode d'une classe sur un objet passé en paramètre
     * avec les arguments donnés
     * @param m {@link Method} - La méthode à invoquer
     * @param desc {@link String} - La description de la méthode
     * @param target {@link Object} - L'ojet sur lequel invoquer la méthode
     * @param args {@link Object}[] - La liste des arguments
     * @return {@link Object} Ce que retourne la méthode
     */
    public static Object callMethod(Method m, String desc, Object target,
            Object... args)
    {
        try
        {
            String call="Appel de la méthode \""+m.getName()+"\" ("+desc+") sur "
                    + "l'objet de la classe \""+target.getClass().getName()+"\" "
                    + "avec les paramètres: "+Arrays.toString(args);
            ApplicationLogger.writeInfo(call);
            return m.invoke(target, args);
        }
        catch (IllegalAccessException ex)
        {
            ApplicationLogger.writeError("La méthode \""+m.getName()+"\" est"+
                    " interdite d'accès pour la classe \""+target.getClass().getName()
                    +"\"", ex);
        }
        catch (IllegalArgumentException ex)
        {
           ApplicationLogger.writeError("Les arguments pour la méthode \"get"+
                    m.getName()+"\" sont incorrects pour la classe \""+
                    target.getClass().getName()+"\"", ex);
        }
        catch (InvocationTargetException ex)
        {
            ApplicationLogger.writeError("La méthode \"get"+m.getName()+"\" n'est"+
                    " pas applicable pour la classe \""+target.getClass().getName()+
                    "\"", ex);
        }
        return null;
    }
}
