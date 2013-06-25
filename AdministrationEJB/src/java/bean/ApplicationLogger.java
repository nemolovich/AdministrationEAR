/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import static bean.ApplicationLogger.displayError;
import static bean.ApplicationLogger.displayInfo;
import static bean.ApplicationLogger.displayWarning;
import static bean.ApplicationLogger.startWrite;
import static bean.ApplicationLogger.writeInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Brian GOHIER
 */
@Named(value = "logger")
@ApplicationScoped
public class ApplicationLogger
{
    private static final Logger log=Logger.getLogger(ApplicationLogger.class.getName());
    private static File logFile;
    private static PrintWriter out;
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final String INFO_TAG="INFO";
    private static final String WARNING_TAG="WARNING";
    private static final String ERROR_TAG="ERROR";
    private static final String HUGE_SEPARATOR="###################################################################";
    private static final String BIG_SEPARATOR="##=================================================================";
    private static final String SEPARATOR="##~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    private static final String SMALL_SEPARATOR="##-----------------------------------------------------------------";
    private static String lastLine=null;
    private static String lastStyle=null;
    private static int nbLastLine=0;

    public ApplicationLogger()
    {
    }
    
    private static String date()
    {
        return "["+format.format(Calendar.getInstance().getTime())+"]";
    }
    
    private static String info()
    {
        return " "+INFO_TAG+": ";
    }
    
    private static String warning()
    {
        return " "+WARNING_TAG+": ";
    }
    
    private static String error()
    {
        return " "+ERROR_TAG+": ";
    }
    
    private static void addHeader()
    {
        String header=HUGE_SEPARATOR+"\r\n"
                                + "## Fichier journal pour l'application: '"+Utils.APPLICATION_NAME+"'";
        for(int i=Utils.APPLICATION_NAME.length();i<=22;i++)
        {
            header+=" ";
        }
        header+=" ##\r\n";
        header+="## Date de création:        "+date()+"                   ##\r\n";
        header+=HUGE_SEPARATOR+"\r\n";
        out.println(header);
        out.flush();
    }
    
