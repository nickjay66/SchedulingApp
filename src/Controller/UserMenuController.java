/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import c195pa.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class UserMenuController implements Initializable {
    
    Stage stage;
    Parent root;
    
    @FXML
    private Label userLabel;
    
    @FXML
    private Label idLbl;

    @FXML
    void onActionAddApptBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateAppointmentMenu.fxml"));
        
        
        root = loader.load();
        //CreateAppointmentMenuController controller = loader.getController();
        //controller.setUsername(user);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
                 
    }

    @FXML
    void onActionAddCustomerBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CreateCustomerMenu.fxml"));
        
        
        root = loader.load();
        CreateCustomerMenuController controller = loader.getController();
        //controller.setUsername(user);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
                 
    }

    @FXML
    void onActionLogOutBttn(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void onActionModApptBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChooseAppointmentMenu.fxml"));
        
        
        root = loader.load();
        //CreateCustomerMenuController controller = loader.getController();
        
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }

    @FXML
    void onActionModCustBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ChooseCustomerMenu.fxml"));
        
        
        root = loader.load();
        //CreateCustomerMenuController controller = loader.getController();
        
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }
    
    @FXML
    void onActionCalendar(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CalendarView.fxml"));
        
        
        root = loader.load();
        //CreateCustomerMenuController controller = loader.getController();
        
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }
    
    @FXML
    void onActionReportsBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportsMenu.fxml"));
        
        
        root = loader.load();
        //CreateCustomerMenuController controller = loader.getController();
        
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }
    
    public void setUsername(String userName) {
        //set userName for personalized exp.
        userLabel.setText(userName);
    }
    
    public void setID(String id) {
       //set Id lable for personalized exp.
        idLbl.setText(id);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }    
    
}
