/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AlertInterface;
import Model.InvalidEntryException;
import Model.User;
import c195pa.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class CreateCustomerMenuController implements Initializable {
    
    Stage stage;
    Parent root;
   
    String countryId;
    String cityId;
    String addressId;
    String customerId;
    
    Connection conn = DBConnection.getConn();

    
    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;
    
    @FXML
    private TextField address2Txt;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField postalTxt;
    
    @FXML
    private TextField countryTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    void onActionCancelBttn(ActionEvent event) throws IOException {
      
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
    void onActionSubmitBttn(ActionEvent event) throws SQLException, IOException, InvalidEntryException {
        
        User userObj = User.getInstance();
        String  name = userObj.getUserName();
        String id = Integer.toString(userObj.getUserID());
        
        //Check to see if customer already exists
        String custCheck = "SELECT * FROM customer WHERE customerName = ?";
        PreparedStatement psCheck = conn.prepareStatement(custCheck);
        psCheck.setString(1, nameTxt.getText());
        ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
               throw new InvalidEntryException("Customer already exists in database.");
            }
                
                
            
            

       //Sets country if not in DB
        Statement stmt = conn.createStatement();
        String sql = "SELECT countryId, country FROM country";
        ResultSet result = stmt.executeQuery(sql);
        
        boolean foundCountry = false;
        boolean foundCity = false;
        boolean foundAddress = false;
        boolean emptyTest = true;
        

        String countryName = countryTxt.getText();
        String cityName = cityTxt.getText();
        String address = addressTxt.getText();
        String address2 = address2Txt.getText();
        String postalCode = postalTxt.getText();
        String phone = phoneTxt.getText();
        String customerName = nameTxt.getText();
      
               
        //alert lambda expression allows for multiple alerts to be made without the extra classes
       if (countryTxt.getText().isEmpty() || cityTxt.getText().isEmpty() || addressTxt.getText().isEmpty() || postalTxt.getText().isEmpty() || nameTxt.getText().isEmpty()) {
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);

                                                             alert.showAndWait(); };
            alertInterface.AlertUser("Please ensure all fields are completed to create new customer");
            emptyTest = false;
        }
        //String addressId = "";
        
        //Sets country if not in DB
        while (result.next() && emptyTest == true) {
            if (result.getString("country").equals(countryName)) {
                countryId = Integer.toString(result.getInt("countryId"));
                System.out.println("Country exists already in DB");
                foundCountry = true;
                } 
          
            }
            if (foundCountry == false) {
                insertCountryDB(countryName, name);
        }
         
         String sqlCity = "SELECT cityId, city, countryId FROM city";
         ResultSet resultCity = stmt.executeQuery(sqlCity);
        //Sets city if not in DB
        while (resultCity.next() && emptyTest == true) {
            if (resultCity.getString("city").equals(cityName)) {
                cityId = Integer.toString(resultCity.getInt("cityId"));
                System.out.println("City already exists in DB");
                foundCity = true;
            }
        }
        if (foundCity == false) {
            insertCityDB(cityName, countryId, name);
    }
            
        String sqlAddress = "SELECT addressId, address, address2, postalCode, phone, cityId FROM address";
        ResultSet resultAddress = stmt.executeQuery(sqlAddress);
        //Sets address if not in DB
        while (resultAddress.next() && emptyTest == true) {
            if (resultAddress.getString("address").equals(address)) {
                addressId = Integer.toString(resultAddress.getInt("addressId"));
                System.out.println("Address already exists in DB");
                foundAddress = true;
            }
        }
        
        if (foundAddress == false) {
            if (address2.isEmpty()) {
                address2 = "N/A";
            }
            insertAddressDB(address, address2, cityId, postalCode, phone, name);
        }
       
        //Finally, create new customer
        if (emptyTest == true) {
        insertCustomerDB(customerName, addressId, name);
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
                
        
                 root = loader.load();
                 UserMenuController controller = loader.getController();
                 controller.setUsername(name);
                 controller.setID(id);
                 
                 Scene scene2 = new Scene(root);
                 stage.setScene(scene2); // sets the stage.
                 stage.show();
        }

   }
    
       String insertCountryDB(String newCountry, String userName) {

        //Note that this assumes an autoincrement on the country id column! 
        String sql = "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, now(), ?, now(), ?)";
        countryId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newCountry);
            ps.setString(2, userName);
            ps.setString(3, userName);

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM country"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            countryId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CreateCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return countryId;
    }
       //Insert new city into databse and associate with country
       String insertCityDB(String newCity, String countryId, String userName) {

        
        String sql = "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, now(), ?, now(), ?)";
        cityId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int newId = Integer.parseInt(countryId);
            ps.setString(1, newCity);
            ps.setInt(2, newId);
            ps.setString(3, userName);
            ps.setString(4, userName);

            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM city"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            cityId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CreateCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cityId;
    }
       
       //Insert new address in database and associate with city
       String insertAddressDB(String newAddress, String newAddress2, String cityId, String postalCode, String phone, String userName) {

        
        String sql = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?)";
        addressId = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            int newId = Integer.parseInt(cityId);
            ps.setString(1, newAddress);
            ps.setString(2, newAddress2);
            ps.setInt(3, newId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setString(6, userName);
            ps.setString(7, userName);
                 
            ps.execute();
            ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM address"); //retrieve newly assigned country id
            ResultSet rs = ps.executeQuery();
            rs.next(); //only one record, so no need for a loop.  
            addressId = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(CreateCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return addressId;
    }
       
       String insertCustomerDB(String customerName, String addressId, String userName) throws SQLException {
           
           String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, now(), ?, now(), ?)";
           customerId = null;
           try {
               PreparedStatement ps = conn.prepareStatement(sql);
               int newId = Integer.parseInt(addressId);
               ps.setString(1, customerName);
               ps.setInt(2, newId);
               ps.setInt(3, 1);
               ps.setString(4, userName);
               ps.setString(5, userName);
               
               ps.execute();
               ps = conn.prepareStatement("SELECT LAST_INSERT_ID() FROM customer");
               ResultSet rs = ps.executeQuery();
               rs.next();
               customerId = rs.getString(1);
           } catch (SQLException ex) {
            Logger.getLogger(CreateCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } 
           return customerId;
       }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
