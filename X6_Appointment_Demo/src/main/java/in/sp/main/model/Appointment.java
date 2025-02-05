package in.sp.main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "appointment") // Specifies the table name in the database
public class Appointment {

    @Id
    private Long id;

    @Column(nullable = false) // Maps this field to a non-nullable column
    private LocalDateTime start;

    @Column(name = "end_time", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime end;


    
   
}
