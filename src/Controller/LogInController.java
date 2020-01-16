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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class LogInController implements Initializable {
    
    Stage stage;
    Parent root;
    
    @FXML
    private Label UsernameLbl;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label PasswordLbl;

    @FXML
    private TextField PasswordTXT;

    @FXML
    private Label WelcomeLbl;
    
    @FXML
    private Button SubmitBttn;
     
    @FXML
    void onActionSubmitClick(ActionEvent event) throws SQLException, IOException, InvalidEntryException {
         Connection conn = DBConnection.getConn();
         Statement stmt = conn.createStatement();
         Locale locale = Locale.getDefault();
             
         //Write SQL Statement
         String sqlStatement = "SELECT userId, userName, password FROM user";
            
         //Execute Statement and Create ResultSet Object
        ResultSet result = stmt.executeQuery(sqlStatement);
        String user = UsernameTxt.getText();
        String pass = PasswordTXT.getText();
        int userIdNum = 0;
        boolean check = false;
         
         //Get all records from ResultSet object
         while(result.next()) {
           
             if (result.getString("userName").equals(user) && result.getString("password").equals(pass)) {
                 
                 //Create user object to store data
                 User userObj = User.getInstance();
                 userObj.setUserName(user);
                 userObj.setPassword(pass);
                 userObj.setUserID(result.getInt("userId"));
                 userIdNum = result.getInt("userId");
                 
                 try {  
        // This block configure the logger with handler and formatter 
                    String userName = userObj.getUserName();
                    Logger logger = Logger.getLogger("LogInLog");  
                    FileHandler fh; 
                    fh = new FileHandler("C:\\C195PA\\src\\LogInLog.txt", true);  
                    logger.addHandler(fh);
                    SimpleFormatter formatter = new SimpleFormatter();  
                    fh.setFormatter(formatter);  
                    //"/Users/nick/NetBeansProjects/C195PA/LogInLog.txt

                    // the following statement is used to log any messages  
                    logger.info("User: " + userName + " " + "has logged in.");  

                   } catch (SecurityException e) {  
                   e.printStackTrace();  
                   } catch (IOException e) {  
                    e.printStackTrace();  
                   } 
                 
                 //This should be the creation of the next screen which populates username
                 stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserMenu.fxml"));
                 
                 int temp = result.getInt("userId");
                 String userId = Integer.toString(temp);
                 
                 //See if there are any current appointments coming up in 15 minutes and alert
                 Timestamp ts = new Timestamp(System.currentTimeMillis());
                 System.out.println(ts.toString());
                 Calendar cal = Calendar.getInstance();
                 cal.add(Calendar.MINUTE, 15);
                 Timestamp tsPlus = new Timestamp(cal.getTime().getTime());
                 System.out.println(tsPlus.toString());
             
                 String checkApps = "SELECT start FROM appointment WHERE userId = ?";
                 PreparedStatement ps = conn.prepareStatement(checkApps);
                 ps.setInt(1, userIdNum);
                 ResultSet rs = ps.executeQuery();
  
                     while (rs.next()) {
                          ZoneId newzid = ZoneId.systemDefault();
 
                          SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                          java.sql.Timestamp dtStart = rs.getTimestamp("start");
                          ZonedDateTime newzdtStart = dtStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                          ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
                          Timestamp timestamp = Timestamp.valueOf(newLocalStart.toLocalDateTime());
                              
                          if (ts.before(timestamp) && tsPlus.after(timestamp) && locale.getCountry() == "MX"){
                             
                                       AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                                        alert.setContentText(al);
                                                                                        alert.showAndWait(); };
                                        alertInterface.AlertUser("Tiene una cita a partir de 15 minutos");
                                   } else if (ts.before(timestamp) && tsPlus.after(timestamp) && locale.getCountry() != "MX") { 
                             AlertInterface alertInterface = (String al) -> { Alert alert = new Alert(Alert.AlertType.WARNING);
                                                                                     alert.setContentText(al);
                                                                                     alert.showAndWait(); };
                                     alertInterface.AlertUser("You have an appointment starting within 15 minutes");
                            }
                     }
                 root = loader.load();
                 UserMenuController controller = loader.getController();
                 controller.setUsername(user);
                 controller.setID(userId);
                 
                 Scene scene2 = new Scene(root);
                 stage.setScene(scene2); // sets the stage.
                 stage.show();
                 check = true;
                 break;
                 
                 
                }
            } 
             if (check == false && locale.getCountry() == "MX") {
                 ResourceBundle resource = ResourceBundle.getBundle("Controller.myBundle", locale);
                 throw new InvalidEntryException(resource.getString("error"));
                
                } else if (check == false && locale.getCountry() != "MX") {
                 throw new InvalidEntryException();
                }
 
             }
         
    

        //To Do - Needs to check if username and passwrd match a user in database
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      //Will change language to Spanish if someone in Mexico uses app
      Locale locale = Locale.getDefault();
      ResourceBundle resource = ResourceBundle.getBundle("Controller.myBundle", locale);
      if (locale.getCountry() == "MX") {
          WelcomeLbl.setText(resource.getString("welcome"));
          UsernameLbl.setText(resource.getString("username"));
          PasswordLbl.setText(resource.getString("password"));
          SubmitBttn.setText(resource.getString("submit"));
          
        } 

   }
} //TO DO Once exceptions are created, ensure that exceptions can access myBundle
       


