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
public class AnnounceModal {
    private int ID;
    private String title;
    private String content;

    public AnnounceModal() {
    }

    public AnnounceModal(int ID, String title, String content) {
        this.ID = ID;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
       return title;
    }

    
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
