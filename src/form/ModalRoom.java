/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

/**
 *
 * @author zcmgyu
 */
public class ModalRoom {
    private int id;
    private String room;
    private int status;

    public ModalRoom() {
    }

    public ModalRoom(int id, String room, int status) {
        this.id = id;
        this.room = room;
        this.status = status;
    }

    @Override
    public String toString() {
        if (status == 1) {
            return "Đã thuê";
        } else if (status == 0) {
            return "Chưa thuê";
        }
        return "Tất cả";
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

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
