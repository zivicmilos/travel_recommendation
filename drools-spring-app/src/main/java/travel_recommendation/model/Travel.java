package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "travels")
@Role(Role.Type.EVENT)
@Expires("2h30m")
public class Travel {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"travels"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @Column(name = "travel_date")
    private LocalDate travelDate;

    @Column(name = "transportation_type")
    private TransportationType transportationType;

    @Column(name = "grade")
    private int grade;

    @Column(name = "cost")
    private double cost;

    public Travel(User user, Destination destination, LocalDate travelDate, TransportationType transportationType, int grade, int cost) {
        this.user = user;
        this.destination = destination;
        this.travelDate = travelDate;
        this.transportationType = transportationType;
        this.grade = grade;
        this.cost = cost;
    }

}
