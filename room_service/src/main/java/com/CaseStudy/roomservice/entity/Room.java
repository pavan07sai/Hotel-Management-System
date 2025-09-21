package  com.CaseStudy.roomservice.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Room number must not be empty")
    private String roomNumber;
    
    @NotBlank(message = "Room type must not be empty")
    private String type; //single double suite
    
    @Positive(message = "Price must be greater than zero")
    private double price;
    
    @Column(name = "available")
    @NotNull(message = "Availability must be specified")
    private boolean available;

}