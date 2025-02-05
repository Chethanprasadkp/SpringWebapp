package in.sp.main.controller;

import in.sp.main.service.AppointmentService;
import in.sp.main.exception.GlobalExceptionHandler;
import in.sp.main.model.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Register global exception handler
                .build();
    }

    @Test
    void testNoAppointmentsFoundException() throws Exception {
        String date = "2025-01-16";

        // Simulate service returning an empty list
        when(appointmentService.getAppointmentsForDate(date)).thenReturn(List.of());

        mockMvc.perform(get("/appointments/{date}", date))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No appointments found for the date: 2025-01-16"));
    }

    @Test
    void testDateTimeParseException() throws Exception {
        String invalidDate = "2023-22-222"; // Invalid date format

        mockMvc.perform(get("/appointments/{date}", invalidDate))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date format, please use yyyy-MM-dd."));
    }

    
    
 
    
    @Test
    void testValidDateWithAppointments() throws Exception {
        String date = "2025-01-16";

        List<Appointment> mockAppointments = List.of(
                new Appointment(
                    1234L, 
                    LocalDateTime.parse("2020-02-01T09:00:00"), 
                    LocalDateTime.parse("2020-02-01T18:00:00") // Full-day appointment (9 hours)
                )
        );

        when(appointmentService.getAppointmentsForDate(date)).thenReturn(mockAppointments);

        // Perform the GET request
        mockMvc.perform(get("/appointments/{date}", date))
            .andExpect(status().isOk()); // Expect HTTP 200 OK
    }

    @Test
    void testGenericExceptionHandler() throws Exception {
        String date = "2025-01-16";

        when(appointmentService.getAppointmentsForDate(date))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/appointments/{date}", date))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An unexpected error occurred: Unexpected error"));
    }
}
