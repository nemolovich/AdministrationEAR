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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
    private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final DateFormat formatFull = new SimpleDateFormat(
            "EEEEE dd MMMMM yyyy à HH:mm:ss",
            Locale.FRANCE);
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
        header+="## Date de création:        "+date()+"                ##\r\n";
        header+=HUGE_SEPARATOR+"\r\n";
        out.print(header);
        out.flush();
    }
    
    private static String getStyleFrom(String message)
    {
        return getStyleFrom(message, null, null);
    }
    
    private static String getStyleFrom(String message, String style)
    {
        return getStyleFrom(message, style, null);
    }
    
    private static String getStyleFrom(String message, String style, String date)
    {
        String classe="info";
        String div="";
        String spanClass="";
        if(date!=null)
        {
            try
            {
                Date d=format.parse(date.substring(1, date.length()-1));
                date=formatFull.format(d);
                spanClass+=" icon_info\" title=\""+(style!=null?style+": ":"")+date;
            }
            catch (ParseException ex)
            {
                displayError("Impossible de parser la date '"+date+"'", null);
            }
        }
        spanClass+="\"/>\n";
        if(style==null&&lastStyle!=null)
        {
            div+=
            "        </ul><!-- pour style"+lastStyle+"-->\n"+
            "    </div>\n"+
            "</div>\n";
            lastStyle=null;
        }
        else if(style!=null&&style.equals(WARNING_TAG))
        {
            classe="warn";
        }
        else if(style!=null&&style.equals(ERROR_TAG))
        {
            classe="error";
        }
        if(style!=null&&lastStyle!=null&&!lastStyle.equals(classe))
        {
            div+=
            "        </ul>\n"+
            "    </div>\n"+
            "</div><!-- Fermeture du style\"+lastStyle+\"-->\n";
            div+=
            "<div class=\"ui-messages ui-widget\">\n"+
            "    <div class=\"ui-messages-"+classe+" ui-corner-all\">\n"+
            "        <span class=\"ui-messages-"+classe+"-icon"+spanClass+
            "        <ul>\n";
        }
        else if(style!=null&&lastStyle==null)
        {
            div+=
            "<div class=\"ui-messages ui-widget\">\n"+
            "    <div class=\"ui-messages-"+classe+" ui-corner-all\">\n"+
            "        <span class=\"ui-messages-"+classe+"-icon"+spanClass+
            "        <ul>\n";
        }
        if(style!=null)
        {
            div+=
            "            <li>\n"+
            "                <span class=\"ui-messages-"+classe+"-summary\">\n";
            lastStyle=style;
        }
        div+=message.replaceAll("''", "&apos;")+"\n";
        if(style!=null)
        {
            div+=
            "                </span>\n"+
            "            </li>\n";
        }
        return div;
    }
    
    public static String getHTMLLog()
    {
        lastStyle=null;
        FileOutputStream fileWriter = null;
        Reader reader = null;
        boolean inTab=false;
        String logHTML="";
        String logLine="";
        int details_report_id=0;
        boolean inDetails=false;
        try
        {
            FileInputStream fileReader=new FileInputStream(logFile);
            reader=new BufferedReader(new InputStreamReader(fileReader,"UTF8"));
            int c;
            while((c=reader.read())!=-1)
            {
                if(c=='\n')
                {
                    if(logLine.equals(BIG_SEPARATOR))
                    {
                        logHTML+=getStyleFrom("\n<hr class=\"big_sep\"/>\n\n",null);
                    }
                    else if(logLine.equals(SEPARATOR))
                    {
                        logHTML+=getStyleFrom("\n<hr class=\"sep\"/>\n\n",null);
                    }
                    else if(logLine.equals(SMALL_SEPARATOR))
                    {
                        if(inDetails)
                        {
                            logHTML+=
    "                    </span>\n" +
    "                </span>\n" +
    "                <script id=\"details_report_"+details_report_id+"_s\" type=\"text/javascript\">\n" +
    "                    PrimeFaces.cw('Inplace','details_report_"+details_report_id+"_js',\n" +
    "                                {id:'details_report_"+details_report_id+"',\n" +
    "                                    effect:'slide',\n" +
    "                                    effectSpeed:'normal',\n" +
    "                                    event:'click',\n" +
    "                                    toggleable:true\n" +
    "                                });\n" +
    "                </script>";
                            details_report_id++;
                        }
                        logHTML+=getStyleFrom("\n<hr class=\"small_sep\"/>\n\n",null);
                    }
                    else if(logLine.equals(HUGE_SEPARATOR))
                    {
                        inTab^=true;
                        if(inTab)
                        {
                            logHTML+="<TABLE class=\"infos_header\">\n";
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
                            if((value.startsWith("'")&&value.endsWith("'"))||
                                    (value.startsWith("[")&&value.endsWith("]")))
                            {
                                value=value.substring(1,value.length()-1);
                            }
                            if(inTab)
                            {
                                logHTML+=
                                    "    <TR>\n" +
                                    "        <TD class=\"label\">"+field+":</TD>\n" +
                                    "        <TD class=\"fields\">"+value+"</TD>\n" +
                                    "    </TR>\n";
                            }
                            else
                            {
                                logHTML+=getStyleFrom("COMMENT: Field: "+field+", value: "+value+"<br/>\n");
                            }
                        }
                        else
                        {
                            if(inTab)
                            {
                                logHTML+=
                                    "    <TR>\n" +
                                    "        <TD class=\"label\" colspan=\"2\">\n"+
                                    logLine+
                                    "        </TD>\n" +
                                    "    </TR>\n";
                            }
                            else
                            {
                                logHTML+=getStyleFrom("COMMENT: "+logLine+"<br/>\n");
                            }
                        }
                    }
                    else if(logLine.length()>21&&logLine.substring(22).startsWith(INFO_TAG+": "))
                    {
                        logHTML+=getStyleFrom(logLine.substring(24+INFO_TAG.length()), INFO_TAG, logLine.substring(0, 21));
                    }
                    else if(logLine.length()>21&&logLine.substring(22).startsWith(WARNING_TAG+": "))
                    {
                        logHTML+=getStyleFrom(logLine.substring(24+WARNING_TAG.length()), WARNING_TAG, logLine.substring(0, 21));
                    }
                    else if(logLine.length()>21&&logLine.substring(22).startsWith(ERROR_TAG+": "))
                    {
                        logHTML+=getStyleFrom(logLine.substring(24+ERROR_TAG.length()), ERROR_TAG, logLine.substring(0, 21));
                    }
                    else if(logLine.startsWith("\tCause: "))
                    {
                        logHTML+=
    "                "+logLine+"<br/>\n" +
    "                Détails de l'erreur: \n" +
    "                <span id=\"details_report_"+details_report_id+"\" class=\"ui-inplace ui-hidden-container\">\n" +
    "                    <span id=\"details_report_"+details_report_id+"_display\" class=\"ui-inplace-display\" style=\"background:none;display:inline\">\n" +
    "                        <a href=\"#\" class=\"blankLink\" title=\"Afficher les détails de l'erreur\">Afficher</a>\n" +
    "                    </span><br/>\n" +
    "                    <span id=\"details_report_"+details_report_id+"_content\" class=\"ui-inplace-content\" style=\"display:none\">\n" +
    "                        <a href=\"#\" class=\"blankLink\" name=\"details_report_"+details_report_id+"_hide\" title=\"Masquer les détails\"\n" +
    "                            onclick=\"details_report_"+details_report_id+"_js.hide();\" >Masquer</a>\n<br/>\n";
                        inDetails=true;
                    }
                    else if(logLine.startsWith("\tObjet: \""))
                    {
                        logHTML+=
    "                Détails de l'objet: \n" +
    "                <span id=\"details_report_"+details_report_id+"\" class=\"ui-inplace ui-hidden-container\">\n" +
    "                    <span id=\"details_report_"+details_report_id+"_display\" class=\"ui-inplace-display\" style=\"background:none;display:inline\">\n" +
    "                        <a href=\"#\" class=\"blankLink\" title=\"Afficher les détails de l'objet\">Afficher</a>\n" +
    "                    </span><br/>\n" +
    "                    <span id=\"details_report_"+details_report_id+"_content\" class=\"ui-inplace-content\" style=\"display:none\">\n" +
    "                        <a href=\"#\" class=\"blankLink\" name=\"details_report_"+details_report_id+"_hide\" title=\"Masquer les détails\"\n" +
    "                            onclick=\"details_report_"+details_report_id+"_js.hide();\" >Masquer</a>\n<br/>\n" +
    "                            "+logLine.substring(9)+"\n<br/>";
                            
                        inDetails=true;
                    }
                    else if(logLine.startsWith("\t[INSIDE] ")||logLine.startsWith("\t[DELETED]"))
                    {
                        
                        if(inDetails)
                        {
                            logHTML+=
    "                    </span>\n" +
    "                </span>\n" +
    "                <script id=\"details_report_"+details_report_id+"_s\" type=\"text/javascript\">\n" +
    "                    PrimeFaces.cw('Inplace','details_report_"+details_report_id+"_js',\n" +
    "                                {id:'details_report_"+details_report_id+"',\n" +
    "                                    effect:'slide',\n" +
    "                                    effectSpeed:'normal',\n" +
    "                                    event:'click',\n" +
    "                                    toggleable:true\n" +
    "                                });\n" +
    "                </script>";
                            details_report_id++;
                        }
                        logHTML+=logLine.substring(10).replaceAll("''","'")+"<br/>\n\r";
                    }
                    else
                    {
                        logHTML+=logLine+"<br/>\n";
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
            displayError("Impossible de trouver le fichier journal", ex);
        }
        catch (UnsupportedEncodingException ex)
        {
            displayError("Le type d'encodage du fichier journal est incorrect", ex);
        }
        catch (IOException ex)
        {
            displayError("Erreur (I/O) lors de la lecture du fichier journal", ex);
        }
        finally
        {
            if(reader!=null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ex)
                {
                    displayError("Erreur lors de la fermeture du fichier journal", ex);
                }
            }
        }
//        try {
//            fileWriter = new FileOutputStream(new File("page.html"), false);
//            Writer writer=new BufferedWriter(new OutputStreamWriter(fileWriter,"UTF8"));
//            PrintWriter htmlFile=new PrintWriter(writer);
//            htmlFile.print(logHTML);
//            htmlFile.flush();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fileWriter.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
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
            write(SEPARATOR+"\r\n");
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
//            try
//            {
//                int n=Integer.parseInt("STRING");
//            }
//            catch (NumberFormatException ex)
//            {
//                writeError("Un test d'erreur", ex);
//            }
            return true;
        }
        return false;
    }
    
    public static boolean stopWrite()
    {
        write("\r\n"+SEPARATOR);
        writeInfo("Fin des écritures dans le journal");
        write(BIG_SEPARATOR);
        out.close();
        return false;
    }
    
    public static void destroy()
    {
        ApplicationLogger.stopWrite();
        ApplicationLogger.displayInfo("Fin d'enregistrement du journal");
    }
    
    public static void addSmallSep()
    {
        write(SMALL_SEPARATOR);
    }
    
    public static void addSep()
    {
        write(SEPARATOR);
    }
    
    public static void addBigSep()
    {
        write(BIG_SEPARATOR);
    }
    
    public static void addHugegSep()
    {
        write(HUGE_SEPARATOR);
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
        write(SMALL_SEPARATOR);
        write(message, ERROR_TAG, ex);
        write(SMALL_SEPARATOR);
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
            for(int i=before.length();i<26;i++)
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
