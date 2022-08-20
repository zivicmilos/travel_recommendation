package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.time.LocalDate;

@Role(Role.Type.EVENT)
@Expires("2h30m")
public class Travel {
    @JsonBackReference
    private User user;
    private Destination destination;
    private LocalDate travelDate;
    private TransportationType transportationType;
    private int grade;
    private double cost;

    public Travel(User user, Destination destination, LocalDate travelDate, TransportationType transportationType, int grade, int cost) {
        this.user = user;
        this.destination = destination;
        this.travelDate = travelDate;
        this.transportationType = transportationType;
        this.grade = grade;
        this.cost = cost;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
