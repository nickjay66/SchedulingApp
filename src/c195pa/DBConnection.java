/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195pa;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nick
 */ //Creates database connection
public class DBConnection {
    
    private static final String databaseName = "U05uEJ";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U05uEJ";
    private static final String password = "53688611996";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException {
        //finds the driver
        Class.forName(driver);        
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection successful");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException {
    
        conn.close();
        System.out.println("Connection closed");
    }

    public static Connection getConn() {
        return conn;
    }
    
   
}
