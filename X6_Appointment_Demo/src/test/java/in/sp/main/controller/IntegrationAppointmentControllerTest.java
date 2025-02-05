package in.sp.main.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.json.JsonAssert;
import org.springframework.http.HttpStatus;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@ActiveProfiles("test")
public class IntegrationAppointmentControllerTest {

  
    
    @Test
    @Order(1)
    void testNoAppointmentsFoundException() throws Exception {
    	
    	  String expected = "No appointments found for the date: 2025-01-16";
    	  
        String date = "2025-01-16";

        TestRestTemplate restTemplate = new TestRestTemplate();

       
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/appointments/" + date, String.class);

 
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


        assertEquals(expected, response.getBody());
    }
    
    
    @Test @Order(2)
    void testDateTimeParseException() throws Exception {
        String invalidDate = "2023-22-222"; 
        String expected = "Invalid date format, please use yyyy-MM-dd.";
        
        TestRestTemplate restTemplate = new TestRestTemplate();


        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/appointments/" + invalidDate, String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

   
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals(expected, response.getBody());

   
    }
    
    
    @Test
    @Order(3)
    void testAppointmentFound() throws Exception {
        String date = "2020-02-07";

        // Expected JSON response from the API
        String jsonExpected = "[\r\n"
                + "    {\r\n"
                + "        \"id\": 1245,\r\n"
                + "        \"start\": \"2020-02-07T08:30:00\",\r\n"
                + "        \"end\": \"2020-02-07T12:30:00\"\r\n"
                + "    },\r\n"
                + "    {\r\n"
                + "        \"id\": 1246,\r\n"
                + "        \"start\": \"2020-02-07T12:30:00\",\r\n"
                + "        \"end\": \"2020-02-07T16:30:00\"\r\n"
                + "    }\r\n"
                + "]";

       
        TestRestTemplate restTemplate = new TestRestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/appointments/" + date, String.class);

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(jsonExpected, response.getBody(), false);
    }

    
    
 

    }

