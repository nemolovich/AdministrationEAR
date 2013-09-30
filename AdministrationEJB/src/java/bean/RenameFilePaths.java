/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bean.facade.FilePathFacade;
import entity.FilePath;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author COMPUMONSTER
 */
@Named(value="renameFilePaths")
@SessionScoped
public class RenameFilePaths implements Serializable
{
    @EJB
    private FilePathFacade facade;
    
    public String replaceAll()
    {
        for(FilePath fp:this.facade.findAll())
        {
            String path=fp.getFilePath();
            if(path.contains("\\"))
            {
                System.err.println("Before: "+fp.getFilePath());
                fp.setFilePath(path.replaceAll("\\\\", "/"));
                System.err.println("After: "+fp.getFilePath());
                this.facade.edit(fp);
            }
        }
        return "/index";
    }
}
