/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AlertInterface;
import Model.Appointment;
import Model.Customer;
import Model.User;
import c195pa.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.text.DateFormatter;

/**
 *
 * @author nick
 */
public class ChooseAppointmentController implements Initializable {
    
    ObservableList <Appointment> appointmentIds = FXCollections.observableArrayList();
    Connection conn = DBConnection.getConn();
    Stage stage;
    Parent root;
    
    @FXML
    private Button onActionSubmit;

    @FXML
    private TableView<Appointment> AppointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, String> custNameCol;

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
    void onActionSubmit(ActionEvent event) throws SQLException, IOException, ParseException {

        //alert lambda expression allows for multiple alerts to be made without the extra classes
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            //Alert alert = new Alert(Alert.AlertType.WARNING);
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();
                                                             alert.showAndWait(); };
           alertInterface.AlertUser("Please choose one appointment from the list or press cancel");
        }
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyAppointmentMenu.fxml"));
        Appointment app = AppointmentTable.getSelectionModel().getSelectedItem();
        //Figure out how to get the EFFING appointment ID!!!!!!!!
        //FIX ME START HERE!!
        int appointmentId = app.getAppIdInt();
        System.out.println(appointmentId);
       
        
        
        String custIdSql = "SELECT * FROM appointment WHERE appointmentId = ?";
        PreparedStatement prepare = conn.prepareStatement(custIdSql);
        prepare.setInt(1, appointmentId);
        ResultSet result = prepare.executeQuery();
        result.next();
        int customerId = result.getInt("customerId");
        
        //collect data from query to populate next page menu
        
        String title = result.getString("title");
        String description = result.getString("description");
        String locale = result.getString("location");
        String contact = result.getString("contact");
        String type = result.getString("type");
        String url = result.getString("url");

        //Convert from UTC back to user timezone
        ZoneId newzid = ZoneId.systemDefault();
 
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        java.sql.Timestamp dtStart = result.getTimestamp("start");
        ZonedDateTime newzdtStart = dtStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
        Timestamp timestamp = Timestamp.valueOf(newLocalStart.toLocalDateTime());
        String start = sdf.format(timestamp);
     
        
        SimpleDateFormat fEnd = new SimpleDateFormat("HH:mm:ss");
        java.sql.Timestamp dtEnd = result.getTimestamp("end");
        ZonedDateTime newzdtEnd = dtEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime newLocalEnd = newzdtEnd.withZoneSameInstant(newzid);
        Timestamp timestampE = Timestamp.valueOf(newLocalEnd.toLocalDateTime());
        String end = sdf.format(timestampE);
       
        SimpleDateFormat fDate = new SimpleDateFormat("MM/dd/yyyy");
        java.sql.Timestamp dt = result.getTimestamp("start");
        String date = fDate.format(dt);
     
       
        String appId = Integer.toString(result.getInt("appointmentId"));
       
       
        
        //get customer name
        String sql = "SELECT customerName FROM customer WHERE customerId = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String custName = rs.getString("customerName");
                 
              
        root = loader.load();
        ModifyAppointmentMenuController controller = loader.getController();
        controller.setFields(custName, title, description, locale, contact, type, url, start, end, date, appId);
        
                 
        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
       
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         Statement stmt;
       
        try {
            User user = User.getInstance();
            int userId = user.getUserID();
            
            String sql = "SELECT * FROM appointment WHERE userId = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, userId);
            ResultSet result = prep.executeQuery();
            
            while (result.next()) {
               
                ObservableValue<Integer> appID = new SimpleIntegerProperty(result.getInt("appointmentId")).asObject();
                ObservableValue<Integer> custId = new SimpleIntegerProperty(result.getInt("customerId")).asObject();
                ObservableValue<String> description = new SimpleStringProperty(result.getString("description"));
                ObservableValue<String> locale = new SimpleStringProperty(result.getString("location"));
                ObservableValue<String> type = new SimpleStringProperty(result.getString("type"));
                ObservableValue<String> urlObj = new SimpleStringProperty(result.getString("url"));
                ObservableValue<String> titleObj = new SimpleStringProperty(result.getString("title"));
                
                Appointment app = new Appointment();
                app.setAppointmentId(appID);
                app.setCustomerId(custId);
                app.setDescription(description);
                app.setLocation(locale);
                app.setTypeOfApp(type);
                app.setUrl(urlObj);
                app.setTitle(titleObj);
                app.setAppIdInt(result.getInt("appointmentId"));
                
                int tempId = result.getInt("customerId");
                
               
                
                //Get the date and start time
                java.util.Date tempDate= result.getTimestamp("start");
                SimpleDateFormat f = new SimpleDateFormat("mm/dd/yyyy hh:mm:ss");
                String strDate = f.format(tempDate);
                SimpleDateFormat date = new SimpleDateFormat("mm/dd/yyyy");
                SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                String moDaYe = date.format(tempDate);
                String strTime = time.format(tempDate);
                SimpleStringProperty moDaYeObj = new SimpleStringProperty(moDaYe);
                SimpleStringProperty strTimeObj = new SimpleStringProperty(strTime);
                //app.setStart(strTimeObj);
                //app.setDate(moDaYeObj);
                
                
                //Get the end time
                java.util.Date tempEndTime = result.getTimestamp("end");
                DateFormat fEnd = new SimpleDateFormat("hh:mm:ss");
                String strEnd = fEnd.format(tempEndTime);
                SimpleStringProperty strEndObj = new SimpleStringProperty(strEnd);
                //app.setEnd(strEndObj);
                
                 
                
                //Collect customerName associated with customerId
                String sqlName = "SELECT customerName FROM customer WHERE customerId = ?";
                PreparedStatement ps = conn.prepareStatement(sqlName);
                ps.setInt(1, tempId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String tempName = rs.getString("customerName");
                SimpleStringProperty nameObj = new SimpleStringProperty(tempName);
                app.setContact(nameObj);
              
                
                appointmentIds.add(app);
                
                
                
            }
            AppointmentTable.setItems(appointmentIds);
            //lambda statement used to set columns with ObservableValues
            appIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
            custNameCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getContact();
                    });
        } catch (SQLException ex) {
            Logger.getLogger(ChooseCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
    

