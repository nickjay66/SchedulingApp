/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AlertInterface;
import Model.AppType;
import Model.Appointment;
import Model.User;
import c195pa.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class TypeReportController implements Initializable {
    
    Connection conn = DBConnection.getConn();
    String type;
    Stage stage;
    Parent root;
     
    @FXML
    private Label numOfTypes;

    @FXML
    private TextField monthTxt;

    @FXML
    void onActionBackBttn(ActionEvent event) throws IOException {

         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ReportsMenu.fxml"));
        
        
        root = loader.load();

        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
        
    }

    @FXML
    void onActionGenerateBttn(ActionEvent event) {
        String month = monthTxt.getText();
       
       try {
        User user = User.getInstance();
        int userId = user.getUserID();
        int counter = 0;
        int index = 1;
        ArrayList<String> typeArray = new ArrayList<String>();
    
        
        //Get start and end time to sort through
        String typeNew = month + " " + "00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date parsedDate;
        parsedDate = sdf.parse(typeNew);
        
        Timestamp startMonth = new java.sql.Timestamp(parsedDate.getTime());
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate);
        cal.add(Calendar.MONTH, 1);
        Timestamp tsPlus = new Timestamp(cal.getTime().getTime());

        
        String sql = "SELECT type FROM appointment WHERE userID = ? and start BETWEEN ? and ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setTimestamp(2, startMonth);
        ps.setTimestamp(3, tsPlus);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            
            if (!typeArray.contains(rs.getString("type"))) {
                counter++;
            }
            typeArray.add(rs.getString("type"));
            numOfTypes.setText(Integer.toString(counter));
        }
        
        
        } catch (ParseException ex) {
            Logger.getLogger(TypeReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TypeReportController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    
    }
}
