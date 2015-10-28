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
public class ShareData {
    private String data;
    private AnnounceModal am;
    private static ShareData obj;
    
    public static ShareData getInstance() {
        if (obj == null) {
            obj = new ShareData();
        }
        return obj;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AnnounceModal getAm() {
        return am;
    }

    public void setAm(AnnounceModal am) {
        this.am = am;
    }
    
    
}
