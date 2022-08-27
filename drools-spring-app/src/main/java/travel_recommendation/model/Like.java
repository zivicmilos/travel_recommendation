package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
@Role(Role.Type.EVENT)
@Timestamp("time")
@Expires("2h30m")
public class Like {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnoreProperties({"name", "lastname", "password", "email", "dateOfBirth", "status", "location", "userRank",
            "travels", "transportationType", "destinationType", "weather", "continent", "budget", "age", "userWeather",
            "sumSpent", "averageSpent"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties({"weather", "destinationTypes", "transportationTypes", "likes", "username",
            "score", "recommendedTransportationType", "cost", "grade", "location.coordinates"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;
    private Date time;

    public Like(User user, Destination destination, Date time) {
        this.user = user;
        this.destination = destination;
        this.time = time;
    }

    public boolean isLikedToday() {
        Date now = new Date();
        now.setHours(now.getHours()+2);
        return this.time.getYear() == now.getYear() && this.time.getMonth() == now.getMonth() && this.time.getDate() == now.getDate();
    }

}
