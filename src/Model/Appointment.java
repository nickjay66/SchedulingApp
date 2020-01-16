/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author nick
 */
public class Appointment {
    
    private ObservableValue<Integer> appointmentId;
    private ObservableValue<Integer> customerId;
    private ObservableValue<Integer> userId;
    private ObservableValue<String> title;
    private ObservableValue<String> description;
    private ObservableValue<String> location;
    private ObservableValue<String> contact;
    private ObservableValue<String> typeOfApp;
    private ObservableValue<String> url;
    private ObservableValue<LocalDateTime> start;
    private ObservableValue<LocalDateTime> end;
    private ObservableValue<LocalDateTime> date;
    private int appIdInt;

    public int getAppIdInt() {
        return appIdInt;
    }

    public void setAppIdInt(int appIdInt) {
        this.appIdInt = appIdInt;
    }
    
    
    public Appointment() {
        
    }
    
    public Appointment(ObservableValue<Integer> appointmentId, ObservableValue<Integer> customerId, ObservableValue<Integer> userId, ObservableValue<String> title, ObservableValue<String> description, ObservableValue<String> location, ObservableValue<String> contact, ObservableValue<String> typeOfApp, ObservableValue<String> url, ObservableValue<LocalDateTime> start, ObservableValue<LocalDateTime> end, ObservableValue<LocalDateTime> date) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.typeOfApp = typeOfApp;
        this.url = url;
        this.start = start;
        this.end = end;
        this.date = date;
    }

    public ObservableValue<LocalDateTime> getStart() {
        return start;
    }

    public void setStart(ObservableValue<LocalDateTime> start) {
        this.start = start;
    }

    public ObservableValue<LocalDateTime> getEnd() {
        return end;
    }

    public void setEnd(ObservableValue<LocalDateTime> end) {
        this.end = end;
    }

    public ObservableValue<LocalDateTime> getDate() {
        return date;
    }

    public void setDate(ObservableValue<LocalDateTime> date) {
        this.date = date;
    }

    public ObservableValue<Integer> getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(ObservableValue<Integer> appointmentId) {
        this.appointmentId = appointmentId;
    }

    public ObservableValue<Integer> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(ObservableValue<Integer> customerId) {
        this.customerId = customerId;
    }

    public ObservableValue<Integer> getUserId() {
        return userId;
    }

    public void setUserId(ObservableValue<Integer> userId) {
        this.userId = userId;
    }

    public ObservableValue<String> getTitle() {
        return title;
    }

    public void setTitle(ObservableValue<String> title) {
        this.title = title;
    }

    public ObservableValue<String> getDescription() {
        return description;
    }

    public void setDescription(ObservableValue<String> description) {
        this.description = description;
    }

    public ObservableValue<String> getLocation() {
        return location;
    }

    public void setLocation(ObservableValue<String> location) {
        this.location = location;
    }

    public ObservableValue<String> getContact() {
        return contact;
    }

    public void setContact(ObservableValue<String> contact) {
        this.contact = contact;
    }

    public ObservableValue<String> getTypeOfApp() {
        return typeOfApp;
    }

    public void setTypeOfApp(ObservableValue<String> typeOfApp) {
        this.typeOfApp = typeOfApp;
    }

    public ObservableValue<String> getUrl() {
        return url;
    }

    public void setUrl(ObservableValue<String> url) {
        this.url = url;
    }
    
}
