package in.sp.main.repository;

import in.sp.main.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test") // Activates the 'test' profile to use application-test.properties
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

//    @BeforeEach
//    void setUp() {
//        // Set up initial data if needed
//        appointmentRepository.save(new Appointment(1L, LocalDateTime.of(2025, 2, 1, 9, 0), LocalDateTime.of(2025, 2, 1, 12, 0)));
//        appointmentRepository.save(new Appointment(2L, LocalDateTime.of(2025, 2, 1, 13, 0), LocalDateTime.of(2025, 2, 1, 17, 0)));
//        // Add more appointments as necessary for your tests
//    }


    @Test
    @Order(1)
    void testFindAppointmentsForDate_SpecificDate() {
     
        LocalDateTime startOfDay = LocalDateTime.of(2020, 2, 1, 0, 0, 0, 0); // 2020-02-01 00:00:00
        LocalDateTime endOfDay = LocalDateTime.of(2020, 2, 1, 23, 59, 59, 999999); // 2020-02-01 23:59:59

       
        List<Appointment> appointments = appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay);

  
        assertNotNull(appointments);
        assertEquals(4, appointments.size()); 
    }

    @Test
    @Order(2)
    void testFindAppointmentsForDate_SpecificDate_Range() {
        
        LocalDateTime startOfDay = LocalDateTime.of(2020, 2, 3, 0, 0, 0, 0); // 2020-02-03 00:00:00
        LocalDateTime endOfDay = LocalDateTime.of(2020, 2, 3, 23, 59, 59, 999999); // 2020-02-03 23:59:59


        List<Appointment> appointments = appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay);

        
        assertNotNull(appointments);
        assertEquals(3, appointments.size()); // There are 3 appointments for 2020-02-03
    }

    @Test
    @Order(3)
    void testFindAppointmentsForDate_SpecificDate_Empty() {
       
        LocalDateTime startOfDay = LocalDateTime.of(2020, 2, 10, 0, 0, 0, 0); // 2020-02-10 00:00:00
        LocalDateTime endOfDay = LocalDateTime.of(2020, 2, 10, 23, 59, 59, 999999); // 2020-02-10 23:59:59


        List<Appointment> appointments = appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay);

        
        assertNotNull(appointments);
        assertEquals(0, appointments.size()); // There are no appointments for 2020-02-10
    }

    @Test
    @Order(4)
    void testFindAppointmentsForDate_RangeWithinYear() {
        
    	 LocalDateTime startOfDay = LocalDateTime.of(2020, 2, 7, 0, 0, 0, 0); // 2020-02-03 00:00:00
         LocalDateTime endOfDay = LocalDateTime.of(2020, 2, 7, 23, 59, 59, 999999); // 2020-02-03 23:59:59

      
        List<Appointment> appointments = appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay);

       
        assertNotNull(appointments);
        assertEquals(2, appointments.size()); 
    }
}
