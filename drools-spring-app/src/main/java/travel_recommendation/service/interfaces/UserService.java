package travel_recommendation.service.interfaces;

import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.Travel;
import travel_recommendation.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    List<Travel> getTravelsByUsername(String username);

    void cancelTravel(TravelDto travelDto);
}