    public static String getHTMLLog()
    {
        Reader reader = null;
        boolean inTab=false;
        String logHTML="";
        String logLine="";
        try
        {
            FileInputStream fileReader=new FileInputStream(logFile);
            reader=new BufferedReader(new InputStreamReader(fileReader,"UTF8"));
            int c;
            while((c=reader.read())!=-1)
            {
                if(c=='\n')
                {
                    if(logLine.equals(BIG_SEPARATOR)||
                            logLine.equals(SEPARATOR)||
                            logLine.equals(SMALL_SEPARATOR))
                    {
                        logHTML+="<hr/>";
                    }
                    else if(logLine.equals(HUGE_SEPARATOR))
                    {
                        inTab^=true;
                        if(inTab)
                        {
                            logHTML+="<TABLE>\n";
                        }
                        else
                        {
                            logHTML+="</TABLE>\n";
                        }
                    }
                    else if(logLine.startsWith("## "))
                    {
                        logLine=logLine.substring(3);
                        if(logLine.endsWith(" ##"))
                        {
                            logLine=logLine.substring(0,logLine.indexOf(" ##"));
                        }
                        if(logLine.contains(":"))
                        {
                            String field=logLine.substring(0, logLine.indexOf(":"));
                            String value=logLine.substring(field.length()+1);
                            while(value.startsWith(" "))
                            {
                                value=value.substring(1);
                            }
                            while(value.endsWith(" "))
                            {
                                value=value.substring(0,value.length()-1);
                            }
                            logHTML+="--value:--"+value+"--<br/>";
                            if((value.startsWith("'")&&value.endsWith("'"))||
                                    (value.startsWith("[")&&value.endsWith("]")))
                            {
                                value=value.substring(1,value.length()-1);
                            }
                            if(inTab)
                            {
                                logHTML+="<TR><TD class=\"label\">"+field+":</TD>\n"
                                        + "<TD class=\"fields\">"+value+"</TD></TR>\n";
                            }
                            else
                            {
                                logHTML+="COMMENT: Field: "+field+", value: "+value+"<br/>";
                            }
                        }
                        else
                        {
                            if(inTab)
                            {
                                logHTML+="<TR><TD class=\"label\" colspan=\"2\">"+
                                        logLine+"</TD></TR>\n";
                            }
                            else
                            {
                                logHTML+="COMMENT: "+logLine+"<br/>";
                            }
                        }
                    }
                    else
                    {
                        logHTML+=logLine+"<br/>";
                    }
                    logLine="";
                }
                else if(c=='\r')
                {
                    // Rien car '\r\n' dans le log
                }
                else
                {
                    logLine+=(char)c;
                }
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return logHTML;
    }
    
    public static void start(String fileName)
    {
        logFile=new File(fileName);
        displayInfo("Début d'enregistrement du journal");
        if(!startWrite())
        {
            displayError("Impossible d'écrire dans le journal",null);
            return;
        }
        else
        {
            write("\r\n"+BIG_SEPARATOR);
            writeInfo("Début des écritures dans le journal");
        }
    }
    
    public static boolean startWrite()
    {
        boolean created=false;
        
        if(!logFile.exists())
        {
            try
            {
                displayInfo("Création du fichier '"+
                        logFile.getAbsolutePath()+"'");
                if(logFile.createNewFile())
                {
                    displayInfo("Fichier '"+
                            logFile.getAbsolutePath()+"' créé");
                    created=true;
                }
                else
                {
                    displayError("Le fichier '"+logFile.getAbsolutePath()+"' n'a pa pu être créé",null);
                    return false;
                }
            }
            catch (IOException ex)
            {
                displayError("Erreur lors de la création du fichier '"+
                        logFile.getAbsolutePath()+"'", ex);
                return false;
            }
        }
        try
        {
            FileOutputStream fileWriter=new FileOutputStream(logFile, true);
            Writer writer=new BufferedWriter(new OutputStreamWriter(fileWriter,"UTF8"));
            out=new PrintWriter(writer);
        }
        catch (FileNotFoundException ex)
        {
            displayError("Le fichier journal n'a pas pu être trouvé", ex);
            return false;
        }
        catch (UnsupportedEncodingException ex)
        {
            displayError("L'encodage du fichier journal n'a pas été supporté", ex);
            return false;
        }
        catch (IOException ex)
        {
            displayError("Le fichier n'est pas accessible en écriture", ex);
            return false;
        }
        
        if(logFile.canRead()&&logFile.canWrite())
        {
            if(created)
            {
                addHeader();
            }
            return true;
        }
        return false;
    }
    
    public static boolean stopWrite()
    {
        write("\r\n"+SEPARATOR);
        writeInfo("Fin des écritures dans le journal");
        write("\r\n"+BIG_SEPARATOR);
        out.close();
        return false;
    }
    
    public static void destroy()
    {
        ApplicationLogger.stopWrite();
        ApplicationLogger.displayInfo("Fin d'enregistrement du journal");
    }
    
    public static void writeInfo(String message)
    {
        write(message, INFO_TAG);
    }
    
    public static void writeWarning(String message)
    {
        write(message, WARNING_TAG);
    }
    
    public static void writeError(String message, Exception ex)
    {
        write(message, ERROR_TAG, ex);
    }
    
    public static void write(String message)
    {
        write(message, null, null);
    }
    
    public static void write(String message, String style)
    {
        write(message, style, null);
    }
    
    public static void write(String message, String style, Exception ex)
    {
        if(lastLine==null||(lastLine!=null&&!lastLine.equals(message)))
        {
            String add=(style!=null?(style.equals(INFO_TAG)?info():
                        style.equals(WARNING_TAG)?warning():
                        style.equals(ERROR_TAG)?error():""):"");
            String before=lastStyle!=null?lastStyle:"";
            for(int i=before.length();i<23;i++)
            {
                before=" "+before;
            }
            if(nbLastLine>0)
            {
                out.append(before+": Même ligne "+nbLastLine+" fois\r\n");
                out.flush();
                displayInfo(before+": Même ligne "+nbLastLine+" fois\r\n");
            }
            if(style!=null)
            {
                add=date()+add;
                lastStyle=style;
            }
            out.append(add+message.replaceAll("'", "''")+"\r\n");
            out.flush();
            if(ex!=null)
            {
                out.append("\tCause: "+ex.getClass().getName()+": "+ex.getLocalizedMessage()+":\r\n");
                out.flush();
                for(StackTraceElement e:ex.fillInStackTrace().getStackTrace())
                {
                    out.append("\t"+e.toString()+"\r\n");
                    out.flush();
                }
            }
            if(style!=null)
            {
                if(style.equals(ERROR_TAG))
                {
                    displayError(message, ex);
                }
                else if(style.equals(WARNING_TAG))
                {
                    displayWarning(message);
                }
                else if(style.equals(INFO_TAG))
                {
                    displayInfo(message);
                }
            }
            lastLine=message;
            nbLastLine=0;
        }
        else
        {
            nbLastLine++;
            out.flush();
        }
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
