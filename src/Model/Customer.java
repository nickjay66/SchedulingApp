/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author nick
 */
public class Customer {
    private String custName;
    private String custAddress;
    private String custPhone;
    private int custId;
    
    private static final Customer INSTANCE = new Customer();
    
    public Customer() {
        
    }
    
    public Customer(String custName, String custAddress, int custId) {
        
    }
    
    public static Customer getInstance() {
        return INSTANCE;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }
    

    public void setCustName(String custName) {

    this.custName = custName;
    }
     
    public String getCustName() {
    
    return custName;
    }
    
    public void setCustAddress(String custAddress) {
        
        this.custAddress = custAddress;
    }
    
    public String getCustAddress() {
        
        return custAddress;
    }
    
    public void setCustPhone(String custPhone) {
        
        this.custPhone = custPhone;
    }
    
    public String getCustPhone() {
        
        return custPhone;
    }


}
