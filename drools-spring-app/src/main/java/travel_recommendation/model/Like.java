package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("time")
@Expires("2h30m")
public class Like {
    @JsonIgnoreProperties({"name", "lastname", "password", "email", "dateOfBirth", "status", "location", "userRank",
            "travels", "transportationType", "destinationType", "weather", "continent", "budget", "age", "userWeather",
            "sumSpent", "averageSpent"})
    private User user;
    @JsonIgnoreProperties({"weather", "destinationTypes", "transportationTypes", "likes", "username",
            "score", "recommendedTransportationType", "cost", "grade", "location.coordinates"})
    private Destination destination;
    private Date time;

    public Like(User user, Destination destination, Date time) {
        this.user = user;
        this.destination = destination;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isLikedToday() {
        Date now = new Date();
        now.setHours(now.getHours()+2);
        return this.time.getYear() == now.getYear() && this.time.getMonth() == now.getMonth() && this.time.getDate() == now.getDate();
    }


    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
