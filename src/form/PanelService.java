/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author zcmgyu
 */
public final class PanelService extends javax.swing.JPanel {

    /**
     * Creates new form PanelService
     */
    public PanelService() {
        initComponents();
        String[] str = {"  Chưa trả  ", "  Đã trả  "};
        JComboBox cbbTable = new JComboBox(str);
        tblService.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(cbbTable));
        JTableHeader header = tblService.getTableHeader();
        header.setDefaultRenderer(new PanelService.HeaderRenderer(tblService));
        lblService.setText("Tiền phòng");
        initServiceDetail(sqlAll); // Load page 1
        initWidthTable();
        initCellAlign();
        initFloor();
        initRoom(null);
        initStatus();
        //
        btnRoomCharge.setSelected(true);
        btnRoomCharge.setBackground(new Color(0, 102, 255));
    }
    
    final void initFloor() {
        try (Connection cn = Tools.getConn();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tblFloor");
        ) {
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            dcbm.addElement(new ModalFloor(0, " Tất cả"));
            while (rs.next()) {
                int ID = rs.getInt(1);
                String floor = rs.getString(2);
                dcbm.addElement(new ModalFloor(ID, floor));
            }
            cbbFloor.setModel(dcbm);
        } catch (SQLException ex) {
            Logger.getLogger(DialogEditSevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    final void initRoom(String sql) {
        if (sql == null) {
            sql = "SELECT * FROM tblRoom";
        }
 
        try (Connection cn = Tools.getConn();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
        ) {
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            dcbm.addElement(new ModalRoom(0, " Tất cả"));
            while (rs.next()) {
                int id = rs.getInt(1);
                String roomName = rs.getString(2);
                dcbm.addElement(new ModalRoom(id, roomName));
            }
            cbbRoom.setModel(dcbm);
        } catch (SQLException ex) {
            Logger.getLogger(PanelService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    final void initStatus() {
        String sqlStatus = "SELECT * FROM tblRoom";
        try (Connection cn = Tools.getConn();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sqlStatus);
        ) {
            DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
            dcbm.addElement(new ModalStatus(-1, " Tất cả"));
            dcbm.addElement(new ModalStatus(1, " Đã đóng tiền"));
            dcbm.addElement(new ModalStatus(0, " Chưa đóng tiền"));
            cbbStatus.setModel(dcbm);
        } catch (SQLException ex) {
            Logger.getLogger(PanelService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int btnSelected = 1;
    
    String sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s\n" +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
    
    
    Vector vColumn;
    Vector vData;
    final void initServiceDetail(String sql) {
        
        vColumn = new Vector();
        vData = new Vector();
        System.out.println(sql);
        
        try (Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);) {
            ResultSetMetaData rsmd = rs.getMetaData();
            vColumn.add("Mã");
            vColumn.add("Số phòng");
            vColumn.add("Ngày trả");
            vColumn.add("Tiền trả");
            vColumn.add("Tình trạng");
            while (rs.next()) {
                Vector vRow = new Vector();
                vRow.add(rs.getString(1));
                vRow.add("Phòng " + rs.getString(2));
                vRow.add(rs.getString(3) + " - " + rs.getString(4));
                vRow.add(rs.getString(5) + " VNĐ");
                if (rs.getInt(6) == 0) {
                    vRow.add("Chưa thanh toán hóa đơn");
                } else {
                    vRow.add("Đã thanh toán");
                }
                vData.add(vRow);
            }
            tblService.setModel(new DefaultTableModel(vData, vColumn));
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentManagementForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void initCellAlign() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 5; i++) {
            tblService.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }
    }
    void initWidthTable() {
        TableColumnModel columnModel = tblService.getColumnModel();
        int[] width = {30, 70, 90, 100, 150};
        for (int i = 0; i < width.length - 1; i++) {
            columnModel.getColumn(i).setPreferredWidth(width[i]);
        }
    }
    
    private static class HeaderRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;
        public HeaderRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            Component component = renderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, col);
            component.setFont(component.getFont().deriveFont(Font.BOLD));
            return component;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAdd = new org.jdesktop.swingx.JXButton();
        btnEdit = new org.jdesktop.swingx.JXButton();
        btnDelete = new org.jdesktop.swingx.JXButton();
        btnRoomCharge = new org.jdesktop.swingx.JXButton();
        btnElectric = new org.jdesktop.swingx.JXButton();
        btnWater = new org.jdesktop.swingx.JXButton();
        btnCleaning = new org.jdesktop.swingx.JXButton();
        btnParking = new org.jdesktop.swingx.JXButton();
        cbbFloor = new javax.swing.JComboBox<>();
        cbbRoom = new javax.swing.JComboBox<>();
        cbbStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblService = new org.jdesktop.swingx.JXTable();
        lblService = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 458));

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setBorder(null);
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/plus_1.png"))); // NOI18N
        btnAdd.setOpaque(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        btnEdit.setMaximumSize(new java.awt.Dimension(75, 36));
        btnEdit.setMinimumSize(new java.awt.Dimension(75, 36));
        btnEdit.setOpaque(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/close.png"))); // NOI18N
        btnDelete.setOpaque(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRoomCharge.setBackground(new java.awt.Color(255, 255, 255));
        btnRoomCharge.setText("Tiền phòng");
        btnRoomCharge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRoomChargeMousePressed(evt);
            }
        });
        btnRoomCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoomChargeActionPerformed(evt);
            }
        });

        btnElectric.setBackground(new java.awt.Color(255, 255, 255));
        btnElectric.setText("Tiền điện");
        btnElectric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnElectricMousePressed(evt);
            }
        });
        btnElectric.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElectricActionPerformed(evt);
            }
        });

        btnWater.setBackground(new java.awt.Color(255, 255, 255));
        btnWater.setText("Tiền nước");
        btnWater.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnWaterMousePressed(evt);
            }
        });
        btnWater.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWaterActionPerformed(evt);
            }
        });

        btnCleaning.setBackground(new java.awt.Color(255, 255, 255));
        btnCleaning.setText("Tiền vệ sinh");
        btnCleaning.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCleaningMousePressed(evt);
            }
        });
        btnCleaning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleaningActionPerformed(evt);
            }
        });

        btnParking.setBackground(new java.awt.Color(255, 255, 255));
        btnParking.setText("Tiền giữ xe");
        btnParking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnParkingMouseReleased(evt);
            }
        });
        btnParking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParkingActionPerformed(evt);
            }
        });

        cbbFloor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbFloor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbFloorItemStateChanged(evt);
            }
        });
        cbbFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFloorActionPerformed(evt);
            }
        });

        cbbRoom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbRoomActionPerformed(evt);
            }
        });

        cbbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbStatusActionPerformed(evt);
            }
        });

        jLabel1.setText("Tầng");

        jLabel2.setText("Phòng");

        jLabel3.setText("Tình trạng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRoomCharge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnElectric, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWater, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCleaning, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(btnParking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbFloor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(btnRoomCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnElectric, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnWater, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCleaning, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnParking, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCleaning, btnElectric, btnParking, btnRoomCharge, btnWater});

        add(jPanel1, java.awt.BorderLayout.LINE_END);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setOpaque(false);

        tblService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblService.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tblService.setEditable(false);
        tblService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblServiceMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblService);

        lblService.setText("jLabel4");
        lblService.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblService, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tblServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServiceMouseClicked
        System.out.println("Sự kiện khi click chuột vào jtable");
        int rowIndex = tblService.getSelectedRow();
        // Giải thích: vì khi nhấn vào nút để nó Sort thì nó sẽ get nhầm giá trị như lúc trước khi sort
        int rowModel = tblService.convertRowIndexToModel(rowIndex);
        int currentUseServiceID = Integer.parseInt((String) tblService.getModel().getValueAt(rowModel, 0));
        System.out.println("CurrentUseServiceID: " 
                + Integer.parseInt((String) tblService.getModel().getValueAt(rowModel, 0)));
        ShareData.getInstance().setCurrentUseServiceID(currentUseServiceID);
    }//GEN-LAST:event_tblServiceMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        DialogAddService service = new DialogAddService(null, true);
        service.setLocationRelativeTo(this);
        service.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int rowId = tblService.getSelectedRow();
        if (rowId > -1) {
            DialogEditSevice editSevice = new DialogEditSevice(null, true, sqlSend);
            editSevice.setLocationRelativeTo(this);
            editSevice.setVisible(true);
        } else {
            ToolsPopup.showErrorPopup("Vui lòng chọn dịch vụ để chỉnh sửa");
        }
    }//GEN-LAST:event_btnEditActionPerformed
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int rowId = tblService.getSelectedRow();
        if (rowId > -1) {
            int response = ToolsPopup.showConfirmPopup("Bạn có chắc chắn xóa người này");
            switch (response) {
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.YES_OPTION:
                    String sql = "DELETE FROM tblUseService WHERE ID = " 
                        + ShareData.getInstance().getCurrentUseServiceID();
                    System.out.println(sql);
                    try (Connection cn = Tools.getConn();
                        PreparedStatement pst = cn.prepareStatement(sql);) {
                        pst.executeUpdate();
                        switch(ShareData.getInstance().getCurrentBtn()) {
                            case 1: 
                                ShareData.getInstance().getPs().btnRoomCharge.setSelected(true);
                                ShareData.getInstance().getPs().btnRoomCharge.setBackground(new Color(0, 102, 255));
                                break;
                            case 2: 
                                ShareData.getInstance().getPs().btnElectric.setSelected(true);
                                ShareData.getInstance().getPs().btnElectric.setBackground(new Color(0, 102, 255));
                                ShareData.getInstance().getPs().btnRoomCharge.setSelected(false);
                                ShareData.getInstance().getPs().btnRoomCharge.setBackground(Color.WHITE);
                                break;
                            case 3: 
                                ShareData.getInstance().getPs().btnWater.setSelected(true);
                                ShareData.getInstance().getPs().btnWater.setBackground(new Color(0, 102, 255));
                                ShareData.getInstance().getPs().btnRoomCharge.setSelected(false);
                                ShareData.getInstance().getPs().btnRoomCharge.setBackground(Color.WHITE);
                                break;
                            case 4: 
                                ShareData.getInstance().getPs().btnCleaning.setSelected(true);
                                ShareData.getInstance().getPs().btnCleaning.setBackground(new Color(0, 102, 255));
                                ShareData.getInstance().getPs().btnRoomCharge.setSelected(false);
                                ShareData.getInstance().getPs().btnRoomCharge.setBackground(Color.WHITE);
                                break;
                            case 5: 
                                ShareData.getInstance().getPs().btnParking.setSelected(true);
                                ShareData.getInstance().getPs().btnParking.setBackground(new Color(0, 102, 255));
                                ShareData.getInstance().getPs().btnRoomCharge.setSelected(false);
                                ShareData.getInstance().getPs().btnRoomCharge.setBackground(Color.WHITE);
                                break;
                        }
//                        ShareData.getInstance().getPs().initServiceDetail(
//                                "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
//                                "FROM tblRoom r, tblUseService us, tblService s\n" +
//                                "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = " 
//                                        + ShareData.getInstance().getCurrentServiceID());
                        ShareData.getInstance().getPs().initServiceDetail(sqlSend);
                        ShareData.getInstance().getPs().initWidthTable();
                        ShareData.getInstance().getPs().initCellAlign();
                        ToolsPopup.showSuccessPopup("Xóa thành công");
                    } catch (SQLException ex) {
                        Logger.getLogger(ApartmentManagementForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case JOptionPane.CLOSED_OPTION:
                    System.out.println("JOptionPane closed");
                    break;
                default:
                    break;
            }
        } else {
            ToolsPopup.showErrorPopup("Vui lòng chọn người để xóa");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnElectricActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElectricActionPerformed
        lblService.setText("Tiền điện");
        ShareData.getInstance().setCurrentServiceID(2);
        tblService.removeAll();
        btnRoomCharge.setSelected(false);
        btnElectric.setSelected(true);
        btnWater.setSelected(false);
        btnCleaning.setSelected(false);
        btnParking.setSelected(false);
        btnSelected = 2;
        sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s " +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
        initServiceDetail(sqlAll);
        System.out.println("SQL ALL NEW ======= : " + sqlAll);
        initWidthTable();
        initCellAlign();
    }//GEN-LAST:event_btnElectricActionPerformed

    private void btnRoomChargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomChargeActionPerformed
        lblService.setText("Tiền phòng");
        initFloor();
        initRoom(null);
        initFloor();
        ShareData.getInstance().setCurrentServiceID(1);
        tblService.removeAll();
        btnRoomCharge.setSelected(true);
        btnElectric.setSelected(false);
        btnWater.setSelected(false);
        btnCleaning.setSelected(false);
        btnParking.setSelected(false);
        btnSelected = 1;
        sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s " +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
        initServiceDetail(sqlAll);
        initWidthTable();
        initCellAlign();
    }//GEN-LAST:event_btnRoomChargeActionPerformed

    private void btnWaterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWaterActionPerformed
        lblService.setText("Tiền nước");
        initFloor();
        initRoom(null);
        initFloor();
        ShareData.getInstance().setCurrentServiceID(3);
        tblService.removeAll();
        btnRoomCharge.setSelected(false);
        btnElectric.setSelected(false);
        btnWater.setSelected(true);
        btnCleaning.setSelected(false);
        btnParking.setSelected(false);
        btnSelected = 3;
        sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s " +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
        ShareData.getInstance().getPs().initServiceDetail(sqlAll);
        initWidthTable();
        initCellAlign();
    }//GEN-LAST:event_btnWaterActionPerformed

    private void btnCleaningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleaningActionPerformed
        lblService.setText("Tiền vệ sinh");
        initFloor();
        initRoom(null);
        initFloor();
        ShareData.getInstance().setCurrentServiceID(4);
        tblService.removeAll();
        btnRoomCharge.setSelected(false);
        btnElectric.setSelected(false);
        btnWater.setSelected(false);
        btnCleaning.setSelected(true);
        btnParking.setSelected(false);
        btnSelected = 4;
        sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s " +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
        initServiceDetail(sqlAll);
        initWidthTable();
        initCellAlign();
    }//GEN-LAST:event_btnCleaningActionPerformed

    private void btnParkingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParkingActionPerformed
        lblService.setText("Tiền giữ xe");
        initFloor();
        initRoom(null);
        initFloor();
        ShareData.getInstance().setCurrentServiceID(5);
        tblService.removeAll();
        btnRoomCharge.setSelected(false);
        btnElectric.setSelected(false);
        btnWater.setSelected(false);
        btnCleaning.setSelected(false);
        btnParking.setSelected(true);
        btnSelected = 5;
        sqlAll = "SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s " +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = "
                + btnSelected + " ORDER BY r.Room, us.[Month], us.[Year]";
        initServiceDetail(sqlAll);
        ShareData.getInstance().getPs().initServiceDetail(sqlAll);  
        initWidthTable();
        initCellAlign();
    }//GEN-LAST:event_btnParkingActionPerformed

    private void btnRoomChargeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoomChargeMousePressed
        btnRoomCharge.setBackground(new Color(0, 153, 255));
        btnElectric.setBackground(Color.WHITE);
        btnWater.setBackground(Color.WHITE);
        btnCleaning.setBackground(Color.WHITE);
        btnParking.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnRoomChargeMousePressed

    private void btnElectricMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElectricMousePressed
        btnRoomCharge.setBackground(Color.WHITE);
        btnElectric.setBackground(new Color(0, 153, 255));
        btnWater.setBackground(Color.WHITE);
        btnCleaning.setBackground(Color.WHITE);
        btnParking.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnElectricMousePressed

    private void btnWaterMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWaterMousePressed
        btnRoomCharge.setBackground(Color.WHITE);
        btnElectric.setBackground(Color.WHITE);
        btnWater.setBackground(new Color(0, 153, 255));
        btnCleaning.setBackground(Color.WHITE);
        btnParking.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnWaterMousePressed

    private void btnCleaningMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCleaningMousePressed
        btnRoomCharge.setBackground(Color.WHITE);
        btnElectric.setBackground(Color.WHITE);
        btnWater.setBackground(Color.WHITE);
        btnCleaning.setBackground(new Color(0, 153, 255));
        btnParking.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCleaningMousePressed

    private void btnParkingMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnParkingMouseReleased
        btnRoomCharge.setBackground(Color.WHITE);
        btnElectric.setBackground(Color.WHITE);
        btnWater.setBackground(Color.WHITE);
        btnCleaning.setBackground(Color.WHITE);
        btnParking.setBackground(new Color(0, 153, 255));
    }//GEN-LAST:event_btnParkingMouseReleased
    
    String sqlSend;
    StringBuffer sqlFilter;
    void filter(){
        sqlFilter  = new StringBuffer("SELECT us.ID, r.Room, us.[Month], us.[Year], us.Expense, us.[Status]\n" +
                 "FROM tblRoom r, tblUseService us, tblService s\n" +
                 "WHERE r.ID = us.RoomID AND s.ID = us.ServiceID AND s.ID = " 
                + btnSelected);
        
        if (cbbFloor.getSelectedIndex() > 0) {
            System.out.println("CBB Floor: " + cbbFloor.getSelectedIndex());
            ModalFloor mf = (ModalFloor) cbbFloor.getSelectedItem();
            sqlFilter.append(" AND r.FloorID = ").append(mf.getId());
        }
        if(cbbRoom.getSelectedIndex()> 0){
            ModalRoom mr = (ModalRoom) cbbRoom.getSelectedItem();
            sqlFilter.append(" AND r.ID = ").append(mr.getId());
        }
        if(cbbStatus.getSelectedIndex()> 0){
            ModalStatus ms = (ModalStatus) cbbStatus.getSelectedItem();
            sqlFilter.append(" AND us.[Status] = ").append(ms.getId());
        }
        System.out.println("sqlStatus: " + sqlFilter);
        sqlFilter.append("\nORDER BY r.Room, us.[Month], us.[Year]");
        sqlSend = sqlFilter.toString();
        initServiceDetail(sqlFilter.toString());
        initWidthTable();
        initCellAlign();
    }
    private void cbbFloorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFloorActionPerformed
        filter();
    }//GEN-LAST:event_cbbFloorActionPerformed

    private void cbbRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbRoomActionPerformed
        filter();
    }//GEN-LAST:event_cbbRoomActionPerformed

    private void cbbFloorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbFloorItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String sqlRoom = "SELECT * FROM tblRoom WHERE FloorID = ";
            ModalFloor mf = (ModalFloor) cbbFloor.getSelectedItem();
            switch (mf.getId()) {
                case 0:
                    initRoom("SELECT * FROM tblRoom");
                    break;
                case 1: 
                    initRoom(sqlRoom + 1);
                    System.out.println("------SOUT: SQLROOM " + sqlRoom + 1);
                    break;
                case 2:
                    initRoom(sqlRoom + 2);
                    break;
                case 3: 
                    initRoom(sqlRoom + 3);
                    break;
                case 4:
                    initRoom(sqlRoom + 4);
                    break;
                case 5:
                    initRoom(sqlRoom + 5);
                    break;
                case 6:
                    initRoom(sqlRoom + 6);
                    break;
                case 7: 
                    initRoom(sqlRoom + 7);
                    break;
                default:
                    initRoom(sqlRoom);
                    break;
            }
        }
    }//GEN-LAST:event_cbbFloorItemStateChanged

    private void cbbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbStatusActionPerformed
        filter();
    }//GEN-LAST:event_cbbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXButton btnAdd;
    public org.jdesktop.swingx.JXButton btnCleaning;
    private org.jdesktop.swingx.JXButton btnDelete;
    private org.jdesktop.swingx.JXButton btnEdit;
    public org.jdesktop.swingx.JXButton btnElectric;
    public org.jdesktop.swingx.JXButton btnParking;
    public org.jdesktop.swingx.JXButton btnRoomCharge;
    public org.jdesktop.swingx.JXButton btnWater;
    private javax.swing.JComboBox<String> cbbFloor;
    private javax.swing.JComboBox<String> cbbRoom;
    private javax.swing.JComboBox<String> cbbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblService;
    private org.jdesktop.swingx.JXTable tblService;
    // End of variables declaration//GEN-END:variables
}
