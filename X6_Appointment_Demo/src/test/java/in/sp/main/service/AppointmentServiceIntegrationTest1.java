package in.sp.main.service;

import in.sp.main.model.Appointment;
import in.sp.main.repository.AppointmentRepository;
import in.sp.main.service.AppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Transactional 
@ActiveProfiles("test") 
public class AppointmentServiceIntegrationTest1 {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;
  

    @Test
    @Order(1)
    void testGetAppointmentsForDate_2A_1F() {
        // Arrange
        String date = "2020-02-01";

        // Act
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        // Assert
        assertNotNull(appointments);
        assertEquals(1, appointments.size()); 
        assertEquals(1237,appointments.get(0).getId());
    }

    @Test
    @Order(2)
    void testGetAppointmentsForDate_2M_1F() {
        // Arrange
        String date = "2020-02-03";

        // Act
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        assertNotNull(appointments);
        assertEquals(1, appointments.size()); // mutliple motning appointment
        assertEquals(1241,appointments.get(0).getId());
    }

    @Test
    @Order(3)
    void testGetAppointmentsForDate_1M_1F() {
        // Arrange
        String date = "2020-02-07";

        // Act
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        // Assert
        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertEquals(1245,appointments.get(0).getId());
        assertEquals(1246,appointments.get(1).getId());

    }
    
    @Test
    @Order(3)
    void testGetAppointmentsForDate_2M() {
        // Arrange
        String date = "2020-02-09";

        // Act
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        // Assert
        assertNotNull(appointments);
        assertEquals(1, appointments.size()); 
        assertEquals(1249,appointments.get(0).getId());
    }


    @Test
    @Order(4)
    void testNoAppointmentsForDate() {
        // Arrange
        String date = "2020-02-10"; // No appointments on this date

        // Act
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        // Assert
        assertNotNull(appointments);
        assertEquals(0, appointments.size()); // No appointments expected
    }
}
