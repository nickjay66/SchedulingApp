/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.SchedulingException;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author nick
 */
public class ModifyAppointmentMenuController implements Initializable {
    
    Stage stage;
    Parent root;
    Connection conn = DBConnection.getConn();

    @FXML
    private TextField modCustNameTxt;

    @FXML
    private TextField modTitleTxt;

    @FXML
    private TextField modDescriptionTxt;

    @FXML
    private TextField modLocalTxt;

    @FXML
    private TextField modContactTxt;

    @FXML
    private TextField modTypeTxt;

    @FXML
    private TextField modUrlTxt;

    @FXML
    private TextField modDate;

    @FXML
    private TextField modTimeText;

    @FXML
    private TextField modEndText;
    
    @FXML
    private TextField modAppId;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        User userObj = User.getInstance();
        String userName = userObj.getUserName();
        String userPass = userObj.getPassword();
        String userId = Integer.toString(userObj.getUserID());
                 
        //This should be the creation of the next screen which populates username
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
                 
              
        root = loader.load();
        UserMenuController controller = loader.getController();
        controller.setUsername(userName);
        controller.setID(userId);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }

    @FXML
    void onActionDelete(ActionEvent event) throws SQLException, IOException {
      
        //FIX ME Deletion is not working. I think sql db is locked to not allow deletion
        String appId = modAppId.getText();
        String sql = "DELETE FROM appointment WHERE appointmentId = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(appId));
        ps.execute();
        
        //Create popup confirming deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete");
        alert.setContentText("Your appointment has been deleted");
        alert.showAndWait();
        
        //Go back to userMenu
        User userObj = User.getInstance();
        String userName = userObj.getUserName();
        String userPass = userObj.getPassword();
        String userId = Integer.toString(userObj.getUserID());
                 
        //This should be the creation of the next screen which populates username
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
                 
              
        root = loader.load();
        UserMenuController controller = loader.getController();
        controller.setUsername(userName);
        controller.setID(userId);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }

    @FXML
    void onActionSubmit(ActionEvent event) throws SQLException, ParseException, IOException, SchedulingException {
        
        String appId = modAppId.getText();
        String cust = modCustNameTxt.getText();
        String title = modTitleTxt.getText();
        String description = modDescriptionTxt.getText();
        String locale = modLocalTxt.getText();
        String contact = modContactTxt.getText();
        String url = modUrlTxt.getText();
        String start = modTimeText.getText();
        String end = modEndText.getText();
        String type = modTypeTxt.getText();
        String date = modDate.getText();
        
        User use = User.getInstance();
        String userName = use.getUserName();
        String userId = Integer.toString(use.getUserID());
        
        //FIX ME!!!!
        //Create DateTime from strings
        
        //Create Start dateTime obj
        SimpleDateFormat sdfZone = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
        String dateCombo = (date + " " + start);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        java.util.Date dtStart = sdf.parse(dateCombo);
        java.sql.Timestamp dtSql = new java.sql.Timestamp(dtStart.getTime());
        String s = sdfZone.format(dtSql);
        Date dtStartDate = sdfZone.parse(s);
        ZonedDateTime newzdtStart = ZonedDateTime.ofInstant(dtStartDate.toInstant(), ZoneId.of("UTC"));
        Timestamp timestamp = Timestamp.valueOf(newzdtStart.toLocalDateTime());
      
        
        //Create End dateTime obj
        String dateComboEnd = (date + " " + end);
        java.util.Date dtEnd = sdf.parse(dateComboEnd);
        java.sql.Timestamp dtSqlEnd = new java.sql.Timestamp(dtEnd.getTime());
        String e = sdfZone.format(dtSqlEnd);
        Date dtEndDate = sdfZone.parse(e);
        ZonedDateTime newzdtEnd = ZonedDateTime.ofInstant(dtEndDate.toInstant(), ZoneId.of("UTC"));
        Timestamp timestampEnd = Timestamp.valueOf(newzdtEnd.toLocalDateTime());
        
        String checkTime = "SELECT * FROM appointment WHERE start BETWEEN ? and ?";
        PreparedStatement psCheck = conn.prepareStatement(checkTime);
        psCheck.setTimestamp(1, timestamp);
        psCheck.setTimestamp(2, timestampEnd);
        ResultSet rsCheck = psCheck.executeQuery();
        
        if (rsCheck.next() == true) {
            throw new SchedulingException("Appointment already scheduled for that time");
        }
        
        //Check if appt. being scheduled outside of buisness hours
            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
            String month = check.format(dtSql);
            Timestamp startTime = Timestamp.valueOf(month + " " + "08:00:00");
            Timestamp endTime = Timestamp.valueOf(month + " " + "17:00:00");
                if (timestamp.before(startTime) || timestampEnd.after(endTime)) {
                   throw new SchedulingException();
                }
        
        int intAppId = Integer.parseInt(appId);
        String sql = "Update appointment SET title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, createDate = now(), createdBy = ?, lastUpdate = now(), lastUpdateBy = ? WHERE appointmentID = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, locale);
        ps.setString(4, contact);
        ps.setString(5, type);
        ps.setString(6, url);
        ps.setTimestamp(7, timestamp);
        ps.setTimestamp(8, timestampEnd);
        ps.setString(9,userName);
        ps.setString(10, userName);
        ps.setInt(11, intAppId);
        
        ps.execute();
        
        //Go back to User Menu
                 
        //This should be the creation of the next screen which populates username
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
                 
              
        root = loader.load();
        UserMenuController controller = loader.getController();
        controller.setUsername(userName);
        controller.setID(userId);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
            
      
    }
    
    public void setFields(String cust, String title, String description, String locale, String contact, String type, String url, String start, String end, String date, String appId) {
        
        //This needs to be all observableValues
        //Start Here FIX ME
        
        modCustNameTxt.setDisable(true);
        modCustNameTxt.setText(cust);
        modTitleTxt.setText(title);
        modDescriptionTxt.setText(description);
        modLocalTxt.setText(locale);
        modContactTxt.setText(contact);
        modUrlTxt.setText(url);
        modTimeText.setText(start);
        modEndText.setText(end);
        modTypeTxt.setText(type);
        modDate.setText(date);
        modAppId.setDisable(true);
        modAppId.setText(appId);
        
        
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
