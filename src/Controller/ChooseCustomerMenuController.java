/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Address;
import Model.AlertInterface;
import Model.City;
import Model.Country;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author nick
 */
public class ChooseCustomerMenuController implements Initializable {

    ObservableList<Customer> customerNames = FXCollections.observableArrayList();
    Connection conn = DBConnection.getConn();
    Stage stage;
    Parent root;
    
    @FXML
    private TableView<Customer> customerNameTable;
    
    @FXML
    private TableColumn<Customer, String> nameCol;
    
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
    void onActionSubmit(ActionEvent event) throws SQLException, IOException {
        
        //alert lambda expression allows for multiple alerts to be made without the extra classes
        if (customerNameTable.getSelectionModel().getSelectedItem() == null) {
            AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                             alert.setContentText(al);
                                                             alert.showAndWait();
                                                             alert.showAndWait(); };
            alertInterface.AlertUser("Please choose one customer from the list or press cancel");            
        }
        
        Customer cust = Customer.getInstance();
        cust = (customerNameTable.getSelectionModel().getSelectedItem());
        String custName = cust.getCustName();
        
        //Set tempId for ModMenu
        String sqlCustomerId = "SELECT customerId, addressId FROM customer WHERE customerName = ?";
        PreparedStatement ps = conn.prepareStatement(sqlCustomerId);
        ps.setString(1, custName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int custId = rs.getInt("customerId");
        int addressId = rs.getInt("addressId");
        
      
        //Find associated address
        String sqlAddress = "SELECT address, address2, postalCode, phone, cityId FROM address WHERE addressId = ?";
        
        PreparedStatement psAddress = conn.prepareStatement(sqlAddress);
        psAddress.setInt(1, addressId);
        ResultSet rsAddress = psAddress.executeQuery();
        rsAddress.next();
        int cityId = rsAddress.getInt("cityId");
        String postal = rsAddress.getString("postalCode");
        String phone = rsAddress.getString("phone");
       
        //Find associated city
        String sqlCity = "SELECT city, countryId FROM city WHERE cityId = ?";
        
        PreparedStatement psCity = conn.prepareStatement(sqlCity);
        psCity.setInt(1, cityId);
        ResultSet rsCity = psCity.executeQuery();
        rsCity.next();
        int countryId = rsCity.getInt("countryId");
        
        //Find associated country
        String sqlCountry = "SELECT country FROM country WHERE countryId = ?";
        
        PreparedStatement psCountry = conn.prepareStatement(sqlCountry);
        psCountry.setInt(1, countryId);
        ResultSet rsCountry = psCountry.executeQuery();
        rsCountry.next();
        String countryName = rsCountry.getString("country");
             
        //Create objects of each field and set variables
        Address address = new Address(rsAddress.getString("address"), rsAddress.getString("address2"), rsAddress.getString("postalCode"), rsAddress.getString("phone"));
        String add1 = address.getAddress();
        String add2 = address.getAddress2();
        String post = address.getPostalCode();
        String phoneNum = address.getPhone();
        
        City city = new City();
        city.setCityName(rsCity.getString("city"));
        String city1 = city.getCityName();
        
        Country country = new Country();
        country.setCountry(countryName);
        String country1 = country.getCountry();
              
        
        Stage stage2;
        Parent root;

        stage2 = (Stage)((Button)event.getSource()).getScene().getWindow();
                //(Stage) Modify.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyCustomerMenu.fxml"));
        
        
        root = loader.load();
        ModifyCustomerMenuController controller = loader.getController();
        
        //Set fields on next menu
        controller.setCustomer(custName);
        controller.setAddress(add1, add2, post, phoneNum);
        controller.setCity(city1);
        controller.setCountry(country1);
        controller.setTempId(custId);
        controller.setTempAddressId(addressId);
        controller.setTempCityId(cityId);
        controller.setTempCountryId(countryId);
        
  
           
        Scene scene2 = new Scene(root);
        stage2.setScene(scene2); // sets the stage.
        stage2.show();
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Statement stmt;
       
        try {
            stmt = conn.createStatement();
            String sql = "SELECT customerName FROM customer";
            ResultSet result = stmt.executeQuery(sql);
            
            while (result.next()) {
                Customer tempCust = new Customer();
                tempCust.setCustName(result.getString("customerName"));
                customerNames.add(tempCust);
            }
            customerNameTable.setItems(customerNames);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        } catch (SQLException ex) {
            Logger.getLogger(ChooseCustomerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
