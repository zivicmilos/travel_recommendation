package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deleted_travels")
@Role(Role.Type.EVENT)
@Expires("2h30m")
public class DeletedTravel {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @Column(name = "travel_date")
    private LocalDate travelDate;

    public DeletedTravel(User user, Destination destination, LocalDate travelDate) {
        this.user = user;
        this.destination = destination;
        this.travelDate = travelDate;
    }
}
