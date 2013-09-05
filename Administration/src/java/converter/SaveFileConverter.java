/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import bean.data.DataManager;
import converter.struct.FileConverter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Brian GOHIER
 */
@ManagedBean(name = "saveFileConverter")
@RequestScoped
public class SaveFileConverter extends FileConverter
{

    public SaveFileConverter()
    {
        super(DataManager.getFilesInPath());
    }
}
