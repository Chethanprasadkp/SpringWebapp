package in.sp.main.repository;

import in.sp.main.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Query appointments for a given date
    @Query("SELECT a FROM Appointment a WHERE a.start >= :startOfDay AND a.end <= :endOfDay")
    List<Appointment> findAppointmentsForDate(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
