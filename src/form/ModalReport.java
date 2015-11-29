/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.sql.Date;

/**
 *
 * @author zcmgyu
 */
public class ModalReport {
//    SELECT rp.ID, r.Room 'Phòng', rp.BrokenDate 'Ngày hỏng',
//    rp.Title 'Tiêu đề', rp.Description 'Miêu tả', rp.DateReport 'Ngày báo hỏng'
//    FROM tblReport rp,  tblRoom r WHERE rp.RoomID = r.ID
    private int id;
    private String room;
    private Date brokenDate;
    private String title;
    private String description;
    private Date reportDate;

    public ModalReport() {
    }

    public ModalReport(int id, String room, Date brokenDate, String title, String description, Date reportDate) {
        this.id = id;
        this.room = room;
        this.brokenDate = brokenDate;
        this.title = title;
        this.description = description;
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return room;
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public Date getBrokenDate() {
        return brokenDate;
    }
    public void setBrokenDate(Date brokenDate) {
        this.brokenDate = brokenDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getReportDate() {
        return reportDate;
    }
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }
}
