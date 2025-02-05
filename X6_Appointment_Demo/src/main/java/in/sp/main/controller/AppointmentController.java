package in.sp.main.controller;

import in.sp.main.model.Appointment;
import in.sp.main.service.AppointmentService;
import in.sp.main.exception.NoAppointmentFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{date}")
    public ResponseEntity<List<Appointment>> getAppointments(@PathVariable String date) {

        LocalDate parsedDate = LocalDate.parse(date);

        List<Appointment> appointments = appointmentService.getAppointmentsForDate(parsedDate.toString());

        if (appointments.isEmpty()) {
            throw new NoAppointmentFoundException("No appointments found for the date: " + date);
        }

        return ResponseEntity.ok(appointments);
    }
}
