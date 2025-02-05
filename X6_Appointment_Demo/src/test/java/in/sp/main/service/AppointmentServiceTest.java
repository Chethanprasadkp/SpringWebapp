package in.sp.main.service;

import in.sp.main.model.Appointment;
import in.sp.main.repository.AppointmentRepository;
import java.time.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }
   
    @Test
    @Order(1)
    void testFullDayAppointment() {
        // Arrange
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay(); // 2020-02-01T00:00:00
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999); // 2020-02-01T23:59:59.999999999

        List<Appointment> mockAppointments = List.of(
            new Appointment(
                1234L, 
                LocalDateTime.parse("2020-02-01T09:00:00"), 
                LocalDateTime.parse("2020-02-01T18:00:00") // Full-day appointment (9 hours)
            )
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsForDate(date);

        // Assert
        assertEquals(1, result.size());
        assertEquals(mockAppointments.get(0), result.get(0));
    }
    
    @Test
    @Order(2)
    void testGetAppointmentsForDate_2fullday() {
   
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T09:00:00"), 
                             LocalDateTime.parse("2020-02-01T18:00:00")),
            new Appointment(1237L, 
                             LocalDateTime.parse("2020-02-01T07:00:00"), 
                             LocalDateTime.parse("2020-02-01T15:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);


        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

   
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(1237L, appointments.get(0).getId());
        
    }
    @Test
    @Order(3)
    void testGetAppointmentsForDate_1M_1AFday() {
        // Arrange: 
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T09:00:00"), 
                             LocalDateTime.parse("2020-02-01T12:00:00")),
            new Appointment(1237L, 
                             LocalDateTime.parse("2020-02-01T14:00:00"), 
                             LocalDateTime.parse("2020-02-01T17:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);


        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertEquals(1234L, appointments.get(0).getId());
        assertEquals(1237L, appointments.get(1).getId());
    }
    
    @Test
    @Order(4)
    void testGetAppointmentsForDate_2M_day() {
      
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T09:00:00"), 
                             LocalDateTime.parse("2020-02-01T12:00:00")),
            new Appointment(1237L, 
                             LocalDateTime.parse("2020-02-01T07:00:00"), 
                             LocalDateTime.parse("2020-02-01T11:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

       
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(1237L, appointments.get(0).getId());
    
    }
    
    @Test
    @Order(4)
    void testGetAppointmentsForDate_2AF_day() {
       
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T14:00:00"), 
                             LocalDateTime.parse("2020-02-01T16:00:00")),
            new Appointment(1237L, 
                             LocalDateTime.parse("2020-02-01T13:00:00"), 
                             LocalDateTime.parse("2020-02-01T15:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

       
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(1237L, appointments.get(0).getId());
    }
    
    @Test
    @Order(5)
    void testGetAppointmentsForDate_1M_2AFday() {
   
        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T09:00:00"), 
                             LocalDateTime.parse("2020-02-01T12:00:00")),
            new Appointment(1235L, 
                             LocalDateTime.parse("2020-02-01T14:00:00"), 
                             LocalDateTime.parse("2020-02-01T17:00:00")),
            new Appointment(1237L, 
                    LocalDateTime.parse("2020-02-01T13:00:00"), 
                    LocalDateTime.parse("2020-02-01T15:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

      
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);

        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertEquals(1234L, appointments.get(0).getId());
        assertEquals(1237L, appointments.get(1).getId());
    }
    
    
    @Test
    @Order(6)
    void testGetAppointmentsForDate_2M_1AFday() {

        String date = "2020-02-01";
        LocalDateTime startOfDay = LocalDate.parse(date).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.parse(date).atTime(23, 59, 59, 999999999);
        List<Appointment> mockAppointments = List.of(
            new Appointment(1233L, 
                             LocalDateTime.parse("2020-02-01T09:00:00"), 
                             LocalDateTime.parse("2020-02-01T12:00:00")),
            new Appointment(1234L, 
                             LocalDateTime.parse("2020-02-01T07:00:00"), 
                             LocalDateTime.parse("2020-02-01T11:00:00")),
            new Appointment(1237L, 
                    LocalDateTime.parse("2020-02-01T13:00:00"), 
                    LocalDateTime.parse("2020-02-01T15:00:00"))
        );

        when(appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay)).thenReturn(mockAppointments);

  
        List<Appointment> appointments = appointmentService.getAppointmentsForDate(date);


        assertNotNull(appointments);
        assertEquals(2, appointments.size());
        assertEquals(1234L, appointments.get(0).getId());
        assertEquals(1237L, appointments.get(1).getId());
        
 
     
    }
    
    
    
}
