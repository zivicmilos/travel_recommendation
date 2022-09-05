package travel_recommendation.service.interfaces;

import travel_recommendation.dto.LikeDto;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.model.enums.DestinationType;
import travel_recommendation.model.enums.TransportationType;
import travel_recommendation.model.enums.Weather;

import java.util.List;

public interface DestinationService {
    List<Destination> getDestinationList(String username, TransportationType transportationType, double budget,
                                         DestinationType destinationType, Weather weather, String continent);

    String like(LikeDto like);

    void reserve(TravelDto travelDto);

}
