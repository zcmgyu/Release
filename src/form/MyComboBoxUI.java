/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * http://www.java-forums.org/awt-swing/24577-changing-border-jcombobox.html
 * @author zcmgyu
 */

public class MyComboBoxUI extends WindowsComboBoxUI {
    protected ComboPopup createPopup() {
        BasicComboPopup bcp = (BasicComboPopup) super.createPopup();
 
        // set the border around the popup
//        bcp.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        
        
 
        //there is an inner border around the list, insdie the scroller
        //    it can be set thus:
//         JList list = bcp.getList();
//         list.setBorder(BorderFactory.createLineBorder(Color.green, 2));
        

        bcp.setBorder(new EmptyBorder(0, 10, 0, 0));
        bcp.setOpaque(true);
        return bcp;
    }
}
