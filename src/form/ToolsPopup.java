/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author zcmgyu
 */
public class ToolsPopup {
    static void showSuccessPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Thành công",
                            JOptionPane.INFORMATION_MESSAGE, 
                            new ImageIcon(new Object().getClass().getResource("/image/checkbox-marked-circle.png")));
    }
    
    static void showErrorPopup(String message) {
        JOptionPane.showMessageDialog(null, message, "Báo lỗi",
                    JOptionPane.ERROR_MESSAGE, new ImageIcon(new Object().getClass().getResource("/image/alert.png")));
    }
}
