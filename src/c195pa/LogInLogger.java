/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195pa;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class LogInLogger {
    
     private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
     
      public void makeSomeLog() 
    { 
   
        LOGGER.log(Level.INFO, "User Log-in"); 
    } 
}
