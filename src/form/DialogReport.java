/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author zcmgyu
 */
public final class DialogReport extends javax.swing.JDialog {

    /**
     * Creates new form
     *
     * @param parent
     * @param modal
     */
    public DialogReport(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtTitle.setBorder(ToolsDesign.comboundBorderAll());
        txtDescription.setBorder(ToolsDesign.comboundBorderAll());
        java.util.Date td = new Date(Calendar.getInstance().getTimeInMillis());
        cal.setDate(td);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        scrollDescription = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextPane();
        btnSend = new org.jdesktop.swingx.JXButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chỉnh sửa thông báo");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Báo hỏng");

        txtTitle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTitle.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Ngày xảy ra hỏng hóc");

        cal.setDateFormatString("dd - MM - yyyy");
        cal.setMaxSelectableDate(new java.util.Date(253370743299000L));
        cal.setMinSelectableDate(new java.util.Date(-62135791101000L));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel2.setText("Miêu tả chi tiết hỏng hóc");

        scrollDescription.setBorder(null);

        txtDescription.setBorder(null);
        scrollDescription.setViewportView(txtDescription);

        btnSend.setBackground(new java.awt.Color(255, 255, 255));
        btnSend.setBorder(null);
        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/send.png"))); // NOI18N
        btnSend.setOpaque(false);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(cal, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(98, 219, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollDescription, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTitle))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        Date date = new Date(cal.getDate().getTime());
        System.out.println("DATE: " + date);
        java.util.Date today = new Date(Calendar.getInstance().getTimeInMillis());
        String sql = "INSERT INTO tblReport VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        // INSERT INTO tblReport VALUES(1, N'Hỏng ống nước', '2015/11/15', N'Des', '2015/11/15', 1)
        if (!title.equals("") && !description.equals("") && date != null) {
            try (
                    Connection cn = Tools.getConn();
                    PreparedStatement ps = cn.prepareStatement(sql);
            ) {
                ps.setInt(1, ShareData.getInstance().getCurrentRoomID());
                ps.setString(2, title);
                ps.setDate(3, date);
                ps.setString(4, description);
                ps.setDate(5, (Date) today);
                ps.setInt(6, 1); // set Status
                ps.setInt(7, 0); // set Assignee
                ps.setString(8, "");
                ////////////////////////////
                int updated = ps.executeUpdate();
                if (updated > 0) {
                    JOptionPane.showMessageDialog(null, "Gửi thành công");
                    ShareData.getInstance().getPrt().initReport(null);
                    this.dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DialogReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bạn nhập chưa đầy đủ các mục", "Báo lỗi", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/image/alert.png")));
        }
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXButton btnSend;
    private com.toedter.calendar.JDateChooser cal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scrollDescription;
    private javax.swing.JTextPane txtDescription;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
