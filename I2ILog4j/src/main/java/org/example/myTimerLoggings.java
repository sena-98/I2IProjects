
package org.example;

import java.time.LocalTime;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class myTimerLoggings {
    static final    String x = "saniye degisti";
    static final    String y = "dakika degisti";
    static final    String z = "saat degisti";
    static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
      

    LocalTime previousTime = LocalTime.now().minusSeconds(1);
      while(true){
          LocalTime currentTime = LocalTime.now();
          if(previousTime.getSecond() != currentTime.getSecond() ){
              if(currentTime.getSecond() != 0){
                  logger.debug(x);
              }
          }
          if(previousTime.getMinute() != currentTime.getMinute()){
              if(currentTime.getMinute() != 0){
                 logger.info(y);
              }  
          }
          if(previousTime.getHour() != currentTime.getHour() ){
              logger.error(z);
          }
          
          try {
              Thread.sleep(1000);
          } catch (InterruptedException ex) {
              java.util.logging.Logger.getLogger(myTimerLoggings.class.getName()).log(Level.SEVERE, null, ex);
          }
          previousTime = currentTime;
      }

    }
   
}
