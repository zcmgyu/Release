/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author zcmgyu
 */
public class ToolsDesign {
//    public static void comboundBorderLF(CompoundBorder cb) {
//        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
//        Border empty = new EmptyBorder(0, 10, 0, 10); 
//        cb = new CompoundBorder(line, empty);
//    }
    public static CompoundBorder comboundBorderAll() {
        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
        Border empty = new EmptyBorder(5, 10, 5, 10); 
        return new CompoundBorder(line, empty);
    }
    public static CompoundBorder compoundBorderLabel() {
        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
        Border empty = new EmptyBorder(0, 0, 0, 0); 
        return new CompoundBorder(line, empty);
    }
    
    public static CompoundBorder compoundBorderForJXSearchField() {
        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
        Border empty = new EmptyBorder(5, 10, 5, 3); 
        return new CompoundBorder(line, empty);
    }
    
    
}
