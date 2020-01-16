/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195pa;


import static c195pa.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nick
 */
public class C195PA extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            DBConnection.makeConnection();
            
            //Create statement object
            Statement stmt = conn.createStatement();
            
            //Write SQL Statement
            String sqlStatement = "SELECT * FROM user";
            
            //Execute Statement and Create ResultSet Object
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            
            
            launch(args);
            DBConnection.closeConnection();
            
           /* boolean append = true;
            FileHandler handler = new FileHandler("default.log", append);
 
            Logger logger = Logger.getLogger("com.javacodegeeks.snippets.core");
            logger.addHandler(handler);
            logger.fine("User Log-ins");*/

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
    
}
