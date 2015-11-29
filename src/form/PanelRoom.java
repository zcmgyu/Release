/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author zcmgyu
 */
public final class PanelRoom extends javax.swing.JPanel {

    /**
     * Creates new form PanelDRoom
     */
    public PanelRoom() {
        initComponents();
        initListRoom(null);
        initfloorModal();
        initStatus();
    }

    // Khởi tạo danh sách phòng
//    JXButton btnRoom;
    void initListRoom(String sql) {
        //        String sql = "SELECT COUNT(ID) AS RoomCount FROM tblRoom";
        if (sql == null) {
            sql = "SELECT * FROM tblRoom";
        }
        System.out.println("TRUOC RS");
        // Xoa truoc khi khoi tao
        pnListButton.removeAll();
        try (Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                System.out.println("Trong RS");
                // tạo 1 JPanel    
                JXPanel pnRoom = new JXPanel();
                pnRoom.setPreferredSize(new Dimension(80, 80));

                pnListButton.add(pnRoom);
                pnListButton.setPreferredSize(new Dimension(pnBottom.getWidth(), 1000));
                JXButton btnRoom = new JXButton();
                int roomID = rs.getInt(1);
//                System.out.println("IN RA SET ROOM ID: " + rs.getInt(1));
                String roomName = rs.getString(2);
                btnRoom.addActionListener((ActionEvent e) -> {
                        // Lay gia tri truoc khi tao ra Dialog Room Detail
//                        System.out.println("ROOM NAME: " + btnRoom.getText());
                        System.out.println("RoomID" + roomID);
                        //                    ShareData.getInstance().setRoomID(Integer.parseInt(btnRoom.getText()));
                        ShareData.getInstance().setCurrentRoomID(roomID);
                        ShareData.getInstance().setCurrentRoomName(roomName);

                        DialogRoomDetail drd = new DialogRoomDetail(null, true);
//                        DialogRoomDetail drd = ShareData.getInstance().getDrd();
                        drd.setLocationRelativeTo(this);
                        drd.setVisible(true);
                });
// 
                btnRoom.setText(Integer.toString(rs.getInt(2)));
                btnRoom.setPreferredSize(new Dimension(80, 80));
                
                pnRoom.setLayout(new BorderLayout());
                pnRoom.add(btnRoom, BorderLayout.CENTER);  
                
                // Kiểm tra tình trạng của phòng

                if (rs.getBoolean(5)) {
                    btnRoom.setBackground(Color.LIGHT_GRAY);
                } else {
                    btnRoom.setBackground(new Color(255, 102, 102));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelRoom.class.getName()).log(Level.SEVERE, null, ex);
        }

//        for (int i = 0; i < 100; i++) {
//            JXPanel pnRoom = new JXPanel();
//            pnRoom.setBackground(Color.BLACK);
//            pnRoom.setPreferredSize(new Dimension(80, 80));
//            pnListButton.add(pnRoom);
//            pnListButton.setPreferredSize(new Dimension(pnBottom.getWidth(), HEIGHT));
//            JXButton btnRoom = new JXButton(Integer.toString(i));
//            btnRoom.setPreferredSize(new Dimension(80, 80));
//            btnRoom.setBackground(Color.red);
//            pnRoom.setLayout(new BorderLayout());
//            pnRoom.add(btnRoom, BorderLayout.CENTER);
//        }
    }

