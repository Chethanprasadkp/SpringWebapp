package in.sp.main.exception;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid date format, please use yyyy-MM-dd.");
    }
   
    
    @ExceptionHandler(NoAppointmentFoundException.class)
    public ResponseEntity<String> handleNoAppointmentFoundException(NoAppointmentFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

 
    @ExceptionHandler(Exception.class)
   public ResponseEntity<String> handleException(Exception ex) {
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("An unexpected error occurred: " + ex.getMessage());
    }
    

   
}
