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
public class InvalidEntryException extends Exception {
    
    public InvalidEntryException() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid");
        alert.setHeaderText("Invalid");
        alert.setContentText("Either username or password does not match or does not exists in database");

        alert.showAndWait();
    }
    
    public InvalidEntryException(String error) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid");
        alert.setHeaderText("Invalid");
        alert.setContentText(error);

        alert.showAndWait();
    }
}
