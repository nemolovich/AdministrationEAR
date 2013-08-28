package converter;

import bean.log.ApplicationLogger;
import java.io.File;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Brian GOHIER
 */
@ManagedBean
@RequestScoped
public class FileConverter implements Converter
{

    public FileConverter()
    {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
        String value)
    {
        for(File file: ApplicationLogger.getFilesInPath())
        {
            if(value!=null&&file.getName().equals(value))
            {
                return file;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
        Object value)
    {
        for(File file:ApplicationLogger.getFilesInPath())
        {
            if(value!=null&&file.equals(value))
            {
                return file.getName();
            }
        }
        return value==null?"":null;
    }
    
}