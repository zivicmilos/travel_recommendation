package travel_recommendation.model;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.time.LocalDate;

@Role(Role.Type.EVENT)
@Expires("2h30m")
public class DeletedTravel {
    private User user;
    private Destination destination;
    private LocalDate travelDate;

    public DeletedTravel(User user, Destination destination, LocalDate travelDate) {
        this.user = user;
        this.destination = destination;
        this.travelDate = travelDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
