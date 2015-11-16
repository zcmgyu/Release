package form;

/**
 *
 * @author zcmgy
 */
public class AccountModal {
    private String username;
    private int role;
    private String daylatest;
    public AccountModal() {
    }

    public AccountModal(String username, int role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    
    
    
    
}
