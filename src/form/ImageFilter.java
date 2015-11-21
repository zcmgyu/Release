/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author zcmgy
 */
public class ImageFilter extends FileFilter{

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) {
            return true;
        } else if ((f.getName().endsWith(".png")) || (f.getName().endsWith(".jpg"))
                || (f.getName().endsWith(".jpeg")) || (f.getName().endsWith(".JPG"))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "File Image";
    }
    
}
