/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Alert;

/**
 *
 * @author nick
 */
public class SchedulingException extends Exception {
    
    public SchedulingException() {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Scheduling Error");
            alert.setHeaderText("Scheduling Error");
            alert.setContentText("Appointment cannot be outside business hours");

            alert.showAndWait();
    }
    
    public SchedulingException(String error) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Scheduling Error");
        alert.setHeaderText("Scheduling Error");
        alert.setContentText(error);
        
        alert.showAndWait();
    }
}