    // Khởi tạo Tầng trong Combobox
    void initfloorModal() {
        cbbFloor.removeAllItems();
        cbbFloor.addItem(new ModalFloor(-1, "Tất cả"));
        String sql = "SELECT * FROM tblFloor";
        try (Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String floor = rs.getString(2);
                ModalFloor fm = new ModalFloor(id, floor);
                cbbFloor.addItem(fm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Khởi tạo Tầng trong Combobox
    void initStatus() {
        cbbStatus.removeAllItems();
        cbbStatus.addItem(new ModalStatus(-1, "Tất cả"));
        String sql = "SELECT * FROM tblStatus";
        try (Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String status = rs.getString(2);
                ModalStatus sm = new ModalStatus(id, status);
                cbbStatus.addItem(sm);

            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Bắt sự kiện nhấn vào một nút
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jXButton1 = new org.jdesktop.swingx.JXButton();
        jXButton2 = new org.jdesktop.swingx.JXButton();
        pnTop = new javax.swing.JPanel();
        cbbFloor = new javax.swing.JComboBox();
        cbbStatus = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tbnReport = new org.jdesktop.swingx.JXButton();
        pnBottom = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnListButton = new javax.swing.JPanel();

        jPanel1.setPreferredSize(new java.awt.Dimension(80, 80));

        jXButton1.setBackground(new java.awt.Color(255, 51, 51));
        jXButton1.setText("jXButton1");
        jXButton1.setPreferredSize(new java.awt.Dimension(80, 80));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jXButton2.setText("jXButton2");

        setLayout(new java.awt.BorderLayout());

        pnTop.setBackground(new java.awt.Color(255, 102, 102));

        cbbFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFloorActionPerformed(evt);
            }
        });

        cbbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbStatusActionPerformed(evt);
            }
        });

        jLabel1.setText("Tầng");

        jLabel2.setText("Tình trạng");

        tbnReport.setText("Quản lí báo hỏng");

        javax.swing.GroupLayout pnTopLayout = new javax.swing.GroupLayout(pnTop);
        pnTop.setLayout(pnTopLayout);
        pnTopLayout.setHorizontalGroup(
            pnTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cbbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addComponent(tbnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnTopLayout.setVerticalGroup(
            pnTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(tbnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pnTop, java.awt.BorderLayout.PAGE_START);

        pnBottom.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        pnListButton.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(pnListButton);

        javax.swing.GroupLayout pnBottomLayout = new javax.swing.GroupLayout(pnBottom);
        pnBottom.setLayout(pnBottomLayout);
        pnBottomLayout.setHorizontalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        pnBottomLayout.setVerticalGroup(
            pnBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
        );

        add(pnBottom, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    String sql;

    void resetSQL() {
        sql = "SELECT * FROM tblRoom WHERE 1 = 1";
    }

    void filter() {
        if (cbbFloor.getSelectedIndex() > 0) {
            System.out.println("CBB FLOOR: " + cbbFloor.getSelectedIndex());
            ModalFloor fm = (ModalFloor) cbbFloor.getSelectedItem();
            sql += " AND FloorID = " + fm.getId();
            System.out.println("SQL FLOOR: " + sql);
            this.updateUI();
        } else {
            initListRoom(sql);
            this.updateUI();
        }
        if (cbbStatus.getSelectedIndex() > 0) {
            System.out.println("CBB STATUS: " + cbbStatus.getSelectedIndex());
            ModalStatus sm = (ModalStatus) cbbStatus.getSelectedItem();
            sql += " AND StatusID = " + sm.getId();
            System.out.println("SQL STATUS: " + sql);
            this.updateUI();
        } else {
            initListRoom(sql);
            this.updateUI();
        }
        initListRoom(sql);
        resetSQL();
    }


    private void cbbFloorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFloorActionPerformed
        filter();
    }//GEN-LAST:event_cbbFloorActionPerformed

    private void cbbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbStatusActionPerformed
        filter();
    }//GEN-LAST:event_cbbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbbFloor;
    private javax.swing.JComboBox cbbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXButton jXButton1;
    private org.jdesktop.swingx.JXButton jXButton2;
    private javax.swing.JPanel pnBottom;
    private javax.swing.JPanel pnListButton;
    private javax.swing.JPanel pnTop;
    private org.jdesktop.swingx.JXButton tbnReport;
    // End of variables declaration//GEN-END:variables
}
