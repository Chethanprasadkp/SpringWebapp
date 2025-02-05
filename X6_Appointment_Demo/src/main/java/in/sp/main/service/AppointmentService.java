package in.sp.main.service;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import in.sp.main.model.Appointment;
import in.sp.main.repository.AppointmentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

   
    public List<Appointment> getAppointmentsForDate(String dateString) {

        LocalDate date = LocalDate.parse(dateString);

        // Convert to LocalDateTime for start of day (00:00) and end of day (23:59:59.999999999)
        LocalDateTime startOfDay = date.atStartOfDay();  // 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);  // 23:59:59.999999999

        List<Appointment> appointments = appointmentRepository.findAppointmentsForDate(startOfDay, endOfDay);


        System.out.println("Fetched appointments for " + dateString + ": " + appointments);


        List<Appointment> dailyAppointments = appointments.stream()
                .sorted(sortByStartTime())  
                .collect(Collectors.toList());

        if (dailyAppointments.isEmpty()) {
            return Collections.emptyList();  
        }

    
        System.out.println("Sorted appointments: " + dailyAppointments);

        List<Appointment> result = new ArrayList<>();


        Optional<Appointment> fullDayAppointment = dailyAppointments.stream()
                .filter(isLongDurationAppointment())
                .findFirst();

        if (fullDayAppointment.isPresent()) {
            result.add(fullDayAppointment.get());  
        } else {
         
            Appointment morningAppointment = dailyAppointments.stream()
                    .filter(isMorningAppointment())  // Ends before 13:00
                    .findFirst()
                    .orElse(null);

            Appointment afternoonAppointment = dailyAppointments.stream()
                    .filter(isAfternoonAppointment())  // Starts after 12:00
                    .findFirst()
                    .orElse(null);

            System.out.println("Morning appointment: " + morningAppointment);
            System.out.println("Afternoon appointment: " + afternoonAppointment);

            if (morningAppointment != null) result.add(morningAppointment);
            if (afternoonAppointment != null) result.add(afternoonAppointment);
        }

        return result;  
    }
    
    private Predicate<Appointment> isLongDurationAppointment() {
        return app -> Duration.between(app.getStart(), app.getEnd()).toHours() >= 8;
    }


    private Predicate<Appointment> isMorningAppointment() {
        return app -> app.getEnd().toLocalTime().isBefore(LocalTime.of(13, 0));
    }


    private Predicate<Appointment> isAfternoonAppointment() {
        return app -> app.getStart().toLocalTime().isAfter(LocalTime.of(12, 0));
    }

    private Comparator<Appointment> sortByStartTime() {
        return Comparator.comparing(Appointment::getStart);
    }

    @PostConstruct
    public void loadAppointments() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appointments1.json")) {
            if (inputStream == null) {
                throw new IOException("File not found: appointments1.json");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());  

           
            List<Appointment> loadedAppointments = objectMapper.readValue(inputStream, new TypeReference<List<Appointment>>() {});      
            appointmentRepository.saveAll(loadedAppointments);

           
            System.out.println("Appointments loaded and saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
