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
public class DesignTools {
    public static void comboundBorderLF(CompoundBorder cb) {
        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
        Border empty = new EmptyBorder(0, 10, 0, 10); 
        cb = new CompoundBorder(line, empty);
    }
    public static void comboundBorderAll(CompoundBorder cb) {
        Border line = BorderFactory.createLineBorder(Color.lightGray, 2);
        Border empty = new EmptyBorder(10, 10, 10, 10); 
        cb = new CompoundBorder(line, empty);
    }
    
    
}
