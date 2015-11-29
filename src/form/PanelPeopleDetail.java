/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public final class PanelPeopleDetail extends javax.swing.JPanel {

    /**
     * Creates new form ResidentManager
     */
    Vector vColumn;
    Vector vData;
    int rowModel;
    

    public PanelPeopleDetail() {
        initComponents();
        initPeopleDetail(null);
        initWidthTable();
        JTableHeader header = tblPeopleDetail.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(tblPeopleDetail));
        initCellAlign();
        ///
        initCbbGender();
        initCbbFloor();
//        initCbbTotal();
        // Chinh border
        txtSearch.setBorder(ToolsDesign.comboundBorderAll());
        searchfield.setBorder(ToolsDesign.compoundBorderForJXSearchField());
        searchfield.setFindPopupMenu(popup);
        
    }

    void initCellAlign() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblPeopleDetail.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        tblPeopleDetail.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
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

    void initWidthTable() {
        TableColumnModel columnModel = tblPeopleDetail.getColumnModel();
        int[] width = {40, 150, 80, 70, 80, 80, 80, 80, 200, 80, 80};
        for (int i = 0; i < width.length; i++) {
            columnModel.getColumn(i).setPreferredWidth(width[i]);
        }
    }


    final void initPeopleDetail(String sql) {
        vColumn = new Vector();
        vData = new Vector();
        if (sql == null) {
            sql = "select h.ID, h.Name [Họ và tên], h.Birthday [Ngày sinh], h.Gender [Giới tính],"
                    + " h.Birthplace [Nơi sinh], h.[Native Country] [Dân tộc], h.Nation [Quốc tịch],"
                    + " h.Occupation [Nghề nghiệp], h.Workplace [Nơi công tác],"
                    + " h.[Date of arrival] [Ngày ở],  r.Room [Số phòng]\n"
                    + "from tblHuman h, tblRoom r \n"
                    + "where h.RoomID = r.id";
        }
        System.out.println(sql);
        try (Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);) {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                vColumn.add(rsmd.getColumnName(i));
            }
            while (rs.next()) {
                Vector vRow = new Vector();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i == 4) {
                        vRow.add((rs.getInt(4) == 1) ? "Nam" : "Nữ");
                    } else {
                        vRow.add(rs.getString(i));
                    }
                }
                vData.add(vRow);
            }
            tblPeopleDetail.setModel(new DefaultTableModel(vData, vColumn));
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentManagementForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void initCbbGender(){
        cbbGender.removeAllItems();
        cbbGender.addItem(new ModalGender(-1, "  Giới tính  " ));
        cbbGender.addItem(new ModalGender(0, "  Nữ  "));
        cbbGender.addItem(new ModalGender(1, "  Nam  "));
    }
    
    void initCbbFloor(){
        cbbFloor.removeAllItems();
        cbbFloor.addItem(new ModalFloor(-1, " Tầng "));
        String sql = "SELECT * FROM tblFloor";
        try(    Connection cn = Tools.getConn();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql)
                )
        {
            while (rs.next()){
                int id = rs.getInt(1);
                String floorName = rs.getString(2);
                ModalFloor mf = new ModalFloor(id, floorName);
                cbbFloor.addItem(mf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PanelPeopleDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    void initCbbTotal(){
//        cbbTotal.removeAllItems();
//        cbbTotal.addItem("   Tất cả ");
//        cbbTotal.addItem("   Họ và tên ");
//        cbbTotal.addItem("   Nơi sinh ");
//        cbbTotal.addItem("   Dân tộc ");
//        cbbTotal.addItem("   Quốc tịch ");
//        cbbTotal.addItem("   Nghề nghiệp ");
//        cbbTotal.addItem("   Nơi công tác ");
//    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popup = new javax.swing.JPopupMenu();
        mnName = new javax.swing.JCheckBoxMenuItem();
        mnBirthPlace = new javax.swing.JCheckBoxMenuItem();
        mnNativeCountry = new javax.swing.JCheckBoxMenuItem();
        mnNation = new javax.swing.JCheckBoxMenuItem();
        mnOccupation = new javax.swing.JCheckBoxMenuItem();
        mnWorkPlace = new javax.swing.JCheckBoxMenuItem();
        txtSearch = new org.jdesktop.swingx.JXTextField();
        cbbTotal = new javax.swing.JComboBox();
        pnlFooter = new javax.swing.JPanel();
        btnAdd = new org.jdesktop.swingx.JXButton();
        btnEdit = new org.jdesktop.swingx.JXButton();
        btnDelete = new org.jdesktop.swingx.JXButton();
        btnDeltail = new org.jdesktop.swingx.JXButton();
        cbbGender = new javax.swing.JComboBox();
        cbbFloor = new javax.swing.JComboBox();
        searchfield = new org.jdesktop.swingx.JXSearchField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPeopleDetail = new org.jdesktop.swingx.JXTable();

        mnName.setSelected(true);
        mnName.setText("Họ và tên");
        popup.add(mnName);

        mnBirthPlace.setSelected(true);
        mnBirthPlace.setText("Nơi sinh");
        popup.add(mnBirthPlace);

        mnNativeCountry.setSelected(true);
        mnNativeCountry.setText("Dân tộc");
        popup.add(mnNativeCountry);

        mnNation.setSelected(true);
        mnNation.setText("Quốc tịch");
        popup.add(mnNation);

        mnOccupation.setSelected(true);
        mnOccupation.setText("Nghề nghiệp");
        mnOccupation.setToolTipText("");
        popup.add(mnOccupation);

        mnWorkPlace.setSelected(true);
        mnWorkPlace.setText("Nơi công tác");
        popup.add(mnWorkPlace);

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        cbbTotal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTotalActionPerformed(evt);
            }
        });

        pnlFooter.setBackground(new java.awt.Color(153, 153, 255));

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

        btnDeltail.setBackground(new java.awt.Color(255, 255, 255));
        btnDeltail.setBorder(null);
        btnDeltail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/launch.png"))); // NOI18N
        btnDeltail.setOpaque(false);
        btnDeltail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeltailActionPerformed(evt);
            }
        });

        cbbGender.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGenderActionPerformed(evt);
            }
        });

        cbbFloor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbFloor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFloorActionPerformed(evt);
            }
        });

        searchfield.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        searchfield.setToolTipText("Tìm kiếm tại đây");
        searchfield.setInheritsPopupMenu(true);
        searchfield.setPrompt("Tìm kiếm");
        searchfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchfieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFooterLayout = new javax.swing.GroupLayout(pnlFooter);
        pnlFooter.setLayout(pnlFooterLayout);
        pnlFooterLayout.setHorizontalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeltail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchfield, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlFooterLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbbFloor, cbbGender});

        pnlFooterLayout.setVerticalGroup(
            pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchfield, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnDeltail, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        tblPeopleDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPeopleDetail.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tblPeopleDetail.setEditable(false);
        tblPeopleDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPeopleDetailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPeopleDetail);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pnlFooter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        DialogAddHuman abc = new DialogAddHuman(null, true);
        abc.setLocationRelativeTo(this);
        abc.setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int rowId = tblPeopleDetail.getSelectedRow();
        if (rowId > -1) {
            DialogEditHuman replaceHumanForm = new DialogEditHuman(null, true);
            replaceHumanForm.setLocationRelativeTo(this);
            replaceHumanForm.setVisible(true);
        } else {
            ToolsPopup.showErrorPopup("Vui lòng chọn người để chỉnh sửa");
        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int rowId = tblPeopleDetail.getSelectedRow();
        if (rowId > -1) {
            int response = ToolsPopup.showConfirmPopup("Bạn có chắc chắn xóa người này");
            switch (response) {
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.YES_OPTION:
//                    System.out.println("LONG");
//                    System.out.println("VData: " + vData);
//                    System.out.println("VaData: " + vData.get(rowId));
//                    Vector vRow = (Vector) vData.get(rowId);
                    String sql = "DELETE FROM tblHuman WHERE id = " + ShareData.getInstance().getCurrentHumanID();
                    System.out.println(sql);
                    try (Connection cn = Tools.getConn();
                            PreparedStatement pst = cn.prepareStatement(sql);) {
                        pst.executeUpdate();
                        // Popup
                        ToolsPopup.showSuccessPopup("Xóa thành công");
                        ShareData.getInstance().getPpd().initPeopleDetail(null);
                        ShareData.getInstance().getPpd().initWidthTable();
                        ShareData.getInstance().getPpd().initCellAlign();
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

    
    private void btnDeltailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeltailActionPerformed
        int rowId = tblPeopleDetail.getSelectedRow();
        int rowModel = tblPeopleDetail.convertColumnIndexToModel(rowId);
        if (rowId > -1) {
            DialogDetailHuman detailHuman = new DialogDetailHuman(null, true, sqlDetail);
            System.out.println("Before Detail: " + sql);
            detailHuman.setLocationRelativeTo(this);
            detailHuman.setVisible(true);
        } else {
            ToolsPopup.showErrorPopup("Vui lòng chọn người để hiển thị");
        }
    }//GEN-LAST:event_btnDeltailActionPerformed
    // Click chuột vào sẽ get được giá trị ID của human trong tblHuman
    private void tblPeopleDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPeopleDetailMouseClicked
        System.out.println("Sự kiện khi click chuột vào jtable");
        int rowIndex = tblPeopleDetail.getSelectedRow();
        // Giải thích: vì khi nhấn vào nút để nó Sort thì nó sẽ get nhầm giá trị như lúc trước khi sort
        rowModel = tblPeopleDetail.convertRowIndexToModel(rowIndex);
        int currentHumanID = Integer.parseInt((String) tblPeopleDetail.getModel().getValueAt(rowModel, 0));
        System.out.println("CurrentHumanID: " + Integer.parseInt((String) tblPeopleDetail.getModel().getValueAt(rowModel, 0)));
        ShareData.getInstance().setCurrentHumanID(currentHumanID);
        System.out.println("rowIndex: "+rowIndex);
    }//GEN-LAST:event_tblPeopleDetailMouseClicked

    private void cbbGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGenderActionPerformed
        filter();
    }//GEN-LAST:event_cbbGenderActionPerformed

    private void cbbFloorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFloorActionPerformed
        filter();
    }//GEN-LAST:event_cbbFloorActionPerformed

    private void cbbTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTotalActionPerformed
        filter();
    }//GEN-LAST:event_cbbTotalActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        filter();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void searchfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchfieldActionPerformed
        filter();
    }//GEN-LAST:event_searchfieldActionPerformed

    StringBuffer sql;
    String sqlDetail ;
    void filter(){
        sql  = new StringBuffer("select h.ID, h.Name [Họ và tên], h.Birthday [Ngày sinh], h.Gender [Giới tính],"
                    + " h.Birthplace [Nơi sinh], h.[Native Country] [Dân tộc], h.Nation [Quốc tịch],"
                    + " h.Occupation [Nghề nghiệp], h.Workplace [Nơi công tác],"
                    + " h.[Date of arrival] [Ngày ở],  r.Room [Số phòng]\n"
                    + "from tblHuman h, tblRoom r \n"
                    + "where h.RoomID = r.id ");
        sqlDetail = "Select * from tblHuman WHERE 0 = 0";
        if (cbbGender.getSelectedIndex() > 0) {
            System.out.println("CBB Gender: " + cbbGender.getSelectedIndex());
            ModalGender mg = (ModalGender) cbbGender.getSelectedItem();
            sql.append(" AND h.Gender = ").append(mg.getId());
            sqlDetail += " AND Gender = " + mg.getId();
//            initPeopleDetail(sql);
            System.out.println("Gender SQL: " + sql);
            System.out.println("SQL Detail: " + sqlDetail);
//            this.updateUI();
        } else {
//            initPeopleDetail(sql);
//            this.updateUI();
        }
        if(cbbFloor.getSelectedIndex()> 0){
            ModalFloor mf = (ModalFloor) cbbFloor.getSelectedItem();
            sql.append("AND r.FloorID = ").append(mf.getId());
            System.out.println("CbbFloorSQL: "+sql);
//            initPeopleDetail(sql);
        }
        else{
//            initPeopleDetail(sql);
        }
        sql.append("AND (");
        if (mnName.isSelected()) {
            sql.append("h.name LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                    sqlDetail += "AND name LIKE " + "N'%" + searchfield.getText().trim() + "%'";
                    System.out.println(sql);
//                    initPeopleDetail(sql);
        }
        if (mnBirthPlace.isSelected()) {
            sql.append(" OR h.Birthplace LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                        sqlDetail += " AND Birthplace LIKE " + "N'%" + searchfield.getText().trim() + "%'";
                        System.out.println(sql);
//                        initPeopleDetail(sql);
        }
        if (mnNativeCountry.isSelected()) {
            sql.append(" OR h.[Native Country] LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                        sqlDetail += " AND [Native Country] LIKE " + "N'%" + searchfield.getText().trim() + "%'";
                        System.out.println(sql);
//                        initPeopleDetail(sql);
        }
        if (mnNation.isSelected()) {
            sql.append(" OR h.Nation LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                        sqlDetail += " AND Nation LIKE " + "N'%" + searchfield.getText().trim() + "%'";
                        System.out.println(sql);
//                        initPeopleDetail(sql);
        }
        if (mnOccupation.isSelected()) {
            sql.append(" OR h.Occupation LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                        sqlDetail += " AND Occupation LIKE " + "N'%" + searchfield.getText().trim() + "%'";
//                        System.out.println(sql);
        }
        if (mnWorkPlace.isSelected()) {
            sql.append(" OR h.Workplace LIKE " + "N'%").append(searchfield.getText().trim()).append("%'");
                        sqlDetail += " AND Workplace LIKE " + "N'%" + searchfield.getText().trim() + "%'";
                        System.out.println(sql);
//                        initPeopleDetail(sql);
        } 
        sql.append(")");
//        else {
//            sql += " AND ( h.name LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR h.Birthplace LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR h.[Native Country] LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR h.Nation LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR h.Occupation LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR h.Workplace LIKE " + "N'%" + searchfield.getText().trim() + "%')"
//                    ;
//            sqlDetail += " AND (name LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR Birthplace LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR [Native Country] LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR Nation LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR Occupation LIKE " + "N'%" + searchfield.getText().trim() + "%'"
//                    + " OR Workplace LIKE " + "N'%" + searchfield.getText().trim() + "%')"
//                    ;
//            System.out.println(sql);
//            initPeopleDetail(sql);
//            System.out.println("ERROR");
//        }
//        if(cbbTotal.getSelectedIndex() > 0){
//            switch(cbbTotal.getSelectedIndex()){
//                case(1):sql += " AND h.name LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sqlDetail += "AND name LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//                case(2):sql += " AND h.Birthplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sqlDetail += " AND Birthplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//                case(3):sql += " AND h.[Native Country] LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sqlDetail += " AND [Native Country] LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//                case(4):sql += " AND h.Nation LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sqlDetail += " AND Nation LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//                case(5):sql += " AND h.Occupation LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sqlDetail += " AND Occupation LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//                case(6):sql += " AND h.Workplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        sql += " AND Workplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'";
//                        System.out.println(sql);
//                        initPeopleDetail(sql);
//                        break;
//            }
//        } else{
//            sql += " AND ( h.name LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR h.Birthplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR h.[Native Country] LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR h.Nation LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR h.Occupation LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR h.Workplace LIKE " + "N'%" + txtSearch.getText().trim() + "%')"
//                    ;
//            sqlDetail += " AND (name LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR Birthplace LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR [Native Country] LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR Nation LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR Occupation LIKE " + "N'%" + txtSearch.getText().trim() + "%'"
//                    + " OR Workplace LIKE " + "N'%" + txtSearch.getText().trim() + "%')"
//                    ;
//            System.out.println(sql);
//            initPeopleDetail(sql);
//        }
initPeopleDetail(sql.toString());
        initWidthTable();
        initCellAlign();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXButton btnAdd;
    private org.jdesktop.swingx.JXButton btnDelete;
    private org.jdesktop.swingx.JXButton btnDeltail;
    private org.jdesktop.swingx.JXButton btnEdit;
    private javax.swing.JComboBox cbbFloor;
    private javax.swing.JComboBox cbbGender;
    private javax.swing.JComboBox cbbTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBoxMenuItem mnBirthPlace;
    private javax.swing.JCheckBoxMenuItem mnName;
    private javax.swing.JCheckBoxMenuItem mnNation;
    private javax.swing.JCheckBoxMenuItem mnNativeCountry;
    private javax.swing.JCheckBoxMenuItem mnOccupation;
    private javax.swing.JCheckBoxMenuItem mnWorkPlace;
    private javax.swing.JPanel pnlFooter;
    private javax.swing.JPopupMenu popup;
    private org.jdesktop.swingx.JXSearchField searchfield;
    private org.jdesktop.swingx.JXTable tblPeopleDetail;
    private org.jdesktop.swingx.JXTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
