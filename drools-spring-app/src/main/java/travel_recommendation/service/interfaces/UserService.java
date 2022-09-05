package travel_recommendation.service.interfaces;

import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.SuspiciousEvent;
import travel_recommendation.model.Travel;
import travel_recommendation.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserByUsername(String username);

    List<Travel> getTravelsByUsername(String username);

    void cancelTravel(TravelDto travelDto);

    void updateUserRank(String username);

    void updateUserRank(User user);

    String cepRules();

    List<SuspiciousEvent> getSuspiciousEvents();
}
