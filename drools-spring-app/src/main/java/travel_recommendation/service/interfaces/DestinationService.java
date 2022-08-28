package travel_recommendation.service.interfaces;

import travel_recommendation.dto.LikeDto;
import travel_recommendation.model.*;

import java.util.List;

public interface DestinationService {
    List<Destination> getDestinationList(String username, TransportationType transportationType, double budget,
                                         DestinationType destinationType, Weather weather, String continent);

    String like(LikeDto like);

    void reserve(Travel travel);

    void cepRules();
}
