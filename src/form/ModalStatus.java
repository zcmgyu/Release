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
public class ModalStatus {
    private int id;
    private String status;

    public ModalStatus() {
    }

    public ModalStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
    
}
