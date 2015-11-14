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
    
    private PanelAnnounce pa;
    private PanelRoom pr;
    private PanelPeopleDetail ppd;
    
    private int roomID;
    private String roomName;
    private int[] memberListID;
    
    
    private static ShareData obj;
    
    public static ShareData getInstance() {
        if (obj == null) {
            obj = new ShareData();
        }
        if(obj.pa == null) {
            obj.pa = new PanelAnnounce();
        }
        
        if(obj.pr == null) {
            obj.pr = new PanelRoom();
        }
        
        if(obj.ppd == null) {
            obj.ppd = new PanelPeopleDetail();
        }
        return obj;
    }

    public PanelAnnounce getPa() {
        return pa;
    }

    public void setPa(PanelAnnounce pa) {
        this.pa = pa;
    }   

    public PanelRoom getPr() {
        return pr;
    }

    public void setPr(PanelRoom pr) {
        this.pr = pr;
    }

    public PanelPeopleDetail getPpd() {
        return ppd;
    }

    public void setPpd(PanelPeopleDetail ppd) {
        this.ppd = ppd;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int[] getMemberListID() {
        return memberListID;
    }

    public void setMemberListID(int[] memberListID) {
        this.memberListID = memberListID;
    }
    
    
    
}
