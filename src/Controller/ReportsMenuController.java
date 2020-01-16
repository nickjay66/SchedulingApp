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
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class ReportsMenuController implements Initializable {
    
    Stage stage;
    Parent root;
    
    Connection conn = DBConnection.getConn();
    ObservableList<Appointment> appList = FXCollections.observableArrayList();
    
    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, LocalDateTime> c1Col;

    @FXML
    private TableColumn<Appointment, String> c2Col;

    @FXML
    private TableColumn<Appointment, String> c3Col;

    @FXML
    private TableColumn<Appointment, String> c4Col;

    @FXML
    private TableColumn<Appointment, String> c5Col;

    @FXML
    private TableColumn<Appointment, Integer> c6Col;

    @FXML
    private TableColumn<Appointment, String> c7Col;

    @FXML
    private TextField custTxt;
    
    @FXML
    private TextField userNameTxt;
    
    @FXML
    private TextField typeTxt;

    @FXML
    void onActionCancelBttn(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
        
        
        root = loader.load();

        Scene scene2 = new Scene(root);
        stage.setScene(scene2); // sets the stage.
        stage.show();
        
    }

    @FXML
    void onActionCustomerBttn(ActionEvent event) throws SQLException {

        String cust = custTxt.getText();
        String sqlCustCheck = "SELECT customerId FROM customer WHERE customerName = ?";
        PreparedStatement ps = conn.prepareStatement(sqlCustCheck);
        ps.setString(1, cust);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int customerId = rs.getInt("customerId");
        
        if (customerId == 0) {
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();
                                                             alert.showAndWait(); };
           alertInterface.AlertUser("customer does not exist in database");
        }
        
        try {
            String sql = "SELECT * FROM appointment WHERE customerId = ?";
            PreparedStatement psCheck = conn.prepareStatement(sql);
            psCheck.setInt(1, customerId);
            ResultSet custApp = psCheck.executeQuery();
            
            SimpleDateFormat formatHr = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat formatDt = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat justMonth = new SimpleDateFormat("MM");
            appList = FXCollections.observableArrayList();
            
            while (custApp.next()) {
          
                java.sql.Timestamp starty = custApp.getTimestamp("start");
                java.sql.Timestamp end = custApp.getTimestamp("end");
                String endStr = formatHr.format(end);
                String date = formatDt.format(starty);
               // String month = justMonth.format(start);
                
                
                appList.add(new Appointment(new ReadOnlyObjectWrapper(custApp.getInt("appointmentId")),
                                        new ReadOnlyObjectWrapper(custApp.getInt("customerId")),
                                        new ReadOnlyObjectWrapper(custApp.getInt("userId")),
                                        new ReadOnlyStringWrapper(custApp.getString("title")),
                                        new ReadOnlyStringWrapper(custApp.getString("description")),
                                        new ReadOnlyStringWrapper(custApp.getString("location")),
                                        new ReadOnlyStringWrapper(custApp.getString("contact")),
                                        new ReadOnlyStringWrapper(custApp.getString("type")),
                                        new ReadOnlyStringWrapper(custApp.getString("url")),
                                        new ReadOnlyObjectWrapper(custApp.getTimestamp("start")),
                                        new ReadOnlyObjectWrapper(custApp.getTimestamp("end")),
                                        new ReadOnlyObjectWrapper(date)));
 
                
                 // if (monthTxt.getText() == month) {
                    c1Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    c2Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getContact();
                    });
                    c3Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
                    c4Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getLocation();
                    });
                    c5Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getDescription();
                    });
                    c6Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    c7Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTitle();
                    });
                    
                   appointmentTable.setItems(appList);
                    
            }
            
        } catch (SQLException ex) {    
            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    void onActionScheduleBttn(ActionEvent event) throws SQLException {

        String user = userNameTxt.getText();
        String sqlUserCheck = "SELECT userId FROM user WHERE userName = ?";
        PreparedStatement psCheck = conn.prepareStatement(sqlUserCheck);
        psCheck.setString(1, user);
        ResultSet rsCheck = psCheck.executeQuery();
        rsCheck.next();
        int userId = rsCheck.getInt("userId");
        
        if (userId == 0) {
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();
                                                             alert.showAndWait(); };
           alertInterface.AlertUser("user does not exist in database");
        }
       
        
        try {
            //Create sql statement to group all user appointments in order by date
            String sql = "SELECT * FROM appointment WHERE userId = ? ORDER BY start ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat formatHr = new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat formatDt = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat justMonth = new SimpleDateFormat("MM");
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
                    c1Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getStart();
                    });
                    c2Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getContact();
                    });
                    c3Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTypeOfApp();
                    });
                    c4Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getLocation();
                    });
                    c5Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getDescription();
                    });
                    c6Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getAppointmentId();
                    });
                    c7Col.setCellValueFactory(cellData -> {
                    return cellData.getValue().getTitle();
                    });
                    
                   appointmentTable.setItems(appList);
                    
              //  }
            }
            
    
        } catch (SQLException ex) {    
            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    void onActionTypeBttn(ActionEvent event) throws SQLException, IOException, ParseException {

        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TypeReport.fxml"));
        
        
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
        // TODO
    }    
    
}
