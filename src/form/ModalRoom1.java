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
public class ModalRoom1 {
    private int id;
    private String room;;

    public ModalRoom1(int id, String room) {
        this.id = id;
        this.room = room;
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
    
    
}
