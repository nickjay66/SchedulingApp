/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class CalendarViewController implements Initializable {

    ObservableList<Appointment> appList = FXCollections.observableArrayList();
    ObservableList<Appointment> sortByMonthList = FXCollections.observableArrayList();
    Connection conn = DBConnection.getConn();
    Stage stage;
    Parent root;
    
  @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthWeekCol;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TextField weekTxt;

    @FXML
    private RadioButton monthTog;

    @FXML
    private ToggleGroup tog;

    @FXML
    private RadioButton weekTog;

    @FXML
    private ChoiceBox<String> monthMenu;

    @FXML
    void onActionSubmit(ActionEvent event) throws SQLException, ParseException {

        
        
        if (tog.getSelectedToggle() == monthTog) {
            
            Connection conn = DBConnection.getConn();
            Statement stmt =  conn.createStatement();
            
            if (monthMenu.getSelectionModel().getSelectedItem() == "01") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 1";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
               //This is not working
                /*}
                if (sortByMonthList.isEmpty()) {
                    CalendarViewController viewer = new CalendarViewController();
                    viewer.reset();*/
                }
                
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "02") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 2";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                    
                    
              //This is not workiing  
               /* }
                if (sortByMonthList.isEmpty()) {
                    CalendarViewController viewer = new CalendarViewController();
                    viewer.reset();
                    */
                }
                
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "03") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 3";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                
                
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "04") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 4";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "05") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 5";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "06") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 6";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "07") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 7";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "08") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 8";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "09") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 9";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "10") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 10";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "11") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 11";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month)));
                }
            } else if (monthMenu.getSelectionModel().getSelectedItem() == "12") {
                String sql = "SELECT * FROM appointment WHERE MONTH(start) = 12";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    String month = sdf.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month))); }
            } else {
                String sql = "SELECT * FROM appointment";
                ResultSet rs = stmt.executeQuery(sql);
                SimpleDateFormat formatHr = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat formatDt = new SimpleDateFormat("mm/dd/yyyy");
                SimpleDateFormat justMonth = new SimpleDateFormat("mm");
                appList = FXCollections.observableArrayList();
                while (rs.next()) {
                java.sql.Timestamp starty = rs.getTimestamp("start");
                
              
                java.sql.Timestamp end = rs.getTimestamp("end");
                String endStr = formatHr.format(end);
                String date = formatDt.format(starty);
               // String month = justMonth.format(start);
                
                
                appList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(date)));
                
                }
            }
            
             if (!sortByMonthList.isEmpty()) {
                appointmentTable.getItems().clear();
               
                
                monthWeekCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    appIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    custIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getCustomerId();
                    });
                    appTypeCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
                   appointmentTable.setItems(sortByMonthList);
            
           }
        }
        if (tog.getSelectedToggle() == weekTog) {
            
            String sql = "SELECT * FROM appointment WHERE start BETWEEN ? AND DATE_ADD(?, INTERVAL 7 DAY)";
            String date = weekTxt.getText();
            String time = "00:00:00";
            String dateCombo = date + " " + time;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            java.util.Date dt = sdf.parse(dateCombo); 
            java.sql.Timestamp dtSql = new java.sql.Timestamp(dt.getTime());
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, dtSql);
            ps.setTimestamp(2, dtSql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Timestamp monthWeek = rs.getTimestamp("start");
                    SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
                    String month = simple.format(monthWeek);
                    
                    
                    
                    sortByMonthList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(month))); 
                }
                if (!sortByMonthList.isEmpty()) {
                appointmentTable.getItems().clear();
               
                
                monthWeekCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    appIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    custIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getCustomerId();
                    });
                    appTypeCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
                   appointmentTable.setItems(sortByMonthList);
            
            }
        }
    }
    
 
        //This is not working
   /* public void reset() throws SQLException {
        
              appointmentTable.setItems(appList);
              monthWeekCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    appIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    custIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getCustomerId();
                    });
                    appTypeCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
               
        }*/
       
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
        
        
        root = loader.load();

        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        User use = User.getInstance();
        int userId = use.getUserID();
        
        try {
            Connection conn = DBConnection.getConn();
            String sql = "SELECT * FROM appointment WHERE userId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat formatHr = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat formatDt = new SimpleDateFormat("mm/dd/yyyy");
            SimpleDateFormat justMonth = new SimpleDateFormat("mm");
            appList = FXCollections.observableArrayList();
            while (rs.next()) {
                java.sql.Timestamp starty = rs.getTimestamp("start");
                
              
                java.sql.Timestamp end = rs.getTimestamp("end");
                String endStr = formatHr.format(end);
                String date = formatDt.format(starty);
               // String month = justMonth.format(start);
                
                
                appList.add(new Appointment(new ReadOnlyObjectWrapper(rs.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(rs.getInt("userId")),
                                        new ReadOnlyStringWrapper(rs.getString("title")),
                                        new ReadOnlyStringWrapper(rs.getString("description")),
                                        new ReadOnlyStringWrapper(rs.getString("location")),
                                        new ReadOnlyStringWrapper(rs.getString("contact")),
                                        new ReadOnlyStringWrapper(rs.getString("type")),
                                        new ReadOnlyStringWrapper(rs.getString("url")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(rs.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(date)));
 
                
                 // if (monthTxt.getText() == month) {
                    monthWeekCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    appIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    custIdCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getCustomerId();
                    });
                    appTypeCol.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
                   appointmentTable.setItems(appList);
                    
              //  }
            }
            
           ObservableList<String> monthList = FXCollections.observableArrayList();
           monthList.addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "reset");
           monthMenu.setItems(monthList);
         
      
               
            
    }   catch (SQLException ex) {    
            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   }
}
