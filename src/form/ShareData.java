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
    private PanelReport prt;
    
    private DialogRoomDetail drd;
    
    private int currentRoomID;
    private String currentRoomName;
    private int currentHumanID;
    
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
        if (obj.prt == null) {
            obj.prt = new PanelReport();
        }
//        if (obj.drd == null) {
//            obj.drd = new DialogRoomDetail(null, true);
//        }
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

    public int getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(int roomID) {
        this.currentRoomID = roomID;
    }

    public String getCurrentRoomName() {
        return currentRoomName;
    }

    public void setCurrentRoomName(String roomName) {
        this.currentRoomName = roomName;
    }

    public PanelReport getPrt() {
        return prt;
    }

    public void setPrt(PanelReport prt) {
        this.prt = prt;
    }

    public int getCurrentHumanID() {
        return currentHumanID;
    }

    public void setCurrentHumanID(int currentHumanID) {
        this.currentHumanID = currentHumanID;
    }

    public DialogRoomDetail getDrd() {
        return drd;
    }

    public void setDrd(DialogRoomDetail drd) {
        this.drd = drd;
    }
    
    
    
}
