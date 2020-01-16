/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author nick
 */
public class User {
    
    private int userID;
    private String userName;
    private String password;
    
    //Allows only one instance of User to be shared across program once logged in
    private static final User INSTANCE = new User();
    
    public User() {
        
    }
    
    public User(int userID, String userName, String password) {
        
    }
    
    public static User getInstance() {
        return INSTANCE;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    
}
