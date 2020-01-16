/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AlertInterface;
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
import java.sql.Statement;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class CreateAppointmentMenuController implements Initializable {

    Stage stage;
    Parent root;
    ObservableList<Appointment> appointmentsScheduled = FXCollections.observableArrayList();
    
    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField meetDescriptionTxt;

    @FXML
    private TextField meetLocalTxt;

    @FXML
    private TextField contactTxt;

    @FXML
    private TextField meetTypeTxt;

    @FXML
    private TextField urlTxt;
    
    @FXML
    private TextField meetDate;

    @FXML
    private TextField meetTimeText;

    @FXML
    private TextField meetLengthText;

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
        
        
        root = loader.load();
        //CreateAppointmentMenuController controller = loader.getController();
        //controller.setUsername(user);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    
    }

    @FXML
    void onActionSubmit(ActionEvent event) throws SQLException, ParseException, IOException, SchedulingException {

        try {
            
        
        String customer = customerNameTxt.getText();
        String title = titleTxt.getText();
        String description = meetDescriptionTxt.getText();
        String location = meetLocalTxt.getText();
        String contact = contactTxt.getText();
        String type = meetTypeTxt.getText();
        String url = urlTxt.getText();
        String date = meetDate.getText();
        String start = meetTimeText.getText();
        String end = meetLengthText.getText();
        
        //alert lambda expression allows for multiple alerts to be made without the extra classes and only one interface
        if (customer.isEmpty() || title.isEmpty() || description.isEmpty() || location.isEmpty() || contact.isEmpty() || type.isEmpty() || url.isEmpty() || date.isEmpty() || start.isEmpty() || end.isEmpty()) {
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();  };                
            alertInterface.AlertUser("Please ensure all fields are completed to create appointment");
        }
        
        Connection conn = DBConnection.getConn();
        Statement stmt = conn.createStatement();
        
        User userObj = User.getInstance();
        

        String sqlCust = "SELECT customerId FROM customer WHERE customerName = ?";
        PreparedStatement psCust = conn.prepareStatement(sqlCust);
        psCust.setString(1, customer);
        ResultSet rsCust = psCust.executeQuery();
        if (!rsCust.next()) {
             AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();};
            alertInterface.AlertUser("Customer does not exist. Please first add customer to DB");
        }
        int customerId = rsCust.getInt("customerId");
       
        
        //Create Start dateTime obj
        String dateCombo = (date + " " + start);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        java.util.Date dtStart = sdf.parse(dateCombo); 
        java.sql.Timestamp dtSql = new java.sql.Timestamp(dtStart.getTime());
        
        
        //Create End dateTime obj
        String dateComboEnd = (date + " " + end);
        SimpleDateFormat sdfEnd = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        java.util.Date dtEnd = sdfEnd.parse(dateComboEnd);
        java.sql.Timestamp dtEndSql = new java.sql.Timestamp(dtEnd.getTime());
        
        //Check if appointment time already exists
        String checkTime = "SELECT * FROM appointment WHERE start BETWEEN ? and ?";
        PreparedStatement psCheck = conn.prepareStatement(checkTime);
        psCheck.setTimestamp(1, dtSql);
        psCheck.setTimestamp(2, dtEndSql);
        ResultSet rsCheck = psCheck.executeQuery();
        
        if (rsCheck.next() == true) {
            throw new SchedulingException("Appointment already scheduled for that time");
        }
        
        String checkETime = "SELECT * FROM appointment WHERE end BETWEEN ? and ?";
        PreparedStatement psECheck = conn.prepareStatement(checkETime);
        psECheck.setTimestamp(1, dtSql);
        psECheck.setTimestamp(2, dtEndSql);
        ResultSet rsECheck = psECheck.executeQuery();
        
        if (rsECheck.next() == true) {
            throw new SchedulingException("Appointment already scheduled for that time");
        }
        //Check if appt. being scheduled outside of buisness hours
            SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
            String month = check.format(dtSql);
            Timestamp startTime = Timestamp.valueOf(month + " " + "08:00:00");
            Timestamp endTime = Timestamp.valueOf(month + " " + "17:00:00");
                if (dtSql.before(startTime) || dtEndSql.after(endTime)) {
                   throw new SchedulingException();
                }
            
        //Create insert statement
        String sqlInsertAdd = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, CreatedBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)";
        PreparedStatement psNewAdd = conn.prepareStatement(sqlInsertAdd);
        psNewAdd.setInt(1, customerId);
        psNewAdd.setInt(2, userObj.getUserID());
        psNewAdd.setString(3, title);
        psNewAdd.setString(4, description);
        psNewAdd.setString(5, location);
        psNewAdd.setString(6, contact);
        psNewAdd.setString(7, type);
        psNewAdd.setString(8, url);
        psNewAdd.setTimestamp(9, dtSql);
        psNewAdd.setTimestamp(10, dtEndSql);
        psNewAdd.setString(11, userObj.getUserName());
        psNewAdd.setString(12, userObj.getUserName());
        
       
    
        psNewAdd.execute();
        psNewAdd = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM appointment"); //retrieve newly assigned appointmentId
        ResultSet rs = psNewAdd.executeQuery();
        rs.next(); //only one record, so no need for a loop.  
        int appIdInt = rs.getInt(1);
        ObservableValue<Integer> appIdObj = new SimpleIntegerProperty(rs.getInt(1)).asObject();
        ObservableValue<String> contactObj = new SimpleStringProperty(contact);
        ObservableValue<Integer> custIdObj = new SimpleIntegerProperty(customerId).asObject();
        ObservableValue<String> descriptObj = new SimpleStringProperty(description);
        ObservableValue<String> locationObj = new SimpleStringProperty(location);
        ObservableValue<String> titleObj = new SimpleStringProperty(title);
        ObservableValue<String> typeObj = new SimpleStringProperty(type);
        ObservableValue<String> urlObj = new SimpleStringProperty(url);
        ObservableValue<Integer> userIdObj = new SimpleIntegerProperty(userObj.getUserID()).asObject();
        //ObservableValue<LocalDateTime> startObj = new SimpleStringProperty(start);
        //ObservableValue<LocalDateTime> endObj = new SimpleDateProperty(end);
        
        Appointment app = new Appointment();
        app.setAppointmentId(appIdObj);
        app.setContact(contactObj);
        app.setCustomerId(custIdObj);
        app.setDescription(descriptObj);
        app.setLocation(locationObj);
        app.setTitle(titleObj);
        app.setTypeOfApp(typeObj);
        app.setUrl(urlObj);
        app.setUserId(userIdObj);
        //app.setStart(startObj);
        //app.setEnd(endObj);
        app.setAppIdInt(appIdInt);
        
        
        //not sure if this is working?
        //auto set appointment to time zone
          ResultSet res = null;
		
                      
			
		
		//to retrieve from database
                //FIX ME MESSING UP TIMES!!
                
                try {  
                    String sql;
                 
                    sql = "SELECT start, end FROM appointment WHERE appointmentId = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, appIdInt);
                    res = ps.executeQuery();
                    res.next();
                    
                    SimpleDateFormat twelveHourSDF = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
                    String s = twelveHourSDF.format(res.getTimestamp("start"));
                    String e = twelveHourSDF.format(res.getTimestamp("end"));
                    Date parsedStart = twelveHourSDF.parse(s);
                    Date parsedEnd = twelveHourSDF.parse(e);

                    
                   
				ZonedDateTime newzdtStart = ZonedDateTime.ofInstant(parsedStart.toInstant(), ZoneId.of("UTC"));
                                ZonedDateTime newzdtEnd = ZonedDateTime.ofInstant(parsedEnd.toInstant(), ZoneId.of("UTC"));

                                //convert ZonedDateTime to Timestamp
                                Timestamp timestamp = Timestamp.valueOf(newzdtStart.toLocalDateTime());
                                Timestamp timestampEnd = Timestamp.valueOf(newzdtEnd.toLocalDateTime());
                                String st = "UPDATE appointment SET start = ?, end = ? WHERE appointmentId = ?";
                                PreparedStatement update = conn.prepareStatement(st);
                                update.setTimestamp(1, timestamp);
                                update.setTimestamp(2, timestampEnd);
                                update.setInt(3, appIdInt);
                                update.execute();
                                
				
			
                                  appointmentsScheduled.add(app);
                        
                    
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
		
			
                
			
		} catch (SQLException e) {
			System.err.println(e);
        }
                
                //Update start in sql
            
        
        //add to observablelist for reference later in calendar
        
        
        //Go back to userMenu
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
        
        
        root = loader.load();
        //CreateAppointmentMenuController controller = loader.getController();
        //controller.setUsername(user);
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
