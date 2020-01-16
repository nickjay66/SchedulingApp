/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
public class ModifyCustomerMenuController implements Initializable {
    
    Stage stage;
    Parent root;
   
    String countryId;
    String cityId;
    String addressId;
    String customerId;
    int tempId;
    int tempAddressId;
    int tempCityId;
    int tempCountryId;
    
    Connection conn = DBConnection.getConn();

    
    
    @FXML
    private TextField modNameTxt;

    @FXML
    private TextField modAddressTxt;

    @FXML
    private TextField modCityTxt;

    @FXML
    private TextField modPostalTxt;

    @FXML
    private TextField modCountryTxt;

    @FXML
    private TextField modPhoneTxt;

    @FXML
    private TextField modAddress2Txt;

    @FXML
    void onActionCancelBttn(ActionEvent event) throws IOException, SQLException {

        User userObj = User.getInstance();
        String userName = userObj.getUserName();
        String userPass = userObj.getPassword();
        String userId = Integer.toString(userObj.getUserID());
        
        //Find customerId
        String sqlId = "SELECT customerId FROM customer WHERE customerName = ?";
        PreparedStatement psId = conn.prepareStatement(sqlId);
        psId.setString(1, modNameTxt.getText());
        ResultSet rs = psId.executeQuery();
        rs.next();
        int custId = rs.getInt("customerId");
        
        //Delete appointments associated with customer
        String sqlAdd = "DELETE FROM appointment WHERE customerId = ?";
        PreparedStatement psAdd = conn.prepareStatement(sqlAdd);
        psAdd.setInt(1, custId);
        psAdd.execute();
        
        String sqlCust = "DELETE FROM customer WHERE customerId = ?";
        PreparedStatement psCust = conn.prepareStatement(sqlCust);
        psCust.setInt(1, custId);
        psCust.execute();
        
        //Create popup confirming deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete");
        alert.setContentText("Customer and all associated appointments have been deleted");
        alert.showAndWait();
        
        
                 
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
    void onActionSubmitBttn(ActionEvent event) throws SQLException, IOException {
        
        //FIX ME!!!!!!
       //Sets country if not in DB
        Statement stmt = conn.createStatement();
     
        boolean foundCountry = false;
        boolean foundCity = false;
        boolean foundAddress = false;
        
        String countryName = modCountryTxt.getText();
        String cityName = modCityTxt.getText();
        String address = modAddressTxt.getText();
        String address2 = modAddress2Txt.getText();
        String postalCode = modPostalTxt.getText();
        String phone = modPhoneTxt.getText();
        String customerName = modNameTxt.getText();
        
        //Used to populate next screen
        User userObj = User.getInstance();
        String  name = userObj.getUserName();
        String id = Integer.toString(userObj.getUserID());
       
        Customer cust = Customer.getInstance();
        String custName = cust.getCustName();
        System.out.println(custName);
        
        //Use tempCust set on ChooseCustomerMenu to update customerName
        String sqlUpdateCustomer = "Update customer SET customerName = ? WHERE customerId = ?";
        PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateCustomer);
        psUpdate.setString(1, customerName);
        psUpdate.setInt(2, tempId);
        psUpdate.execute();
        
        //Use tempAddressId set on ChooseCustomerMenu to update address
        String sqlUpdateAddress = "Update address SET address = ?, address2 = ?, postalCode = ?, phone = ? WHERE addressId = ?";
        PreparedStatement psUpdateAdd = conn.prepareStatement(sqlUpdateAddress);
        psUpdateAdd.setString(1, address);
        psUpdateAdd.setString(2, address2);
        psUpdateAdd.setString(3, postalCode);
        psUpdateAdd.setString(4, phone);
        psUpdateAdd.setInt(5, tempAddressId);
        psUpdateAdd.execute();
        
        //Use temCityId set on ChooseCustomerMeu to update City
        String sqlUpdateCity = "Update city SET city = ?  WHERE cityId = ?";
        PreparedStatement psUpdateCity = conn.prepareStatement(sqlUpdateCity);
        psUpdateCity.setString(1, cityName);
        psUpdateCity.setInt(2, tempCityId);
        psUpdateCity.execute();
        
        //Use tempCountryId set on ChooseCustomerMenu to update country
        String sqlUpdateCountry = "Update country SET country = ?  WHERE countryId = ?";
        PreparedStatement psUpdateCountry = conn.prepareStatement(sqlUpdateCountry);
        psUpdateCountry.setString(1, countryName);
        psUpdateCountry.setInt(2, tempCountryId);
        psUpdateCountry.execute();
        


       
        //Create new scene            
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
    
      
    public void setCustomer(String custName) {
           
           modNameTxt.setText(custName);
       }
    
    public void setAddress(String address, String address2, String postalCode, String phone) {
        
           modAddressTxt.setText(address);
           modAddress2Txt.setText(address2);
           modPostalTxt.setText(postalCode);
           modPhoneTxt.setText(phone);
    }
    
    public void setCountry(String country) {
        
           modCountryTxt.setText(country);
    }

    
    public void setCity(String city) {
        
           modCityTxt.setText(city);
    }
    
    public void setTempId(int Id) {
        
           tempId = Id;
    }
    
    public void setTempAddressId(int Id) {
        
           tempAddressId = Id;
    }
    
    public void setTempCityId(int Id) {
        
           tempCityId = Id;
    }
    
    public void setTempCountryId(int Id) {
        
           tempCountryId = Id;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
