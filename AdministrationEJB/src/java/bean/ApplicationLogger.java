/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian GOHIER
 */
public class ApplicationLogger
{
    private static final Logger log=Logger.getLogger(ApplicationLogger.class.getName());
    private static File logFile;
    private static PrintWriter out;

    public ApplicationLogger()
    {
    }
    
    public static void start(String fileName)
    {
        logFile=new File(fileName);
        displayInfo("Début d'enregistrement du journal");
        if(!startWrite())
        {
            displayError("Impossible d'écrire dans le journal",null);
        }
        else
        {
            try
            {
                out=new PrintWriter(logFile,"UTF-8");
            }
            catch (FileNotFoundException ex)
            {
                displayError("Le fichier journal n'a pas pu être trouvé", ex);
            }
            catch (UnsupportedEncodingException ex)
            {
                displayError("L'encodage du fichier journal n'a pas été supporté", ex);
            }
            String header="HEADER";
            out.println(header);
        }
    }
    
    public static boolean startWrite()
    {
        File file=logFile;
        if(!file.exists())
        {
            try
            {
                displayInfo("Création du fichier '"+
                        file.getAbsolutePath()+"'");
                boolean created=file.createNewFile();
                displayInfo("Fichier '"+
                        file.getAbsolutePath()+"' créé");
            }
            catch (IOException ex)
            {
                displayError("Erreur lors de la création du fichier '"+
                        file.getAbsolutePath()+"'", ex);
            }
        }
        if(file.canRead()&&file.canWrite())
        {
            displayInfo("Début de l'écriture du journal dans le fichier '"+
                    file.getAbsolutePath()+"'");
            return true;
        }
        return false;
    }
    
    public static boolean stopWrite()
    {
        out.flush();
        out.close();
        return false;
    }
    
    public static void destroy()
    {
        ApplicationLogger.stopWrite();
        ApplicationLogger.displayInfo("Fin d'enregistrement du journal");
    }
    
    public static void displayInfo(String message)
    {
        ApplicationLogger.log.log(Level.INFO, message);
    }
    
    public static void displayWarning(String message)
    {
        ApplicationLogger.log.log(Level.WARNING, message);
    }
    
    public static void displayError(String message, Exception ex)
    {
        ApplicationLogger.log.log(Level.SEVERE, message, ex);
    }
}
